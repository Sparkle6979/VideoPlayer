package org.zjudevelop.playerbackbend.utils;



import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.zjudevelop.playerbackbend.pojo.exception.FrameGrabException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/1 16:28
 */
public class VideoProcessUtil {

//    private static final int coverCol = 1146;
//    private static final int coverRow = 717;
    private static final int keyFrameNum = 30;
    public static byte[] getCoverFromVideoPath(String videoPath, int col,int row) {

        int frameNumber = keyFrameNum;


        Picture picture = null;
        try {
            picture = FrameGrab.getFrameFromFile(new File(videoPath), frameNumber);
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            BufferedImage resultImage = new BufferedImage(col, row, BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = resultImage.createGraphics();
            graphics.drawImage(bufferedImage,0,0,col,row,null);
            graphics.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", os);
            return os.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JCodecException e) {
            throw new RuntimeException(e);
        }
    }
}
