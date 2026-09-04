package me.lucko.fabric.api.permissions.v0;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@SuppressWarnings("unused")
public interface Permissions {
    static <S> boolean check(@NotNull S source, @NotNull String permission, boolean defaultValue) {
        throw new AssertionError("Stub!");
    }

    static <S> boolean check(@NotNull S source, @NotNull String permission, int defaultRequiredLevel) {
        throw new AssertionError("Stub!");
    }

    static <S> boolean check(@NotNull S source, @NotNull String permission) {
        throw new AssertionError("Stub!");
    }

    static <S> Predicate<S> require(@NotNull String permission, int defaultRequiredLevel) {
        throw new AssertionError("Stub!");
    }

    static <S> Predicate<S> require(@NotNull String permission, boolean defaultValue) {
        throw new AssertionError("Stub!");
    }

    static <S> Predicate<S> require(@NotNull String permission) {
        throw new AssertionError("Stub!");
    }
}
