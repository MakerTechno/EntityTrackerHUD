package nowebsite.makertechno.entity_tracker.client.gui.components.base.tagpointer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import nowebsite.makertechno.entity_tracker.client.render.texture.TIcon;
import org.jetbrains.annotations.NotNull;

public class BlinkTagPointer extends TagPointerIconComponent{
    private final int intervalBlink;
    private int intervalCount;
    private float alpha;
    private boolean reverse;
    public BlinkTagPointer(TIcon icon, int intervalBlink) {
        super(icon);
        this.intervalBlink = intervalBlink;
        this.intervalCount = 0;
    }
    public static boolean isValidPatterns(int intervalBlink) {
        return intervalBlink > 1;
    }
    @Override
    public void render(@NotNull GuiGraphics graphics, float partialTick) {
        if (++intervalCount >= intervalBlink) {
            osc();
            intervalCount = 0;
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        super.render(graphics, partialTick);
    }

    private void osc() {
        if (!reverse) {
            alpha += 0.02F;
            if (alpha >= 1f) {
                alpha = 1f;
                reverse = true;
            }
        } else {
            alpha -= 0.02F;
            if (alpha <= 0f) {
                alpha = 0f;
                reverse = false;
            }
        }
    }
}
