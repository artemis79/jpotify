package NewIdea;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static NetworkMainPanel2 networkPanel=new NetworkMainPanel2();
    public static JPanel jPanel=new JPanel();
    public MainFrame(){

        super();
        jPanel.setSize(new Dimension(700,800));
        jPanel.setVisible(true);
        this.setSize(new Dimension(1000,800));
        this.setLayout(new BorderLayout());
        this.add(networkPanel,BorderLayout.EAST);
        this.add(jPanel,BorderLayout.CENTER);
        this.setVisible(true);

    }

    public static NetworkMainPanel2 getNetworkPanel() {
        return networkPanel;
    }


}
