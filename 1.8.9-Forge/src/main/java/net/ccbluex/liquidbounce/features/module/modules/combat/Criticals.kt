/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.AttackEvent
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.entity.EntityLivingBase
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import net.minecraft.network.play.client.C0BPacketEntityAction

@ModuleInfo(name = "Criticals", description = "Automatically deals critical hits.", category = ModuleCategory.COMBAT)
class Criticals : Module() {

    val modeValue = ListValue("Mode", arrayOf("Packet", "NcpPacket", "NoGround", "infinity", "Hop", "TPHop", "Jump", "LowJump", "FakeSense", "NewPacket", "Bakemono", "AAC3", "AAC4.3.12", "MeiTanTei"), "NewPacket")
    val delayValue = IntegerValue("Delay", 0, 0, 500)
    private val hurtTimeValue = IntegerValue("HurtTime", 10, 0, 10)

    val msTimer = MSTimer()

    override fun onEnable() {
        if (modeValue.get().equals("NoGround", ignoreCase = true))
            mc.thePlayer.jump()
    }

    @EventTarget
    fun onAttack(event: AttackEvent) {
        if (event.targetEntity is EntityLivingBase) {
            val entity = event.targetEntity

            if (!mc.thePlayer.onGround || mc.thePlayer.isOnLadder || mc.thePlayer.isInWeb || mc.thePlayer.isInWater ||
                    mc.thePlayer.isInLava || mc.thePlayer.ridingEntity != null || entity.hurtTime > hurtTimeValue.get() ||
                    LiquidBounce.moduleManager[Fly::class.java]!!.state || !msTimer.hasTimePassed(delayValue.get().toLong()))
                return

            val x = mc.thePlayer.posX
            val y = mc.thePlayer.posY
            val z = mc.thePlayer.posZ

            when (modeValue.get().toLowerCase()) {
                "packet" -> {
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0114514, z, true))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0010014111, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.001121, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.00110, z, false))
                    mc.thePlayer.onCriticalHit(entity)
                }
                "AAC3" -> {
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x+250, y+500E-5, z+250, true))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x+0.1, y+0.1, z+0.1, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y+0.782, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y, z+150, false))
                    mc.thePlayer.onCriticalHit(entity)
                }
                "AAC4.3.12" ->{
                    val posy = doubleArrayOf(0.00150000001304,0.0140000001304,0.001500001304)
                    for (i in posy.indices) {
                        mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y +  0.0525000001304, z, true))
                        mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + posy[i], z, false))
                    }
                    mc.thePlayer.onCriticalHit(entity)
                }
                "infinity" -> {
                    mc.thePlayer.sendQueue.addToSendQueue(
                        C0BPacketEntityAction(
                            mc.thePlayer,
                            C0BPacketEntityAction.Action.RIDING_JUMP
                        )
                    )
                    val posy = doubleArrayOf(0.125,0.01147)
                    for (i in posy.indices) {
                        mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + posy[i], z, false))
                    }
                    mc.thePlayer.onCriticalHit(entity)
                }
                "fakesense" -> {
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0114514, z, true))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0010999, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0015001304, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0012016413, z, false))
                    mc.thePlayer.onCriticalHit(entity)
                }
                "newpacket" -> {
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.04132332, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.023243243674, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.01, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0011, z, false))
                    mc.thePlayer.onCriticalHit(entity)
                }
                "bakemono" -> {
                    val posy = doubleArrayOf(-0.092,0.0202,-0.06101,0.011)
                    for (i in posy.indices) {
                        mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + posy[i], z, false))
                    }
                    mc.thePlayer.onCriticalHit(entity)
                }
                "ncppacket" -> {
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.11, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.1100013579, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.0000013579, z, false))
                    mc.thePlayer.onCriticalHit(entity)
                }

                "hop" -> {
                    mc.thePlayer.motionY = 0.1
                    mc.thePlayer.fallDistance = 0.1f
                    mc.thePlayer.onGround = false
                }

                "tphop" -> {
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.02, z, false))
                    mc.netHandler.addToSendQueue(C04PacketPlayerPosition(x, y + 0.01, z, false))
                    mc.thePlayer.setPosition(x, y + 0.01, z)
                    mc.thePlayer.onCriticalHit(entity)
                }
                "meitantei" -> mc.thePlayer.onCriticalHit(entity)
                "jump" -> mc.thePlayer.motionY = 0.42
                "lowjump" -> mc.thePlayer.motionY = 0.3425
            }

            msTimer.reset()
        }
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val packet = event.packet

        if (packet is C03PacketPlayer && modeValue.get().equals("NoGround", ignoreCase = true))
            packet.onGround = false
    }

    override val tag: String?
        get() = modeValue.get()
}
