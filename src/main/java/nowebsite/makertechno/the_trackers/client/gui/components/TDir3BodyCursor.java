package nowebsite.makertechno.the_trackers.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.the_trackers.client.gui.icons.IconComponent;
import nowebsite.makertechno.the_trackers.core.track.algorithm.CameraProjector;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4fStack;

public class TDir3BodyCursor extends TDirectProjCursor {
    private final IconComponent component2, component3;
    private Transform tO = new Transform(0,0,0);
    public TDir3BodyCursor(IconComponent component1, IconComponent component2, IconComponent component3) {
        super(component1);
        this.component2 = component2;
        this.component3 = component3;
    }

    @Override
    public void flush() {
        super.flush();
        component2.flush();
        component3.flush();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, float partialTick, @NotNull Player player, Vec3 target) {
        tO = renderStatic(graphics, partialTick, player, target, component, component2, component3, scale, tO);
    }

    private static @NotNull Transform renderStatic(@NotNull GuiGraphics graphics, float partialTick, @NotNull Player player, Vec3 target, IconComponent component1, IconComponent component2, IconComponent component3, float scale, Transform tO) {
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

        float dx = projScrPoint[0] - tO.x;
        float dy = projScrPoint[1] - tO.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float signedDelta = distance * 0.008F * Math.signum(dx);
        float rot = tO.rot + signedDelta;

        Transform transformSmooth = tO.smoothModify(projScrPoint[0], projScrPoint[1], rot, partialTick);

        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        matrix4fStack.pushMatrix();

        for (int i = 0; i < 3; i++) {
            matrix4fStack.pushMatrix();

            float r = (float) component1.getIcon().height() * 1.6F * scale;
            float a = transformSmooth.rot + i * Mth.TWO_PI / 3f;
            float px = transformSmooth.x + (float) Math.cos(a) * r;
            float py = transformSmooth.y + (float) Math.sin(a) * r;

            float theta = (float) Math.atan2(transformSmooth.y - py, transformSmooth.x - px) + Mth.HALF_PI;

            matrix4fStack.translate(px, py, 0f);
            matrix4fStack.rotateZ(theta);
            matrix4fStack.translate(-(float) switch (i) {
                case 0 -> component1.getIcon().width();
                case 1 -> component2.getIcon().width();
                case 2 -> component3.getIcon().width();
                default -> 0;
            } / 2f * scale, -(float) switch (i) {
                case 0 -> component1.getIcon().height();
                case 1 -> component2.getIcon().height();
                case 2 -> component3.getIcon().height();
                default -> 0;
            } / 2f * scale, 0f);
            matrix4fStack.scale(scale);

            RenderSystem.applyModelViewMatrix();
            switch (i) {
                case 0 -> component1.render(graphics, partialTick);
                case 1 -> component2.render(graphics, partialTick);
                case 2 -> component3.render(graphics, partialTick);
            }

            matrix4fStack.popMatrix();
        }

        RenderSystem.resetTextureMatrix();
        matrix4fStack.popMatrix();


        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        return transformSmooth;
    }
    private static final class Transform {
        private float x, y, rot;
        private Transform(float x, float y, float rot) {
            this.x = x;
            this.y = y;
            this.rot = rot;
        }
        @Contract("_, _, _, _ -> this")
        private Transform smoothModify(float x, float y, float rot, float partialTick) {
            this.x = Mth.lerp(partialTick, this.x, x);
            this.y = Mth.lerp(partialTick, this.y, y);
            this.rot = Mth.lerp(partialTick, this.rot, rot);
            return this;
        }
    }
}
