package life.grass.grassregulation.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class RegulationEvent implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {

        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {

            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onPistonPushShulkerBox(BlockPistonExtendEvent event) {
        List<Block> blocks = event.getBlocks();
        boolean hasShulker = false;
        for (Block block : blocks) {
            if (block.getType().toString().contains("SHULKER_BOX")) {
                hasShulker = true;
                break;
            }
        }
        if (hasShulker) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRideBoat(PlayerInteractEntityEvent event) {

        if (event.getRightClicked().getType().equals(EntityType.BOAT) || event.getRightClicked().getType().equals(EntityType.MINECART)) {

            Entity vehicle = event.getPlayer().getVehicle();
            if (vehicle != null && (vehicle.getType().equals(EntityType.BOAT) || vehicle.getType().equals(EntityType.MINECART))) {

                event.setCancelled(true);
                event.getPlayer().sendTitle("", "乗り物から乗り物に乗ることはできません", 10, 70, 20);

            } else if (event.getRightClicked().getLocation().getY() > event.getPlayer().getLocation().getY() + 1.0) {

                event.setCancelled(true);
                event.getPlayer().sendTitle("", "そこからは乗れません", 10, 70, 20);

            }
        }
    }

    @EventHandler
    public void onDropExp(EntitySpawnEvent event) {
        if (event.getEntityType().equals(EntityType.EXPERIENCE_ORB)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLavaChange(BlockFormEvent event) {
        if (event.getNewState().getType().equals(Material.STONE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (block == null) return;

        switch (block.getType()) {
            case BREWING_STAND:
            case BREWING_STAND_ITEM:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClickVillager(PlayerInteractEntityEvent event) {

        if (event.getRightClicked() instanceof Villager) {

            event.setCancelled(true);

            switch ((int) Math.ceil(Math.random() * 4)) {

                case 1:
                    event.getPlayer().sendTitle("ﾊｧﾝ...", "", 10, 70, 20);
                    break;
                case 2:
                    event.getPlayer().sendTitle("ﾊｧﾝ?", "", 10, 70, 20);
                    break;
                case 3:
                    event.getPlayer().sendTitle("ﾊﾝｯ", "", 10, 70, 20);
                    break;
                case 4:
                    event.getPlayer().sendTitle("ﾊｧﾝ!!!", "", 10, 70, 20);
                    break;
            }
        }
    }
}
