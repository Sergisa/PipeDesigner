package com.sergisa;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    private static final String IMAGE_FILENAME = "testimage.jpg";
    private static boolean warnAboutReload = true;
    public static void adjustImage(JTextField pctField, JTextField degreeField, JLabel imageLabel, int amount) {
        Icon icon = imageLabel.getIcon();
        ImageIcon imageIcon = (ImageIcon)icon;
        Image image = imageIcon.getImage();

        BufferedImage bi = convertToBufferedImage(image);

        bi = resizeImage(bi, amount);
        bi = rotateImageBetter(bi, amount);

        imageLabel.setIcon(new ImageIcon(bi));
    }
    public static BufferedImage resizeImage(BufferedImage bi, int percent) {
        double scale = percent / 100.0;
        AffineTransform resize = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp op = new AffineTransformOp (
                resize,
                AffineTransformOp.TYPE_BICUBIC);
        return op.filter(bi, null);
    }
    public static BufferedImage rotateImage(BufferedImage bi, int degrees) {
        double radians = Math.toRadians(degrees);
        AffineTransform rotate = AffineTransform.getRotateInstance(radians);
        AffineTransformOp op = new AffineTransformOp (
                rotate,
                AffineTransformOp.TYPE_BILINEAR);
        return op.filter(bi, null);
    }


    public static BufferedImage rotateImageBetter(BufferedImage bi, int degrees) {
        double radians = Math.toRadians(degrees);
        AffineTransform rotate = AffineTransform.getRotateInstance(radians);

        int height = bi.getHeight();
        int width = bi.getWidth();
        Rectangle newSize = rotate.createTransformedShape(
                new Rectangle(width, height)).getBounds();

        rotate = new AffineTransform();
        rotate.translate(newSize.width*0.5, newSize.height*0.5);
        rotate.rotate(radians);
        rotate.translate(-width*0.5, -height*0.5);

        AffineTransformOp op = new AffineTransformOp(
                rotate,
                AffineTransformOp.TYPE_BILINEAR);
        return op.filter(bi, null);
    }

    public static BufferedImage convertToBufferedImage(ImageIcon image) {
        return convertToBufferedImage(image.getImage());
    }
    public static BufferedImage convertToBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bi;
    }

    public static void saveImage(Image image, File file) throws IOException {
        BufferedImage bi = convertToBufferedImage(image);
        ImageIO.write(bi, "png", file);
    }


    public static void saveJpgImage(Image image, File file) throws IOException {
        BufferedImage bi = convertToBufferedImage(image);

        BufferedImage rgbImage = new BufferedImage(
                bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        ColorConvertOp toRGB = new ColorConvertOp(null);
        toRGB.filter(bi, rgbImage);

        ImageIO.write(rgbImage, "jpg", file);
    }
    public static void saveImageToFile(JLabel imageLabel) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(imageLabel.getParent());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try {
                ImageIcon icon = (ImageIcon)imageLabel.getIcon();
                saveImage(icon.getImage(), file);

                JOptionPane.showMessageDialog(
                        imageLabel.getParent(),
                        "File saved to: " + file,
                        "File Saved Successfully",
                        JOptionPane.PLAIN_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        imageLabel.getParent(),
                        "Error saving file: " + e.getMessage(),
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    public static void reloadImage(JLabel imageLabel) {
        if (warnAboutReload) {
            JLabel message = new JLabel("Warning, this will reload the image!");
            JCheckBox noReloadWarn = new JCheckBox("Don't tell me that again");
            JComponent[] controls = new JComponent[] {
                    message,
                    noReloadWarn
            };
            JOptionPane.showMessageDialog(
                    imageLabel.getParent(),
                    controls,
                    "Image Will Reload",
                    JOptionPane.PLAIN_MESSAGE);
            warnAboutReload = !noReloadWarn.isSelected();
        }

        imageLabel.setIcon(new ImageIcon(IMAGE_FILENAME));
    }

}
