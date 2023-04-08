package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArena;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArenaLogic;
import me.hsgamer.minigamecore.base.Feature;

import java.util.Collections;
import java.util.List;

public class BlockRushArenaLogic extends TemplateGameArenaLogic {
    public BlockRushArenaLogic(TemplateGameArena arena) {
        super(arena);
    }

    @Override
    public List<Feature> loadFeatures() {
        return Collections.singletonList(
                new SimpleBoundingFeature(arena, false)
        );
    }
}
