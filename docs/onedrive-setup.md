# OneDrive 云备份配置指南

X Backup 支持通过 [Reden](https://redenmc.com) 平台将备份自动上传至 Microsoft OneDrive，从而实现异地容灾保护。

> [!NOTE]
> 云备份功能由 Reden 平台中转完成，上传的备份文件存储在与您的 Reden 账号绑定的 OneDrive 中。使用此功能需要先在 Reden 平台注册账号并获取 Token。

---

## 前置条件

- 已安装并运行 X Backup
- 拥有 [redenmc.com](https://redenmc.com) 账号
- OneDrive 账号（通过 Reden 平台绑定）

---

## 配置步骤

### 第一步：获取 Cloud Backup Token

1. 访问 [https://redenmc.com/x-backup/plans](https://redenmc.com/x-backup/plans)
2. 登录或注册你的 Reden 账号
3. 按照页面引导绑定你的 Microsoft OneDrive 账号
4. 获取专属的 `cloud_backup_token`

### 第二步：写入配置文件

打开服务器根目录下的配置文件：

```
config/x-backup.config.json
```

找到（或添加）`cloud_backup_token` 字段，填入你在第一步中获取的 Token：

```json
{
  "cloud_backup_token": "你的Token"
}
```

保存文件后，**重启服务器**使配置生效。

---

## 工作方式

配置 Token 后，X Backup 会在以下情况自动上传备份到 OneDrive：

- 每次使用 `/xb create` 创建备份成功后，自动在后台开始上传
- 上传过程中服务器可正常运行，不影响游戏

上传期间，游戏内会收到提示消息：

```
正在上传备份 #<id>...
备份 #<id> 已上传
```

---

## 手动上传备份

如果某个备份未自动上传（如网络中断、Token 尚未配置等情况），可使用以下命令手动触发上传：

```
/xb debug upload <id>
```

其中 `<id>` 是备份的编号（可通过 `/xb list` 查看）。

若该备份已上传过，命令会提示已上传，不会重复上传。

---

## 常见问题

### 提示"免费计划限制"

免费计划对云备份的存储容量或次数有限制。若超出限制，游戏内会出现如下提示，并附带可点击的链接：

> 操作失败：已超出免费计划限额

点击提示中的链接，或访问 [https://redenmc.com/x-backup/plans](https://redenmc.com/x-backup/plans) 查看和升级你的订阅计划。

### 上传失败后会重试吗？

会。上传过程中若发生网络错误，X Backup 会自动重试（最多 10 次）。同时，上传进度会保存在 `.tmp/xb.upload.json` 中，**重启后可从断点续传**，无需重新上传整个备份。

### 配置后未自动上传

请确认：
1. `cloud_backup_token` 字段值不为空且正确无误
2. 服务器已在修改配置后**完整重启**（不是热重载）
3. 查看服务器日志（`logs/latest.log`）中是否有 `X Backup/OneDrive` 相关的错误信息

---

## 配置示例

完整的 `config/x-backup.config.json` 中云备份相关配置示例：

```json
{
  "backup_interval": 10800,
  "cloud_backup_token": "your_token_here"
}
```

其余配置项保持默认即可。
