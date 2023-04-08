package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.game.simple.SimpleGameArena;
import me.hsgamer.gamesinthebox.planner.Planner;
import me.hsgamer.gamesinthebox.util.ActionBarUtil;
import me.hsgamer.hscore.common.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BlockRushArena extends SimpleGameArena {
    private final BlockRush expansion;

    public BlockRushArena(BlockRush expansion, @NotNull String name, @NotNull BlockRushGame game, @NotNull Planner planner) {
        super(name, game, planner);
        this.expansion = expansion;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    protected void onPointChanged(@NotNull UUID uuid, int point, int totalPoints) {
        if (point == 0) return;

        String message = expansion.getMessageConfig().getPoint();
        int absPoint = Math.abs(point);

        String finalMessage = message
                .replace("{point}", Integer.toString(absPoint))
                .replace("{total}", Integer.toString(totalPoints));

        ActionBarUtil.sendActionBar(uuid, finalMessage);
    }

    @Override
    public @NotNull List<String> getDefaultHologramLines(@NotNull String name) {
        return Optional.ofNullable(expansion.getMessageConfig().getDefaultHologramLines().get(name))
                .map(CollectionUtils::createStringListFromObject)
                .orElseGet(() -> super.getDefaultHologramLines(name));
    }

    @Override
    public long getDelay() {
        return expansion.getMainConfig().getGameInterval();
    }

    @Override
    public long getPeriod() {
        return expansion.getMainConfig().getGameInterval();
    }

    @Override
    public boolean isAsync() {
        return expansion.getMainConfig().isGameAsync();
    }
}
