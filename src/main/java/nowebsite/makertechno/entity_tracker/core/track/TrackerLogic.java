package nowebsite.makertechno.entity_tracker.core.track;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.entity.PartEntity;
import nowebsite.makertechno.entity_tracker.client.gui.TGui;
import nowebsite.makertechno.entity_tracker.client.gui.components.TRenderComponent;
import nowebsite.makertechno.entity_tracker.client.gui.components.TrackerEntityState;
import nowebsite.makertechno.entity_tracker.core.config.TConfig;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TrackerLogic {
    private static final Map<UUID, TrackerEntityState> RENDERING = new HashMap<>();
    public static final AABB PICK_RANGE = new AABB(
            - 64.0D,
            - 48.0D,
            - 64.0D,
            + 64.0D,
            + 64.0D,
            + 64.0D
    );
    private static int count = 0;

    public static Map<UUID, TrackerEntityState> getRENDERING() {
        return RENDERING;
    }

    public static void trigger() {
        if (++count >= TConfig.interval) {
            count = 0;
            process();
        }
    }

    public static void process() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = TGui.getCameraPlayer(minecraft);

        if (minecraft.level == null || player == null) return;

        List<Entity> nearestEntities = minecraft.level.getEntities(null, PICK_RANGE.move(player.position())).stream()
                .filter(entity -> !(entity instanceof PartEntity<?>))
                .filter(TrackerLogic::isTargetEntity)
                .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(player)))
                .limit(TConfig.maxTrackingQuantity)
                .toList();

        nearestEntities.stream()
                .filter(entity -> !processExisting(entity))
                .forEach(TrackerLogic::tryCreateNew);

        Set<UUID> retained = nearestEntities.stream()
                .map(Entity::getUUID)
                .collect(Collectors.toSet());

        RENDERING.keySet().removeIf(key -> !retained.contains(key));
    }

    private static boolean processExisting(@NotNull Entity entity) {
        TrackerEntityState state = RENDERING.get(entity.getUUID());
        if (state != null) {
            state.updatePos(entity.getRopeHoldPosition(0));
            return true;
        }
        return false;
    }

    private static void tryCreateNew(Entity entity) {
        for (Pair<EntityType<?>, Supplier<? extends TRenderComponent>> pair : getAllEntityGroups()) {
            if (isTargetType(pair.getFirst(), entity)) {
                TrackerEntityState state = new TrackerEntityState(pair.getSecond().get());
                state.updatePos(entity.getRopeHoldPosition(0));
                RENDERING.put(entity.getUUID(), state);
            }
        }
    }

    private static @NotNull List<Pair<EntityType<?>, Supplier<? extends TRenderComponent>>> getAllEntityGroups() {
        List<Pair<EntityType<?>, Supplier<? extends TRenderComponent>>> all = new ArrayList<>();
        if (TConfig.centerRelativeAvailable) all.addAll(TConfig.CRCursorWithEntities);
        if (TConfig.trackFullAvailable) all.addAll(TConfig.DTCursorWithEntities);
        //all.addAll(else);
        return all;
    }

    private static boolean isTargetEntity(Entity entity) {
        return getAllEntityGroups().stream()
                .anyMatch(pair -> isTargetType(pair.getFirst(), entity));
    }

    private static <T extends Entity> boolean isTargetType(@NotNull EntityTypeTest<Entity, T> entityTypeTest, Entity entity) {
        return entityTypeTest.tryCast(entity) != null;
    }
}