package nowebsite.makertechno.terra_ethud.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import nowebsite.makertechno.terra_ethud.algorithm.Cursors;
import nowebsite.makertechno.terra_ethud.algorithm.MercatorProjectMap;
import nowebsite.makertechno.terra_ethud.client.track.EntityTracker;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4fStack;

import javax.annotation.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class TGui implements LayeredDraw.Layer {
    private final Minecraft minecraft;
    private final GuiLayerManager layerManager = new GuiLayerManager();

    public TGui(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.layerManager.add(TGuiLayers.POINTER, this::rendererTargetPointer);

    }

    @Nullable
    public static Player getCameraPlayer(@NotNull Minecraft minecraft) {
        return minecraft.getCameraEntity() instanceof Player player ? player : null;
    }

    public void rendererTargetPointer(GuiGraphics guiGraphics, DeltaTracker partialTick){
        Player player = getCameraPlayer(this.minecraft);
        if (!minecraft.options.hideGui && player != null){
            EntityTracker.getEntitiesPositions().forEach(vec3CursorsPair -> {
                double[] arcAndDist = MercatorProjectMap.calculateAngle(
                    player.getEyePosition(),
                    player.getViewVector(1f),
                    vec3CursorsPair.getA()
                );
                renderRotatingTool(
                    guiGraphics,
                    vec3CursorsPair.getB(),
                    (float) arcAndDist[0],
                    arcAndDist[1]
                );
            });
        }
    }
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        RenderSystem.enableDepthTest();
        this.layerManager.render(guiGraphics, deltaTracker);
        RenderSystem.disableDepthTest();
    }
    public void renderRotatingTool(@NotNull GuiGraphics guiGraphics, Cursors cursor, float angleRadians, double direct) {
        //pickDirect is ease-in-out from 15 to 130 when direct is in [0, 0.8), and infinity closes to 130 when direct bigger than 1
        float pickDirect = 15 + (float) (Math.atan(direct / 0.8) * 130 / Math.PI);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        );
        //alpha close to 0 when pickDirect close to 15, close to 1 when pickDirect close to 60, equal to 1 when pickDirect greater than 60
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, Math.min(1, Math.max(0, (pickDirect - 25) / 50)));
        Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        matrix4fStack.pushMatrix();
        matrix4fStack.translate((float) guiGraphics.guiWidth() / 2, (float) guiGraphics.guiHeight() / 2, 0);
        matrix4fStack.rotateZ((float) (-angleRadians - Math.PI/2));
        matrix4fStack.translate(-(float) cursor.getWidth() / 2, -(float) cursor.getHeight() / 2, 0);
        matrix4fStack.translate(0, -pickDirect, 0);
        RenderSystem.applyModelViewMatrix();
        guiGraphics.blit(cursor.getLocation(), 0, 0, 0, 0, cursor.getWidth(), cursor.getHeight(), cursor.getWidth(), cursor.getHeight());
        matrix4fStack.popMatrix();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }
}
