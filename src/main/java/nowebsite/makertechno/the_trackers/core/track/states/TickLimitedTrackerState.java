package nowebsite.makertechno.the_trackers.core.track.states;

import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.the_trackers.client.gui.components.TRenderComponent;

public class TickLimitedTrackerState extends ControllableTrackerState{
    private final int maxTicked;

    private int ticking = 0;
    public TickLimitedTrackerState(String identifyName, TRenderComponent component, int maxTicked, boolean isVisible) {
        super(identifyName, component, isVisible);
        this.maxTicked = maxTicked;
    }

    @Override
    public void setPos(Vec3 pos) {
        super.setPos(pos);
        if (ticking < maxTicked) ticking++;
        else this.close();
    }
}
