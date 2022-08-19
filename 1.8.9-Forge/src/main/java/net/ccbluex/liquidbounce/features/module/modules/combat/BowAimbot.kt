/*
 * FakeSense Hack Client
 * Thanks FunkNight.
 * HomePage:FakeSense.life
 */
package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render3DEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.EntityUtils
import net.ccbluex.liquidbounce.utils.RotationUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemBow
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import java.awt.Color

@ModuleInfo(name = "BowAimbot", description = "Automatically aims at players when using a bow.", category = ModuleCategory.COMBAT)
class BowAimbot : Module() {

    private val silentValue = BoolValue("Silent", true)
    private val predictValue = BoolValue("Predict", true)
    private val throughWallsValue = BoolValue("ThroughWalls", false)
    private val predictSizeValue = FloatValue("PredictSize", 2F, 0.1F, 5F)
    private val priorityValue = ListValue("Priority", arrayOf("Health", "Distance", "Direction"), "Direction")
    private val markValue = BoolValue("Mark", true)
    public val fastBow = BoolValue("FastBow", true)
    private val packetsValue = IntegerValue("Packets", 20, 3, 20)

    private var target: Entity? = null

    override fun onDisable() {
        target = null
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        target = null

        if (mc.thePlayer.itemInUse?.item is ItemBow) {
            val entity = getTarget(throughWallsValue.get(), priorityValue.get()) ?: return

            target = entity
            RotationUtils.faceBow(target, silentValue.get(), predictValue.get(), predictSizeValue.get())
        }
        if (fastBow.get()){
            if (!mc.thePlayer.isUsingItem)
                return

            if (mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().item is ItemBow) {
                mc.netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, mc.thePlayer.currentEquippedItem, 0F, 0F, 0F))

                val yaw = if (RotationUtils.targetRotation != null)
                    RotationUtils.targetRotation.yaw
                else
                    mc.thePlayer.rotationYaw

                val pitch = if (RotationUtils.targetRotation != null)
                    RotationUtils.targetRotation.pitch
                else
                    mc.thePlayer.rotationPitch

                for (i in 0 until packetsValue.get())
                    mc.netHandler.addToSendQueue(C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, true))

                mc.netHandler.addToSendQueue(C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN))
                mc.thePlayer.itemInUseCount = mc.thePlayer.inventory.getCurrentItem().maxItemUseDuration - 1
            }
        }
    }

    @EventTarget
    fun onRender3D(event: Render3DEvent) {
        if (target != null && !priorityValue.get().equals("Multi", ignoreCase = true) && markValue.get())
            RenderUtils.drawPlatform(target, Color(37, 126, 255, 70))
    }

    private fun getTarget(throughWalls: Boolean, priorityMode: String): Entity? {
        val targets = mc.theWorld.loadedEntityList.filter {
            it is EntityLivingBase && EntityUtils.isSelected(it, true) &&
                    (throughWalls || mc.thePlayer.canEntityBeSeen(it))
        }

        return when (priorityMode.toUpperCase()) {
            "DISTANCE" -> targets.minBy { mc.thePlayer.getDistanceToEntity(it) }
            "DIRECTION" -> targets.minBy { RotationUtils.getRotationDifference(it) }
            "HEALTH" -> targets.minBy { (it as EntityLivingBase).health }
            else -> null
        }
    }

    fun hasTarget() = target != null && mc.thePlayer.canEntityBeSeen(target)
}