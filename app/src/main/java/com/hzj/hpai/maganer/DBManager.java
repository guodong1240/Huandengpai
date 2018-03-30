package com.hzj.hpai.maganer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import db.bean.LocalPPT;
import db.dao.DaoMaster;
import db.dao.DaoSession;
import db.dao.LocalPPTDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zx on 2016/12/29.
 */

public class DBManager {

    private final static String db_name="LocalPPT-db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper=new DaoMaster.DevOpenHelper(context,db_name,null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context){
        if (mInstance==null){
            synchronized (DBManager.class){
                if (mInstance==null){
                    mInstance=new DBManager(context);
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
     * @param ppt
     */
    public void insertUser(LocalPPT ppt) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        LocalPPTDao localPPTDao = daoSession.getLocalPPTDao();
        localPPTDao.insert(ppt);
    }

    /**
     * 插入用户集合
     *
     * @param ppt
     */
    public void insertUserList(List<LocalPPT> ppt) {
        if (ppt == null || ppt.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        LocalPPTDao localPPTDao = daoSession.getLocalPPTDao();
        localPPTDao.insertInTx(ppt);
    }

    /**
     * 删除一条记录
     *
     * @param ppt
     */
    public void deleteUser(LocalPPT ppt) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        LocalPPTDao localPPTDao = daoSession.getLocalPPTDao();
        localPPTDao.delete(ppt);
    }

    /**
     * 更新一条记录
     *
     * @param user
     */
    public void updateUser(LocalPPT user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        LocalPPTDao localPPTDao = daoSession.getLocalPPTDao();
        localPPTDao.update(user);
    }

    /**
     * 查询用户列表
     */
    public List<LocalPPT> queryUserList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        LocalPPTDao localPPTDao = daoSession.getLocalPPTDao();
        QueryBuilder<LocalPPT> qb = localPPTDao.queryBuilder();
        List<LocalPPT> list = qb.list();
        return list;
    }


    /**
     * 删除所有记录
     */
    public void deleteAll() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        LocalPPTDao localPPTDao = daoSession.getLocalPPTDao();
        localPPTDao.deleteAll();
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
