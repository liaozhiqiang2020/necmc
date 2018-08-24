package com.sv.mc.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.apache.commons.codec.binary.Base64;


/**
 * 二维码工具类
 *
 * @author gu
 */
public class QrCodeUtils {

    /**
     * 黑色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 白色
     */
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 宽
     */
    private static final int WIDTH = 350;
    /**
     * 高
     */
    private static final int HEIGHT = 350;

    /**
     * 图片高度增加60
     */
    private static final int PIC_HEIGHT = HEIGHT + 13;

    /**
     * 二维码传图片
     *
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width, PIC_HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < PIC_HEIGHT; y++) {
                image.setRGB(x, y, WHITE);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 生成二维码
     *
     * @param content 扫描二维码的内容
     * @param format  图片格式 jpg
     *                文件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static BufferedImage generateQrCode(String content, String format) throws Exception {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        @SuppressWarnings("rawtypes")
        Map hints = new HashMap();
        // 设置UTF-8， 防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN, 0);
        // 设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 画二维码
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        BufferedImage image = toBufferedImage(bitMatrix);
        return image;
    }


    /**
     * 把生成的图片写到本地磁盘
     *
     * @param qrcFile       路径
     * @param qrCodeContent 二维码内容
     * @param pressText     增加的文字
     * @throws Exception
     */
    public static void generateQrCode(File qrcFile, String qrCodeContent, String pressText,int fontsize) throws Exception {


        BufferedImage image = generateQrCode(qrCodeContent, "jpg");

        Graphics g = image.getGraphics();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //设置字体
        Font font = new Font("粗体", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.black);
        FontMetrics metrics = g.getFontMetrics(font);
        // 文字在图片中的坐标 这里设置在中间

        int startX = (WIDTH - metrics.stringWidth(pressText)) / 2;
        System.out.println("文字长度"+fontsize);
        System.out.println("文字大小"+font.getSize());
        System.out.println("X起始坐标"+startX);
        int startY = HEIGHT + (PIC_HEIGHT - HEIGHT) / 2;
        g.drawString(pressText, startX, startY);

        g.dispose();
        image.flush();
        try {
            ImageIO.write(image, "jpg", qrcFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 生成二维码并使用Base64编码
     *
     * @param content 二维码内容
     * @return 返回base64图片
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static String getBase64QRCode(String content) throws Exception {


        String format = "png";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        @SuppressWarnings("rawtypes")
        Map hints = new HashMap();

        // 设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN, 0);
        // 设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 画二维码
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
        ImageIO.write(image, format, os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        byte b[] = os.toByteArray();//从流中获取数据数组。
       // String base64String = new BASE64Encoder().encode(b);
        String base64String = new String(new Base64().encode(b));

        // Base64编码
        return base64String;

    }





    /**
     * test
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        File qrcFile = new File("D:/1.jpg");
        String qrCodeContent = "a";
        String pressText = "a";
        /*fontsize=
        generateQrCode(qrcFile, qrCodeContent, pressText);
*/

    }


}
