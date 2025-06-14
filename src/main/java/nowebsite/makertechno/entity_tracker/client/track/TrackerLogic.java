package nowebsite.makertechno.entity_tracker.client.track;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import nowebsite.makertechno.entity_tracker.client.gui.TGui;
import nowebsite.makertechno.entity_tracker.config.TConfig;
import nowebsite.makertechno.entity_tracker.define.TCursor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackerLogic {
    private static final Set<Pair<? extends Entity, TCursor>> ENTITIES = new HashSet<>();
    private static final Set<Pair<Vec3, TCursor>> ENTITIES_POSITIONS = new HashSet<>();
    private static int count = 0;
    public static void addEntities(Set<? extends Pair<? extends Entity, TCursor>> entities) {
        synchronized (ENTITIES) {
            ENTITIES.addAll(entities);
        }
    }
    public static void clearEntities() {
        synchronized (ENTITIES) {
            ENTITIES.clear();
        }
    }
    public static void setEntitiesPositions(Set<Pair<Vec3, TCursor>> positions) {
        synchronized (ENTITIES_POSITIONS) {
            ENTITIES_POSITIONS.clear();
            ENTITIES_POSITIONS.addAll(positions);
        }
    }
    public static Set<Pair<Vec3, TCursor>> getEntitiesPositions() {
        return ENTITIES_POSITIONS;
    }

    public static void trigger() {
        if (count >= 40) {
            count = 0;
            triggerEntities();
        }
        else {
            count++;
            triggerPositions();
        }
    }
    public static void triggerEntities() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = TGui.getCameraPlayer(minecraft);
        if (minecraft.level != null &&  player != null) {
            clearEntities();
            for (Pair<EntityType<?>, TCursor> pair: TConfig.pointerWithEntities) {
                addEntities(
                    minecraft.level.getEntities(
                        pair.getFirst(),
                            new AABB(
                                player.getX() - 64.0D,
                                player.getY() - 48.0D,
                                player.getZ() - 64.0D,
                                player.getX() + 64.0D,
                                player.getY() + 64.0D,
                                player.getZ() + 64.0D
                            ),
                            entity -> true
                        )
                        .stream()
                        .map(entity -> new Pair<>(entity, pair.getSecond()))
                        .filter(pair1 -> !(pair1.getFirst() instanceof PartEntity))
                        .collect(Collectors.toSet())
                );
            }
        }
    }
    public static void triggerPositions() {
        setEntitiesPositions(
            ENTITIES.stream()
                .map(pair -> new Pair<>(pair.getFirst().getRopeHoldPosition(0), pair.getSecond()))
                .collect(Collectors.toSet())
        );
    }
}