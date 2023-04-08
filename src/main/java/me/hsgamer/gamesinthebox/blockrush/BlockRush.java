package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.expansion.SingleGameExpansion;
import me.hsgamer.gamesinthebox.expansion.extra.Reloadable;
import me.hsgamer.gamesinthebox.game.Game;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class BlockRush extends SingleGameExpansion implements Reloadable {
    private final BlockRushMainConfig mainConfig = ConfigGenerator.newInstance(BlockRushMainConfig.class, new BukkitConfig(new File(getDataFolder(), "config.yml")));
    private final BlockRushMessageConfig messageConfig = ConfigGenerator.newInstance(BlockRushMessageConfig.class, new BukkitConfig(new File(getDataFolder(), "messages.yml")));

    @Override
    protected @NotNull Game getGame() {
        return new BlockRushGame(this);
    }

    @Override
    protected @NotNull String @NotNull [] getGameType() {
        return new String[]{"block-rush", "rush"};
    }

    public BlockRushMainConfig getMainConfig() {
        return mainConfig;
    }

    public BlockRushMessageConfig getMessageConfig() {
        return messageConfig;
    }

    @Override
    public void onReload() {
        mainConfig.reloadConfig();
        messageConfig.reloadConfig();
    }
}
