package nowebsite.makertechno.the_trackers.core.track.states;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.the_trackers.api.component.StaticComponent;
import nowebsite.makertechno.the_trackers.client.gui.components.TRenderComponent;

import java.util.function.Function;

public class ControllableTrackerState implements StaticComponent {
    protected final String identifyName;
    protected final TrackerStepState state;

    protected boolean isVisible;
    protected boolean isClosed = false;

    protected Function<Vec3, Vec3> applier = vec3 -> vec3;
    public ControllableTrackerState(String identifyName, TRenderComponent component, boolean isVisible) {
        this.identifyName = identifyName;
        this.state = new TrackerStepState(component);
        this.isVisible = isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 正常更新实体的时候传入的是实体坐标，applier不应该被改动。
     * 静态模式下会传入玩家位置供判断。
     */
    public void setPos(Vec3 pos) {
        state.updatePos(applier.apply(pos));
    }

    public Vec3 getPos() {
        return state.getPos();
    }

    public void render(GuiGraphics graphics, float partialTick, Player player) {
        if (isVisible) state.renderComponent(graphics, partialTick, player);
    }

    public String getIdentifyName() {
        return identifyName;
    }

    @Override
    public void close() {
        isClosed = true;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    public TrackerStepState getState() {
        return state;
    }

    @Override
    public void posUpdater(Function<Vec3, Vec3> applier) {
        this.applier = applier;
    }
}
