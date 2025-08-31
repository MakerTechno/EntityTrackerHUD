package nowebsite.makertechno.entity_tracker.client.gui.components.base.tagpointer;

import nowebsite.makertechno.entity_tracker.client.render.texture.TIcon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TagPointerFactory {
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Supplier<TagPointerIconComponent> getPointerComponent(TIcon icon, @NotNull String pattern) {
        String [] patterns = pattern.split(":");

        try {
            int countNum;
            if (patterns[0].equals("blink")) {
                countNum = Integer.parseInt(patterns[1]);
                if (BlinkTagPointer.isValidPatterns(countNum)) return () -> new BlinkTagPointer(icon, countNum);
            } else if (patterns[0].equals("rainbow")) {
                countNum = Integer.parseInt(patterns[1]);
                if (RainbowTagPointer.isValidPatterns(countNum)) return () -> new RainbowTagPointer(icon, countNum);
            }
        } catch (NumberFormatException ignored) {}
        return () -> new TagPointerIconComponent(icon);
    }
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Supplier<TagPointerIconComponent> getDefault(TIcon icon) {
        return () -> new TagPointerIconComponent(icon);
    }

    public static boolean hasElementPattern(@NotNull String pattern) {
        String [] patterns = pattern.split(":");
        if (patterns.length != 2) return false;
        try {
            if (patterns[0].equals("blink")) {
                if (BlinkTagPointer.isValidPatterns(Integer.parseInt(patterns[1]))) return true;
            } else if (patterns[0].equals("rainbow")) {
                if (RainbowTagPointer.isValidPatterns(Integer.parseInt(patterns[1]))) return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
