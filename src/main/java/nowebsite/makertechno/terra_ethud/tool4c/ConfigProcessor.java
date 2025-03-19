package nowebsite.makertechno.terra_ethud.tool4c;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import nowebsite.makertechno.terra_ethud.define.TEntityIcon;
import nowebsite.makertechno.terra_ethud.define.TEnumUtils;
import nowebsite.makertechno.terra_ethud.define.TPointer;
import nowebsite.makertechno.terra_ethud.define.TCursor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class ConfigProcessor {
    public static boolean isValidEntityBindCursor(Object o) {
        if (!(o instanceof String)) return false;
        String[] split = ((String) o).split("\\|");
        if (split.length != 3) return false;
        Optional<EntityType<?>> optional = EntityType.byString(split[0]);
        if (optional.isEmpty()) return false;
        if (TEnumUtils.getByName(split[1], TPointer.values()) == null) return false;
        return TEnumUtils.getByName(split[2], TEntityIcon.values()) != null;
    }
    @NotNull
    public static Set<Pair<EntityType<?>, TCursor>> collectEntityBindCursor(@NotNull List<? extends String> list) {
        return list.stream().map(s -> {
                String[] split = s.split("\\|");
                Optional<EntityType<?>> optional = EntityType.byString(split[0]);
                TPointer pointer = TEnumUtils.getByName(split[1], TPointer.values());
                TEntityIcon icon = TEnumUtils.getByName(split[2], TEntityIcon.values());
                return new Pair<>(optional, new TCursor(pointer, icon));
            })
            .filter(p -> p.getFirst().isPresent() && p.getSecond().pointer() != null && p.getSecond().icon() != null)
            .map(p -> new Pair<EntityType<?>, TCursor>(p.getFirst().get(), p.getSecond()))
            .collect(Collectors.toSet());
    }
}
