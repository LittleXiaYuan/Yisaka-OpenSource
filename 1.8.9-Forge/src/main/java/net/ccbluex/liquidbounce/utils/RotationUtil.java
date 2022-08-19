package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SideOnly(Side.CLIENT)
public final class RotationUtil extends MinecraftInstance implements Listenable
{
    private static Random random;
    private static int keepLength;
    public static Rotation targetRotation;
    public static Rotation serverRotation;
    public static boolean keepCurrentRotation;
    private static double x;
    private static double y;
    private static double z;

    public static float[] getRotations(final EntityLivingBase ent) {
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f;
        return getRotationFromPosition(x, z, y);
    }
    public static VecRotation searchCenterforTargetStrafe(final AxisAlignedBB bb) {

        VecRotation vecRotation = null;

        final Vec3 vec3 = new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5,
                0, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
        final Rotation rotation = toRotation(vec3, false);

        final VecRotation currentVec = new VecRotation(vec3, rotation);

        if (vecRotation == null)
            vecRotation = currentVec;

        return vecRotation;
    }

    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - mc.thePlayer.posX;
        final double zDiff = z - mc.thePlayer.posZ;
        final double yDiff = y - mc.thePlayer.posY - 1.2;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }

    public static float getRotations(final EntityLivingBase ent, final float otPos) {
        final double x = ent.posX + Math.sin(otPos);
        final double z = ent.posZ + Math.cos(otPos);
        return getRotationFromPosition(x, z);
    }

    public static float getRotationFromPosition(final double x, final double z) {
        final double xDiff = x - mc.thePlayer.posX;
        final double zDiff = z - mc.thePlayer.posZ;
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        return yaw;
    }
    public static float[] getRotationFromPotion(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2D;
        double dist = (double) MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D));
        return new float[]{yaw,pitch};
    }
    public static VecRotation faceBlock(final BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        VecRotation vecRotation = null;
        for (double xSearch = 0.1; xSearch < 0.9; xSearch += 0.1) {
            for (double ySearch = 0.1; ySearch < 0.9; ySearch += 0.1) {
                for (double zSearch = 0.1; zSearch < 0.9; zSearch += 0.1) {
                    final Vec3 eyesPos = new Vec3(RotationUtils.mc.thePlayer.posX, RotationUtils.mc.thePlayer.getEntityBoundingBox().minY + RotationUtils.mc.thePlayer.getEyeHeight(), RotationUtils.mc.thePlayer.posZ);
                    final Vec3 posVec = new Vec3((Vec3i)blockPos).addVector(xSearch, ySearch, zSearch);
                    final double dist = eyesPos.distanceTo(posVec);
                    final double diffX = posVec.xCoord - eyesPos.xCoord;
                    final double diffY = posVec.yCoord - eyesPos.yCoord;
                    final double diffZ = posVec.zCoord - eyesPos.zCoord;
                    final double diffXZ = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
                    final Rotation rotation = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
                    final Vec3 rotationVector = getVectorForRotation(rotation);
                    final Vec3 vector = eyesPos.addVector(rotationVector.xCoord * dist, rotationVector.yCoord * dist, rotationVector.zCoord * dist);
                    final MovingObjectPosition obj = RotationUtils.mc.theWorld.rayTraceBlocks(eyesPos, vector, false, false, true);
                    if (obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                        final VecRotation currentVec = new VecRotation(posVec, rotation);
                        if (vecRotation == null || getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation())) {
                            vecRotation = currentVec;
                        }
                    }
                }
            }
        }
        return vecRotation;
    }

    public static void faceBow(final Entity target, final boolean silent, final boolean predict, final float predictSize) {
        final EntityPlayerSP player = RotationUtils.mc.thePlayer;
        final double posX = target.posX + (predict ? ((target.posX - target.prevPosX) * predictSize) : 0.0) - (player.posX + (predict ? (player.posX - player.prevPosX) : 0.0));
        final double posY = target.getEntityBoundingBox().minY + (predict ? ((target.getEntityBoundingBox().minY - target.prevPosY) * predictSize) : 0.0) + target.getEyeHeight() - 0.15 - (player.getEntityBoundingBox().minY + (predict ? (player.posY - player.prevPosY) : 0.0)) - player.getEyeHeight();
        final double posZ = target.posZ + (predict ? ((target.posZ - target.prevPosZ) * predictSize) : 0.0) - (player.posZ + (predict ? (player.posZ - player.prevPosZ) : 0.0));
        final double posSqrt = Math.sqrt(posX * posX + posZ * posZ);
        final BowAimbot BE = (BowAimbot) LiquidBounce.moduleManager.getModule(BowAimbot.class);
        float velocity = (BE.getState() && BE.getFastBow().get())? 1.0f : (player.getItemInUseDuration() / 20.0f);
        velocity = (velocity * velocity + velocity * 2.0f) / 3.0f;
        if (velocity > 1.0f) {
            velocity = 1.0f;
        }
        final Rotation rotation = new Rotation((float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(velocity * velocity * velocity * velocity - 0.006000000052154064 * (0.006000000052154064 * (posSqrt * posSqrt) + 2.0 * posY * (velocity * velocity)))) / (0.006000000052154064 * posSqrt)))));
        if (silent) {
            setTargetRotation(rotation);
        }
        else {
            limitAngleChange(new Rotation(player.rotationYaw, player.rotationPitch), rotation, (float)(10 + new Random().nextInt(6))).toPlayer((EntityPlayer)RotationUtils.mc.thePlayer);
        }
    }
    public static Rotation getRotationsForAura(Entity entity, float maxRange) {
        double diffY;
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        RotationHelper BestPos = new RotationHelper(entity.posX, entity.posY, entity.posZ);
        RotationHelper myEyePos = new RotationHelper(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        for (diffY = entity.getEntityBoundingBox().minY + 0.7; diffY < entity.getEntityBoundingBox().maxY - 0.1; diffY += 0.1) {
            if (myEyePos.distanceTo(new RotationHelper(entity.posX, diffY, entity.posZ)) >= myEyePos.distanceTo(BestPos)) continue;
            BestPos = new RotationHelper(entity.posX, diffY, entity.posZ);
        }
        if (myEyePos.distanceTo(BestPos) >= maxRange) {
            return null;
        }
        diffY = BestPos.getY() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new Rotation(yaw,pitch);
    }
    public static Rotation getCustomRotation(Entity target,float customRotationPitch) {
        double xDiff = target.posX - mc.thePlayer.posX;
        double yDiff = target.posY - mc.thePlayer.posY - customRotationPitch;
        double zDiff = target.posZ - mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)((- Math.atan2(yDiff, dist)) * 180.0 / 3.141592653589793);
        float[] array = new float[2];
        float rotationYaw = mc.thePlayer.rotationYaw;
        array[0] = rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw);
        float rotationPitch = mc.thePlayer.rotationPitch;
        array[1] = rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch);
        return new Rotation(array[0], array[1]);
    }
    public static Rotation getBasicRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + (elb.getEyeHeight() - 0.4) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new Rotation(yaw, pitch);
    }
    public static Rotation toRotation(final Vec3 vec, final boolean predict) {
        final Vec3 eyesPos = new Vec3(RotationUtils.mc.thePlayer.posX, RotationUtils.mc.thePlayer.getEntityBoundingBox().minY + RotationUtils.mc.thePlayer.getEyeHeight(), RotationUtils.mc.thePlayer.posZ);
        if (predict) {
            eyesPos.addVector(RotationUtils.mc.thePlayer.motionX, RotationUtils.mc.thePlayer.motionY, RotationUtils.mc.thePlayer.motionZ);
        }
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        return new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))));
    }

    public static Vec3 getCenter(final AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }

    public static VecRotation searchCenter(final AxisAlignedBB bb, final boolean outborder, final boolean random,
                                           final boolean predict, final boolean throughWalls, final float distance) {
        if(outborder) {
            final Vec3 vec3 = new Vec3(bb.minX + (bb.maxX - bb.minX) * (x * 0.3 + 1.0), bb.minY + (bb.maxY - bb.minY) * (y * 0.3 + 1.0), bb.minZ + (bb.maxZ - bb.minZ) * (z * 0.3 + 1.0));
            return new VecRotation(vec3, toRotation(vec3, predict));
        }

        final Vec3 randomVec = new Vec3(bb.minX + (bb.maxX - bb.minX) * x * 0.8, bb.minY + (bb.maxY - bb.minY) * y * 0.8, bb.minZ + (bb.maxZ - bb.minZ) * z * 0.8);
        final Rotation randomRotation = toRotation(randomVec, predict);

        final Vec3 eyes = mc.thePlayer.getPositionEyes(1F);

        VecRotation vecRotation = null;

        for(double xSearch = 0.15D; xSearch < 0.85D; xSearch += 0.1D) {
            for (double ySearch = 0.15D; ySearch < 1D; ySearch += 0.1D) {
                for (double zSearch = 0.15D; zSearch < 0.85D; zSearch += 0.1D) {
                    final Vec3 vec3 = new Vec3(bb.minX + (bb.maxX - bb.minX) * xSearch,
                            bb.minY + (bb.maxY - bb.minY) * ySearch, bb.minZ + (bb.maxZ - bb.minZ) * zSearch);
                    final Rotation rotation = toRotation(vec3, predict);
                    final double vecDist = eyes.distanceTo(vec3);

                    if (vecDist > distance)
                        continue;

                    if(throughWalls || isVisible(vec3)) {
                        final VecRotation currentVec = new VecRotation(vec3, rotation);

                        if (vecRotation == null || (random ? getRotationDifference(currentVec.getRotation(), randomRotation) < getRotationDifference(vecRotation.getRotation(), randomRotation) : getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation())))
                            vecRotation = currentVec;
                    }
                }
            }
        }

        return vecRotation;
    }
    // LnkuidBance
    public static VecRotation searchCenter(final AxisAlignedBB bb,final boolean throughWalls, final float distance) {
        double ySearch;
        boolean entityonl = false;
        boolean entityonr = false;
        VecRotation vecRotation = null;
        if ((bb.maxX - bb.minX) < (bb.maxZ - bb.minZ)) {
            entityonr = true;
        }if ((bb.maxX - bb.minX) > (bb.maxZ - bb.minZ)) {
            entityonl = true;
        }if((bb.maxX - bb.minX) == (bb.maxZ - bb.minZ)) {
            entityonr = false;
            entityonl = false;
        }
        double  x = bb.minX + (bb.maxX - bb.minX) * (entityonl ? 0.25: 0.5);
        double  z = bb.minZ + (bb.maxZ - bb.minZ) * (entityonr ? 0.25: 0.5);
        for(double xSearch = 0.1D; xSearch < 0.9D; xSearch += 0.15D) {
            for (ySearch = 0.1D; ySearch < 0.9D; ySearch += 0.15D) {
                for (double zSearch = 0.1D; zSearch < 0.9D; zSearch += 0.15D) {
                    double pitch = bb.minY + (bb.maxY - bb.minY) * ySearch;
                    Vec3 vec3 = null;
                    vec3 = new Vec3(x, pitch, z);
                    final Rotation rotation = toRotation(vec3, false);
                    final Vec3 eyes = mc.thePlayer.getPositionEyes(1F);

                    final double vecDist = eyes.distanceTo(vec3);
                    if (vecDist > distance)
                        continue;

                    if (vecDist <= (double)distance && (throughWalls || isVisible(vec3))) {
                        final VecRotation currentVec = new VecRotation(vec3, rotation);
                        if (vecRotation == null || (getRotationDifference(currentVec.getRotation()) < getRotationDifference(vecRotation.getRotation()))) {
                            vecRotation = currentVec;
                        }
                    }
                }
            }
        }
        return vecRotation;
    }
    public static double getRotationDifference(final Entity entity) {
        final Rotation rotation = toRotation(getCenter(entity.getEntityBoundingBox()), true);
        return getRotationDifference(rotation, new Rotation(RotationUtils.mc.thePlayer.rotationYaw, RotationUtils.mc.thePlayer.rotationPitch));
    }

    public static double getRotationDifference(final Rotation rotation) {
        return (RotationUtils.serverRotation == null) ? 0.0 : getRotationDifference(rotation, RotationUtils.serverRotation);
    }

    public static double getRotationDifference(final Rotation a, final Rotation b) {
        return Math.hypot(getAngleDifference(a.getYaw(), b.getYaw()), a.getPitch() - b.getPitch());
    }

    @NotNull
    public static Rotation limitAngleChange(final Rotation currentRotation, final Rotation targetRotation, final float turnSpeed) {
        final float yawDifference = getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
        final float pitchDifference = getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());
        return new Rotation(currentRotation.getYaw() + ((yawDifference > turnSpeed) ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + ((pitchDifference > turnSpeed) ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
    }

    private static float getAngleDifference(final float a, final float b) {
        return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
    }

    public static Vec3 getVectorForRotation(final Rotation rotation) {
        final float yawCos = MathHelper.cos(-rotation.getYaw() * 0.017453292f - 3.1415927f);
        final float yawSin = MathHelper.sin(-rotation.getYaw() * 0.017453292f - 3.1415927f);
        final float pitchCos = -MathHelper.cos(-rotation.getPitch() * 0.017453292f);
        final float pitchSin = MathHelper.sin(-rotation.getPitch() * 0.017453292f);
        return new Vec3((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
    }

    public static boolean isFaced(final Entity targetEntity, final double blockReachDistance) {
        return RaycastUtils.raycastEntity(blockReachDistance, entity -> entity == targetEntity) != null;
    }

    public static boolean isVisible(final Vec3 vec3) {
        final Vec3 eyesPos = new Vec3(RotationUtils.mc.thePlayer.posX, RotationUtils.mc.thePlayer.getEntityBoundingBox().minY + RotationUtils.mc.thePlayer.getEyeHeight(), RotationUtils.mc.thePlayer.posZ);
        return RotationUtils.mc.theWorld.rayTraceBlocks(eyesPos, vec3) == null;
    }

    @EventTarget
    public void onTick(final TickEvent event) {
        if (RotationUtils.targetRotation != null) {
            --RotationUtils.keepLength;
            if (RotationUtils.keepLength <= 0) {
                reset();
            }
        }
        if (RotationUtils.random.nextGaussian() > 0.8) {
            RotationUtils.x = Math.random();
        }
        if (RotationUtils.random.nextGaussian() > 0.8) {
            RotationUtils.y = Math.random();
        }
        if (RotationUtils.random.nextGaussian() > 0.8) {
            RotationUtils.z = Math.random();
        }
    }

    @EventTarget
    public void onPacket(final PacketEvent event) {
        final Packet<?> packet = (Packet<?>)event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
            if (RotationUtils.targetRotation != null && !RotationUtils.keepCurrentRotation && (RotationUtils.targetRotation.getYaw() != RotationUtils.serverRotation.getYaw() || RotationUtils.targetRotation.getPitch() != RotationUtils.serverRotation.getPitch())) {
                packetPlayer.yaw = RotationUtils.targetRotation.getYaw();
                packetPlayer.pitch = RotationUtils.targetRotation.getPitch();
                packetPlayer.rotating = true;
            }
            if (packetPlayer.rotating) {
                RotationUtils.serverRotation = new Rotation(packetPlayer.yaw, packetPlayer.pitch);
            }
        }
    }

    public static void setTargetRotation(final Rotation rotation) {
        setTargetRotation(rotation, 0);
    }

    public static void setTargetRotation(final Rotation rotation, final int keepLength) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        rotation.fixedSensitivity(RotationUtils.mc.gameSettings.mouseSensitivity);
        RotationUtils.targetRotation = rotation;
        RotationUtils.keepLength = keepLength;
    }

    public static void reset() {
        RotationUtils.keepLength = 0;
        RotationUtils.targetRotation = null;
    }

    public boolean handleEvents() {
        return true;
    }

    static {
        RotationUtils.random = new Random();
        RotationUtils.serverRotation = new Rotation(0.0f, 0.0f);
        RotationUtils.keepCurrentRotation = false;
        RotationUtils.x = RotationUtils.random.nextDouble();
        RotationUtils.y = RotationUtils.random.nextDouble();
        RotationUtils.z = RotationUtils.random.nextDouble();
    }
    public static Rotation getRotationsNonLivingEntity(Entity entity) {
        return RotationUtil.getRotations(entity.posX, entity.posY + (entity.getEntityBoundingBox().maxY-entity.getEntityBoundingBox().minY)*0.5, entity.posZ);
    }
    public static Rotation getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = RotationUtils.mc.thePlayer;
        double x = posX - player.posX;
        double y = posY - (player.posY + (double)player.getEyeHeight());
        double z = posZ - player.posZ;
        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
        return new Rotation(yaw,pitch);
    }
}
