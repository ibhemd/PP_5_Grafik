import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Level {

    public static void drawLevelImage(Graphics g) throws IOException {

        Graphics2D g2d = (Graphics2D) g;

        BufferedImage levelImage = ImageIO.read(new File("images\\level1.bmp"));
        BufferedImage background = ImageIO.read(new File("images\\Background\\background0.png"));
        BufferedImage blueTile = ImageIO.read(new File("images\\assets\\Tiles\\liquidWaterTop_mid.png"));
        BufferedImage blackTile = ImageIO.read(new File("images\\assets\\Tiles\\grassMid.png"));

        g2d.drawImage(background, 0,0,null);

        for(int x = 0; x < levelImage.getWidth(); x++) {
            for(int y = 0; y < levelImage.getHeight(); y++) {
                if (levelImage.getRGB(x,y) == -16777216) { // BLACK == grassMid.png
                    g2d.drawImage(blackTile, x*70, y*70, null);
                }
                if (levelImage.getRGB(x,y) == -16776961) { // BLUE == liquidWaterTop_mid.png
                    g2d.drawImage(blueTile, x*70, y*70, null);
                }
            }
        }
    }
}
