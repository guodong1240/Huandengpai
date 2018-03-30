package com.hzj.hpai.maganer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import db.bean.SbumitVoice;
import db.dao.DaoMaster;
import db.dao.DaoSession;
import db.dao.SbumitVoiceDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zx on 2016/12/29.
 */

public class VoiceManager {

    private final static String db_name = "SbumitVoice-db";
    private static VoiceManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public VoiceManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, db_name, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static VoiceManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VoiceManager.class) {
                if (mInstance == null) {
                    mInstance = new VoiceManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, db_name, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, db_name, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录
     *
     * @param voice
     */
    public void insertUser(SbumitVoice voice) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        sbumitVoiceDao.insert(voice);
    }

    /**
     * 插入用户集合
     *
     * @param voice
     */
    public void insertUserList(List<SbumitVoice> voice) {
        if (voice == null || voice.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        sbumitVoiceDao.insertInTx(voice);
    }

    /**
     * 删除一条记录
     *
     * @param voice
     */
    public void deleteUser(SbumitVoice voice) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        sbumitVoiceDao.delete(voice);
    }

    /**
     * 更新一条记录
     *
     * @param voice
     */
    public void updateUser(SbumitVoice voice) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        sbumitVoiceDao.update(voice);
    }

    /**
     * 查询用户列表
     */
    public List<SbumitVoice> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        QueryBuilder<SbumitVoice> sbumitVoiceQueryBuilder = sbumitVoiceDao.queryBuilder();
        List<SbumitVoice> list = sbumitVoiceQueryBuilder.list();
        return list;
    }


    /**
     * 删除所有记录
     */
    public void deleteAll() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        sbumitVoiceDao.deleteAll();
    }

    /**
     * 查询相同title的列表
     */
    public List<SbumitVoice> queryVoiceList(String title) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        QueryBuilder<SbumitVoice> qb = sbumitVoiceDao.queryBuilder();
        qb.where(SbumitVoiceDao.Properties.Title.eq(title));
        List<SbumitVoice> list = qb.list();
        return list;
    }
    /**
     * 查询相同nid的列表
     */
    public List<SbumitVoice> queryByNid(String nid) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SbumitVoiceDao sbumitVoiceDao = daoSession.getSbumitVoiceDao();
        QueryBuilder<SbumitVoice> qb = sbumitVoiceDao.queryBuilder();
        qb.where(SbumitVoiceDao.Properties.Nid.eq(nid));
        List<SbumitVoice> list = qb.list();
        return list;
    }

//    /**
//     * 查询用户列表
//     */
//    public List<VideoLookBean> queryUserList(int age) {
//        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
//        DaoSession daoSession = daoMaster.newSession();
//        VideoLookBeanDao userDao = daoSession.getVideoLookBeanDao();
//        QueryBuilder<VideoLookBean> qb = userDao.queryBuilder();
//        qb.where(VideoLookBeanDao.Properties.Look.gt(age)).orderAsc(VideoLookBeanDao.Properties.Look);
//        List<VideoLookBean> list = qb.list();
//        return list;
//    }


}
