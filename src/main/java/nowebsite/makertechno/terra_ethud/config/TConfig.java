package nowebsite.makertechno.terra_ethud.config;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import nowebsite.makertechno.terra_ethud.TerraETHUD;
import nowebsite.makertechno.terra_ethud.algorithm.Cursors;
import nowebsite.makertechno.terra_ethud.algorithm.PointerStyle;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = TerraETHUD.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.EnumValue<PointerStyle> POINTER_STYLE = BUILDER
        .comment("The style of the pointer")
        .defineEnum("pointerStyle", PointerStyle.NORMAL);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> ENTITY_TYPES = BUILDER
        .comment("List of entity types with cursors, separated with \"|\"")
        .defineList(
            "entityTypes|Cursors",
            List.of("minecraft:enderman|normal_white", "minecraft:ender_dragon|ender_dragon_head"),
            () -> "minecraft:wither|normal_red",
            o -> {
                if (!(o instanceof String)) return false;
                String[] split = ((String) o).split("\\|");
                if (split.length != 2) return false;
                Optional<EntityType<?>> optional = EntityType.byString(split[0]);
                if (optional.isEmpty()) return false;
                try {
                    Cursors.getByName(split[1]);
                } catch (IllegalArgumentException e) {
                    return false;
                }
                return true;
            }
        );

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static PointerStyle pointerStyle;
    public static Set<Pair<EntityType<?>, Cursors>> pointerWithEntities;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        pointerStyle = POINTER_STYLE.get();


        pointerWithEntities = ENTITY_TYPES.get().stream()
            .map(s -> {
                String[] split = s.split("\\|");
                Optional<EntityType<?>> optional = EntityType.byString(split[0]);
                Cursors cursor = Cursors.getByName(split[1]);
                return new Pair<>(optional, cursor);
            })
            .filter(p -> p.getA().isPresent() && p.getB() != null)
            .map(p -> new Pair<EntityType<?>, Cursors>(p.getA().get(), p.getB()))
            .collect(Collectors.toSet());
    }
}