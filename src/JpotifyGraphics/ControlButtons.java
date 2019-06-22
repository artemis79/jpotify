package JpotifyGraphics;

import Logic.Song;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ControlButtons extends JPanel {

    private JLabel pauseOrResume;
    private JButton nextSong;
    private JButton previousSong;
    private Song song;
    private final String PAUSE_PATH = "C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Images\\icons8-pause-button-80.png";
    private final String RESUME_PATH = "C:\\Users\\mahsh\\IdeaProjects\\Jpotify\\src\\Images\\icons8-circled-play-80.png";

    public ControlButtons () {
        super();
        File imgPath = new File(PAUSE_PATH);
        try {
            pauseOrResume = new JLabel();
            BufferedImage bufferedImage = ImageIO.read(imgPath);
            pauseOrResume.setIcon(new ImageIcon(bufferedImage));
            System.out.println("wow");
        } catch (IOException e) {
            e.printStackTrace();
        }
        pauseOrResume.addMouseListener(new Pause ());
        nextSong = new JButton("  next  ");
        nextSong.setBackground(Color.CYAN);
        previousSong = new JButton("previous");
        previousSong.setBackground(Color.CYAN);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        this.add(previousSong);
        this.add (pauseOrResume);
        this.add(nextSong);
        this.setVisible(true);

    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public void pauseResumeSong (Song song){
        this.song = song;
    }


    private class Pause implements MouseListener {

        public void actionPerformed(ActionEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (song != null) {
                try {
                    //if the song is in PLAYING state its status is equal to 2
                    if (song.getSongStatus() == 2) {
                        song.pause();
                        File fileResume = new File (RESUME_PATH);
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(fileResume);
                            pauseOrResume.setIcon(new ImageIcon(bufferedImage));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                    //if the song is in PAUSED state its status is equal to 1
                    else if (song.getSongStatus() == 1) {
                        song.resume();
                        File fileResume = new File (PAUSE_PATH);
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(fileResume);
                            pauseOrResume.setIcon(new ImageIcon(bufferedImage));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (InterruptedException e1) {
                    System.out.println("Sorry the song could not be paused");
                    e1.printStackTrace();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
