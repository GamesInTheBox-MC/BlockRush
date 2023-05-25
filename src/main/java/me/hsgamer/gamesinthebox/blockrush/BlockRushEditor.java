package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.game.GameArena;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleMaterialProbabilityFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGame;
import me.hsgamer.gamesinthebox.game.template.TemplateGameEditor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BlockRushEditor extends TemplateGameEditor {
    private final SimpleBoundingFeature.Editor boundingFeatureEditor = SimpleBoundingFeature.editor(true);
    private final SimpleMaterialProbabilityFeature.Editor materialProbabilityFeatureEditor = SimpleMaterialProbabilityFeature.editor();

    public BlockRushEditor(@NotNull TemplateGame game) {
        super(game);
    }

    @Override
    protected @NotNull Map<String, SimpleAction> createActionMap() {
        Map<String, SimpleAction> map = super.createActionMap();
        map.putAll(boundingFeatureEditor.getActions());
        map.putAll(materialProbabilityFeatureEditor.getActions());
        return map;
    }

    @Override
    protected @NotNull List<SimpleEditorStatus> createEditorStatusList() {
        List<SimpleEditorStatus> list = super.createEditorStatusList();
        list.add(boundingFeatureEditor.getStatus());
        list.add(materialProbabilityFeatureEditor.getStatus());
        return list;
    }

    @Override
    public boolean migrate(@NotNull CommandSender sender, @NotNull GameArena gameArena) {
        Optional.ofNullable(gameArena.getFeature(SimpleBoundingFeature.class)).ifPresent(boundingFeatureEditor::migrate);
        Optional.ofNullable(gameArena.getFeature(SimpleMaterialProbabilityFeature.class)).ifPresent(materialProbabilityFeatureEditor::migrate);
        return super.migrate(sender, gameArena);
    }
}
