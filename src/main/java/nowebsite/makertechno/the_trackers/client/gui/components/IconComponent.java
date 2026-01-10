package nowebsite.makertechno.the_trackers.client.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import nowebsite.makertechno.the_trackers.client.gui.provider.TextureCache;
import org.jetbrains.annotations.NotNull;

public class IconComponent {
    private Icon icon;
    public IconComponent(Icon icon) {
        this.icon = icon;
    }
    @SuppressWarnings("all")
    public void render(@NotNull GuiGraphics graphics, float partialTick) {
        if (icon.equals(Icon.NONE)) return;
        graphics.blit(icon.location(),
                0,0,
                0,0,
                icon.width(), icon.height(),
                icon.width(), icon.height()
        );
    }
    public Icon getIcon() {
        return icon;
    }
    public void flush() {
        icon = TextureCache.getIcon(icon.regName());
    }
}
