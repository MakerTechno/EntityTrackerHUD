package nowebsite.makertechno.entity_tracker.client.gui.components.base.tagpointer;

import net.minecraft.client.gui.GuiGraphics;
import nowebsite.makertechno.entity_tracker.client.render.TRenderLike;
import nowebsite.makertechno.entity_tracker.client.render.texture.TIcon;
import nowebsite.makertechno.entity_tracker.client.render.texture.TextureCache;
import org.jetbrains.annotations.NotNull;

public class TagPointerIconComponent implements TRenderLike {
    private TIcon tagPointerIcon;
    public TagPointerIconComponent(TIcon icon) {
        this.tagPointerIcon = icon;
    }
    @Override
    public void render(@NotNull GuiGraphics graphics, float partialTick) {
        graphics.blit(tagPointerIcon.location(),
                0,0,
                0,0,
                tagPointerIcon.width(), tagPointerIcon.height(),
                tagPointerIcon.width(), tagPointerIcon.height()
        );
    }
    public TIcon getTagPointerIcon() {
        return tagPointerIcon;
    }
    @Override
    public void flush() {
        tagPointerIcon = TextureCache.getTagPointer(tagPointerIcon.regName());
    }
}
