package NewIdea;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static NetworkMainPanel2 networkPanel=new NetworkMainPanel2();

    public MainFrame(){

        super();
        this.setSize(new Dimension(1000,800));
        this.setLayout(new BorderLayout());
        this.add(networkPanel,BorderLayout.EAST);
        this.setVisible(true);

    }

    public static NetworkMainPanel2 getNetworkPanel() {
        return networkPanel;
    }


}
