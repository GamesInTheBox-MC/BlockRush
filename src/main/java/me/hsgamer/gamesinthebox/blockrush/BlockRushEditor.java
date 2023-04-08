package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.game.GameArena;
import me.hsgamer.gamesinthebox.game.simple.SimpleGameEditor;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class BlockRushEditor extends SimpleGameEditor {
    private final SimpleBoundingFeature.Editor boundingFeatureEditor = SimpleBoundingFeature.editor();

    public BlockRushEditor(@NotNull BlockRushGame game) {
        super(game);
    }

    @Override
    protected @NotNull Map<String, SimpleAction> createActionMap() {
        Map<String, SimpleAction> actionMap = super.createActionMap();

        actionMap.putAll(boundingFeatureEditor.getActions());

        return actionMap;
    }

    @Override
    protected @NotNull List<@NotNull SimpleEditorStatus> createEditorStatusList() {
        List<SimpleEditorStatus> editorStatusList = super.createEditorStatusList();

        editorStatusList.add(boundingFeatureEditor.getStatus());

        return editorStatusList;
    }

    @Override
    public boolean migrate(@NotNull CommandSender sender, @NotNull GameArena gameArena) {
        if (!(gameArena instanceof BlockRushArena)) return false;

        boundingFeatureEditor.migrate(gameArena.getFeature(SimpleBoundingFeature.class));

        return super.migrate(sender, gameArena);
    }
}
