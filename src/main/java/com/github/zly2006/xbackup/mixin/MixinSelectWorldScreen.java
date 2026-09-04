package com.github.zly2006.xbackup.mixin;

import com.github.zly2006.xbackup.BackupDatabaseService;
import com.github.zly2006.xbackup.XBackup;
//? if poly_lib {
import com.github.zly2006.xbackup.gui.BackupsGui;
//?}
import net.fabricmc.loader.api.FabricLoader;
//? if <26 {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
//? if >= 1.21.6 {
/*import net.minecraft.client.gui.components.Tooltip;
*///?}
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(SelectWorldScreen.class)
public class MixinSelectWorldScreen extends Screen {
    protected MixinSelectWorldScreen(Component title) {
        super(title);
    }

    //? if poly_lib {
    @Unique
    Button buttonWidget;

    @Shadow
    private WorldSelectionList list;

    @Inject(
            method = "init",
            at = @At("RETURN")
    )
    private void postInit(CallbackInfo ci) {
        //? if >=1.21.6 {
        /*Tooltip backupTooltip;
        if (FabricLoader.getInstance().isModLoaded("polylib")) {
            backupTooltip = Tooltip.create(Component.translatable("xb.button.backups"));
        } else {
            backupTooltip = Tooltip.create(Component.translatable("xb.gui.no_polylib").withStyle(ChatFormatting.RED));
        }
        *///?}
        buttonWidget = Button.builder(Component.literal("回"),
                (button) -> {
            if (!FabricLoader.getInstance().isModLoaded("polylib")) {
                return;
            }
            if (list.getSelectedOpt().isPresent()) {
                String name = list.getSelectedOpt().get().summary.getLevelId();
                BackupDatabaseService service = new BackupDatabaseService(
                        Path.of("saves").toAbsolutePath().normalize(),
                        XBackup.INSTANCE.getDatabaseFromWorld(Path.of("saves", name)),
                        Path.of("").toAbsolutePath().resolve(XBackup.config.getBlobPath()).normalize(),
                        XBackup.config
                );
                BackupsGui.Companion.open(service, Path.of("saves", name));
            }
        }).bounds(this.width / 2 + 160, this.height - 28, 20, 20)
        //? if >= 1.21.6 {
        /*.tooltip(backupTooltip)
        *///?}
        .build();
        buttonWidget.active = list.getSelectedOpt().isPresent();
        this.addRenderableWidget(buttonWidget);
    }

    @Inject(
            method = "updateButtonStatus",
            at = @At("RETURN")
    )
    private void worldSelected(CallbackInfo ci) {
        if (buttonWidget != null) {
            buttonWidget.active = list.getSelectedOpt().isPresent();
        }
    }

    //? if < 1.21.6 {
    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void render(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (buttonWidget != null && buttonWidget.isHovered()) {
            if (FabricLoader.getInstance().isModLoaded("polylib")) {
                setTooltipForNextRenderPass(Component.translatable("xb.button.backups"));
            } else {
                setTooltipForNextRenderPass(Component.translatable("xb.gui.no_polylib").withStyle(ChatFormatting.RED));
            }
        }
    }
    //?}
    //?}
}
