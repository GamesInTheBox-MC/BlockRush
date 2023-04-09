package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.game.simple.feature.SimplePointFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGame;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArena;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArenaLogic;
import me.hsgamer.gamesinthebox.game.template.TemplateGameEditor;
import me.hsgamer.gamesinthebox.game.template.expansion.TemplateGameExpansion;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BlockRush extends TemplateGameExpansion {
    public static final SimplePointFeature.PointValue POINT_BLOCK = new SimplePointFeature.PointValue("block", 1, false);

    private final BlockRushMessageConfig messageConfig = ConfigGenerator.newInstance(BlockRushMessageConfig.class, new BukkitConfig(new File(getDataFolder(), "messages.yml")));

    @Override
    public TemplateGameArenaLogic createArenaLogic(TemplateGameArena arena) {
        return new BlockRushArenaLogic(arena);
    }

    @Override
    public TemplateGameEditor getEditor(TemplateGame game) {
        return new BlockRushEditor(game);
    }

    @Override
    public List<SimplePointFeature.PointValue> getPointValues() {
        return Collections.singletonList(POINT_BLOCK);
    }

    @Override
    public String getDisplayName() {
        return messageConfig.getDisplayName();
    }

    @Override
    public List<String> getDefaultHologramLines(String name) {
        return Optional.ofNullable(messageConfig.getDefaultHologramLines().get(name))
                .map(CollectionUtils::createStringListFromObject)
                .orElseGet(() -> super.getDefaultHologramLines(name));
    }

    @Override
    protected @NotNull String @NotNull [] getGameType() {
        return new String[]{"block-rush", "rush"};
    }

    public BlockRushMessageConfig getMessageConfig() {
        return messageConfig;
    }

    @Override
    public void onReload() {
        super.onReload();
        messageConfig.reloadConfig();
    }
}
