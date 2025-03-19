package nowebsite.makertechno.terra_ethud.define;

import net.minecraft.resources.ResourceLocation;
import nowebsite.makertechno.terra_ethud.TerraETHUD;
import org.jetbrains.annotations.NotNull;

public interface TRenderItem {
    String getName();
    int getWidth();
    int getHeight();
    ResourceLocation getLocation();
    static @NotNull ResourceLocation getGuiResources(String location) {
        return ResourceLocation.fromNamespaceAndPath(TerraETHUD.MOD_ID, "textures/gui/sprites/hud/"+location);
    }
}
