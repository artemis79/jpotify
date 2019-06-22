package JpotifyGraphics;

import Logic.Song;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;

public class ArtworkFrame extends JPanel {

    private Song song;
    private JLabel labelImage;
    private Labels labels;

    public ArtworkFrame (Song song){
        super();
        this.setPreferredSize(new Dimension(250 , 70));
        this.song = song;
        if (song != null) {
            try {
                ByteArrayInputStream bos = new ByteArrayInputStream(song.getSongImage());
                Image image = ImageIO.read(bos);
                image.getScaledInstance(10 , 10 , Image.SCALE_DEFAULT);
                labelImage.setPreferredSize(new Dimension(100 , 100));
                labelImage.setIcon(new ImageIcon(image));
                labels = new Labels();
            } catch (Exception e) {
                System.out.println("Could not show the image");
                e.printStackTrace();
            }
            this.setLayout(new FlowLayout());
            this.add (labelImage);
            this.add (labels);
        }
        else{
            labels = new Labels();
            labelImage = new JLabel();
            this.setLayout(new FlowLayout());
            this.add (labelImage);
            this.add (labels);
        }
        this.setVisible(true);

    }

    public void setArtwork(Song song) {
        this.song = song;
        labels.setLabels();
        try {
            if (song.getSongImage() != null) {
                try {
                    ByteArrayInputStream bos = new ByteArrayInputStream(song.getSongImage());
                    BufferedImage bufferedImage = ImageIO.read(bos);
                    bufferedImage = resize(bufferedImage, 70, 70);
                    labelImage.setIcon(new ImageIcon(bufferedImage));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }


    private class Labels extends JPanel {
        private JLabel labelName;
        private JLabel labelArtist;

        public Labels (){
            super();
            if (song != null){
                labelName = new JLabel(song.getSongName());
                labelArtist = new JLabel(song.getArtist());
            }
            else{
                labelName = new JLabel("Song Name");
                labelArtist = new JLabel("Artist Name");
            }
            this.setLayout(new BoxLayout(this , BoxLayout.PAGE_AXIS));
            this.add (labelName);
            this.add (labelArtist);
            this.setVisible(true);
        }

        public void setLabels (){
            labelName.setText(song.getSongName());
            labelArtist.setText(song.getArtist());

        }
    }

}
