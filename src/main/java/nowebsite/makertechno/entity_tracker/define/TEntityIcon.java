package nowebsite.makertechno.entity_tracker.define;

import net.minecraft.resources.ResourceLocation;

public enum TEntityIcon implements TRenderItem {
    NONE("none","none", 1, 1),
    ENDER_DRAGON_HEAD("ender_dragon_head","ender_dragon.png", 16, 16),
    WITHER_HEAD("wither_head","wither.png", 16, 16),
    KING_SLIME("king_slime","king_slime.png", 16, 16),
    EYE_OF_CTHULHU("eye_of_cthulhu","eye_of_cthulhu.png", 16, 16),
    BRAIN_OF_CTHULHU("brain_of_cthulhu","brain_of_cthulhu.png", 16, 16),
    QUEEN_BEE("queen_bee","queen_bee.png", 16, 16),
    EATER_OF_WORLDS("eater_of_worlds","eater_of_worlds.png", 16, 16),
    POINTER_X("point_x","point_x.png", 16, 16),;
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
