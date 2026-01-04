package nowebsite.makertechno.the_trackers.client.gui.icons;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public record Icon(String regName, @Nullable ResourceLocation location, int width, int height) {
    public static final Icon NONE = new Icon("none", null, 16, 16);
}
