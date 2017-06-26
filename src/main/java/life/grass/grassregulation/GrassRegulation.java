package life.grass.grassregulation;

import life.grass.grassregulation.event.RegulationEvent;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class GrassRegulation extends JavaPlugin {

    private static GrassRegulation instance;

    private static GrassRegulation getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        getServer().getPluginManager().registerEvents(new RegulationEvent(), this);
        removeRecipes();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private static void removeRecipes() {

        List<Recipe> backup = new ArrayList<>();

        Iterator<Recipe> iterator = getInstance().getServer().recipeIterator();
        while (iterator.hasNext()) {
            Recipe r = iterator.next();
            if (isAllowedRecipe(r)) {
                backup.add(r);
            }
        }
        getInstance().getServer().clearRecipes();
        for (Recipe r : backup)
            getInstance().getServer().addRecipe(r);

    }

    private static boolean isAllowedRecipe(Recipe recipe) {
        Material item = recipe.getResult().getType();
        if (recipe instanceof FurnaceRecipe) {
            return !isSmeltProhibitedItem(item);
        }
        return !isCraftProhibitedItem(item);
    }

    private static boolean isCraftProhibitedItem(Material item) {

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
                item.equals(Material.IRON_NUGGET) ||
                item.equals(Material.TNT) ||
                item.equals(Material.CAKE) ||
                item.equals(Material.BREAD) ||
                item.equals(Material.PUMPKIN_PIE) ||
                item.equals(Material.MUSHROOM_SOUP) ||
                item.equals(Material.BEETROOT_SOUP)||
                item.equals(Material.RABBIT_STEW) ||
                item.equals(Material.WHEAT) ||
                item.equals(Material.HAY_BLOCK) ||
                item.equals(Material.COOKIE);


    }

    private static boolean isSmeltProhibitedItem(Material item) {

        return item.equals(Material.COOKED_BEEF)||
                item.equals(Material.COOKED_CHICKEN) ||
                item.equals(Material.COOKED_FISH) ||
                item.equals(Material.GRILLED_PORK) ||
                item.equals(Material.COOKED_MUTTON) ||
                item.equals(Material.COOKED_RABBIT) ||
                item.equals(Material.BAKED_POTATO);

    }
}
