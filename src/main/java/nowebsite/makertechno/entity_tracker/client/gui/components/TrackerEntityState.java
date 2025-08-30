package nowebsite.makertechno.entity_tracker.client.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class TrackerEntityState {
    private Vec3 currentPos;
    private final TRenderComponent component;
    public TrackerEntityState(TRenderComponent component) {
        this.component = component;
    }
    public void updatePos(Vec3 pos) {
        this.currentPos = pos;
    }
    public void renderComponent(GuiGraphics guiGraphics, float partialTick, Player player) {
        component.render(guiGraphics, partialTick, player, currentPos);
    }
    public TRenderComponent getComponent() {
        return component;
    }
}
