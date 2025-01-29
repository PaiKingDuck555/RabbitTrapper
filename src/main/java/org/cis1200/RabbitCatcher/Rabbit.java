package org.cis1200.RabbitCatcher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rabbit {
    public static final String IMG_FILE = "files/bunny.png";
    private int positionX;
    private int positionY;
    private final int width;
    private final int height;
    private static BufferedImage img;

    public Rabbit() {
        positionX = 5;
        positionY = 5;
        width = 100;
        height = 100;
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int val) {
        positionX = val;
    }

    public void setPositionY(int val) {
        positionY = val;
    }

    public void draw(Graphics g) {
        g.drawImage(img, positionY * 100, positionX * 100, width, height, null);
    }

}
