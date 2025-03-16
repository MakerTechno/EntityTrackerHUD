package nowebsite.makertechno.terra_ethud.algorithm;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MercatorProjectMap {
    /**
     * 墨卡托投影，将单位向量投影到平面
     *
     * @param vec 任意链接向量 (Vec3)
     * @return 平面坐标 (u, v)
     */
    @Contract("_ -> new")
    public static @NotNull Vec2 mercatorProject(@NotNull Vec3 vec) {
        Vec3 vTarget = vec.normalize();
        // 墨卡托投影公式
        double u = Math.atan2(vTarget.z, vTarget.x); // 水平坐标(经度)
        double v = Math.log(Math.tan(Math.PI / 4 + Math.asin(vTarget.y) / 2)); // 垂直坐标(纬度)
        return new Vec2((float) u, (float) v);
    }

    public static @NotNull Vec3 eyeAlignedProjection(@NotNull Vec3 relativeVec, @NotNull Vec3 playerEyeSight) {
        // 计算玩家视角的水平角度
        double playerYaw = Math.atan2(playerEyeSight.z, playerEyeSight.x);
        // 将相对向量旋转到玩家视角对齐的坐标系
        double rotatedX = relativeVec.x * Math.cos(-playerYaw) - relativeVec.z * Math.sin(-playerYaw);
        double rotatedZ = relativeVec.x * Math.sin(-playerYaw) + relativeVec.z * Math.cos(-playerYaw);
        return new Vec3(rotatedX, relativeVec.y, rotatedZ);
    }


    // 计算玩家和生物位置的投影并计算角度
    public static double @NotNull [] calculateAngle(Vec3 playerPos, Vec3 playerEyeSight, @NotNull Vec3 entityPos) {
        // 计算玩家位置的投影
        Vec2 base = mercatorProject(eyeAlignedProjection(playerEyeSight, playerEyeSight));
        // 计算生物位置的投影
        Vec2 target = mercatorProject(eyeAlignedProjection(entityPos.subtract(playerPos), playerEyeSight));

        // 计算连线角度
        Vec2 result = target.add(base.negated());

        // 计算距离
        double distance = Math.sqrt(Math.pow(target.x - base.x, 2) + Math.pow(target.y - base.y, 2));
        return new double[]{Math.atan2(result.y, result.x) + Math.PI, distance};
    }
}
