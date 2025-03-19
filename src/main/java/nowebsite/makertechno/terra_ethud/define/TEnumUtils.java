package nowebsite.makertechno.terra_ethud.define;

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
