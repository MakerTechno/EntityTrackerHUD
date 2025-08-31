package nowebsite.makertechno.entity_tracker.client.render.texture;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import nowebsite.makertechno.entity_tracker.EntityTracker;
import nowebsite.makertechno.entity_tracker.core.tool.TextureBuildTool;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class TextureCache {
    private static final Map<String, EntityIcon> ENTITY_ICON_CACHE = new HashMap<>();
    private static final Map<String, PointerIcon> POINTER_ICON_CACHE = new HashMap<>();
    private static final Map<String, TagPointerIcon> TAG_POINTER_ICON_CACHE = new HashMap<>();
    private static final Set<ResourceLocation> MISCELLANEOUS_CACHE = new HashSet<>();

    static {
        initEntityIcons(
                "ender_dragon_head",
                "wither_head",
                "king_slime",
                "eye_of_cthulhu",
                "brain_of_cthulhu",
                "queen_bee",
                "eater_of_worlds",
                "skeletron",
                "point_x"
        );
        initPointers(
                "normal_white",
                "normal_red",
                "normal_green"
        );
    }

    public static EntityIcon getEntityIcon(String regName) {
        return ENTITY_ICON_CACHE.computeIfAbsent(regName, key -> {
            ResourceLocation location = getResourceFromModLocale(key);
            return TextureBuildTool.initTIcon(key, location, EntityIcon::new).orElse(EntityIcon.NONE);
        });
    }
    public static void initEntityIcons(String ...strings) {
        if (strings != null) {
            for (String key : strings) {
                try {
                    ENTITY_ICON_CACHE.put(key, TextureBuildTool.initTIcon(key, getResourceFromModLocale(key), EntityIcon::new).orElseThrow());
                } catch (NoSuchElementException e) {
                    warnUnload(key);
                }
            }
        }
    }
    public static PointerIcon getPointer(String regName) {
        return POINTER_ICON_CACHE.computeIfAbsent(regName, key -> {
            ResourceLocation location = getResourceFromModLocale(key);
            return TextureBuildTool.initTIcon(key, location, PointerIcon::new).orElse(PointerIcon.NONE);
        });
    }
    public static void initPointers(String ...strings) {
        if (strings != null) {
            for (String key : strings) {
                try {
                    POINTER_ICON_CACHE.put(key, TextureBuildTool.initTIcon(key, getResourceFromModLocale(key), PointerIcon::new).orElseThrow());
                } catch (NoSuchElementException e) {
                    warnUnload(key);
                }
            }
        }
    }

    public static TagPointerIcon getTagPointer(String regName) {
        return TAG_POINTER_ICON_CACHE.computeIfAbsent(regName, key -> {
            ResourceLocation location = getResourceFromModLocale(key);
            return TextureBuildTool.initTIcon(key, location, TagPointerIcon::new).orElse(TagPointerIcon.NONE);
        });
    }
    public static void initTagPointers(String ...strings) {
        if (strings != null) {
            for (String key : strings) {
                try {
                    TAG_POINTER_ICON_CACHE.put(key, TextureBuildTool.initTIcon(key, getResourceFromModLocale(key), TagPointerIcon::new).orElseThrow());
                } catch (NoSuchElementException e) {
                    warnUnload(key);
                }
            }
        }
    }

    private static void warnUnload(String key) {
        EntityTracker.LOGGER.warn("Failed to load image named {}, check if it was not in EntityTracker's resource with a valid path or in invalid config file.", key);
    }

    @Contract("_ -> new")
    private static @NotNull ResourceLocation getResourceFromModLocale(String name) {
        return ResourceLocation.fromNamespaceAndPath(EntityTracker.MOD_ID, "textures/gui/sprites/hud/"+name+".png");
    }
}
