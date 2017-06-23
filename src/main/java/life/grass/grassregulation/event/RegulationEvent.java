package life.grass.grassregulation.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class RegulationEvent implements Listener {

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {

        Material item = event.getRecipe().getResult().getType();

        if (isProhibitedItem(item)) {

            event.getInventory().setResult(new ItemStack(Material.AIR));

        }

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        event.getPlayer().sendMessage("-----^-^-^-^--^--^-^--^-^-");

        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {

            event.setCancelled(true);

        }

    }

    private static boolean isProhibitedItem(Material item) {

        return item.equals(Material.ENCHANTMENT_TABLE) ||
                item.equals(Material.ANVIL) ||
                item.equals(Material.BREWING_STAND_ITEM) ||
                item.equals(Material.HOPPER) ||
                item.equals(Material.COAL_BLOCK) ||
                item.equals(Material.IRON_BLOCK) ||
                item.equals(Material.GOLD_BLOCK) ||
                item.equals(Material.LAPIS_BLOCK) ||
                item.equals(Material.EMERALD_BLOCK) ||
                item.equals(Material.REDSTONE_BLOCK) ||
                item.equals(Material.DIAMOND_BLOCK) ||
                item.equals(Material.GOLD_NUGGET) ||
                item.equals(Material.GOLD_INGOT) ||
                item.equals(Material.IRON_INGOT) ||
                item.equals(Material.CAKE);

    }

}
