package nowebsite.makertechno.entity_tracker.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import nowebsite.makertechno.entity_tracker.config.TConfig;
import nowebsite.makertechno.entity_tracker.client.track.TrackerLogic;
import org.jetbrains.annotations.NotNull;

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
        if (!minecraft.options.hideGui && player != null && TConfig.available){
            renderDir(guiGraphics, player);
        }
    }

    private void renderDir(GuiGraphics guiGraphics, Player player) {
        TrackerLogic.getEntitiesPositions().forEach(
            vec3CursorsPair -> RendererToScreen.centerRelativePointer(guiGraphics, player, vec3CursorsPair)
        );
    }

    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        RenderSystem.enableDepthTest();
        this.layerManager.render(guiGraphics, deltaTracker);
        RenderSystem.disableDepthTest();
    }
}
