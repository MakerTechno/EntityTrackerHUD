package nowebsite.makertechno.entity_tracker.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.entity_tracker.client.gui.components.base.EntityIconComponent;
import nowebsite.makertechno.entity_tracker.client.gui.components.base.PointerIconComponent;
import nowebsite.makertechno.entity_tracker.client.render.texture.EntityIcon;
import nowebsite.makertechno.entity_tracker.client.render.texture.PointerIcon;
import nowebsite.makertechno.entity_tracker.core.config.TConfig;
import nowebsite.makertechno.entity_tracker.core.track.algorithm.DirProjector;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4fStack;

public class TRelativeCursor implements TRenderComponent{
    private final PointerIconComponent pointerIconComponent;
    private final EntityIconComponent entityIconComponent;
    private Transform tO = new Transform(0,  0);
    private float scale;
    public TRelativeCursor(PointerIconComponent pointerIconComponent, EntityIconComponent entityIconComponent) {
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
        TransformBase transform = transformLocation(player, target, pointerIconComponent.getPointerIcon(), entityIconComponent.getEntityIcon());
        this.tO = TRelativeCursor.renderStatic(graphics, partialTick, transform, tO, pointerIconComponent, entityIconComponent, scale);
    }

    private static Transform renderStatic(
            GuiGraphics graphics,
            float partialTick,
            @NotNull TransformBase transformBase, Transform tO,
            PointerIconComponent pointerIconComponent, EntityIconComponent entityIconComponent,
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
            @NotNull PointerIconComponent pointerIconComponent, @NotNull EntityIconComponent entityIconComponent,
            @NotNull Matrix4fStack matrix4fStack,
            @NotNull TransformBase result,
            Transform transform,
            float scale,
            float partialTick
    ) {
        float radius = (float) (-result.angleRadians - Math.PI/2);

        PointerIcon pointer = pointerIconComponent.getPointerIcon();
        EntityIcon entityIcon = entityIconComponent.getEntityIcon();

        float pw = -(float) pointer.width() / 2 * scale, ph = -(float) pointer.height() / 2 * scale;
        float ew = (float) entityIcon.width() / 2 * scale, eh = (float) entityIcon.height() / 2 * scale;
        float dist = -(result.pickDirect - pointer.height() - (int) Math.ceil(Math.sqrt(2 * entityIcon.height()) + 2)) * scale;

        Transform transformNew = new Transform(radius, dist);
        Transform transformSmooth = transform.smoothTransformModify(transformNew, partialTick);

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

        if (entityIcon != EntityIcon.NONE) {
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

    private static @NotNull TransformBase transformLocation(@NotNull Player player, Vec3 target, @NotNull PointerIcon pointer, @NotNull EntityIcon entityIcon) {
        double[] arcAndDist = DirProjector.calculateAngle(
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
        float alpha = Math.min(1, Math.max(0, (pickDirect - 25) / (pointer.height() + entityIcon.height())));
        return new TransformBase(angleRadians, pickDirect, alpha);
    }

    private record TransformBase(float angleRadians, float pickDirect, float alpha) {}

    private static class Transform {
        float radius;
        float dist;
        private Transform(float radius, float dist) {
            this.radius = radius;
            this.dist = dist;
        }
        public Transform smoothTransformModify(@NotNull Transform transform, float partialTick) {
            if(Mth.abs(this.radius - transform.radius) < Mth.PI * 1.5) this.radius = Mth.lerp(partialTick, this.radius, transform.radius);
            else this.radius = transform.radius;
            this.dist = Mth.lerp(partialTick, this.dist, transform.dist);
            return this;
        }
    }
}