package nowebsite.makertechno.entity_tracker.define;

import net.minecraft.resources.ResourceLocation;

public enum TPointer implements TRenderItem {
    NORMAL_WHITE("normal_white","white.png", 16, 8),
    NORMAL_RED("normal_red","red.png", 16, 8),
    NORMAL_GREEN("normal_green","green.png", 16, 8);
    private final String name;
    private final ResourceLocation location;
    private final int width, height;
    TPointer(String regName, String location, int width, int height) {
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
