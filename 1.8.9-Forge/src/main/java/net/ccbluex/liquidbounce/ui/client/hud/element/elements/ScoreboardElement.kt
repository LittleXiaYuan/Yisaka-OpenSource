/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import Core.C08Packet.utils.render.ColorManager
import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.client.hud.element.Side
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.render.ColorUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FontValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.scoreboard.ScoreObjective
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.util.EnumChatFormatting
import java.awt.Color

/**
 * CustomHUD scoreboard
 *
 * Allows to move and customize minecraft scoreboard
 */
@ElementInfo(name = "Scoreboard", force = true)
class ScoreboardElement(x: Double = 5.0, y: Double = 0.0, scale: Float = 1F,
                        side: Side = Side(Side.Horizontal.RIGHT, Side.Vertical.MIDDLE)) : Element(x, y, scale, side) {

    private val textRedValue = IntegerValue("Text-R", 255, 0, 255)
    private val textGreenValue = IntegerValue("Text-G", 255, 0, 255)
    private val textBlueValue = IntegerValue("Text-B", 255, 0, 255)

    private val backgroundColorRedValue = IntegerValue("Background-R", 0, 0, 255)
    private val backgroundColorGreenValue = IntegerValue("Background-G", 0, 0, 255)
    private val backgroundColorBlueValue = IntegerValue("Background-B", 0, 0, 255)
    private val backgroundColorAlphaValue = IntegerValue("Background-Alpha", 95, 0, 255)

    private val rectValue = BoolValue("Rect", false)
    private val rectColorModeValue = ListValue("Rect-Color", arrayOf("Custom", "Rainbow"), "Custom")
    private val rectColorRedValue = IntegerValue("Rect-R", 0, 0, 255)
    private val rectColorGreenValue = IntegerValue("Rect-G", 111, 0, 255)
    private val rectColorBlueValue = IntegerValue("Rect-B", 255, 0, 255)
    private val rectColorBlueAlpha = IntegerValue("Rect-Alpha", 255, 0, 255)

    private val shadowValue = BoolValue("Shadow", false)
    private val fontValue = FontValue("Font", Fonts.minecraftFont)

    /**
     * Draw element
     */
    override fun drawElement(): Border? {
        if (NoScoreboard.state)
            return null
        val counter = intArrayOf(1)

        val fontRenderer = fontValue.get()
        val textColor = textColor().rgb
        val backColor = backgroundColor().rgb

        val rectColorMode = rectColorModeValue.get()
        val rectCustomColor = Color(rectColorRedValue.get(), rectColorGreenValue.get(), rectColorBlueValue.get(),
                rectColorBlueAlpha.get()).rgb

        val worldScoreboard: Scoreboard = mc.theWorld.scoreboard
        var currObjective: ScoreObjective? = null
        val playerTeam = worldScoreboard.getPlayersTeam(mc.thePlayer.name)

        if (playerTeam != null) {
            val colorIndex = playerTeam.chatFormat.colorIndex

            if (colorIndex >= 0)
                currObjective = worldScoreboard.getObjectiveInDisplaySlot(3 + colorIndex)
        }

        val objective = currObjective ?: worldScoreboard.getObjectiveInDisplaySlot(1) ?: return null

        val scoreboard: Scoreboard = objective.scoreboard
        var scoreCollection = scoreboard.getSortedScores(objective)
        val scores = Lists.newArrayList(Iterables.filter(scoreCollection) { input ->
            input?.playerName != null && !input.playerName.startsWith("#")
        })

        scoreCollection = if (scores.size > 15)
            Lists.newArrayList(Iterables.skip(scores, scoreCollection.size - 15))
        else
            scores

        var maxWidth = mc.fontRendererObj.getStringWidth(objective.displayName)

        for (score in scoreCollection) {
            val scorePlayerTeam = scoreboard.getPlayersTeam(score.playerName)
            val width = "${ScorePlayerTeam.formatPlayerName(scorePlayerTeam, score.playerName)}: ${EnumChatFormatting.RED}${score.scorePoints}"
            maxWidth = maxWidth.coerceAtLeast(mc.fontRendererObj.getStringWidth(width))
        }

        val maxHeight = scoreCollection.size * mc.fontRendererObj.FONT_HEIGHT
        val l1 = -maxWidth - 3 - if (rectValue.get()) 3 else 0



        Gui.drawRect(l1 - 2, -2, 5, maxHeight + mc.fontRendererObj.FONT_HEIGHT, backColor)

        scoreCollection.forEachIndexed { index, score ->
            val team = scoreboard.getPlayersTeam(score.playerName)

            val name = ScorePlayerTeam.formatPlayerName(team, score.playerName)
            val scorePoints = "${EnumChatFormatting.RED}${score.scorePoints}"

            val width = 5 - if (rectValue.get()) 4 else 0
            val height = maxHeight - index * mc.fontRendererObj.FONT_HEIGHT

            GlStateManager.resetColor()

            if (!name.toLowerCase().contains("zqat.top") && !name.contains("mushmc.com") && !name.contains("mc110.net") && !name.contains("redesky.com") && !name.toLowerCase().contains("pixel") && !name.toLowerCase().contains("mc986") && !name.toLowerCase().contains("loyisa.cn") && !name.toLowerCase().contains("net") && !name.toLowerCase().contains("com")
            ) {
                mc.fontRendererObj.drawStringWithShadow(name, l1.toFloat(), height.toFloat(), 553648127)
            } else {
                val charArray = "YisakaTime".toCharArray()
                var length = 0
                for (charIndex in charArray) {
                    mc.fontRendererObj.drawStringWithShadow(
                            charIndex.toString(),
                            l1 + length.toFloat(),
                            height.toFloat(),
                            ColorManager.astolfoRainbow(counter[0] * 100, 5, 107)
                    )
                    length += mc.fontRendererObj.getCharWidth(charIndex)
                    counter[0] = counter[0] + 1
                }
            }
            mc.fontRendererObj.drawStringWithShadow(scorePoints, (width - mc.fontRendererObj.getStringWidth(scorePoints)).toFloat(), height.toFloat(), textColor)

            if (index == scoreCollection.size - 1) {
                val displayName = objective.displayName

                GlStateManager.resetColor()

                mc.fontRendererObj.drawStringWithShadow(displayName, (l1 + maxWidth / 2 - mc.fontRendererObj.getStringWidth(displayName) / 2).toFloat(), (height -
                        mc.fontRendererObj.FONT_HEIGHT).toFloat(), textColor)
            }

            if (rectValue.get()) {
                val rectColor = when {
                    rectColorMode.equals("Rainbow", ignoreCase = true) -> ColorUtils.rainbow(400000000L * index).rgb
                    else -> rectCustomColor
                }

                RenderUtils.drawRect(2F, if (index == scoreCollection.size - 1) -2F else height.toFloat(), 5F, if (index == 0) mc.fontRendererObj.FONT_HEIGHT.toFloat() else height.toFloat() + mc.fontRendererObj.FONT_HEIGHT * 2F, rectColor)
            }
        }

        return Border(-maxWidth.toFloat() - 5 - if (rectValue.get()) 3 else 0, -2F, 5F, maxHeight.toFloat() + mc.fontRendererObj.FONT_HEIGHT)
    }

    private fun backgroundColor() = Color(backgroundColorRedValue.get(), backgroundColorGreenValue.get(),
            backgroundColorBlueValue.get(), backgroundColorAlphaValue.get())

    private fun textColor() = Color(textRedValue.get(), textGreenValue.get(),
            textBlueValue.get())

}