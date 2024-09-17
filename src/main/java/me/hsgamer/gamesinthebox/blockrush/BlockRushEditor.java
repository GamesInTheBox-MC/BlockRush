package me.hsgamer.gamesinthebox.blockrush;

import me.hsgamer.gamesinthebox.blockrush.feature.ListenerFeature;
import me.hsgamer.gamesinthebox.game.GameArena;
import me.hsgamer.gamesinthebox.game.simple.action.BooleanAction;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleMaterialProbabilityFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGame;
import me.hsgamer.gamesinthebox.game.template.TemplateGameEditor;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BlockRushEditor extends TemplateGameEditor {
    private final SimpleBoundingFeature.Editor boundingFeatureEditor = SimpleBoundingFeature.editor(true);
    private final SimpleMaterialProbabilityFeature.Editor materialProbabilityFeatureEditor = SimpleMaterialProbabilityFeature.editor();
    private Boolean dropItemOnBreak;

    public BlockRushEditor(@NotNull TemplateGame game) {
        super(game);
    }

    @Override
    protected @NotNull Map<String, SimpleAction> createActionMap() {
        Map<String, SimpleAction> map = super.createActionMap();
        map.putAll(boundingFeatureEditor.getActions());
        map.putAll(materialProbabilityFeatureEditor.getActions());
        map.put("drop-item-on-break", new BooleanAction() {
            @Override
            protected boolean performAction(@NotNull CommandSender sender, @NotNull Boolean value, String... args) {
                dropItemOnBreak = value;
                return true;
            }

            @Override
            public @NotNull String getDescription() {
                return "Set whether to drop items when breaking blocks";
            }
        });
        return map;
    }

    @Override
    protected @NotNull List<SimpleEditorStatus> createEditorStatusList() {
        List<SimpleEditorStatus> list = super.createEditorStatusList();
        list.add(boundingFeatureEditor.getStatus());
        list.add(materialProbabilityFeatureEditor.getStatus());
        list.add(new SimpleEditorStatus() {
            @Override
            public void sendStatus(@NotNull CommandSender commandSender) {
                MessageUtils.sendMessage(commandSender, "&6&lBlock Rush");
                MessageUtils.sendMessage(commandSender, "&eDrop item on break: &f" + (dropItemOnBreak == null ? "Default" : dropItemOnBreak));
            }

            @Override
            public void reset(@NotNull CommandSender commandSender) {
                dropItemOnBreak = null;
            }

            @Override
            public boolean canSave(@NotNull CommandSender commandSender) {
                return true;
            }

            @Override
            public Map<String, Object> toPathValueMap(@NotNull CommandSender commandSender) {
                if (dropItemOnBreak != null) {
                    return Collections.singletonMap("drop-item-on-break", dropItemOnBreak);
                }
                return Collections.emptyMap();
            }
        });
        return list;
    }

    @Override
    public boolean migrate(@NotNull CommandSender sender, @NotNull GameArena gameArena) {
        Optional.ofNullable(gameArena.getFeature(SimpleBoundingFeature.class)).ifPresent(boundingFeatureEditor::migrate);
        Optional.ofNullable(gameArena.getFeature(SimpleMaterialProbabilityFeature.class)).ifPresent(materialProbabilityFeatureEditor::migrate);
        Optional.ofNullable(gameArena.getFeature(ListenerFeature.class)).ifPresent(listenerFeature -> dropItemOnBreak = listenerFeature.isDropItemOnBreak());
        return super.migrate(sender, gameArena);
    }
}
