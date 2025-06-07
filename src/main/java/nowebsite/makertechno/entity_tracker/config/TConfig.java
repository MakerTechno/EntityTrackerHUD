package nowebsite.makertechno.entity_tracker.config;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import nowebsite.makertechno.entity_tracker.EntityTracker;
import nowebsite.makertechno.entity_tracker.algorithm.ProjectAlgorithmLib;
import nowebsite.makertechno.entity_tracker.define.MappingStyle;
import nowebsite.makertechno.entity_tracker.define.TCursor;
import nowebsite.makertechno.entity_tracker.tool4c.ConfigProcessor;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = EntityTracker.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.BooleanValue AVAILABLE = BUILDER
            .comment("Enable tracking pointer")
            .translation("entity_tracker.configuration.available")
            .define("Available", true);
    private static final ModConfigSpec.EnumValue<MappingStyle> MAPPING_STYLE = BUILDER
            .comment("The style of the pointer")
            .translation("entity_tracker.configuration.mappingStyle")
            .defineEnum("mappingStyle", MappingStyle.CENTER_RELATIVE);
    public static final ModConfigSpec.EnumValue<ProjectAlgorithmLib.Type> PROJECT_ALGORITHM = BUILDER
            .comment("The algorithm of the projection")
            .translation("entity_tracker.configuration.projectAlgorithm")
            .defineEnum("projectAlgorithm", ProjectAlgorithmLib.Type.AITOFF);
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ENTITY_TYPES = BUILDER
            .comment("List of entity types with cursors, separated with \"|\"")
            .translation("entity_tracker.configuration.tracking")
            .defineList(
                    "entityTypes|Pointer|EntityIcon",
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
                            "terra_entity:drippler|normal_red|none",
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
                            "terra_entity:giant_antlion_swarmer|normal_red|none"
                    ),
                    () -> "minecraft:villager|normal_green|none",
                    ConfigProcessor::isValidEntityBindCursor
            );

    public static final ModConfigSpec SPEC = BUILDER.build();
    public static boolean available;
    public static MappingStyle mappingStyle;
    public static ProjectAlgorithmLib.Type projectAlgorithm;
    public static Set<Pair<EntityType<?>, TCursor>> pointerWithEntities;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        available = AVAILABLE.get();
        mappingStyle = MAPPING_STYLE.get();
        projectAlgorithm = PROJECT_ALGORITHM.get();
        pointerWithEntities = ConfigProcessor.collectEntityBindCursor(ENTITY_TYPES.get());
    }

    @SubscribeEvent
    static void onFileChanged(final ModConfigEvent.Reloading event) {
        onLoad(event);
    }
}