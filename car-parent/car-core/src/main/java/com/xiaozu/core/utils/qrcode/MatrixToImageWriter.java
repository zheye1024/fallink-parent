package com.xiaozu.core.utils.qrcode;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author:80906
 * @Des:
 * @Date:2018/5/25
 */
public final class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }


    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }


    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream,byte[] logo) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (logo!=null&&logo.length>0){
            ByteArrayInputStream inputStream = new ByteArrayInputStream(logo);
            Graphics2D gs = image.createGraphics();
            int ratioWidth = image.getWidth() * 2 / 10;
            int ratioHeight = image.getHeight() * 2 / 10;
            //载入logo
            Image img = ImageIO.read(inputStream);
            int logoWidth = img.getWidth(null) > ratioWidth ? ratioWidth : img.getWidth(null);
            int logoHeight = img.getHeight(null) > ratioHeight ? ratioHeight : img.getHeight(null);
            int x = (image.getWidth() - logoWidth) / 2;
            int y = (image.getHeight() - logoHeight) / 2;
            gs.drawImage(img, x, y, logoWidth, logoHeight, null);
            gs.setColor(Color.black);
            gs.setBackground(Color.WHITE);
            gs.dispose();
            img.flush();
        }

        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

}
