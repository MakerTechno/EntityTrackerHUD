package nowebsite.makertechno.the_trackers.client.gui.components;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BasicComponentFactory {

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Supplier<IconComponent> getDefault(Icon icon) {
        return () -> new IconComponent(icon);
    }

    /**
     * 传入语句: "(blink/rainbow):(specific pattern)"
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Supplier<IconComponent> getIconComponent(Icon icon, @NotNull String pattern) {
        String [] patterns = pattern.split(":");

        try {
            int countNum;
            if (patterns[0].equals("blink")) {
                countNum = Integer.parseInt(patterns[1]);
                if (BlinkIconComponent.isValidPatterns(countNum)) return () -> new BlinkIconComponent(icon, countNum);
            } else if (patterns[0].equals("rainbow")) {
                countNum = Integer.parseInt(patterns[1]);
                if (RainbowIconComponent.isValidPatterns(countNum)) return () -> new RainbowIconComponent(icon, countNum);
            }
        } catch (NumberFormatException ignored) {}
        return getDefault(icon);
    }

    /**
     * 传入语句: "(blink/rainbow):(specific pattern)"
     */
    public static boolean hasElementPattern(@NotNull String pattern) {
        String [] patterns = pattern.split(":");
        if (patterns.length != 2) return false;
        try {
            if (patterns[0].equals("blink")) {
                if (BlinkIconComponent.isValidPatterns(Integer.parseInt(patterns[1]))) return true;
            } else if (patterns[0].equals("rainbow")) {
                if (RainbowIconComponent.isValidPatterns(Integer.parseInt(patterns[1]))) return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
