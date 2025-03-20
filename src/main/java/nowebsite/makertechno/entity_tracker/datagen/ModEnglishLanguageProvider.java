package nowebsite.makertechno.entity_tracker.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import nowebsite.makertechno.entity_tracker.EntityTracker;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput packOutput, String locale) {
        super(packOutput, EntityTracker.MOD_ID, locale);
    }

    protected void addTranslations() {
        add("entity_tracker.configuration.title", "Entity Tracker Settings");
        add("entity_tracker.configuration.section.entity.tracker.client.toml", "Entity Tracker");
        add("entity_tracker.configuration.section.entity.tracker.client.toml.title", "Entity Tracker Configuration");

        add("entity_tracker.configuration.available", "Available");
        add("entity_tracker.configuration.available.tooltip", "Enable or disable the tracking pointer");
        add("entity_tracker.configuration.mappingStyle", "Mapping style");
        add("entity_tracker.configuration.mappingStyle.tooltip", "Choose the style of the tracking pointer");
        add("entity_tracker.configuration.projectAlgorithm", "Project Algorithm");
        add("entity_tracker.configuration.projectAlgorithm.tooltip", "Set the algorithm specific for the center-relative pointer");
        add("entity_tracker.configuration.tracking", "Tracking");
        add("entity_tracker.configuration.tracking.button", "Settings for tracking entities");
        add("entity_tracker.configuration.tracking.tooltip", "List of entity types with cursor types, separated with \"|\"");
    }
}
