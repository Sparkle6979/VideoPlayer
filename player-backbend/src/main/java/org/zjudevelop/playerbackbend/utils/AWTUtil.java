package org.zjudevelop.playerbackbend.utils;

import static org.jcodec.common.model.ColorSpace.RGB;

import org.jcodec.codecs.png.PNGDecoder;
import org.jcodec.codecs.png.PNGEncoder;
import org.jcodec.common.Preconditions;
import org.jcodec.common.VideoCodecMeta;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.ColorUtil;
import org.jcodec.scale.RgbToBgr;
import org.jcodec.scale.Transform;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/3 15:54
 */
public class AWTUtil {
    private static final int alphaR = 0xff;
    private static final int alphaG = 0xff;
    private static final int alphaB = 0xff;

    public static void toBufferedImage2(Picture src, BufferedImage dst) {
        byte[] data = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
        byte[] srcData = src.getPlaneData(0);
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (srcData[i] + 128);
        }
    }

    public static BufferedImage toBufferedImage(Picture src) {
        if (src.getColor() != ColorSpace.BGR) {
            Picture bgr = Picture.createCropped(src.getWidth(), src.getHeight(), ColorSpace.BGR, src.getCrop());
            if (src.getColor() == ColorSpace.RGB) {
                new RgbToBgr().transform(src, bgr);
            } else {
                Transform transform = ColorUtil.getTransform(src.getColor(), ColorSpace.RGB);
                transform.transform(src, bgr);
                new RgbToBgr().transform(bgr, bgr);
            }
            src = bgr;
        }
        BufferedImage dst = new BufferedImage(src.getCroppedWidth(), src.getCroppedHeight(),
                BufferedImage.TYPE_3BYTE_BGR);

        if (src.getCrop() == null)
            toBufferedImage2(src, dst);
        else
            toBufferedImageCropped(src, dst);

        return dst;
    }

    private static void toBufferedImageCropped(Picture src, BufferedImage dst) {
        byte[] data = ((DataBufferByte) dst.getRaster().getDataBuffer()).getData();
        byte[] srcData = src.getPlaneData(0);
        int dstStride = dst.getWidth() * 3;
        int srcStride = src.getWidth() * 3;
        for (int line = 0, srcOff = 0, dstOff = 0; line < dst.getHeight(); line++) {
            for (int id = dstOff, is = srcOff; id < dstOff + dstStride; id += 3, is += 3) {
                data[id] = (byte) (srcData[is] + 128);
                data[id + 1] = (byte) (srcData[is + 1] + 128);
                data[id + 2] = (byte) (srcData[is + 2] + 128);
            }
            srcOff += srcStride;
            dstOff += dstStride;
        }
    }

    public static void writePNG(Picture picture, File pngFile) throws IOException {
        Picture rgb = picture.getColor().equals(RGB) ? picture : convertColorSpace(picture, RGB);
        PNGEncoder encoder = new PNGEncoder();
        ByteBuffer tmpBuf = ByteBuffer.allocate(encoder.estimateBufferSize(rgb));
        ByteBuffer encoded = encoder.encodeFrame(rgb, tmpBuf).getData();
        NIOUtils.writeTo(encoded, pngFile);
    }

    public static Picture decodePNG(File f, ColorSpace tgtColor) throws IOException {
        Picture picture = decodePNG0(f);
        Preconditions.checkNotNull(picture, "cant decode " + f.getPath());
        return convertColorSpace(picture, tgtColor);
    }

    public static Picture decodePNG0(File f) throws IOException {
        PNGDecoder pngDec = new PNGDecoder();
        ByteBuffer buf = NIOUtils.fetchFromFile(f);
        VideoCodecMeta codecMeta = pngDec.getCodecMeta(buf);
        Picture pic = Picture.create(codecMeta.getSize().getWidth(), codecMeta.getSize().getHeight(),
                ColorSpace.RGB);
        return pngDec.decodeFrame(buf, pic.getData());
    }

    public static Picture convertColorSpace(Picture pic, ColorSpace tgtColor) {
        Transform tr = ColorUtil.getTransform(pic.getColor(), tgtColor);
        Picture res = Picture.create(pic.getWidth(), pic.getHeight(), tgtColor);
        tr.transform(pic, res);
        return res;
    }

    public static Picture fromBufferedImage(BufferedImage src, ColorSpace tgtColor) {
        return convertColorSpace(fromBufferedImageRGB(src), tgtColor);
    }

    public static Picture fromBufferedImageRGB(BufferedImage src) {
        Picture dst = Picture.create(src.getWidth(), src.getHeight(), RGB);
        bufImgToPicture(src, dst);
        return dst;
    }

    public static void bufImgToPicture(BufferedImage src, Picture dst) {
        byte[] dstData = dst.getPlaneData(0);

        int off = 0;
        for (int i = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++) {
                int rgb1 = src.getRGB(j, i);
                int alpha = (rgb1 >> 24) & 0xff;
                if (alpha == 0xff) {
                    dstData[off++] = (byte) (((rgb1 >> 16) & 0xff) - 128);
                    dstData[off++] = (byte) (((rgb1 >> 8) & 0xff) - 128);
                    dstData[off++] = (byte) ((rgb1 & 0xff) - 128);
                } else {
                    int nalpha = 255 - alpha;
                    dstData[off++] = (byte) (((((rgb1 >> 16) & 0xff) * alpha + alphaR * nalpha) >> 8) - 128);
                    dstData[off++] = (byte) (((((rgb1 >> 8) & 0xff) * alpha + alphaG * nalpha) >> 8) - 128);
                    dstData[off++] = (byte) ((((rgb1 & 0xff) * alpha + alphaB * nalpha) >> 8) - 128);
                }
            }
        }
    }
}
