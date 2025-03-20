package nowebsite.makertechno.entity_tracker.datagen;

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
        add("entity_tracker.configuration.available.tooltip", "启用或禁用跟踪指针");
        add("entity_tracker.configuration.mappingStyle", "映射样式");
        add("entity_tracker.configuration.mappingStyle.tooltip",
            """
            选择跟踪指针的样式
            CENTER_RELATIVE: 中心环绕型
            HEAD_FLAT: 顶部条型
            TRACK_FULL: 全屏边缘型
            """
        );
        add("entity_tracker.configuration.projectAlgorithm", "投影算法");
        add("entity_tracker.configuration.projectAlgorithm.tooltip",
            """
            设置特定于"中心环绕型"指针的算法
            MERCATOR: 墨卡托投影
            AITOFF: 艾托夫投影
            WINKEL_TRIPLE: 温克尔三角投影
            """
        );
        add("entity_tracker.configuration.tracking", "跟踪");
        add("entity_tracker.configuration.tracking.button", "跟踪实体的设置");
        add("entity_tracker.configuration.tracking.tooltip", "带有光标类型的实体类型列表，用\"|\"分隔");
    }
}
