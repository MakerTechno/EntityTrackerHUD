package nowebsite.makertechno.the_trackers.api.component;

import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import nowebsite.makertechno.the_trackers.client.gui.icons.IconComponentFactory;
import nowebsite.makertechno.the_trackers.client.gui.icons.Icon;
import nowebsite.makertechno.the_trackers.client.gui.provider.TextureCache;
import nowebsite.makertechno.the_trackers.core.tool.TextureBuildTool;

import javax.annotation.Nullable;

/**
 * 追踪指针构造器，构造并返回一个{@link BuilderResult}供后续注册指针使用。
 */
@SuppressWarnings("unused")
public class ComponentBuilder {
    private ComponentType type = ComponentType.DIRECT;
    private String cursorPattern;
    private Icon icon1 = Icon.NONE;
    private String component1Pattern = null;
    private Icon icon2 = Icon.NONE;
    private String component2Pattern = null;
    private Icon icon3 = Icon.NONE;
    private String component3Pattern = null;


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
        icon1 = getIcon(location);
        return this;
    }

    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>将传入的物品的贴图作为图标。图标默认为空。</p>
     */
    public ComponentBuilder setIcon1(Item item) {
        icon1 = getIcon(TextureMapping.getItemTexture(item));
        return this;
    }

    /**
     * <p>对于一般情况，该方法设置其中心图标。具有多个图标位的指针需要填充其它icon。</p>
     * <p>传入的值应为内部实体贴图ID。图标默认为空</p>
     */
    public ComponentBuilder setIcon1(String key) {
        icon1 = TextureCache.getIcon(key);
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
     * <p>传入的值应为内部实体贴图ID。图标默认为空</p>
     */
    public ComponentBuilder setIcon2(String key) {
        icon2 = TextureCache.getIcon(key);
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
     * <p>传入的值应为内部实体贴图ID。图标默认为空</p>
     */
    public ComponentBuilder setIcon3(String key) {
        icon3 = TextureCache.getIcon(key);
        return this;
    }

    /**
     * 设置图标1的容器。默认为null(获取时使用default)，设置请参见{@link IconComponentFactory}
     */
    public ComponentBuilder setIcon3Pattern(String pattern) {
        this.component3Pattern = pattern;
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
                cursorPattern
        );
    }

    private static Icon getIcon(ResourceLocation location) {
        return TextureBuildTool.initIcon("dynamic", location, Icon::new).orElse(Icon.NONE);
    }

    public static final class BuilderResult {
        public final @Nullable String component1Pattern, component2Pattern, component3Pattern, cursorPattern;
        public final ComponentType type;
        public final Icon icon1, icon2, icon3;
        private BuilderResult(
                ComponentType type,
                Icon icon1,
                @Nullable String component1Pattern,
                Icon icon2,
                @Nullable String component2Pattern,
                Icon icon3,
                @Nullable String component3Pattern,
                @Nullable String cursorPattern
        ) {
            this.type = type;
            this.icon1 = icon1;
            this.component1Pattern = component1Pattern;
            this.icon2 = icon2;
            this.component2Pattern = component2Pattern;
            this.icon3 = icon3;
            this.component3Pattern = component3Pattern;
            this.cursorPattern = cursorPattern;
        }
    }

    public enum ComponentType {
        RELATIVE,
        DIRECT,
        HEAD_TAG
    }
}
