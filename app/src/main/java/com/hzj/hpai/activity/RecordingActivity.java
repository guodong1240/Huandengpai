package com.hzj.hpai.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzj.hpai.R;
import com.hzj.hpai.adapter.ViewPagerAdapter;
import com.hzj.hpai.bean.Node;
import com.hzj.hpai.bean.RecordParent;
import com.hzj.hpai.bean.RecordeBean;
import com.hzj.hpai.item.Constents;
import com.hzj.hpai.maganer.SoundPlayerManager;
import com.hzj.hpai.maganer.SoundRecorderManager;
import com.hzj.hpai.maganer.VoiceManager;
import com.hzj.hpai.networkrequests.CommonListener;
import com.hzj.hpai.networkrequests.SendActtionTool;
import com.hzj.hpai.tool.UrlTool;
import com.hzj.hpai.utils.DialogUtils;
import com.hzj.hpai.utils.FileUtils;
import com.hzj.hpai.utils.LocalCacheUtil;
import com.hzj.hpai.utils.LogUtils;
import com.hzj.hpai.utils.WeakHandler;
import com.hzj.hpai.view.MyViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.bean.LocalPPT;
import db.bean.SbumitVoice;

/**
 * ppt配音
 */
public class RecordingActivity extends BaseActivity implements View.OnClickListener, CommonListener, DialogUtils.DialogPListListener {

    private TextView title_tv;
    private Button back_bt;
    private MyViewPager icon_viewpager;
    private TextView tv_lastpager;
    private TextView tv_nextpager;
    private TextView tv_pagerNum;
    private ImageView img_preview;
    private TextView tv_time;
    private ImageView img_stop;
    private ImageView img_center;
    private ImageView img_save;
    private Button bt_upload;
    private String NID = "";
    private List<RecordeBean> recordeBeanList;
    private List<ImageView> imgList;
    private ViewPagerAdapter mAdapter;
    private List<String> saveList;
    private List<SbumitVoice> uploadList;

    private SoundPlayerManager playerManager;
    private VoiceManager voiceManager;
    //当前显示的图片
    private int currentPosition = 0;
    //当前的录制状态  0 未录制  1 录制中  2 录制暂停 3 录音完成
    private int currentRecordingStatus = 0;
    private int progress_one, progress_two, progress_three, progress_four;

    @Override
    protected void findViewById() {
        title_tv = ((TextView) findViewById(R.id.title_tv));
        title_tv.setText("配置音频");
        back_bt = ((Button) findViewById(R.id.back_bt));
        back_bt.setOnClickListener(this);

        icon_viewpager = ((MyViewPager) findViewById(R.id.re_viewpager));
        tv_lastpager = ((TextView) findViewById(R.id.tv_lastpage));
        tv_nextpager = ((TextView) findViewById(R.id.tv_nextpage));
        tv_pagerNum = ((TextView) findViewById(R.id.tv_pagenumber));
        uploadList = new ArrayList<>();

        tv_lastpager.setOnClickListener(this);
        tv_nextpager.setOnClickListener(this);

        img_preview = ((ImageView) findViewById(R.id.img_preview));
        tv_time = ((TextView) findViewById(R.id.tv_time));

        img_preview.setVisibility(View.INVISIBLE);
        tv_time.setVisibility(View.INVISIBLE);
        img_preview.setOnClickListener(this);

        img_stop = ((ImageView) findViewById(R.id.img_stop));
        img_center = ((ImageView) findViewById(R.id.img_center));
        img_save = ((ImageView) findViewById(R.id.img_save));

        img_stop.setVisibility(View.INVISIBLE);
        img_save.setVisibility(View.INVISIBLE);
        img_stop.setOnClickListener(this);
        img_center.setOnClickListener(this);
        img_save.setOnClickListener(this);

        bt_upload = ((Button) findViewById(R.id.bt_upload));
        bt_upload.setOnClickListener(this);

        recorderManager = new SoundRecorderManager();
        playerManager = new SoundPlayerManager();
        voiceManager = VoiceManager.getInstance(getApplicationContext());

        icon_viewpager.setIsCanScroll(true);
        getURLData();
        queryAllSum();
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recording);

        verifyStoragePermissions(this);
        saveList = new ArrayList<>();
        recordeBeanList = new ArrayList<>();
        imgList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            NID = intent.getStringExtra("RecordingActivity");
            LogUtils.d("======getIntent==" + NID);
        }
        getLocalFile();

    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
           // Manifest.permission.RECORD_AUDIO
    };

    private static String [] ss_permission={Manifest.permission.RECORD_AUDIO};


    private void verifyStoragePermissions(RecordingActivity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ss=ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (ss!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, ss_permission,REQUEST_EXTERNAL_STORAGE);
        }

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    //获得本地录音文件
    private void getLocalFile() {
        String string = path;
        LogUtils.d("====string===");
        File file = new File(string);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    String[] split = files[i].getName().split("/.");
                    int num = files[i].getName().indexOf(".");
                    String str = files[i].getName().substring(0, num);
                    LogUtils.d(files[i].getName() + "===" + str);
                    saveList.add(str);
                }
            }

        }

    }

    private void setPreviewVoice(int position) {
        if (saveList.size() > 0) {
            if (saveList.contains(recordeBeanList.get(position).getNode_id() + "_0")) {
                img_preview.setImageResource(R.drawable.preview);
                img_preview.setVisibility(View.VISIBLE);
                tv_time.setVisibility(View.INVISIBLE);
                //tv_time.setText("0:00");
                tryListener = false;
            } else {
                img_preview.setVisibility(View.INVISIBLE);
                tv_time.setVisibility(View.INVISIBLE);
            }
        }


    }

    private void getURLData() {
        SendActtionTool.getJson(Constents.VOICE_ICON_LIST, NID, "iconList", this);
    }

    private void updateViewPager() {
        for (int i = 0; i < recordeBeanList.size(); i++) {
            ImageView img = new ImageView(getApplicationContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            img.setLayoutParams(params);
            // img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String url = recordeBeanList.get(i).getImage_url();
            Glide.with(getApplicationContext()).load(url).error(R.drawable.bgimg).into(img);
            imgList.add(img);
        }
        if (imgList != null) {
            mAdapter = new ViewPagerAdapter(imgList);
            icon_viewpager.setAdapter(mAdapter);
        }
        icon_viewpager.setCurrentItem(0);

        icon_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (tryStatus == 1) {
                    playerManager.stopPlaying();
                    tryStatus = 0;
                }
                if (currentRecordingStatus == 1 || currentRecordingStatus == 2) {
                    icon_viewpager.setIsCanScroll(false);
                    // saveFile();
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                tv_pagerNum.setText((position + 1) + "/" + imgList.size());
                setPreviewVoice(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setPreviewVoice(0);


    }

    private boolean isStop = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_bt:
                img_center.setImageResource(R.drawable.record);
                img_stop.setImageResource(R.drawable.rerecording);
                if (tryStatus == 1) {
                    playerManager.stopPlaying();
                    tryListener = false;
                    tryStatus = 0;
                }
                if (currentRecordingStatus == 1 || currentRecordingStatus == 2) {
                    saveFile();
                } else {
                    finish();
                }
                break;
            case R.id.tv_lastpage:
                img_center.setImageResource(R.drawable.record);
                img_stop.setImageResource(R.drawable.rerecording);
                if (tryStatus == 1 || tryListener) {
                    playerManager.stopPlaying();
                    tryListener = false;
                    tryStatus = 0;
                }
                if (currentRecordingStatus == 0 || currentRecordingStatus == 3) {
                    resetAll();
                    if (currentPosition > 0) {
                        tv_pagerNum.setText(currentPosition + "/" + imgList.size());
                        currentPosition--;
                        icon_viewpager.setCurrentItem(currentPosition);
                        setPreviewVoice(currentPosition);
                        tv_time.setText("00:00");
                    }

                } else {
                    saveFile();
                }
                break;
            case R.id.tv_nextpage:


                if (tryStatus == 1 || tryListener) {
                    if (playerManager != null) {
                        playerManager.stopPlaying();
                    }
                    tryListener = false;
                    tryStatus = 0;
                }
                if (currentRecordingStatus == 0 || currentRecordingStatus == 3) {
                    resetAll();
                    if (currentPosition < (imgList.size() - 1)) {
                        currentPosition++;
                        icon_viewpager.setCurrentItem(currentPosition);
                        setPreviewVoice(currentPosition);
                        tv_time.setText("00:00");
                        tv_pagerNum.setText((currentPosition + 1) + "/" + imgList.size());
                    }

                } else {
                    saveFile();
                }
                break;
            case R.id.img_center:
                changeRecording();
                break;
            case R.id.img_stop:
                stopRecord();
                break;
            case R.id.img_save:
                saveFile();
                break;
            case R.id.img_preview:
                previewVoice();
                break;
            case R.id.bt_upload:
                if (tryStatus == 1 || tryListener) {
                    playerManager.stopPlaying();
                    tryListener = false;
                    tryStatus = 0;
                }
                showLoadDialog();
                // upLoadVoice();
                break;
        }
    }

    //显示上传dialog
    private void showLoadDialog() {
        DialogUtils.createTipDialog(this, new DialogUtils.DialogTipListener() {
            @Override
            public void onDeleteListener(LocalPPT ppt) {
                upLoadVoice();

            }
        }, new LocalPPT(), "上传").show();


    }

    //将保存的音频上传服务器
    private int TotalCount = 0;

    private void upLoadVoice() {
        List<SbumitVoice> list = new ArrayList<>();
        if (voiceManager != null) {
            list = voiceManager.queryByNid(NID);
        } else {
            voiceManager = VoiceManager.getInstance(getApplicationContext());
            list = voiceManager.queryByNid(NID);
        }
        TotalCount = list.size();
        //  uploadList.clear();
        //  uploadList.addAll(list);
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(Constents.UID, "");
        LogUtils.d("=====uid==" + string);
        Map<String, String> map = UrlTool.getMapParams(Constents.UID, string);
        Map<String, File> fileMap = new HashMap<>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // TotalCount--;
                String title = list.get(i).getTitle();
                String path = list.get(i).getPath();
                String noid = list.get(i).getNid();
                File file = new File(path);

                fileMap.put(title, file);

                SendActtionTool.postFile(Constents.VOICE_UPLOAD, this, map, title, file, "uploadVoice");
                LogUtils.d("==upload==" + list.get(i).getTitle() + "===" + TotalCount);
                voiceManager.deleteUser(list.get(i));
            }
            //  SendActtionTool.postFiles(Constents.VOICE_UPLOAD, this, fileMap, map, "uploadVoice");
        } else {
            Toast.makeText(this, "您暂时还没有保存的音频", Toast.LENGTH_SHORT).show();
        }

    }

    //试听标志，false未试听，true在试听
    private boolean tryListener = false;
    //试听的状态  0停止 1暂停
    private int tryStatus = 0;

    //试听录音
    private void previewVoice() {
        if (playerManager == null) {
            playerManager = new SoundPlayerManager();
        }
        if (!tryListener) {
            if (tryStatus == 1) {
                img_preview.setImageResource(R.drawable.pre_stop);
                playerManager.rePlay();
                tryListener = true;
                setPreviewTime();
            } else {
                String temporaryPath = LocalCacheUtil.temporaryVoicePath.getPath() + File.separator + "try.mp3";
                File file = new File(temporaryPath);
                if (file.exists()) {
                    file.delete();
                }
                img_preview.setImageResource(R.drawable.pre_stop);
                String pathss = path + File.separator + recordeBeanList.get(currentPosition).getNode_id() + "_0.mp3";
                boolean b = FileUtils.copyFile(pathss, temporaryPath);
                if (b) {
                    LogUtils.d("==localPath==" + pathss);
                    LogUtils.d("==temporaryPath==" + temporaryPath);
                    File files = new File(temporaryPath);

                    playerManager.startPlaying(files);
                    tv_time.setVisibility(View.VISIBLE);
                    currentTime = playerManager.getDuration() / 1000 + 1;
                    LogUtils.d("time" + "===" + currentTime);
                    LogUtils.d("totaltime==" + "===" + playerManager.getDuration());
                    tryListener = true;
                    setPreviewTime();
                }

            }


        } else {
            img_preview.setImageResource(R.drawable.preview);
            LogUtils.d("===stopListener==");
            playerManager.onPausePlaying();
            tryStatus = 1;
            tryListener = false;
        }


    }


    //试听时的时间
    private int currentTime = 0;

    private void setPreviewTime() {
        // currentTime = progress;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (tryListener) {
                        Thread.sleep(1000);
                        mHandler.sendEmptyMessage(200);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    //保存录音信息
    private void saveFile() {

        if (currentRecordingStatus == 0 || currentRecordingStatus == 3 || currentRecordingStatus == 2) {
        } else {
            img_center.setImageResource(R.drawable.record);
            img_stop.setImageResource(R.drawable.rerecording);
//            if (recorderManager != null) {
//                recorderManager.stopRecording();
//            }
            // currentRecordingStatus = 0;
        }
        if (tryStatus == 1 || tryListener) {
            playerManager.stopPlaying();
            tryListener = false;
            tryStatus = 0;
        }
        isStop = true;
        tryListener = false;
        String[] string = new String[]{"您确定要保存音频吗？", "保存", "保存并翻页", "取消", "不保存"};
        Dialog dialog = DialogUtils.createMakepptDialog(this, this, null, string);
        dialog.show();

    }

    //控制进行声音录制或者停止
    private void changeRecording() {

        if (tryStatus == 1 && playerManager != null) {
            playerManager.stopPlaying();
            tryStatus = 0;
            tryListener = false;
        }

        if (currentRecordingStatus == 0 || currentRecordingStatus == 3) {
            //开始录制
            startRecording();
        } else if (currentRecordingStatus == 2) {
            //恢复录制
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                reRecord();
//            } else {
            startRecording();
            // }
        }

    }

    //恢复录制
    private void reRecord() {
        tryListener = false;
        LogUtils.d("===恢复录制==");
        img_preview.setVisibility(View.INVISIBLE);
        img_center.setImageResource(R.drawable.recording);
        img_stop.setImageResource(R.drawable.pause);
        currentRecordingStatus = 1;
        if (recorderManager != null) {
            recorderManager.onRestartRecording();
            setTime();
        }
        isStop = false;

    }

    //暂停录音

    private void stopRecord() {
        isStop = true;

        //暂停
        if (currentRecordingStatus == 1) {
            LogUtils.d("===暂停==");
            currentRecordingStatus = 2;
            if (recorderManager != null) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    recorderManager.onPauseRecording();
//                    img_center.setImageResource(R.drawable.restorationrecording);
//                } else {
                recorderManager.stopRecording();
                img_center.setImageResource(R.drawable.record);
                // }
            }
            img_stop.setImageResource(R.drawable.rerecording);
            img_preview.setVisibility(View.VISIBLE);

        } else if (currentRecordingStatus == 2) {
            LogUtils.d("==重录==");
            //重录
            tv_time.setText("00:00");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                if (recorderManager != null) {
//                    recorderManager.stopRecording();
//                }
//            }
            //startRecording();
            tryListener = false;
            img_preview.setVisibility(View.INVISIBLE);
            img_stop.setVisibility(View.INVISIBLE);
            img_save.setVisibility(View.INVISIBLE);
            img_center.setImageResource(R.drawable.record);
            currentRecordingStatus = 0;
            if (tryListener || (tryStatus != 0)) {
                if (playerManager != null) {
                    playerManager.stopPlaying();
                    img_preview.setImageResource(R.drawable.preview);
                    tv_time.setVisibility(View.INVISIBLE);
                    tryListener = false;
                    tryStatus = 0;
                }

            }

        }
    }

    //跟路径
    private String path = LocalCacheUtil.voiceFilePath.getAbsolutePath();
    //保存路径
    private String savePath;
    private SoundRecorderManager recorderManager;


    //开始录制
    private void startRecording() {

        if (recorderManager == null) {
            recorderManager = new SoundRecorderManager();
        }
        tryListener = false;
        LogUtils.d("===开始录制===");
        progress = 0;
        isStop = false;
        img_center.setImageResource(R.drawable.recording);
        img_stop.setVisibility(View.VISIBLE);
        img_stop.setImageResource(R.drawable.pause);
        img_save.setVisibility(View.VISIBLE);
        tv_time.setVisibility(View.VISIBLE);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            savePath = path + File.separator + recordeBeanList.get(currentPosition).getNode_id() + "_0.mp3";
            //把有关本页录音的本地缓存清掉
            File file1 = new File(savePath);
            if (file1.exists()) {
                file1.delete();
            }
            recorderManager.startRecording(savePath);
            currentRecordingStatus = 1;
            setTime();
        }

    }

    private int progress = 0;

    //录音开始时的时间进度
    private void setTime() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (!isStop) {
                    try {
                        Thread.sleep(1000);
                        LogUtils.d("thread==" + progress);
                        mHandler.sendEmptyMessage(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    public void onSuccess(Object action, Object value) {
        LogUtils.d("==onSuccess=" + action.toString() + "==" + value.toString());
        action = action + "";
        switch (((String) action)) {
            case "iconList":
                try {
                    List<RecordParent.RecordData> dataList = new Gson().fromJson(((JSONObject) value).getString("datas"), new TypeToken<List<RecordParent.RecordData>>() {
                    }.getType());
                    for (int i = 0; i < dataList.size(); i++) {
                        RecordeBean bean = dataList.get(i).getData();
                        recordeBeanList.add(bean);
                    }
                    tv_pagerNum.setText("1/" + recordeBeanList.size());
                    updateViewPager();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case "uploadVoice":
                LogUtils.d("upload_success");
                LogUtils.d("==pri==total=" + TotalCount);
                if (TotalCount == 1) {
                    bt_upload.setText("同步音频（0)");
                    Toast.makeText(this, "上传成功！", Toast.LENGTH_SHORT).show();
                } else {
                    if (TotalCount>0){
                        TotalCount--;
                    }
                }
//                if (uploadList.size() > 0 && voiceManager != null) {
//                    for (int i = 0; i < uploadList.size(); i++) {
//                        voiceManager.deleteUser(uploadList.get(i));
//                    }
//                }

                break;
        }
    }


    @Override
    public void onFaile(Object action, Object value) {
        LogUtils.d("onFaile" + value.toString());
        LogUtils.d("onfaile===" + action.toString());

    }

    @Override
    public void onException(Object action, Object value) {
        LogUtils.d("onException" + value.toString());

    }

    @Override
    public void onStart(Object action) {

    }

    @Override
    public void onFinish(Object action) {
        LogUtils.d("onFinish");

    }

    private WeakHandler mHandler = new WeakHandler(this) {
        @Override
        public void conventHandleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    progress++;
                    LogUtils.d("===handler=" + progress);
                    setProgressTime(progress);
                    break;
                case 200:
                    if (currentTime > 0) {
                        currentTime--;
                        setProgressTime(currentTime);
                    } else {
                        tryListener = false;
                        img_preview.setImageResource(R.drawable.preview);
                        tryStatus = 0;
                    }
            }
        }
    };

    //计算时间
    private void setProgressTime(int progress) {
        progress_one = 0;
        progress_two = 0;
        progress_three = 0;
        progress_four = 0;

        progress_one = progress / 60 / 10;
        progress_two = (progress / 60) % 10;
        progress_three = progress % 60 / 10;
        progress_four = progress % 60 % 10;

        StringBuilder builder = new StringBuilder();
        builder.append(progress_one).append(progress_two).append(":").append(progress_three).append(progress_four);
        tv_time.setText(builder);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (recorderManager != null) {
            recorderManager = null;
        }
        if (playerManager != null) {
            playerManager = null;
        }
    }

    @Override
    public void onMakeListener(Node ppt) {

    }

    //保存
    @Override
    public void onPostListener(Node ppt) {
        /// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (currentRecordingStatus != 0) {
//                recorderManager.stopRecording();
//                currentRecordingStatus = 0;
//            }
        //  } else {
        if ((currentRecordingStatus != 0) && (currentRecordingStatus != 2)) {
            recorderManager.stopRecording();
            currentRecordingStatus = 0;
        }

        // }
        img_stop.setVisibility(View.INVISIBLE);
        img_save.setVisibility(View.INVISIBLE);
        img_center.setImageResource(R.drawable.record);
        currentRecordingStatus = 3;
        if (tryStatus == 1) {
            playerManager.stopPlaying();
            tryListener = false;
            tryStatus = 0;
        }
        icon_viewpager.setIsCanScroll(true);
        getLocalFile();
        saveToDB();
    }

    //保存到数据库
    private void saveToDB() {
        String save_path = path + File.separator + recordeBeanList.get(currentPosition).getNode_id() + "_0.mp3";
        if (voiceManager != null) {
            List<SbumitVoice> list = voiceManager.queryVoiceList(recordeBeanList.get(currentPosition).getNode_id() + "_0.mp3");
            if (list != null && (list.size() != 0)) {
                SbumitVoice voice = list.get(0);
                voiceManager.updateUser(voice);
            } else {
                SbumitVoice voice = new SbumitVoice();
                voice.setNid(NID);
                voice.setTitle(recordeBeanList.get(currentPosition).getNode_id() + "_0.mp3");
                voice.setPath(save_path);
                voiceManager.insertUser(voice);
                queryAllSum();
            }
        } else {
            voiceManager = VoiceManager.getInstance(getApplicationContext());
            SbumitVoice voice = new SbumitVoice();
            voice.setNid(NID);
            voice.setTitle(recordeBeanList.get(currentPosition).getNode_id() + "_0.mp3");
            voice.setPath(save_path);
            voiceManager.insertUser(voice);
            queryAllSum();
        }

    }

    //查询所有需要提交的录音
    private void queryAllSum() {
        List<SbumitVoice> sbumitVoices = null;
        if (voiceManager != null) {
            sbumitVoices = voiceManager.queryByNid(NID);
        } else {
            voiceManager = VoiceManager.getInstance(getApplicationContext());
            sbumitVoices = voiceManager.queryByNid(NID);
        }
        if (sbumitVoices != null) {
            TotalCount = sbumitVoices.size();
            bt_upload.setText("同步音频（" + sbumitVoices.size() + ")");
        }
    }

    //TODO 保存并翻页
    @Override
    public void onDeleteLsitener(Node ppt) {

        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (currentRecordingStatus != 0) {
//                recorderManager.stopRecording();
//            }
        // } else {
//            if ((currentRecordingStatus != 0) && (currentRecordingStatus != 2)) {
//                recorderManager.stopRecording();
//            }
        //  }

        if ((currentRecordingStatus != 0) && (currentRecordingStatus != 2)) {
            recorderManager.stopRecording();
        }
        currentRecordingStatus = 3;
        if (tryStatus == 1 || tryListener) {
            playerManager.stopPlaying();
            tryListener = false;
            tryStatus = 0;
        }
        icon_viewpager.setIsCanScroll(true);
        if (currentPosition < imgList.size() - 1) {
            LogUtils.d("=====currentPositon==xiaoyu==" + currentPosition + "==" + imgList.size());
            saveToDB();
            icon_viewpager.setCurrentItem(currentPosition + 1);
            resetAll();
            getLocalFile();
            //currentPosition++;
        } else if (currentPosition == (imgList.size() - 1)) {
            LogUtils.d("=====currentPositon===" + currentPosition + "==" + imgList.size());
            saveToDB();
            resetAll();
            getLocalFile();
        }
    }

    //翻页后初始化所有的数据
    private void resetAll() {
        img_stop.setVisibility(View.INVISIBLE);
        img_save.setVisibility(View.INVISIBLE);
        img_center.setImageResource(R.drawable.record);
        img_preview.setVisibility(View.INVISIBLE);
        tv_time.setText("00:00");
        tv_time.setVisibility(View.INVISIBLE);
        currentRecordingStatus = 0;

        //

    }

    //不保存
    @Override
    public void onShareListener(Node ppt) {

        File file = new File(savePath);
        if (file.exists()) {
            file.delete();
        }
        if (tryStatus == 1) {
            playerManager.stopPlaying();
            tryListener = false;
            tryStatus = 0;
        }

        //   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (currentRecordingStatus != 0) {
//                recorderManager.stopRecording();
//                currentRecordingStatus = 0;
//            }
//        } else {
        if ((currentRecordingStatus != 0) && (currentRecordingStatus != 2)) {
            recorderManager.stopRecording();
            currentRecordingStatus = 0;
        }

//        }
//        if ((currentRecordingStatus != 0) && (currentRecordingStatus != 2)) {
//            recorderManager.stopRecording();
//            currentRecordingStatus = 0;
//        }
        img_stop.setVisibility(View.INVISIBLE);
        img_save.setVisibility(View.INVISIBLE);
        img_center.setImageResource(R.drawable.record);
        tv_time.setVisibility(View.INVISIBLE);
        img_preview.setVisibility(View.INVISIBLE);
        currentRecordingStatus = 3;
        tv_time.setText("00:00");
        icon_viewpager.setIsCanScroll(true);
        getLocalFile();

    }

    @Override
    public void onCancleListener() {
        currentRecordingStatus = 2;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tryStatus == 1) {
                playerManager.stopPlaying();
                tryListener = false;
                tryStatus = 0;
            }
            if (currentRecordingStatus == 1 || currentRecordingStatus == 2) {
                saveFile();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}
