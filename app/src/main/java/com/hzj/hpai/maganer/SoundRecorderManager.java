package com.hzj.hpai.maganer;

import android.media.MediaRecorder;
import android.os.Build;

import com.czt.mp3recorder.MP3Recorder;
import com.hzj.hpai.utils.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * 录音
 * Created by zx on 2017/1/5.
 */

public class SoundRecorderManager {
    MediaRecorder mRecorder;
    boolean isRecording;
    MP3Recorder mp3Recorder;

    public void startRecording(String string) {
        mp3Recorder = new MP3Recorder(new File(string));
//        mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//        mRecorder.setOutputFile(new File(string).getAbsolutePath());
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
//            mRecorder.prepare();
//            mRecorder.start();
            mp3Recorder.start();
            isRecording = true;
        } catch (IOException e) {
            LogUtils.d("==faile===" + e.toString());
        }

    }


    public void onPauseRecording() {
        if (isRecording) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mRecorder.pause();
            } else {
                LogUtils.d("api 太低不支持！");
            }
            isRecording = false;
        }
    }

    public void onRestartRecording() {
//        if (!isRecording) {
        mRecorder.start();
        isRecording = true;
//        }
    }


    public void stopRecording() {
        // mRecorder.stop();
        mp3Recorder.stop();
        // mRecorder.release();
        //  mRecorder = null;
        mp3Recorder = null;
        isRecording = false;
    }


}
