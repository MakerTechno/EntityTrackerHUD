package nowebsite.makertechno.entity_tracker.client.render;

import net.minecraft.client.gui.GuiGraphics;

public interface TRenderLike {
    void render(GuiGraphics graphics, float partialTick);
    void flush();
}
