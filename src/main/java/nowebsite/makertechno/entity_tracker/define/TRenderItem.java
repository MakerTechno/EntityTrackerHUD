package nowebsite.makertechno.entity_tracker.define;

import net.minecraft.resources.ResourceLocation;
import nowebsite.makertechno.entity_tracker.EntityTracker;
import org.jetbrains.annotations.NotNull;

public interface TRenderItem {
    String getName();
    int getWidth();
    int getHeight();
    ResourceLocation getLocation();
    static @NotNull ResourceLocation getGuiResources(String location) {
        return ResourceLocation.fromNamespaceAndPath(EntityTracker.MOD_ID, "textures/gui/sprites/hud/"+location);
    }
}
