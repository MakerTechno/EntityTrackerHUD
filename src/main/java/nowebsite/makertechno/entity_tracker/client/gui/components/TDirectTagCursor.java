package nowebsite.makertechno.entity_tracker.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.entity_tracker.client.gui.components.base.tagpointer.TagPointerIconComponent;
import nowebsite.makertechno.entity_tracker.core.config.TConfig;
import nowebsite.makertechno.entity_tracker.core.track.algorithm.CameraProjector;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4fStack;

public class TDirectTagCursor implements TRenderComponent {
    final TagPointerIconComponent component;
    float scale;
    private Transform tO = new Transform(0, 0);
    static final GameRenderer renderer = Minecraft.getInstance().gameRenderer;
    public TDirectTagCursor(TagPointerIconComponent component) {
        this.component = component;
        scale = (float) TConfig.scale;
    }
    @Override
    public void flush() {
        component.flush();
        scale = (float) TConfig.scale;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, float partialTick, @NotNull Player player, Vec3 target) {
        tO = renderStatic(graphics, partialTick, player, target, component, scale, tO);
    }

    private static @NotNull Transform renderStatic(@NotNull GuiGraphics graphics, float partialTick, @NotNull Player player, Vec3 target, TagPointerIconComponent component, float scale, Transform tO) {
        float[] projScrPoint = CameraProjector.projectToScreen(
            renderer,
            target,
            player.getEyePosition(),
            Minecraft.getInstance().gameRenderer.getMainCamera(),
            graphics.guiWidth(),
            graphics.guiHeight()
        );

        float alpha = 1;
        if (projScrPoint[2] < 0.2* graphics.guiWidth()) alpha = (float) (projScrPoint[2]*0.6/(0.2* graphics.guiWidth()) + 0.4);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);

        // init
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        );

        Transform transformSmooth = tO.smoothModify(projScrPoint[0], projScrPoint[1], partialTick);

        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        matrix4fStack.pushMatrix(); // 1
        matrix4fStack.translate(-(float) component.getTagPointerIcon().width() / 2 * scale, -(float) component.getTagPointerIcon().height() / 2 * scale, 0);
        matrix4fStack.translate(transformSmooth.x, transformSmooth.y, 0);
        RenderSystem.applyModelViewMatrix();

        /* go on */
        component.render(graphics, partialTick);
        /* end of icon rend */

        RenderSystem.resetTextureMatrix();

        matrix4fStack.popMatrix(); // 0
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        return transformSmooth;
    }

    private static final class Transform {
        private float x, y;
        private Transform(float x, float y) {
            this.x = x;
            this.y = y;
        }
        @Contract("_, _, _ -> this")
        private Transform smoothModify(float x, float y, float partialTick) {
            this.x = Mth.lerp(partialTick, this.x, x);
            this.y = Mth.lerp(partialTick, this.y, y);
            return this;
        }
    }
}
