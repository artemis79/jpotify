package NetworkGraphics;

import Logic.Person;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PersonGUI extends JPanel {

    private Person user;
   // private JLabel name;
    private JButton nameButton;
    public PersonGUI (Person p){

        super();
        user=p;
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(400,400));
       // name=new JLabel();
       // name.setText(user.getUserName());
        nameButton=new JButton(user.getUserName());
        nameButton.setBackground(Color.GRAY);
        //nameButton.setBorderPainted(true);
        this.add(nameButton,BorderLayout.NORTH);
        this.setForeground(Color.DARK_GRAY);
        this.setBackground(Color.GRAY);
        this.setVisible(true);

    }

}
