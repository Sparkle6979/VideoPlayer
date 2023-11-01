package org.zjudevelop.playerbackbend.utils;


import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;


/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/11/1 16:28
 */
public class VideoProcessUtil {

    public static final int coverCol = 1146;
    public static final int coverRow = 717;
    public static final int keyFrameNum = 30;
    public static byte[] getCoverFromVideoPath(String videoPath){
        VideoCapture videoCapture = new VideoCapture(videoPath);

        Mat firstFrame = new Mat();
        videoCapture.read(firstFrame);

        Mat keyFrame = new Mat();
        int frameNum = 0;
        while (videoCapture.isOpened()){
            boolean read = videoCapture.read(keyFrame);
            if(++frameNum >= keyFrameNum)   break;
        }

        return frameNum == keyFrameNum ? Mat2Bytes(keyFrame) : Mat2Bytes(firstFrame);
    }

    public static byte[] Mat2Bytes(Mat mat){
        byte[] result = new byte[mat.cols()*mat.rows()];
        mat.data().get(result);
        return result;
    }
}
