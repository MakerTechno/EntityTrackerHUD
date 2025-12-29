/*
package nowebsite.makertechno.entity_tracker.core.track;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.entity_tracker.client.gui.components.TRelativeCursor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StableTracker {
    private static final Set<Pair<UUID, TRelativeCursor>> UUID_BOUND = new HashSet<>();
    private static final Set<Pair<Vec3, TRelativeCursor>> POSITIONS = new HashSet<>();
    private final Map

    public void updateEntities(Set<Pair<Entity, TRelativeCursor>> currentEntities, long tick) {
        Set<UUID> seenThisTick = new HashSet<>();

        for (Pair<Entity, TRelativeCursor> pair : currentEntities) {
            Entity entity = pair.getFirst();
            UUID id = entity.getUUID();
            seenThisTick.add(id);

            trackedEntities.compute(id, (uuid, state) -> {
                if (state == null) {
                    return new TrackedEntityState(id, entity.position(), pair.getSecond(), tick);
                } else {
                    state.update(entity.position(), tick);
                    return state;
                }
            });
        }

        // 清理离开的实体（可选）
        trackedEntities.entrySet().removeIf(entry -> tick - entry.getValue().lastSeenTick > 20);
    }

    public Collection<TrackedEntityState> getTrackedStates() {
        return trackedEntities.values();
    }
}
*/
