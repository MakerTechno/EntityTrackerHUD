package nowebsite.makertechno.entity_tracker.data.datagen;

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
        add("entity_tracker.configuration.available.tooltip", "Enable or disable the tracking process");

        add("entity_tracker.configuration.gui_scale", "The scale of tracking cursors");
        add("entity_tracker.configuration.gui_scale.tooltip", "Usually 0.6 on 1080p monitors");
        add("entity_tracker.configuration.quantity", "The max quantity of tracking");
        add("entity_tracker.configuration.quantity.tooltip", "Always shows those \"the top N closest\" to you");
        add("entity_tracker.configuration.interval", "Interval between world pos update");
        add("entity_tracker.configuration.interval.tooltip", "Choose 1 by default, provides the fastest reaction");

        add("entity_tracker.configuration.center_relative_available", "Center-relative hud available");
        add("entity_tracker.configuration.center_relative_available.tooltip", "Enable or disable center-relative cursors");
        add("entity_tracker.configuration.project_algorithm", "Project Algorithm");
        add("entity_tracker.configuration.project_algorithm.tooltip", "Set the algorithm specific for the center-relative pointer");
        add("entity_tracker.configuration.track_full_available", "Full screen tag hud available");
        add("entity_tracker.configuration.track_full_available.tooltip", "Enable or disable full screen tag cursors");
        add("entity_tracker.configuration.head_flat_available", "Longitude hud available");
        add("entity_tracker.configuration.head_flat_available.tooltip", "Enable or disable longitude display");

        add("entity_tracker.configuration.center_relative_tracking", "Tracking settings for center-relative cursors");
        add("entity_tracker.configuration.center_relative_tracking.button", "Settings for tracking entities");
        add("entity_tracker.configuration.center_relative_tracking.tooltip", "List of entity types with cursor types, separated with \"|\"");

        add("entity_tracker.configuration.track_full_tracking", "Tracking settings for full-track cursors");
        add("entity_tracker.configuration.track_full_tracking.button", "Settings for tracking entities");
        add("entity_tracker.configuration.track_full_tracking.tooltip", "List of entity types with cursor types, separated with \"|\"");
    }
}
