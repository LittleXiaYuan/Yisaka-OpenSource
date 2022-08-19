package Core.C08Packet.modules.Disabler;
import net.ccbluex.liquidbounce.event.*;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import us.myles.viaversion.libs.javassist.bytecode.annotation.BooleanMemberValue;

import javax.jws.Oneway;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ModuleInfo(
        name = "Disabler",
        description = "Disabler",
        category = ModuleCategory.MOVEMENT
)
public final class Disabler extends Module {
    private final Queue<Short> queueID = new ConcurrentLinkedQueue<>();
    private final ListValue modeValue = new ListValue("Mode", new String[]{"AAC5Helper","AAC3Exploit","AAC5MoveDisabled","Hypixel","Mineplex","AAC1.9.10"}, "AAC5Helper");

    private final IntegerValue AAC3C0F1 = new IntegerValue("AAC3C0F+",0,0,999);
    private final IntegerValue AAC3C0F2 = new IntegerValue("AAC3C0F-",0,0,999);
    private final FloatValue AAC3Y = new FloatValue("AAC3Y",0.99F,0.0F,0.99F);
    private final FloatValue AAC3X = new FloatValue("AAC3X",0.99F,0.0F,0.99F);
    private final FloatValue AAC3Z = new FloatValue("AAC3Z",0.99F,0.0F,0.99F);
    private final BoolValue AAC3C03 = new BoolValue("AAC3C03",true);
    private final BoolValue AAC3onGround = new BoolValue("AAC3onGround",false);
    private final BoolValue AAC3rotating = new BoolValue("AAC3rotating",false);
    //private Object Packet;

    @EventTarget
    public void onWorld(WorldEvent event) {
        if(event.getWorldClient() != null && !queueID.isEmpty())
            return;
        queueID.clear();
    }
    @EventTarget
    public void onPacket(PacketEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;
        final Packet<?> packet = event.getPacket();
        final short uid = -1;
        if (modeValue.get().equals("Mineplex")) {
            if (packet instanceof C00PacketKeepAlive && !(((C00PacketKeepAlive) packet).getKey() >= 1000)) {
                event.cancelEvent();
                int key = ((C00PacketKeepAlive) packet).getKey();
                key -= RandomUtils.nextInt(1000, 2147483647);
                mc.getNetHandler().addToSendQueue(new C00PacketKeepAlive(key));
            }
        }
        if (modeValue.get().equals("AAC1.9.10")) {
            if (packet instanceof C03PacketPlayer) {
                mc.getNetHandler().addToSendQueue(new C0CPacketInput());
                ((C03PacketPlayer) packet).y += 7.0E-9;
            }
        }
        if (modeValue.get().equals("Hypixel")) {
            if (packet instanceof S32PacketConfirmTransaction) {
                final S32PacketConfirmTransaction packetConfirmTransaction = (S32PacketConfirmTransaction) packet;
                if (packetConfirmTransaction.getActionNumber() < 0 && packetConfirmTransaction.getWindowId() == 0) {
                    event.cancelEvent();
                    mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(mc.thePlayer.inventoryContainer.windowId, queueID.isEmpty() ? uid : queueID.poll(), false));
                }
            }
            if (packet instanceof C00PacketKeepAlive) {
                event.cancelEvent();
            }
            if (packet instanceof S00PacketKeepAlive) {
                event.cancelEvent();
                mc.getNetHandler().addToSendQueue(new C00PacketKeepAlive(RandomUtils.nextInt(-114514, 1919810)));
            }
            if (packet instanceof C0FPacketConfirmTransaction) {
                final C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction) packet;
                if (packetConfirmTransaction.getWindowId() < 0 && packetConfirmTransaction.getWindowId() == 0) {
                    event.cancelEvent();
                    mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(0, queueID.isEmpty() ? uid : queueID.poll(), false));
                    queueID.offer(packetConfirmTransaction.getUid());
                }
            }
            if (modeValue.get().equals("AAC5Helper")) {
                if (packet instanceof C08PacketPlayerBlockPlacement) {
                    mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction());
                    mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction());
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging());
                }

                    if (packet instanceof C0FPacketConfirmTransaction) {
                        final C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction) packet;
                        if (packetConfirmTransaction.getWindowId() < 10 && packetConfirmTransaction.getWindowId() == 20) {
                            event.cancelEvent();
                            mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(0, queueID.isEmpty() ? uid : queueID.poll(), false));
                            queueID.offer(packetConfirmTransaction.getUid());
                        }

                            if (packet instanceof C02PacketUseEntity) {
                                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity());

                            }
                        }

                        if (modeValue.get().equals("AAC3Exploit")) {
                            if (packet instanceof C02PacketUseEntity) {
                                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity());
                            }
                            if (AAC3C03.get() || packet instanceof C03PacketPlayer) {
                                mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging());
                                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction());
                                ((C03PacketPlayer) packet).y += AAC3Y.get();
                                ((C03PacketPlayer) packet).x += AAC3X.get();
                                ((C03PacketPlayer) packet).z += AAC3Z.get();
                                ((C03PacketPlayer) packet).onGround = AAC3onGround.get();
                                ((C03PacketPlayer) packet).rotating = AAC3rotating.get();
                            }
                            if (packet instanceof C0FPacketConfirmTransaction) {
                                final C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction) packet;
                                if (packetConfirmTransaction.getWindowId() < AAC3C0F1.get() && packetConfirmTransaction.getWindowId() == AAC3C0F2.get()) {
                                    event.cancelEvent();
                                    mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(0, queueID.isEmpty() ? uid : queueID.poll(), false));
                                    queueID.offer(packetConfirmTransaction.getUid());
                                }
                            }
                            if (modeValue.equals("AAC5MoveDisabled")) {
                                if (packet instanceof C0BPacketEntityAction) {
                                    event.notify();
                                    event.cancelEvent();
                                }
                                if (packet instanceof C00Handshake) {
                                    event.cancelEvent();
                                }
                                if (packet instanceof  C00PacketKeepAlive) {
                                    event.cancelEvent();
                                }
                                        if (packet instanceof C0CPacketInput){
                                            ((C0CPacketInput) packet).getStrafeSpeed();
                                        }
                                    }
                                }
                            }
                        }
                    }
    @Override
    public String getTag() {
        return modeValue.get();
    }
}