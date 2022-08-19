package Core.C08Packet.modules.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.event.*;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.LongJump;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;

@ModuleInfo(name = "ModuleInfo", description = "Show your Module", category = ModuleCategory.RENDER)
public class ModuleInfos extends Module {

    private FloatValue x = new FloatValue("X",650F,1F,1920F);
    private FloatValue y = new FloatValue("Y", 2F,0F,1080F);
    private String i = "§aEnabled";
    private String q = "§cDisabled";

    @EventTarget
    void onRender2D(Render2DEvent event) {
        RenderUtils.drawRect(x.get() + 89.47F, y.get() + -2.70F, x.get() + -5.26F, y.get()+ -17F,new Color(24,24,24));
        RenderUtils.drawRect(x.get() + -5.26F, y.get() + -5.26F, x.get() + 89.47F, y.get()+ 51.0F,new Color(34,34,34));
        Fonts.font35.drawString("ModuleInfo", x.get(), y.get() - 13, new Color(255,255,255).getRGB());
        Speed Speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);
        if(Speed.getState()) {
            Fonts.font35.drawString("Speed:" + " " + this.i, x.get(), y.get(), new Color(255,255,255).getRGB());
        }else{
            Fonts.font35.drawString("Speed:" + " " + this.q, x.get(), y.get(), new Color(255,255,255).getRGB());
        }

        KillAura killaura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
        if(killaura.getState()) {
            Fonts.font35.drawString("KillAura:" + " " + this.i, x.get(), y.get() + 10f, new Color(255,255,255).getRGB());
        }else{
            Fonts.font35.drawString("KillAura:" + " " + this.q, x.get(), y.get() + 10f, new Color(255,255,255).getRGB());
        }

        Scaffold scaffold = (Scaffold) LiquidBounce.moduleManager.getModule(Scaffold.class);
        if(scaffold.getState()) {
            Fonts.font35.drawString("BlockFly:" + " " + this.i, x.get(), y.get() + 20f, new Color(255,255,255).getRGB());
        }else{
            Fonts.font35.drawString("BlockFly:" + " " + this.q, x.get(), y.get() + 20f, new Color(255,255,255).getRGB());
        }

        Fly fly = (Fly) LiquidBounce.moduleManager.getModule(Fly.class);
        if(fly.getState()) {
            Fonts.font35.drawString("Flight:" + " " + this.i, x.get(), y.get() + 30f, new Color(255,255,255).getRGB());
        }else{
            Fonts.font35.drawString("Flight:" + " " + this.q, x.get(), y.get() + 30f, new Color(255,255,255).getRGB());
        }

        LongJump longjump = (LongJump) LiquidBounce.moduleManager.getModule(LongJump.class);
        if(longjump.getState()) {
            Fonts.font35.drawString("LongJump:" + " " + this.i, x.get(), y.get() + 40f, new Color(255,255,255).getRGB());
        }else{
            Fonts.font35.drawString("LongJump:" + " " + this.q, x.get(), y.get() + 40f, new Color(255,255,255).getRGB());
        }
    }
}
