package com.chauncy.cloud.audio;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/7/8 21:38
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AudioTest {

    @Test
    public void audioTest3() {
        String ffmpegPath = "C:\\ffmpeg20200809\\bin\\ffmpeg.exe";
        String inputVideoPath = "C:\\Users\\Administrator\\Desktop\\biwen\\1.mp3";
        // 组装 格式转换 命令
        List<String> command = new LinkedList<>();
        command.add(ffmpegPath);
        command.add("-i");
        command.add(inputVideoPath);
        //command.add(outputVideoPath);
        try {
            // 执行命令
            exeute(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //@Test
    //public void audioTest1() {
    //    System.setProperty("jna.encoding", "UTF-8");
    //
    //    System.out.println("init model ok");
    //    String filename = "/home/xxx/java_jna/src/com/faceid/app/test.jpg"; //修改图片的路径
    //    Pointer ptr = FaceId.INSTANCE.get_feature_from(filename);
    //    String feature_base64 = ptr.getString(0);
    //    System.out.println("feature_base64: " + feature_base64);
    //    Native.free(Pointer.nativeValue(ptr));
    //}
    //
    //@Test
    //public void audioTest2() {
    //
    //    String   = "D:\\devsoft\\ffmpeg\\bin";
    //    Runtime run = null;
    //    System.out.println(new File(webroot).getAbsolutePath());
    //    try {
    //        run = Runtime.getRuntime();
    //        long start = System.currentTimeMillis();
    //        //File diretory = new File("");
    //        System.out.println("开始");
    //        Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -i " + sourcePath + " -codec copy " + targetPath);
    //        System.out.println("结束");
    //        long end = System.currentTimeMillis();
    //        System.out.println("转化结束" + (end - start));
    //        p.getOutputStream().close();
    //        p.getInputStream().close();
    //        p.getErrorStream().close();
    //        p.waitFor();
    //        return true;
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return false;
    //    } finally {
    //        run.freeMemory();
    //    }
    //
    //
    //    //要输出的音频格式
    //    String outputFormat = "mp3";
    //
    //
    //    /**
    //     * 获得转化后的文件名
    //     * @param sourceFilePath : 源视频文件路径
    //     * @return
    //     */
    //    public static String getNewFileName (String sourceFilePath){
    //        File source = new File(sourceFilePath);
    //        String fileName = source.getName().substring(0, source.getName().lastIndexOf("."));
    //        return fileName + "." + outputFormat;
    //    }
    //
    //    /**
    //     * 转化音频格式
    //     * @param sourceFilePath : 源视频文件路径
    //     * @param targetFilePath : 目标音乐文件路径
    //     * @return
    //     */
    //    public static void transform (String sourceFilePath, String targetFilePath){
    //        File source = new File(sourceFilePath);
    //        File target = new File(targetFilePath);
    //        // 设置音频属性
    //        AudioAttributes audio = new AudioAttributes();
    //        audio.setCodec(null);
    //        // 设置转码属性
    //        EncodingAttributes attrs = new EncodingAttributes();
    //        attrs.setOutputFormat(outputFormat);
    //        attrs.setAudioAttributes(audio);
    //        try {
    //            // 音频转换格式类
    //            Encoder encoder = new Encoder();
    //            MultimediaObject mediaObject = new MultimediaObject(source);
    //            encoder.encode(mediaObject, target, attrs);
    //            System.out.println("转换已完成...");
    //        } catch (EncoderException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    /**
    //     * 批量转化音频格式
    //     * @param sourceFolderPath : 源视频文件夹路径
    //     * @param targetFolderPath : 目标音乐文件夹路径
    //     * @return
    //     */
    //    public static void batchTransform (String sourceFolderPath, String targetFolderPath){
    //        File sourceFolder = new File(sourceFolderPath);
    //        if (sourceFolder.list().length != 0) {
    //            Arrays.asList(sourceFolder.list()).forEach(e -> {
    //                transform(sourceFolderPath + "\\" + e, targetFolderPath + "\\" + getNewFileName(e));
    //            });
    //        }
    //    }
    //
    //
    //    public static void main (String[]args){
    //        batchTransform("C:\\Users\\tarzan\\Desktop\\video", "C:\\Users\\tarzan\\Desktop\\audio");
    //    }
    //
    //}

    /**
     * 执行命令
     *
     * @param command 命令
     */
    public void exeute(List<String> command) throws IOException {
        // 执行命令
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start(); // 开始执行
        InputStream errorStream = process.getErrorStream(); // 字节流
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream); // 字节流和字符流的中介
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // 字符流
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line); // 输出转换过程
        }
        // 关闭流
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }


}
