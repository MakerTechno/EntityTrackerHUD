package nowebsite.makertechno.entity_tracker.data.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import nowebsite.makertechno.entity_tracker.EntityTracker;

public class ModChineseLanguageProvider extends LanguageProvider {
    public ModChineseLanguageProvider(PackOutput packOutput, String locale) {
        super(packOutput, EntityTracker.MOD_ID, locale);
    }

    protected void addTranslations() {
        add("entity_tracker.configuration.title", "实体追踪器设置");
        add("entity_tracker.configuration.section.entity.tracker.client.toml", "Entity Tracker");
        add("entity_tracker.configuration.section.entity.tracker.client.toml.title", "Entity Tracker 配置");

        add("entity_tracker.configuration.available", "开关");
        add("entity_tracker.configuration.available.tooltip", "启用或禁用追踪指针");

        add("entity_tracker.configuration.gui_scale", "追踪指针大小");
        add("entity_tracker.configuration.gui_scale.tooltip", "默认值0.6，这在一般1080p设备上表现很好");
        add("entity_tracker.configuration.quantity", "最大追踪数量");
        add("entity_tracker.configuration.quantity.tooltip", "总是显示离你\"最近的N个\"实体");
        add("entity_tracker.configuration.interval", "位置刷新间隔");
        add("entity_tracker.configuration.interval.tooltip", "默认为1的情况下，提供最实时的指示");

        add("entity_tracker.configuration.center_relative_available", "中心环绕式指针开关");
        add("entity_tracker.configuration.center_relative_available.tooltip", "启用或禁用中心环绕式跟踪");
        add("entity_tracker.configuration.project_algorithm", "投影算法");
        add("entity_tracker.configuration.project_algorithm.tooltip",
            """
            设置特定于"中心环绕型"指针的算法
            MERCATOR: 墨卡托投影
            AITOFF: 艾托夫投影
            WINKEL_TRIPLE: 温克尔三角投影
            """
        );
        add("entity_tracker.configuration.track_full_available", "锁定定位式指针开关");
        add("entity_tracker.configuration.track_full_available.tooltip", "启用或禁用锁定定位式指针");
        add("entity_tracker.configuration.head_flat_available", "经度轴开关");
        add("entity_tracker.configuration.head_flat_available.tooltip", "启用或禁用经度轴条");

        add("entity_tracker.configuration.center_relative_tracking", "中心环绕式跟踪清单");
        add("entity_tracker.configuration.center_relative_tracking.button", "跟踪实体的设置");
        add("entity_tracker.configuration.center_relative_tracking.tooltip", "带有光标类型的实体类型列表，用\"|\"分隔");

        add("entity_tracker.configuration.track_full_tracking", "绑定定位式跟踪清单");
        add("entity_tracker.configuration.track_full_tracking.button", "跟踪实体的设置");
        add("entity_tracker.configuration.track_full_tracking.tooltip", "带有光标类型的实体类型列表，用\"|\"分隔");
    }
}
