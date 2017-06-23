package life.grass.grassregulation;

import life.grass.grassregulation.event.RegulationEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class GrassRegulation extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new RegulationEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
