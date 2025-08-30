package nowebsite.makertechno.entity_tracker.client.gui.components.base;

import net.minecraft.client.gui.GuiGraphics;
import nowebsite.makertechno.entity_tracker.client.render.TRenderLike;
import nowebsite.makertechno.entity_tracker.client.render.texture.EntityIcon;
import nowebsite.makertechno.entity_tracker.client.render.texture.TextureCache;
import org.jetbrains.annotations.NotNull;

public class EntityIconComponent implements TRenderLike {
    private EntityIcon entityIcon;
    public EntityIconComponent(EntityIcon icon) {
        this.entityIcon = icon;
    }
    @Override
    public void render(@NotNull GuiGraphics graphics, float partialTick) {
        graphics.blit(
                entityIcon.location(),
                0, 0,
                0, 0,
                entityIcon.width(), entityIcon.height(),
                entityIcon.width(), entityIcon.height()
        );
    }

    public EntityIcon getEntityIcon() {
        return entityIcon;
    }

    @Override
    public void flush() {
        entityIcon = TextureCache.getEntityIcon(entityIcon.regName());
    }
}
