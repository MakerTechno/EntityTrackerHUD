package nowebsite.makertechno.the_trackers.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.the_trackers.client.gui.icons.IconComponent;
import nowebsite.makertechno.the_trackers.client.gui.icons.Icon;
import nowebsite.makertechno.the_trackers.core.config.TConfig;
import nowebsite.makertechno.the_trackers.core.track.algorithm.RelativeProjector;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4fStack;

public class TRelativeCursor implements TRenderComponent{
    private final IconComponent pointerIconComponent, entityIconComponent;
    private Transform tO = new Transform(0,  0);
    private float scale;
    public TRelativeCursor(IconComponent pointerIconComponent, IconComponent entityIconComponent) {
        this.pointerIconComponent = pointerIconComponent;
        this.entityIconComponent = entityIconComponent;
        scale = (float) TConfig.scale;
    }

    @Override
    public void flush() {
        pointerIconComponent.flush();
        entityIconComponent.flush();
        scale = (float) TConfig.scale;
    }

    @Override
    public void render(GuiGraphics graphics, float partialTick, Player player, Vec3 target) {
        TransformBase transform = transformLocation(player, target, pointerIconComponent.getIcon(), entityIconComponent.getIcon());
        this.tO = TRelativeCursor.renderStatic(graphics, partialTick, transform, tO, pointerIconComponent, entityIconComponent, scale);
    }

    private static @NotNull Transform renderStatic(
            GuiGraphics graphics,
            float partialTick,
            @NotNull TransformBase transformBase, Transform tO,
            IconComponent pointerIconComponent, IconComponent entityIconComponent,
            float scale
    ) {
        // init
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        );
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, transformBase.alpha);

        // matrix
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();

        Transform tNew = renderInsights(graphics, pointerIconComponent, entityIconComponent, matrix4fStack, transformBase, tO, scale, partialTick);

        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        return tNew;
    }

    private static Transform renderInsights(
            @NotNull GuiGraphics graphics,
            @NotNull IconComponent pointerIconComponent, @NotNull IconComponent entityIconComponent,
            @NotNull Matrix4fStack matrix4fStack,
            @NotNull TransformBase result,
            Transform transform,
            float scale,
            float partialTick
    ) {
        float radius = (float) (-result.angleRadians - Math.PI/2);

        Icon pointer = pointerIconComponent.getIcon();
        Icon icon = entityIconComponent.getIcon();

        float pw = -(float) pointer.width() / 2 * scale, ph = -(float) pointer.height() / 2 * scale;
        float ew = (float) icon.width() / 2 * scale, eh = (float) icon.height() / 2 * scale;
        float dist = -(result.pickDirect - pointer.height() - (int) Math.ceil(Math.sqrt(2 * icon.height()) + 2)) * scale;

        Transform transformSmooth = transform.smoothTransformModify(radius, dist, partialTick);

        matrix4fStack.pushMatrix(); // 1
        matrix4fStack.translate((float) graphics.guiWidth() / 2, (float) graphics.guiHeight() / 2, 0);

        matrix4fStack.rotateZ(transformSmooth.radius);
        matrix4fStack.pushMatrix(); // 2
        matrix4fStack.translate(pw, ph, 0);
        matrix4fStack.translate(0, -result.pickDirect * scale, 0);
        matrix4fStack.scale(scale, scale, 1);
        RenderSystem.applyModelViewMatrix();

        /* go on */
        pointerIconComponent.render(graphics, partialTick);
        /* end of pointer rend */

        if (icon != Icon.NONE) {
            // roll back
            matrix4fStack.popMatrix(); // 1
            matrix4fStack.pushMatrix(); // 2
            matrix4fStack.translate(-ew, -eh, 0);

            // Maximum the distance as diagonal distance and round up.
            matrix4fStack.translate(0, transformSmooth.dist, 0);

            matrix4fStack.translate(ew, eh, 0);
            matrix4fStack.rotateZ(-radius);
            matrix4fStack.translate(-ew, -eh, 0);

            matrix4fStack.scale(scale, scale, 1);
            RenderSystem.applyModelViewMatrix();

            /* go on */
            entityIconComponent.render(graphics, partialTick);
            /* end of icon rend */
            RenderSystem.resetTextureMatrix();
        }

        matrix4fStack.popMatrix(); // 1
        matrix4fStack.popMatrix(); // 0
        return transformSmooth;
    }

    private static @NotNull TransformBase transformLocation(@NotNull Player player, Vec3 target, @NotNull Icon pointer, @NotNull Icon icon) {
        double[] arcAndDist = RelativeProjector.calculateAngle(
            player.getEyePosition(),
            player.getViewVector(1f),
            target,
            TConfig.projectAlgorithm::project
        );
        float angleRadians = (float) arcAndDist[0];
        double direct = arcAndDist[1];

        // pickDirect is ease-in-out from 15 to 2(pointer.getHeight() + icon.getHeight()) when direct is in [0, 0.8), and infinity closes to 2(pointer.getHeight() + icon.getHeight()) when direct bigger than 1
        float pickDirect = 15 + (float) (Math.atan(direct / 0.8) * 140 / Math.PI);
        //alpha close to 0 when pickDirect close to 25, close to 1 when pickDirect close to x, equal to 1 when pickDirect greater than x
        float alpha = Math.min(1, Math.max(0, (pickDirect - 25) / (pointer.height() + icon.height())));
        return new TransformBase(angleRadians, pickDirect, alpha);
    }

    private record TransformBase(float angleRadians, float pickDirect, float alpha) {}

    private static final class Transform {
        float radius;
        float dist;
        private Transform(float radius, float dist) {
            this.radius = radius;
            this.dist = dist;
        }
        public Transform smoothTransformModify(float radius, float dist, float partialTick) {
            if(Mth.abs(this.radius - radius) < Mth.PI * 1.5) this.radius = Mth.lerp(partialTick, this.radius, radius);
            else this.radius = radius;
            this.dist = Mth.lerp(partialTick, this.dist, dist);
            return this;
        }
    }
}