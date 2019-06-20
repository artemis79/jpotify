package JpotifyGraphics;

import Logic.*;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class PlayerGUI extends JPanel {

    private JSlider songSlider;
    private JSlider soundSlider;
    private Song song;


    public PlayerGUI(Song song) throws InterruptedException {
        super();
        this.song = song;
        this.song.play();
        songSlider = new JSlider();
        soundSlider = new JSlider();
        this.setLayout(new BorderLayout());
        ControlButtons buttons = new ControlButtons(song);
        this.add(buttons, BorderLayout.NORTH);
        this.add(soundSlider, BorderLayout.EAST);
        this.add(songSlider, BorderLayout.CENTER);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
    }


    private class SoundChangeSlider implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {

            }
        }

    }
}
