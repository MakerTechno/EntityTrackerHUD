package nowebsite.makertechno.the_trackers.api.component;

import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import nowebsite.makertechno.the_trackers.client.gui.components.IconComponentFactory;
import nowebsite.makertechno.the_trackers.client.gui.components.Icon;
import nowebsite.makertechno.the_trackers.client.gui.provider.TextureCache;
import nowebsite.makertechno.the_trackers.core.tool.TextureBuildTool;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 追踪指针构造器，构造并返回一个{@link BuilderResult}供后续注册指针使用。
 */
@SuppressWarnings("unused")
public class ComponentBuilder {
    private ComponentType type = ComponentType.DIRECT;
    private String cursorPattern = null;
    private Supplier<Icon> icon1 = () -> Icon.NONE;
    private String component1Pattern = null;
    private Supplier<Icon> icon2 = () -> Icon.NONE;
    private String component2Pattern = null;
    private Supplier<Icon> icon3 = () -> Icon.NONE;
    private String component3Pattern = null;
    private boolean isSmoothMove = false;
    private boolean affectedByPlayerSettingsScale = false;
    private boolean autoLifecycle = false;
    private Function<Float, Float> definedScaleMultiple = scale -> scale;


    public ComponentBuilder() {}

    /**
     * 设置指针类型，默认为三维投影型。
     */
    public ComponentBuilder setComponentType(ComponentType type) {
        this.type = type;
        return this;
    }

    /**
     * 设置指针数据。
     */
    public ComponentBuilder setCursorPattern(String pattern) {
        this.cursorPattern = pattern;
        return this;
    }

    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>传入的索引应为图标索引。图标默认为空。</p>
     */
    public ComponentBuilder setIcon1(ResourceLocation location) {
        icon1 = () -> getIcon(location);
        return this;
    }

    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>将传入的物品的贴图作为图标。图标默认为空。</p>
     */
    public ComponentBuilder setIcon1(Item item) {
        icon1 = () -> getIcon(TextureMapping.getItemTexture(item).withPrefix("textures/"));
        return this;
    }

    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>传入的值应为内部实体D。图标默认为空</p>
     */
    public ComponentBuilder setIcon1(String key) {
        icon1 = () -> TextureCache.getIcon(key);
        return this;
    }

    /**
     * 设置图标1的容器。默认为null(获取时使用default)，设置请参见{@link IconComponentFactory}
     */
    public ComponentBuilder setIcon1Pattern(String pattern) {
        this.component1Pattern = pattern;
        return this;
    }


    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>将传入的物品的贴图作为图标。图标默认为空。</p>
     */
    public ComponentBuilder setIcon2(Item item) {
        icon2 = () -> getIcon(TextureMapping.getItemTexture(item).withPrefix("textures/"));
        return this;
    }

    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>传入的值应为内部实体贴图ID。图标默认为空</p>
     */
    public ComponentBuilder setIcon2(String key) {
        icon2 = () -> TextureCache.getIcon(key);
        return this;
    }

    /**
     * 设置图标1的容器。默认为null(获取时使用default)，设置请参见{@link IconComponentFactory}
     */
    public ComponentBuilder setIcon2Pattern(String pattern) {
        this.component2Pattern = pattern;
        return this;
    }


    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>将传入的物品的贴图作为图标。图标默认为空。</p>
     */
    public ComponentBuilder setIcon3(Item item) {
        icon3 = () -> getIcon(TextureMapping.getItemTexture(item).withPrefix("textures/"));
        return this;
    }

    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>传入的值应为内部实体贴图ID。图标默认为空</p>
     */
    public ComponentBuilder setIcon3(String key) {
        icon3 = () -> TextureCache.getIcon(key);
        return this;
    }

    /**
     * 设置图标1的容器。默认为null(获取时使用default)，设置请参见{@link IconComponentFactory}
     */
    public ComponentBuilder setIcon3Pattern(String pattern) {
        this.component3Pattern = pattern;
        return this;
    }

    /**
     * 设置是否启用插值帧移动。
     */
    public ComponentBuilder setSmoothMove(boolean smoothMove) {
        isSmoothMove = smoothMove;
        return this;
    }

    /**
     * 设置是否受到用户设置大小的影响。
     */
    public ComponentBuilder setAffectedByPlayerSettingsScale(boolean affectedByPlayerSettingsScale) {
        this.affectedByPlayerSettingsScale = affectedByPlayerSettingsScale;
        return this;
    }

    /**
     * 设置是否自动管理生命周期。仅对实体类指针生效，当实体不再扫描到时清理指针。
     */
    public ComponentBuilder setAutoLifecycle(boolean autoLifecycle) {
        this.autoLifecycle = autoLifecycle;
        return this;
    }

    /**
     * 设置乘数再运算器，一般建议在这里对最终大小进行区间移动和缩放。
     * */
    public ComponentBuilder defineScaleMultiple(Function<Float, Float> definedScaleMultipleApplier) {
        this.definedScaleMultiple = definedScaleMultipleApplier;
        return this;
    }

    public BuilderResult build() {
        return new BuilderResult(
                type,
                icon1,
                component1Pattern,
                icon2,
                component2Pattern,
                icon3,
                component3Pattern,
                cursorPattern,
                isSmoothMove,
                autoLifecycle,
                affectedByPlayerSettingsScale,
                definedScaleMultiple
        );
    }

    private static Icon getIcon(ResourceLocation location) {
        return TextureBuildTool.initIcon("dynamic", location.withSuffix(".png"), Icon::new).orElse(Icon.NONE);
    }

    public static final class BuilderResult {
        public final @Nullable String component1Pattern, component2Pattern, component3Pattern, cursorPattern;
        public final ComponentType type;
        public final Supplier<Icon> icon1, icon2, icon3;
        public final boolean isSmoothMove, autoLifecycle, affectedBySettings;
        public final Function<Float, Float> multiple;
        private BuilderResult(
                ComponentType type,
                Supplier<Icon> icon1,
                @Nullable String component1Pattern,
                Supplier<Icon> icon2,
                @Nullable String component2Pattern,
                Supplier<Icon> icon3,
                @Nullable String component3Pattern,
                @Nullable String cursorPattern,
                boolean isSmoothMove,
                boolean autoLifecycle,
                boolean affectedBySettings,
                Function<Float, Float> multiple
        ) {
            this.type = type;
            this.icon1 = icon1;
            this.component1Pattern = component1Pattern;
            this.icon2 = icon2;
            this.component2Pattern = component2Pattern;
            this.icon3 = icon3;
            this.component3Pattern = component3Pattern;
            this.cursorPattern = cursorPattern;
            this.isSmoothMove = isSmoothMove;
            this.autoLifecycle = autoLifecycle;
            this.affectedBySettings = affectedBySettings;
            this.multiple = multiple;
        }
    }

    public enum ComponentType {
        RELATIVE,
        DIRECT,
        HEAD_TAG
    }
}
