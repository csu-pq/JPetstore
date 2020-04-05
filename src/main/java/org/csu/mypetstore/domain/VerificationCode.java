package org.csu.mypetstore.domain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class VerificationCode{
    private int width = 90;
    private int height = 20;
    private int codeCount = 4;
    private int lineCount = 10;
    private String code = null;
    private char[] codeSequence = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    private BufferedImage bufferedImage;

    public VerificationCode(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        createCodeImage();
    }

    private void createCodeImage() {
        int red = 0;
        int green = 0;
        int blue = 0;
        int x = width/(codeCount+2);
        int y = height - 4;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setFont(new Font("Fixedays", Font.BOLD, height-3));
        Random random = new Random();
//        for(int i=0; i<lineCount; i++) {
//            // x轴第一个点的位置
//            int x1 = random.nextInt(width);
//            // y轴第一个点的位置
//            int y1 = random.nextInt(height);
//            // x轴第二个点的位置
//            int x2 = x1 + random.nextInt(width >> 2);
//            // y轴第二个点的位置
//            int y2 = y1 + random.nextInt(height >> 2);
//            red = random.nextInt(255);
//            green = random.nextInt(255);
//            blue = random.nextInt(255);
//            graphics2D.setColor(new Color(red, green, blue));
//            graphics2D.drawLine(x1, y1, x2, y2);
//        }
        StringBuilder randomCode = new StringBuilder(codeCount);
        for (int i=0; i<codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawString(strRand, (i + 1) * x, y);
            randomCode.append(strRand);
        }
        code = randomCode.toString();
    }

    public void write(String path) throws IOException {
        OutputStream outputStream = new FileOutputStream(path);
        this.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public void write(OutputStream outputStream) throws IOException {
        ImageIO.write(bufferedImage, "png", outputStream);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public String getCode() {
        return code;
    }
}
