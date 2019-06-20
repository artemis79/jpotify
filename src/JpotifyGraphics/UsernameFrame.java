package JpotifyGraphics;

import Logic.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UsernameFrame extends JFrame {

    private final static String LABEL = "Username: ";
    private JTextField textField;
    private JButton button;
    private Person user;


    public UsernameFrame (){
        super(LABEL);
        textField = new JTextField();
        button= new JButton("Enter");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action();
            }
        });
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyLocation() == KeyEvent.VK_ENTER){
                    action();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.setLayout(new BorderLayout());
        this.add (textField, BorderLayout.CENTER );
        this.add (button, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    private void action (){
        String username = textField.getText();
        textField.setText("");
        user = new Person(username);
        setVisible(false);
    }

}
