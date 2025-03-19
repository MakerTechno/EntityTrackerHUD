package nowebsite.makertechno.terra_ethud.define;

import net.minecraft.resources.ResourceLocation;

public enum TEntityIcon implements TRenderItem {
    NONE("none","none", 1, 1),
    ENDER_DRAGON_HEAD("ender_dragon_head","ender_dragon.png", 16, 16),
    WITHER_HEAD("wither_head","wither.png", 16, 16),;
    private final String name;
    private final ResourceLocation location;
    private final int width, height;
    TEntityIcon(String regName, String location, int width, int height) {
        this.name = regName;
        this.location = TRenderItem.getGuiResources(location);
        this.width = width;
        this.height = height;
    }
    public String getName() {
        return name;
    }
    public ResourceLocation getLocation() {
        return location;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
