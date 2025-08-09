package nowebsite.makertechno.entity_tracker.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import nowebsite.makertechno.entity_tracker.config.TConfig;
import nowebsite.makertechno.entity_tracker.client.track.TrackerLogic;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TGui implements LayeredDraw.Layer {
    private final Minecraft minecraft;

    public TGui(Minecraft minecraft) {
        this.minecraft = minecraft;
    }
    @Nullable
    public static Player getCameraPlayer(@NotNull Minecraft minecraft) {
        return minecraft.getCameraEntity() instanceof Player player ? player : null;
    }
    public void rendererTargetPointer(GuiGraphics guiGraphics, DeltaTracker partialTick){
        Player player = getCameraPlayer(this.minecraft);
        if (!minecraft.options.hideGui && player != null && TConfig.available){
            renderDir(guiGraphics, player);
        }
    }

    private void renderDir(GuiGraphics guiGraphics, Player player) {
        TrackerLogic.getEntitiesPositions().forEach(
            vec3CursorsPair -> {
                switch (TConfig.mappingStyle) {
                    case CENTER_RELATIVE -> RendererToScreen.centerRelativePointer(guiGraphics, player, vec3CursorsPair);
                    case HEAD_FLAT -> {}
                    case TRACK_FULL -> RendererToScreen.fullScreenPointer(guiGraphics, player, vec3CursorsPair, minecraft.gameRenderer);
                }
            }
        );
    }

    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        RenderSystem.enableDepthTest();
        rendererTargetPointer(guiGraphics, deltaTracker);
        RenderSystem.disableDepthTest();
    }
}
