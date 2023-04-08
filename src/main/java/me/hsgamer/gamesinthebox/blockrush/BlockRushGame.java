package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.game.simple.SimpleGame;
import me.hsgamer.gamesinthebox.planner.Planner;
import me.hsgamer.minigamecore.base.GameState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockRushGame extends SimpleGame {
    private final BlockRush expansion;

    public BlockRushGame(BlockRush expansion) {
        this.expansion = expansion;
    }

    @Override
    protected @NotNull BlockRushArena newArena(@NotNull String name, @NotNull Planner planner) {
        return new BlockRushArena(expansion, name, this, planner);
    }

    @Override
    public @NotNull String getDisplayName() {
        return expansion.getMessageConfig().getDisplayName();
    }

    @Override
    protected List<GameState> loadGameStates() {
        return null;
    }
}
