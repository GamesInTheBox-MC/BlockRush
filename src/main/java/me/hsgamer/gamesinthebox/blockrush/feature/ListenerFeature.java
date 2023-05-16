package me.hsgamer.gamesinthebox.blockrush.feature;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XTag;
import me.hsgamer.gamesinthebox.blockrush.BlockRush;
import me.hsgamer.gamesinthebox.blockrush.BlockRushArenaLogic;
import me.hsgamer.gamesinthebox.game.feature.BoundingFeature;
import me.hsgamer.gamesinthebox.game.simple.SimpleGameArena;
import me.hsgamer.gamesinthebox.game.simple.feature.SimplePointFeature;
import me.hsgamer.gamesinthebox.util.EntityUtil;
import me.hsgamer.minigamecore.base.Feature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ListenerFeature implements Feature, Listener {
    private final BlockRush expansion;
    private final SimpleGameArena arena;
    private final BlockRushArenaLogic logic;

    public ListenerFeature(BlockRush expansion, SimpleGameArena arena, BlockRushArenaLogic logic) {
        this.expansion = expansion;
        this.arena = arena;
        this.logic = logic;
    }

    private BoundingFeature getBoundingFeature() {
        return this.arena.getFeature(BoundingFeature.class);
    }

    private SimplePointFeature getPointFeature() {
        return this.arena.getFeature(SimplePointFeature.class);
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, expansion.getPlugin());
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void clear() {
        unregister();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (!getBoundingFeature().checkBounding(location, true))
            return;
        if (logic.isInGame()) {
            getPointFeature().applyPoint(event.getPlayer().getUniqueId(), BlockRush.POINT_BLOCK);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (getBoundingFeature().checkBounding(location, true))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMultiPlace(BlockMultiPlaceEvent event) {
        boolean isInBound = event.getReplacedBlockStates().stream()
                .map(BlockState::getLocation)
                .anyMatch(location -> getBoundingFeature().checkBounding(location, true));
        if (isInBound)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
        if (!logic.isInGame()) {
            BoundingFeature boundingFeature = getBoundingFeature();
            event.blockList().removeIf(block -> boundingFeature.checkBounding(block.getLocation(), true));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        BoundingFeature boundingFeature = getBoundingFeature();
        if (!logic.isInGame()) {
            event.blockList().removeIf(block -> boundingFeature.checkBounding(block.getLocation(), true));
            return;
        }
        Entity entity = event.getEntity();
        if (entity.getWorld() != boundingFeature.getWorld())
            return;
        Entity source = EntityUtil.getTntSource(entity);
        int count = (int) event.blockList().stream().filter(block -> !XTag.AIR.isTagged(XMaterial.matchXMaterial(block.getType())) && boundingFeature.checkBounding(block.getLocation(), true)).count();
        if (count > 0 && source instanceof org.bukkit.entity.Player)
            getPointFeature().applyPoint(source.getUniqueId(), BlockRush.POINT_BLOCK, point -> point * count);
    }
}
