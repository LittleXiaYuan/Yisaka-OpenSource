package Core.C08Packet.Main;


import Core.C08Packet.HWID.HWIDUtils;
import Core.C08Packet.HWID.WebUtils;
import Core.C08Packet.utils.SystemUtils.SystemUtils;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class WbxMain {
    public static String Name = "Yisaka";

    public static void Main() {
        Display.setTitle(Name + " 正在登录休伯利安");
    }

    public static void Liquid() {
        Display.setTitle(Name + " " + version + "丨舰长: " + WbxMain.username + "[" + WbxMain.rank + "]丨欢迎来到休伯利安,舰长大人。");
    }

    public static String Cracked = "Yisaka";
    public static String version = "1.1.0.2";
    public static String username;
    public static String password;
    public static String rank;
    public static boolean isStarting;

    public static void sendWindowsMessageLogin() throws AWTException, IOException {
      //  FakeSenseMain.Cracked();
        SystemUtils.displayTray("请舰长登记领取登录许可", "Login", TrayIcon.MessageType.WARNING);
        String AT = JOptionPane.showInputDialog("请在下方填写舰长的名字(用户名)");
        final int R = 0;
        WbxMain.username = AT;
        AT = JOptionPane.showInputDialog("请在下方填写舰长的证件号(密码)");
        final int R2 = 0;
        WbxMain.password = AT;
        WbxMain.isStarting = true;

        try {
            if (username == null) {
                JOptionPane.showMessageDialog(null, "舰长名不能为空!", "验证", 0);
                System.exit(0);
            } else if (password == null) {
                JOptionPane.showMessageDialog(null, "证件号不能为空!", "验证", 0);
                System.exit(0);
            } else {
                if (WebUtils.get("https://gitee.com/funknightmai/fkhk-hwid/blob/master/YisakaHWID.json").contains("[" + username + "]")) {
                    if (WebUtils.get("https://gitee.com/funknightmai/fkhk-hwid/blob/master/YisakaHWID.json").contains("[" + username + "]" + HWIDUtils.getHWID() + ":" + password + "[1]")) {
                        rank = "突进级";
                        JOptionPane.showMessageDialog(null, "登录许可签发完成", "登录许可签发处", 1);
                        SystemUtils.displayTray("登录许可签发完成", "正在登录休伯利安", TrayIcon.MessageType.WARNING);
                    } else if (WebUtils.get("https://gitee.com/funknightmai/fkhk-hwid/blob/master/YisakaHWID.json").contains("[" + username + "]" + HWIDUtils.getHWID() + ":" + password + "[2]")) {
                        rank = "崩坏级";
                        JOptionPane.showMessageDialog(null, "登录许可签发完成", "登录许可签发处", 1);
                        SystemUtils.displayTray("登录许可签发完成", "正在登录休伯利安", TrayIcon.MessageType.WARNING);
                    } else if (WebUtils.get("https://gitee.com/funknightmai/fkhk-hwid/blob/master/YisakaHWID.json").contains("[" + username + "]" + HWIDUtils.getHWID() + ":" + password + "[3]")) {
                        rank = "律者级";
                        JOptionPane.showMessageDialog(null, "登录许可签发完成", "登录许可签发处", 1);
                        SystemUtils.displayTray("登录许可签发完成", "正在登录休伯利安", TrayIcon.MessageType.WARNING);
                    } else if (WebUtils.get("https://gitee.com/funknightmai/fkhk-hwid/blob/master/YisakaHWID.json").contains("[" + username + "]" + HWIDUtils.getHWID() + ":" + password + "[4]")) {
                        rank = "原子崩坏级";
                        JOptionPane.showMessageDialog(null, "登录许可签发完成", "登录许可签发处", 1);
                        SystemUtils.displayTray("登录许可签发完成", "正在登录休伯利安", TrayIcon.MessageType.WARNING);
                    } else {
                        JOptionPane.showMessageDialog(null, "登录许可签发失败", "登录许可签发处", 0);
                        JOptionPane.showInputDialog(null, "您还没和学园长登记呢,请登记后重试！", HWIDUtils.getHWID());
                        SystemUtils.displayTray("舰长没能成功取得登录许可T-T", "舰长没能成功取得登录许可T-T", TrayIcon.MessageType.WARNING);
                        System.exit(0);
                    }
                }
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            JOptionPane.showMessageDialog(null, "舰长没能成功取得登录许可T-T");
            SystemUtils.displayTray("舰长没能成功取得登录许可T-T", "舰长没能成功取得登录许可T-T", TrayIcon.MessageType.WARNING);
            System.exit(0);
        }
    }
}