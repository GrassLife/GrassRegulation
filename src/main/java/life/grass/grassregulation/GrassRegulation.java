package life.grass.grassregulation;

import life.grass.grassregulation.event.CraftRegulationEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class GrassRegulation extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CraftRegulationEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
