package nowebsite.makertechno.the_trackers.client.gui.provider;

import nowebsite.makertechno.the_trackers.api.component.ComponentBuilder;
import nowebsite.makertechno.the_trackers.client.gui.cursors.*;
import nowebsite.makertechno.the_trackers.client.gui.components.IconComponent;
import nowebsite.makertechno.the_trackers.client.gui.components.BasicComponentFactory;
import nowebsite.makertechno.the_trackers.client.gui.components.Icon;
import org.jetbrains.annotations.Nullable;

public final class BuilderResultComposer {
    private BuilderResultComposer() {}

    public static TRenderComponent compose(ComponentBuilder.BuilderResult result) {
        TRenderComponent component = switch (result.type) {
            case RELATIVE -> composeRelativeComponent(result);
            case DIRECT -> composeDirectComponent(result);
            case HEAD_TAG -> composeRelativeComponent(result); // TODO
        };
        component.setSmoothMove(result.isSmoothMove);
        component.setAffectedByPlayerScale(result.affectedBySettings);
        component.setRescale(result.rescale);
        component.setTransformAlpha(result.transformAlpha);
        return component;
    }

    public static TRenderComponent composeRelativeComponent(ComponentBuilder.BuilderResult result) {
        return new TRelativeCursor(
            getIconComponent(result.component1Pattern, result.icon1.get()),
            getIconComponent(result.component2Pattern, result.icon2.get())
        );
    }

    public static TRenderComponent composeDirectComponent(ComponentBuilder.BuilderResult result) {
        TAbstractCursor cursor;
        if (result.cursorPattern != null) {
            String[] patterns = result.cursorPattern.split(",");
            if (patterns[0].equals("3body")) {
                if (result.component2Pattern == null){
                    cursor = new TDir3BodyCursor(
                        getIconComponent(result.component1Pattern, result.icon1.get()),
                        getIconComponent(result.component1Pattern, result.icon1.get()),
                        getIconComponent(result.component1Pattern, result.icon1.get())
                    );
                } else {
                    cursor = new TDir3BodyCursor(
                        getIconComponent(result.component1Pattern, result.icon1.get()),
                        getIconComponent(result.component2Pattern, result.icon2.get()),
                        getIconComponent(result.component3Pattern, result.icon3.get())
                    );
                }
                if (patterns.length > 1 && patterns[1].equals("faceCenter")) ((TDir3BodyCursor)cursor).setFaceCenter(true);
            }
            else cursor = new TDirectProjCursor(getIconComponent(result.component1Pattern, result.icon1.get()));
        }
        else cursor = new TDirectProjCursor(getIconComponent(result.component1Pattern, result.icon1.get()));
        return cursor;
    }
    /*public static TRenderComponent composeHeadTagComponent(ComponentBuilder.BuilderResult result) {
        return null;
    }*/


    private static IconComponent getIconComponent(@Nullable String pattern, Icon icon) {
        return pattern == null
                ? BasicComponentFactory.getDefault(icon).get()
                : BasicComponentFactory.getIconComponent(icon, pattern).get();
    }
}
