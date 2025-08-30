package nowebsite.makertechno.entity_tracker.core.config;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import nowebsite.makertechno.entity_tracker.EntityTracker;
import nowebsite.makertechno.entity_tracker.client.gui.components.TRenderComponent;
import nowebsite.makertechno.entity_tracker.core.event.TModClient;
import nowebsite.makertechno.entity_tracker.core.track.TrackerLogic;
import nowebsite.makertechno.entity_tracker.core.track.algorithm.ProjectAlgorithmLib;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@EventBusSubscriber(modid = EntityTracker.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /* Basic settings. */
    private static final ModConfigSpec.BooleanValue AVAILABLE = BUILDER
            .comment("Enable tracking pointer")
            .translation("entity_tracker.configuration.available")
            .define("Available", false);
    private static final ModConfigSpec.DoubleValue GUI_SCALE = BUILDER
            .comment("Scale of cursor")
            .translation("entity_tracker.configuration.gui_scale")
            .defineInRange("Scale",0.6, 0.01, 4);
    private static final ModConfigSpec.IntValue MAX_TRACK_QUANTITY = BUILDER
            .comment("Max tracking quantity")
            .translation("entity_tracker.configuration.quantity")
            .defineInRange("Max quantity",10, 1, 400);
    private static final ModConfigSpec.IntValue REFRESH_POS_INTERVAL = BUILDER
            .comment("Interval between world position refresh")
            .translation("entity_tracker.configuration.interval")
            .defineInRange("Interval", 1, 1, 160);

    /* HUD mode control. */
    private static final ModConfigSpec.BooleanValue CENTER_RELATIVE_AVAILABLE = BUILDER
            .comment("Enable center relative icon display")
            .translation("entity_tracker.configuration.center_relative_available")
            .define("Center relative available", true);
    // Specific setting for center relative mode.
    public static final ModConfigSpec.EnumValue<ProjectAlgorithmLib.Type> PROJECT_ALGORITHM = BUILDER
            .comment("The algorithm of center rela projection")
            .translation("entity_tracker.configuration.project_algorithm")
            .defineEnum("Project algorithm", ProjectAlgorithmLib.Type.AITOFF);

    private static final ModConfigSpec.BooleanValue TRACK_FULL_AVAILABLE = BUILDER
            .comment("Enable full track icon display")
            .translation("entity_tracker.configuration.track_full_available")
            .define("Track full available", true);
    private static final ModConfigSpec.BooleanValue HEAD_FLAT_AVAILABLE = BUILDER
            .comment("Enable longitude icon display")
            .translation("entity_tracker.configuration.head_flat_available")
            .define("Head flat available", true);


    public static final ModConfigSpec.ConfigValue<List<? extends String>> CENTER_RELATIVE_BIND = BUILDER
            .comment("List of entity types with cursors for center relative display, separated with \"|\"")
            .translation("entity_tracker.configuration.center_relative_tracking")
            .defineList(
                    "EntityType|PointerIconType|EntityIconType(|OptionalValue)",
                    List.of(
                            "minecraft:ender_dragon|normal_red|ender_dragon_head",
                            "minecraft:wither|normal_white|wither_head",
                            "terra_entity:king_slime|normal_red|king_slime",
                            "terra_entity:eye_of_cthulhu|normal_red|eye_of_cthulhu",
                            "terra_entity:brain_of_cthulhu|normal_red|brain_of_cthulhu",
                            "terra_entity:eater_of_worlds|normal_red|eater_of_worlds",
                            "terra_entity:queen_bee|normal_red|queen_bee",
                            "terra_entity:skeletron|normal_red|skeletron",


                            "terra_entity:demon_eye|normal_red|none",
                            "terra_entity:flying_fish|normal_red|none",
                            "terra_entity:crimson_kemera|normal_red|none",
                            "terra_entity:eater_of_souls|normal_red|none",
                            "terra_entity:giant_worm|normal_red|none",
                            "terra_entity:tomb_crawler|normal_red|none",
                            "terra_entity:devourer|normal_red|none",
                            "terra_entity:cave_bat|normal_red|none",
                            "terra_entity:jungle_bat|normal_red|none",
                            "terra_entity:snatcher|normal_red|none",
                            "terra_entity:man_eater|normal_red|none",
                            "terra_entity:hornet|normal_red|none",
                            "terra_entity:hell_bat|normal_red|none",
                            "terra_entity:ice_bat|normal_red|none",
                            "terra_entity:spore_bat|normal_red|none",
                            "terra_entity:harpy|normal_red|none",
                            "terra_entity:cursed_skull|normal_red|none",
                            "terra_entity:dark_caster|normal_red|none",
                            "terra_entity:antlion_swarmer|normal_red|none",
                            "terra_entity:giant_antlion_swarmer|normal_red|none",
                            "terra_entity:wyvern|normal_red|none",
                            "terra_entity:granite_elemental|normal_red|none",
                            "terra_entity:ghost|normal_red|none",
                            "terra_entity:fire_imp|normal_red|none",
                            "terra_entity:demon|normal_red|none",
                            "terra_entity:voodoo_demon|normal_red|none"
                    ),
                    () -> "minecraft:player|normal_green|none",
                    ConfigProcessor::isValidEntityBindCenterRelativeCursor
            );

    public static final ModConfigSpec SPEC = BUILDER.build();
    public static boolean available;
    public static double scale;
    public static int maxTrackingQuantity;
    public static int interval;
    public static boolean centerRelativeAvailable;
    public static boolean trackFullAvailable;
    public static boolean headFlatAvailable;
    public static ProjectAlgorithmLib.Type projectAlgorithm;
    public static Set<Pair<EntityType<?>, Supplier<? extends TRenderComponent>>> cursorWithEntities;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        available = AVAILABLE.get();
        scale = GUI_SCALE.get();
        interval = REFRESH_POS_INTERVAL.get();
        maxTrackingQuantity = MAX_TRACK_QUANTITY.get();
        centerRelativeAvailable = CENTER_RELATIVE_AVAILABLE.get();
        trackFullAvailable = TRACK_FULL_AVAILABLE.get();
        headFlatAvailable = HEAD_FLAT_AVAILABLE.get();
        projectAlgorithm = PROJECT_ALGORITHM.get();
        if(TModClient.isLoaded) cursorWithEntities = ConfigProcessor.collectEntityBindCursor(CENTER_RELATIVE_BIND.get());
        TrackerLogic.getRENDERING().forEach((uuid, trackerEntityState) -> trackerEntityState.getComponent().flush());
    }

    @SubscribeEvent
    static void onFileChanged(final ModConfigEvent.Reloading event) {
        onLoad(event);
    }
}