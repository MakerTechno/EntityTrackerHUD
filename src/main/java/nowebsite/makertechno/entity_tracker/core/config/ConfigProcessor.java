package nowebsite.makertechno.entity_tracker.core.config;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import nowebsite.makertechno.entity_tracker.client.gui.components.TRelativeCursor;
import nowebsite.makertechno.entity_tracker.client.gui.components.TRenderComponent;
import nowebsite.makertechno.entity_tracker.client.gui.components.base.EntityIconFactory;
import nowebsite.makertechno.entity_tracker.client.gui.components.base.PointerFactory;
import nowebsite.makertechno.entity_tracker.client.render.texture.EntityIcon;
import nowebsite.makertechno.entity_tracker.client.render.texture.PointerIcon;
import nowebsite.makertechno.entity_tracker.client.render.texture.TextureCache;
import nowebsite.makertechno.entity_tracker.core.event.TModClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ConfigProcessor {
    
    private static final Pair<EntityType<?>, Supplier<? extends TRenderComponent>> OF_EMPTY =new Pair<>(null, TRenderComponent::ofNull);

    public static boolean isValidEntityBindCenterRelativeCursor(Object o) {
        if (!(o instanceof String s)) return false;
        String[] split = s.split("\\|");
        if (split.length < 3 || split.length > 4) return false;

        Optional<EntityType<?>> typeOpt = EntityType.byString(split[0]);
        if (typeOpt.isEmpty()) return false;

        if (TModClient.isLoaded && !split[1].equals("none") && TextureCache.getPointer(split[1]).equals(PointerIcon.NONE)) return false;
        if (TModClient.isLoaded && !split[2].equals("none") && TextureCache.getEntityIcon(split[2]).equals(EntityIcon.NONE)) return false;

        if (split.length == 4) {
            String[] patterns = split[3].split("&");
            if (patterns.length != 2) return false;
            return PointerFactory.hasElementPattern(patterns[0]) && EntityIconFactory.hasElementPattern(patterns[1]);
        }

        return true;
    }

    @NotNull
    public static Set<Pair<EntityType<?>, Supplier<? extends TRenderComponent>>> collectEntityBindCursor(@NotNull List<? extends String> list) {
        return list.stream()
            .map(s -> {
                String[] split = s.split("\\|");
                if (split.length < 3 || split.length > 4) return OF_EMPTY;

                Optional<EntityType<?>> typeOpt = EntityType.byString(split[0]);
                if (typeOpt.isEmpty()) return OF_EMPTY;
                EntityType<?> entityType = typeOpt.get();

                PointerIcon pointerIcon = TextureCache.getPointer(split[1]);
                if (!split[1].equals("none") && pointerIcon.equals(PointerIcon.NONE)) return OF_EMPTY;

                EntityIcon entityIcon = TextureCache.getEntityIcon(split[2]);
                if (!split[2].equals("none") && entityIcon.equals(EntityIcon.NONE)) return OF_EMPTY;

                Supplier<? extends TRenderComponent> supplier;

                if (split.length == 4) {
                    String[] patterns = split[3].split("&");
                    if (patterns.length == 2 &&
                        PointerFactory.hasElementPattern(patterns[0]) &&
                        EntityIconFactory.hasElementPattern(patterns[1])) {
                        supplier = () -> new TRelativeCursor(
                            PointerFactory.getPointerComponent(pointerIcon, patterns[0]),
                            EntityIconFactory.getEntityIconComponent(entityIcon, patterns[1])
                        );
                        return new Pair<EntityType<?>, Supplier<? extends TRenderComponent>>(entityType, supplier);
                    }
                }

                supplier = () -> new TRelativeCursor(
                    PointerFactory.getDefault(pointerIcon),
                    EntityIconFactory.getDefault(entityIcon)
                );
                return new Pair<EntityType<?>, Supplier<? extends TRenderComponent>>(entityType, supplier);
            })
            .filter(p -> p != OF_EMPTY)
            .collect(Collectors.toSet());
    }
}
