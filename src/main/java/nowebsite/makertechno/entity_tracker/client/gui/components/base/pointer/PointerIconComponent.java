package nowebsite.makertechno.entity_tracker.client.gui.components.base.pointer;

import net.minecraft.client.gui.GuiGraphics;
import nowebsite.makertechno.entity_tracker.client.render.TRenderLike;
import nowebsite.makertechno.entity_tracker.client.render.texture.PointerIcon;
import nowebsite.makertechno.entity_tracker.client.render.texture.TextureCache;
import org.jetbrains.annotations.NotNull;

public class PointerIconComponent implements TRenderLike {
    private PointerIcon pointerIcon;
    public PointerIconComponent(PointerIcon icon) {
        this.pointerIcon = icon;
    }
    @Override
    public void render(@NotNull GuiGraphics graphics, float partialTick) {
        graphics.blit(
                pointerIcon.location(),
                0, 0,
                0, 0,
                pointerIcon.width(), pointerIcon.height(),
                pointerIcon.width(), pointerIcon.height()
        );
    }

    public PointerIcon getPointerIcon() {
        return pointerIcon;
    }

    @Override
    public void flush() {
         pointerIcon = TextureCache.getPointer(pointerIcon.regName());
    }
}
