package JpotifyGraphics;

import Logic.Album;
import Logic.Song;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.ListIterator;

public class AlbumArtwork extends JPanel {

    private JLabel albumName;
    private JLabel albumImage;
    private Album album;

    public AlbumArtwork (Album album) throws IOException {
        super();
        this.setOpaque(true);
        this.setBackground(Color.darkGray);
        this.album = album;
        albumName = new JLabel(album.getAlbumName());
        albumImage = new JLabel();
        albumName.setForeground(Color.LIGHT_GRAY);
        byte [] img = album.getAlbumArtwork();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(img);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
        bufferedImage = resize(bufferedImage, 150, 150);
        albumImage.setIcon(new ImageIcon(bufferedImage));
        this.setLayout(new BoxLayout(this , BoxLayout.PAGE_AXIS));
        albumImage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {

                    MainFrame.playSongFromAlbum(album , album.getAlbumSongs().get(0));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
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
        });
        this.add(albumImage);
        this.add (albumName);
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

    public JLabel getAlbumImage (){
        return albumImage;
    }

    public Album getAlbum (){
        return album;
    }

}
