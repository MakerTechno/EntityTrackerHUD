package nowebsite.makertechno.entity_tracker.client.render.texture;

import net.minecraft.resources.ResourceLocation;

public record PointerIcon(String regName, ResourceLocation location, int width, int height) implements TIcon {
    public static final PointerIcon NONE = new PointerIcon("none", null, 16, 8);
}