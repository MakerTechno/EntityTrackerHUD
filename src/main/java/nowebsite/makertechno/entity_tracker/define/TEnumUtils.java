package nowebsite.makertechno.entity_tracker.define;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TEnumUtils {
    public static <T extends TRenderItem>  @Nullable T getByName(String name, T @NotNull [] values) {
        for (T t : values) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }
}
