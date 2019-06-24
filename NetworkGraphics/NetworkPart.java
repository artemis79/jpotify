package NetworkGraphics;

import Logic.Client;
import Logic.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NetworkPart extends JPanel {

  private Client thisUser;
   ArrayList<Person> otherUsers=new ArrayList<>();


  public NetworkPart(Client client){

      super();
      thisUser=client;
      otherUsers=client.getOtherUsers();
      this.setLayout(new GridLayout(20,1));
      this.setSize(new Dimension(400,850));
      this.setOpaque(true);
      this.setBackground(Color.darkGray);
      makeUseresIcon();
      this.setVisible(true);
  }

   public void makeUseresIcon() {


            if(otherUsers.contains(thisUser.getUser()))
                otherUsers.remove(thisUser.getUser());


           for (Person p : otherUsers) {

               PersonGUI personGUI = new PersonGUI(p);
               this.add(personGUI);
           }

       }


}
