package com.chauncy.cloud.audio;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/7/8 22:16
 */
import ws.schild.jave.*;

import java.io.File;

/**
 *  音频转化工具类
 */
public class AudioConvertUtils {
    /**
     * 将本地音频文件转换成mp3格式文件
     *
     * @param localFilePath 本地音频文件物理路径
     * @param targetPath    转换后mp3文件的物理路径
     * @return 音频长度
     */
    public static long changeLocalSourceToMp3(String localFilePath, String targetPath) throws Exception {

        File source = new File(localFilePath);
        MultimediaObject multimediaObject = new MultimediaObject(source);
        MultimediaInfo infos= multimediaObject.getInfo();
        AudioInfo audio1 = infos.getAudio();


        File target = new File(targetPath);

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setSamplingRate(new Integer(11025));

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        Encoder encoder = new Encoder();
        encoder.encode(multimediaObject, target, attrs);
        //返回音频长度
        return multimediaObject.getInfo().getDuration();
    }
}