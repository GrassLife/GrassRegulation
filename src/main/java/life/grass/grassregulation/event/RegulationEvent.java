package life.grass.grassregulation.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
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
}
