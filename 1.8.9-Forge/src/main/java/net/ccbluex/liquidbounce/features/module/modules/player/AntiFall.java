package net.ccbluex.liquidbounce.features.module.modules.player;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "AntiFall", description = "Antifall.", category = ModuleCategory.PLAYER)
public class AntiFall extends Module {
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Hypixel", "Hypixel2",}, "Hypixel");
    private final BoolValue voidcheck = new BoolValue("Void", true);
    private final FloatValue distance = new FloatValue("FallDistance", 5F, 1F, 20F);
    MSTimer timer = new MSTimer();
    private boolean saveMe;
    boolean AAAA = false;
    double mario = 0;

    @EventTarget
    public void onMove(MoveEvent e) {
        final String mode = modeValue.get();
        if (LiquidBounce.moduleManager.getModule(Fly.class).getState())
            return;
        switch (mode.toLowerCase()) {
            case "hypixel":
                float dist = distance.get();
                if (mc.thePlayer.fallDistance > dist && !LiquidBounce.moduleManager.getModule(Fly.class).getState()) {
                    if (!voidcheck.get() || !isBlockUnder()) {
                        if (!saveMe) {
                            saveMe = true;
                            timer.reset();
                        }
                        mc.thePlayer.fallDistance = 0;
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 15, mc.thePlayer.posZ, false));
                    }
                }
                break;
            case "AACAP":
                if (mc.thePlayer.fallDistance >= distance.get() && mc.thePlayer.motionY <= 0 && (AAAA == false || mc.thePlayer.posY <= mario) && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, 0, 0).expand(0, 0, 0)).isEmpty() && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, -10002.25, 0).expand(0, -10003.75, 0)).isEmpty()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 11.4514, mc.thePlayer.posZ, false));
                    mario = mc.thePlayer.posY;
                    AAAA = true;
                    if (mc.thePlayer.onGround) {
                        mario = 0;
                        AAAA = false;
                    }
                }
                break;
        }
    }
    private boolean isBlockUnder () {
        if (mc.thePlayer.posY < 0)
            return false;
        for (int off = 0; off < (int) mc.thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -off, 0);
            if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String getTag () {
        return modeValue.get();
    }
    public static boolean isOverVoid() {
        boolean isOverVoid = true;
        BlockPos block = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        for (double i = mc.thePlayer.posY + 1; i > 0; i -= 0.5) {
            if (isOverVoid) {
                try {
                    if (mc.theWorld.getBlockState(block).getBlock() != Blocks.air) {
                        isOverVoid = false;
                        break;
                    }
                } catch (Exception ignored) { }
            }
            block = block.add(0, -1, 0);
        }

        for (double i = 0; i < 10; i += 0.1) {
            if (MovementUtils.isOnGround(i) && isOverVoid) {
                isOverVoid = false;
                break;
            }
        }

        return isOverVoid;
    }

}
