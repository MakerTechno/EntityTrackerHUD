package nowebsite.makertechno.entity_tracker.client.gui.components.base.pointer;

import nowebsite.makertechno.entity_tracker.client.render.texture.PointerIcon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PointerFactory {
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull PointerIconComponent getPointerComponent(PointerIcon icon, String pattern) {
        // 2025/8/27-19:05 TODO: Add pattern check and more component.
        return new PointerIconComponent(icon);
    }
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull PointerIconComponent getDefault(PointerIcon icon) {
        return new PointerIconComponent(icon);
    }

    public static boolean hasElementPattern(String pattern) {
        return false;
    }
}
