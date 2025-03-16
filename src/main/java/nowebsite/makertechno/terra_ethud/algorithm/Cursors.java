package nowebsite.makertechno.terra_ethud.algorithm;

import net.minecraft.resources.ResourceLocation;
import nowebsite.makertechno.terra_ethud.TerraETHUD;
import org.jetbrains.annotations.Nullable;

public enum Cursors {
    NORMAL_WHITE("normal_white","white.png", 16, 8),
    NORMAL_RED("normal_red","red.png", 16, 8),
    NORMAL_GREEN("normal_green","green.png", 16, 8),
    ENDER_DRAGON_HEAD("ender_dragon_head","ender_dragon.png", 32, 64),;
    private final String name;
    private final ResourceLocation location;
    private final int width, height;
    Cursors(String regName, String location, int width, int height) {
        this.name = regName;
        this.location = ResourceLocation.fromNamespaceAndPath(TerraETHUD.MOD_ID, "textures/gui/sprites/hud/"+location);
        this.width = width;
        this.height = height;
    }
    public String getName() {
        return name;
    }
    public ResourceLocation getLocation() {
        return location;
    }
    public static @Nullable Cursors getByName(String name) {
        for (Cursors cursor : values()) {
            if (cursor.getName().equals(name)) {
                return cursor;
            }
        }
        return null;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
