package nowebsite.makertechno.the_trackers.core.track.states;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.the_trackers.client.gui.components.TRenderComponent;

import java.util.Arrays;

public class ControllableTrackerStateEx extends ControllableTrackerState {
    private TrackerStepState[] states;

    @SuppressWarnings("all")
    public ControllableTrackerStateEx(String identifyName, TRenderComponent[] components, boolean isVisible) {
        super(identifyName, null, isVisible);
        this.states = new TrackerStepState[components.length];
        Arrays.setAll(this.states, i -> new TrackerStepState(components[i]));
    }

    @Override
    public void setPos(Vec3 pos) {
        for (TrackerStepState stepState : states) {
            stepState.updatePos(pos);
        }
    }

    @Override
    public Vec3 getPos() {
        return states[0].getPos();
    }

    @Override
    public void render(GuiGraphics graphics, float partialTick, Player player) {
        if (isVisible) {
            for (TrackerStepState stepState : states) {
                stepState.renderComponent(graphics, partialTick, player);
            }
        }
    }

    public void setStates(TrackerStepState[] states) {
        this.states = states;
    }

    public TrackerStepState[] getStates() {
        return states;
    }
}
