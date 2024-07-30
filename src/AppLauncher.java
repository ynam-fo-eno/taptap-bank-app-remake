import DBObjs.People;
import GUIs.LoginUI;
import GUIs.RegisterUI;
import GUIs.BankAppUI;

import javax.swing.*;
import java.math.BigDecimal;

public class AppLauncher {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            String title ="Timo's Simple Bank App";
            @Override
            public void run() {
                new LoginUI(title).setVisible(true);
                //new BankAppUI(title, new People(1,"Timorpheus","ssss", new BigDecimal("100000"))).setVisible(true);
            }
        });
    }
}
