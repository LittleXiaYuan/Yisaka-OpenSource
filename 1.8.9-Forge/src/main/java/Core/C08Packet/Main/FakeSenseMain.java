package Core.C08Packet.Main;

import Core.C08Packet.HWID.WebUtils;
import scala.xml.Null;

import javax.swing.*;
import java.io.IOException;

public class FakeSenseMain {

    public static void Cracked() throws IOException {
        final String version1 = null;
        if (WbxMain.version.equals(version1)) {
        } else
            JOptionPane.showMessageDialog(null, "舰长的登录许可已经过期了，请更新");
        System.exit(0);
        if (version1 == null) {
        }else{
            if (WebUtils.get("https://gitee.com/funknightmai/fkhk-hwid/blob/master/YisakaVersion.json").contains(version1)){
            }
        }
    }
}