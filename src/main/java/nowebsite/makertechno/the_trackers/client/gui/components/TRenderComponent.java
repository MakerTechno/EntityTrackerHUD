package nowebsite.makertechno.the_trackers.client.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public interface TRenderComponent {
    void flush();
    void render(GuiGraphics graphics, Player player, Vec3 target, float partialTick);
    @Contract(pure = true)
    static @Nullable TRenderComponent ofNull() {
        return null;
    }
}
