package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.hscore.config.annotation.ConfigPath;

public interface BlockRushMainConfig {
    @ConfigPath("game.interval")
    default long getGameInterval() {
        return 20;
    }

    @ConfigPath("game.async")
    default boolean isGameAsync() {
        return true;
    }

    void reloadConfig();
}
