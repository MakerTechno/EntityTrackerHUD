package nowebsite.makertechno.entity_tracker.client.track;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import nowebsite.makertechno.entity_tracker.client.gui.TGui;
import nowebsite.makertechno.entity_tracker.config.TConfig;
import com.mojang.datafixers.util.Pair;
import nowebsite.makertechno.entity_tracker.define.TCursor;

import java.util.ArrayList;
import java.util.List;

public class TrackerLogic {
    private static final List<Pair<Vec3, TCursor>> ENTITIES_POSITIONS = new ArrayList<>();
    //private static int count = 0;
    public static void addEntitiesPositions(List<Pair<Vec3, TCursor>> positions) {
        synchronized (ENTITIES_POSITIONS) {
            ENTITIES_POSITIONS.addAll(positions);
        }
    }
    public static void clearEntitiesPositions() {
        synchronized (ENTITIES_POSITIONS) {
            ENTITIES_POSITIONS.clear();
        }
    }
    public static List<Pair<Vec3, TCursor>> getEntitiesPositions() {
        return ENTITIES_POSITIONS;
    }

    public static void trigger() {
        /*if (count >=4) count = 0;
        else {
            count++;
            return;
        }*/
        Minecraft minecraft = Minecraft.getInstance();
        Player player = TGui.getCameraPlayer(minecraft);
        if (minecraft.level != null &&  player != null) {
            clearEntitiesPositions();
            for (Pair<EntityType<?>, TCursor> pair: TConfig.pointerWithEntities) {
                List<Pair<Vec3, TCursor>> positions = minecraft.level.getEntities(
                    pair.getFirst(),
                    new AABB(
                        player.getX() - 96.0D,
                        player.getY() - 96.0D,
                        player.getZ() - 96.0D,
                        player.getX() + 96.0D,
                        player.getY() + 96.0D,
                        player.getZ() + 96.0D
                    ),
                    entity -> true
                )
                    .stream()
                    .map(entity -> new Pair<>(entity, pair.getSecond()))
                    .filter(pair1 -> !(pair1.getFirst() instanceof PartEntity))
                    .map(pair1 -> new Pair<>(pair1.getFirst().position(), pair1.getSecond()))
                    .toList();
                addEntitiesPositions(positions);
            }
        }
    }
}