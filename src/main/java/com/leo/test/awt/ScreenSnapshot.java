package com.leo.test.awt;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ScreenSnapshot {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {

            int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(new Rectangle(width, height));
            ImageIO.write(image, "png", new File("d:/1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
