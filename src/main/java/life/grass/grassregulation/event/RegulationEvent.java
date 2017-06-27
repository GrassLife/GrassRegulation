package life.grass.grassregulation.event;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

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
    public void onPlayerRideBoat(PlayerInteractEntityEvent event) {

        if (event.getRightClicked().getType().equals(EntityType.BOAT)) {

            Entity vehicle = event.getPlayer().getVehicle();

            if (vehicle != null && vehicle.getType().equals(EntityType.BOAT)) {

                event.setCancelled(true);
                event.getPlayer().sendTitle("", "ボートからボートに乗ることはできません", 10, 70, 20);

            } else if (event.getRightClicked().getLocation().getY() > event.getPlayer().getLocation().getY() + 1.0 ) {

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
