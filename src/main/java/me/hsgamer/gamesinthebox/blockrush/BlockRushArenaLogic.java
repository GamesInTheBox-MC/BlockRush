package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.blockrush.feature.ListenerFeature;
import me.hsgamer.gamesinthebox.blockrush.feature.MaterialFeature;
import me.hsgamer.gamesinthebox.game.feature.PointFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleRewardFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArena;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArenaLogic;
import me.hsgamer.minigamecore.base.Feature;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BlockRushArenaLogic extends TemplateGameArenaLogic {
    private final BlockRush expansion;

    public BlockRushArenaLogic(BlockRush expansion, TemplateGameArena arena) {
        super(arena);
        this.expansion = expansion;
    }

    @Override
    public List<Feature> loadFeatures() {
        return Arrays.asList(
                new SimpleBoundingFeature(arena, false),
                new MaterialFeature(arena),
                new ListenerFeature(expansion, arena, this)
        );
    }

    @Override
    public void forceEnd() {
        arena.getFeature(MaterialFeature.class).clearBlockFast();
        arena.getFeature(ListenerFeature.class).unregister();
    }

    @Override
    public void onWaitingStart() {
        arena.getFeature(ListenerFeature.class).register();
        arena.getFeature(MaterialFeature.class).setBlock();
    }

    @Override
    public boolean isWaitingOver() {
        return super.isWaitingOver() && arena.getFeature(MaterialFeature.class).isFinished();
    }

    @Override
    public void onEndingStart() {
        List<UUID> topList = arena.getFeature(PointFeature.class).getTopUUID();
        arena.getFeature(SimpleRewardFeature.class).tryReward(topList);

        arena.getFeature(MaterialFeature.class).clearBlock();
    }

    @Override
    public boolean isEndingOver() {
        return super.isEndingOver() && arena.getFeature(MaterialFeature.class).isFinished();
    }

    @Override
    public void onEndingOver() {
        arena.getFeature(ListenerFeature.class).unregister();
    }
}
