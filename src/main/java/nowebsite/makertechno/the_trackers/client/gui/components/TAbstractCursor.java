package nowebsite.makertechno.the_trackers.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.the_trackers.core.config.TConfig;

public abstract class TAbstractCursor implements TRenderComponent{

    protected float scaleCached;
    protected float definedScale;
    protected boolean smoothMove = true;
    protected boolean affectedByPlayerScale = true;

    protected TAbstractCursor(float definedScale) {
        this.scaleCached = (float) TConfig.scale;
        this.definedScale = definedScale;
    }
    protected TAbstractCursor() {
        this(1F);
    }

    public void setSmoothMove(boolean smoothMove) {
        this.smoothMove = smoothMove;
    }

    public void setAffectedByPlayerScale(boolean affectedByPlayerScale) {
        this.affectedByPlayerScale = affectedByPlayerScale;
    }

    @Override
    public void flush() {
        this.scaleCached = (float) TConfig.scale;
    }

    @Override
    public void render(GuiGraphics graphics, Player player, Vec3 target, float partialTick) {

        float[] projScrPoint = getProjectScr(graphics, player, target);

        float scale = (affectedByPlayerScale ? scaleCached : 1) * definedScale;

        updateTransformer(projScrPoint, partialTick, scale);
        RenderSystemInit(getAlpha(projScrPoint));
        renderInsights(graphics, projScrPoint, partialTick, scale);
        RenderSystemRestore();
    }

    protected abstract float[] getProjectScr(GuiGraphics graphics, Player player, Vec3 target);
    protected abstract void updateTransformer(float[] projScrPoint, float partialTick, float scale);
    protected abstract float getAlpha(float[] projScrPoint);
    protected abstract void renderInsights(GuiGraphics graphics, float[] projScrPoint, float partialTick, float scale);

    protected static void RenderSystemInit(float alpha) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
    }

    protected static void RenderSystemRestore() {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }
}
