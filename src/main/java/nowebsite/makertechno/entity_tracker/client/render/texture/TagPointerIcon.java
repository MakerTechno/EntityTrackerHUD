package nowebsite.makertechno.entity_tracker.client.render.texture;

import net.minecraft.resources.ResourceLocation;

public record TagPointerIcon(String regName, ResourceLocation location, int width, int height) implements TIcon {
    public static final TagPointerIcon NONE = new TagPointerIcon("none", null, 16, 16);
}