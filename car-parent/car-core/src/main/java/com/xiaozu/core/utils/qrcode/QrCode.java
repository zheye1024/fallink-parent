package com.xiaozu.core.utils.qrcode;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:80906
 * @Des:二维码生成
 * @Date:2018/5/25
 */
public class QrCode {

    /**
     * 生成条形码图片
     * @param content 条形内容
     * @param w 宽度
     * @param h 高度
     * @return
     * @throws Exception
     */
    public static String creatCODE128(String content, int w, int h ) throws Exception {

        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, w);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        //参数调节
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.CODE_128, codeWidth, h, hints);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        //byte[] logoBytes = decoder.decodeBuffer(logo);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", stream,null);
        byte[] bytes = stream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str ="data:image/png;base64,"+encoder.encodeBuffer(bytes).trim();
        return base64Str;
    }


    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param w 宽度
     * @param h 高度
     * @param logo 中心图片数据，图片的字节码
     * @return
     * @throws Exception
     */
    public static String createImge(String content, int w, int h, byte[] logo) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        //参数调节
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, w, h, hints);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        //byte[] logoBytes = decoder.decodeBuffer(logo);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", stream,logo);
        byte[] bytes = stream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str ="data:image/png;base64,"+encoder.encodeBuffer(bytes).trim();
        return base64Str;
    }

    public static String readImge(String imge) throws Exception {
        if (imge == null) //图像数据为空
            return null;
        String strImge=imge.substring(imge.indexOf(",")+1);

        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] b = decoder.decodeBuffer(strImge);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(b);

        BufferedImage image = ImageIO.read(inputStream);

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatReader formatReader = new MultiFormatReader();
        Result result = formatReader.decode(binaryBitmap, hints);
        return result.toString();
    }

}
