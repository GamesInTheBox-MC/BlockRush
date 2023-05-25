package me.hsgamer.gamesinthebox.blockrush.feature;

import me.hsgamer.blockutil.abstraction.BlockProcess;
import me.hsgamer.gamesinthebox.game.feature.BoundingFeature;
import me.hsgamer.gamesinthebox.game.feature.MaterialProbabilityFeature;
import me.hsgamer.gamesinthebox.game.simple.SimpleGameArena;
import me.hsgamer.gamesinthebox.util.BlockHandlerUtil;
import me.hsgamer.hscore.minecraft.block.box.BlockBox;
import me.hsgamer.minigamecore.base.Feature;
import org.bukkit.World;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MaterialFeature implements Feature {
    private final AtomicReference<BlockProcess> currentTask = new AtomicReference<>();
    private final AtomicBoolean hasBlock = new AtomicBoolean(false);
    private final SimpleGameArena arena;

    public MaterialFeature(SimpleGameArena arena) {
        this.arena = arena;
    }

    private void cancelTask() {
        BlockProcess blockProcess = currentTask.getAndSet(null);
        if (blockProcess != null) {
            blockProcess.cancel();
        }
    }

    public void setBlock() {
        cancelTask();
        BoundingFeature boundingFeature = arena.getFeature(BoundingFeature.class);
        MaterialProbabilityFeature materialProbabilityFeature = arena.getFeature(MaterialProbabilityFeature.class);
        World world = boundingFeature.getWorld();
        BlockBox blockBox = boundingFeature.getBlockBox();
        BlockProcess blockProcess = BlockHandlerUtil.getBlockHandler().setRandomBlocks(world, blockBox, materialProbabilityFeature.getMaterialCollection());
        currentTask.set(blockProcess);
        hasBlock.set(true);
    }

    public void clearBlock() {
        cancelTask();
        BoundingFeature boundingFeature = arena.getFeature(BoundingFeature.class);
        World world = boundingFeature.getWorld();
        BlockBox blockBox = boundingFeature.getBlockBox();
        BlockProcess blockProcess = BlockHandlerUtil.getBlockHandler().clearBlocks(world, blockBox);
        currentTask.set(blockProcess);
        hasBlock.set(false);
    }

    public boolean isFinished() {
        BlockProcess blockProcess = currentTask.get();
        return blockProcess == null || blockProcess.isDone();
    }

    public void clearBlockFast() {
        boolean isFinished = isFinished();
        cancelTask();

        if (!isFinished || hasBlock.get()) {
            BoundingFeature boundingFeature = arena.getFeature(BoundingFeature.class);
            World world = boundingFeature.getWorld();
            BlockBox blockBox = boundingFeature.getBlockBox();
            BlockHandlerUtil.getBlockHandler().clearBlocksFast(world, blockBox);
        }
    }

    @Override
    public void clear() {
        clearBlockFast();
    }
}
