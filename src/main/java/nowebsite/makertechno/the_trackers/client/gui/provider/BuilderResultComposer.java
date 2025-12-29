package nowebsite.makertechno.the_trackers.client.gui.provider;

import nowebsite.makertechno.the_trackers.api.component.ComponentBuilder;
import nowebsite.makertechno.the_trackers.client.gui.components.TDir3BodyCursor;
import nowebsite.makertechno.the_trackers.client.gui.components.TDirectProjCursor;
import nowebsite.makertechno.the_trackers.client.gui.components.TRelativeCursor;
import nowebsite.makertechno.the_trackers.client.gui.components.TRenderComponent;
import nowebsite.makertechno.the_trackers.client.gui.icons.IconComponent;
import nowebsite.makertechno.the_trackers.client.gui.icons.IconComponentFactory;
import nowebsite.makertechno.the_trackers.client.gui.icons.Icon;
import org.jetbrains.annotations.Nullable;

public final class BuilderResultComposer {
    private BuilderResultComposer() {}

    public static TRenderComponent compose(ComponentBuilder.BuilderResult result) {
        return switch (result.type) {
            case RELATIVE -> composeRelativeComponent(result);
            case DIRECT -> composeDirectComponent(result);
            case HEAD_TAG -> composeRelativeComponent(result); // TODO
        };
    }

    public static TRenderComponent composeRelativeComponent(ComponentBuilder.BuilderResult result) {
        return new TRelativeCursor(getIconComponent(result.component1Pattern, result.icon1), getIconComponent(result.component2Pattern, result.icon2));
    }

    public static TRenderComponent composeDirectComponent(ComponentBuilder.BuilderResult result) {
        if (result.cursorPattern != null && result.cursorPattern.equals("3body"))
            return new TDir3BodyCursor(
                    getIconComponent(result.component1Pattern, result.icon1),
                    getIconComponent(result.component2Pattern, result.icon2),
                    getIconComponent(result.component3Pattern, result.icon3)
            );
        else return new TDirectProjCursor(getIconComponent(result.component1Pattern, result.icon1));
    }
    /*public static TRenderComponent composeHeadTagComponent(ComponentBuilder.BuilderResult result) {
        return null;
    }*/


    private static IconComponent getIconComponent(@Nullable String pattern, Icon icon) {
        return pattern == null
                ? IconComponentFactory.getDefault(icon).get()
                : IconComponentFactory.getIconComponent(icon, pattern).get();
    }
}
