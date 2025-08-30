package nowebsite.makertechno.entity_tracker.client.render.texture;

import net.minecraft.resources.ResourceLocation;

public record EntityIcon(String regName, ResourceLocation location, int width, int height) implements TIcon{
    public static final EntityIcon NONE = new EntityIcon("none", null, 16, 16);
}
