package nowebsite.makertechno.entity_tracker.client.gui.components.base;

import nowebsite.makertechno.entity_tracker.client.render.texture.EntityIcon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class EntityIconFactory {
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull EntityIconComponent getEntityIconComponent(EntityIcon icon, String pattern) {
        // 2025/8/27-19:05 TODO: Add pattern check and more component.
        return new EntityIconComponent(icon);
    }
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull EntityIconComponent getDefault(EntityIcon icon) {
        return new EntityIconComponent(icon);
    }

    public static boolean hasElementPattern(String pattern) {
        return false;
    }
}
