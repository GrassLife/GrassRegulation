package life.grass.grassregulation;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.Recipe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author misterT2525
 */
class RecipeFilter {
    private static final String OBC_PACKAGE = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_PACKAGE = OBC_PACKAGE.replace("org.bukkit.craftbukkit", "net.minecraft.server");

    private static Class<?> getCraftClass(String name) {
        try {
            return Class.forName(OBC_PACKAGE + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getMinecraftClass(String name) {
        try {
            return Class.forName(NMS_PACKAGE + "." + name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Field getField(Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getFieldValue(Field field, Object object) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private static final Method REGISTER_METHOD;

    static {
        Class<?> craftingManagerClass = getMinecraftClass("CraftingManager");
        Class<?> keyClass = getMinecraftClass("MinecraftKey");
        Class<?> recipeClass = getMinecraftClass("IRecipe");
        try {
            REGISTER_METHOD = craftingManagerClass.getDeclaredMethod("a", keyClass, recipeClass);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static void register(Object key, Object recipe) {
        try {
            REGISTER_METHOD.invoke(null, key, recipe);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private static final Class<?> SHAPED_CRAFT_CLASS;
    private static final Field SHAPED_HANDLE_FIELD;
    private static final Field SHAPED_KEY_FIELD;
    private static final Class<?> SHAPELESS_CRAFT_CLASS;
    private static final Field SHAPELESS_HANDLE_FIELD;
    private static final Field SHAPELESS_KEY_FIELD;

    static {
        SHAPED_CRAFT_CLASS = RecipeFilter.getCraftClass("inventory.CraftShapedRecipe");
        SHAPED_HANDLE_FIELD = RecipeFilter.getField(SHAPED_CRAFT_CLASS, "recipe");
        SHAPED_KEY_FIELD = RecipeFilter.getField(SHAPED_HANDLE_FIELD.getType(), "key");
        SHAPELESS_CRAFT_CLASS = RecipeFilter.getCraftClass("inventory.CraftShapelessRecipe");
        SHAPELESS_HANDLE_FIELD = RecipeFilter.getField(SHAPELESS_CRAFT_CLASS, "recipe");
        SHAPELESS_KEY_FIELD = RecipeFilter.getField(SHAPELESS_HANDLE_FIELD.getType(), "key");
    }

    private static boolean register(Recipe recipe, Field handleField, Field keyField) {
        Object handle = RecipeFilter.getFieldValue(handleField, recipe);
        if (handle == null) {
            return false;
        }
        Object key = RecipeFilter.getFieldValue(keyField, handle);
        RecipeFilter.register(key, handle);
        return true;
    }


    private static boolean register(Recipe recipe) {
        if (SHAPED_CRAFT_CLASS.isInstance(recipe)) {
            return register(recipe, SHAPED_HANDLE_FIELD, SHAPED_KEY_FIELD);
        }
        if (SHAPELESS_CRAFT_CLASS.isInstance(recipe)) {
            return register(recipe, SHAPELESS_HANDLE_FIELD, SHAPELESS_KEY_FIELD);
        }
        return false;
    }


    static void filter(Server server, Predicate<Recipe> predicate) {
        List<Recipe> toAdd = new LinkedList<>();

        Iterator<Recipe> iterator = server.recipeIterator();
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (predicate.test(recipe)) {
                toAdd.add(recipe);
            }
        }

        server.clearRecipes();

        toAdd.forEach(recipe -> {
            if (!RecipeFilter.register(recipe)) {
                server.addRecipe(recipe);
            }
        });
    }
}
