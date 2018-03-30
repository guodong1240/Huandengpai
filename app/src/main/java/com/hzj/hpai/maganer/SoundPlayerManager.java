package com.hzj.hpai.maganer;

import android.media.MediaPlayer;

import com.hzj.hpai.utils.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * 音频播放
 * Created by zx on 2017/1/5.
 */

public class SoundPlayerManager {
    MediaPlayer mPlayer;

    public void startPlaying(File fileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName.getAbsolutePath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            LogUtils.d("prepare_faile" + e.toString());
        }
    }

    public int getDuration() {
        int duration = mPlayer.getDuration();
        return duration;
    }
    public void onPausePlaying(){
        mPlayer.pause();
    }

    public void rePlay(){
        mPlayer.start();
    }

    public void stopPlaying() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

}
