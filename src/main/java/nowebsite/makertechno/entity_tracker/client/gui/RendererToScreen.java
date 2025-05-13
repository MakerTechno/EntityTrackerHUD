package nowebsite.makertechno.entity_tracker.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.entity_tracker.algorithm.CameraProjector;
import nowebsite.makertechno.entity_tracker.algorithm.DirProjector;
import nowebsite.makertechno.entity_tracker.config.TConfig;
import nowebsite.makertechno.entity_tracker.define.TCursor;
import nowebsite.makertechno.entity_tracker.define.TEntityIcon;
import nowebsite.makertechno.entity_tracker.define.TPointer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4fStack;

public class RendererToScreen {
    public static void centerRelativePointer(@NotNull GuiGraphics guiGraphics, @NotNull Player player, @NotNull Pair<Vec3, TCursor> vec3CursorsPair) {
        double[] arcAndDist = DirProjector.calculateAngle(
            player.getEyePosition(),
            player.getViewVector(1f),
            vec3CursorsPair.getFirst(),
            TConfig.projectAlgorithm::project
        );
        TPointer pointer = vec3CursorsPair.getSecond().pointer();
        TEntityIcon icon = vec3CursorsPair.getSecond().icon();
        float angleRadians = (float) arcAndDist[0];
        double direct = arcAndDist[1];

        // pickDirect is ease-in-out from 15 to 2(pointer.getHeight() + icon.getHeight()) when direct is in [0, 0.8), and infinity closes to 2(pointer.getHeight() + icon.getHeight()) when direct bigger than 1
        float pickDirect = 15 + (float) (Math.atan(direct / 0.8) * 140 / Math.PI);
        //alpha close to 0 when pickDirect close to 15, close to 1 when pickDirect close to x, equal to 1 when pickDirect greater than x
        float alpha = Math.min(1, Math.max(0, (pickDirect - 25) / (pointer.getHeight() + icon.getHeight())));

        // init
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        );
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        // matrix
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        matrix4fStack.pushMatrix(); // 1
        matrix4fStack.translate((float) guiGraphics.guiWidth() / 2, (float) guiGraphics.guiHeight() / 2, 0);
        matrix4fStack.rotateZ((float) (-angleRadians - Math.PI/2));
        matrix4fStack.pushMatrix(); // 2
        matrix4fStack.translate(-(float) pointer.getWidth() / 2, -(float) pointer.getHeight() / 2, 0);
        matrix4fStack.translate(0, -pickDirect, 0);
        RenderSystem.applyModelViewMatrix();

        /* go on */
        guiGraphics.blit(
            pointer.getLocation(),
            0, 0,
            0, 0,
            pointer.getWidth(), pointer.getHeight(),
            pointer.getWidth(), pointer.getHeight()
        );
        /* end of pointer rend */

        if (icon != TEntityIcon.NONE) {
            // roll back
            matrix4fStack.popMatrix(); // 1
            matrix4fStack.pushMatrix(); // 2
            matrix4fStack.translate(-(float) icon.getWidth() / 2, -(float) icon.getHeight() / 2, 0);
            matrix4fStack.translate(0, -(pickDirect - pointer.getHeight() - (int) Math.ceil(Math.sqrt(2 * icon.getHeight()) + 2)), 0);

            matrix4fStack.translate((float) icon.getWidth() / 2, (float) icon.getHeight() / 2, 0);
            matrix4fStack.rotateZ((float) (angleRadians + Math.PI / 2));
            matrix4fStack.translate(-(float) icon.getWidth() / 2, -(float) icon.getHeight() / 2, 0);

            RenderSystem.applyModelViewMatrix();

            /* go on */
            guiGraphics.blit(
                icon.getLocation(),
                0, 0,
                0, 0,
                icon.getWidth(), icon.getHeight(),
                icon.getWidth(), icon.getHeight()
            );
            /* end of icon rend */
            RenderSystem.resetTextureMatrix();
        }

        matrix4fStack.popMatrix(); // 1
        matrix4fStack.popMatrix(); // 0
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    public static void fullScreenPointer(@NotNull GuiGraphics guiGraphics,@NotNull Player player, @NotNull Pair<Vec3, TCursor> vec3CursorsPair, GameRenderer renderer) {
        float[] projScrPoint = CameraProjector.projectToScreen(
            renderer,
            vec3CursorsPair.getFirst(),
            player.getEyePosition(),
            Minecraft.getInstance().gameRenderer.getMainCamera(),
            guiGraphics.guiWidth(),
            guiGraphics.guiHeight()
        );
        TCursor cursor = vec3CursorsPair.getSecond();
        TEntityIcon icon = cursor.icon();

        float alpha = 1;
        if (projScrPoint[2] < 0.2*guiGraphics.guiWidth()) alpha = (float) (projScrPoint[2]*0.6/(0.2*guiGraphics.guiWidth()) + 0.4);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);

        // init
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        );

        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        matrix4fStack.pushMatrix(); // 1
        matrix4fStack.translate(-(float) icon.getWidth() / 2, -(float) icon.getHeight() / 2, 0);
        matrix4fStack.translate(projScrPoint[0], projScrPoint[1], 0);
        RenderSystem.applyModelViewMatrix();

        if (icon != TEntityIcon.NONE) {

            /* go on */
            guiGraphics.blit(
                icon.getLocation(),
                0, 0,
                0, 0,
                icon.getWidth(), icon.getHeight(),
                icon.getWidth(), icon.getHeight()
            );
            /* end of icon rend */
            RenderSystem.resetTextureMatrix();
        }

        matrix4fStack.popMatrix(); // 0
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }
}
