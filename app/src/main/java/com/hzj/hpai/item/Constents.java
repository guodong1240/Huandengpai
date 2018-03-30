package com.hzj.hpai.item;

/**
 * Created by zx on 2016/12/26.
 */

public class Constents {
    //开发地址
    public static final String kfUrl = "http://m.kaifa.huandengpai.com/app/home/new?c=__new&s=0";
    //正式地址
    public static final String zsUrl = "http://m.mp.huandengpai.com/app/home/new?c=__new&s=0";

    //qa站
    public static final String qaUrl = "http://m.qa.huandengpai.com/app/home/new?c=__new&s=0";


    //开发地址
    public static final String kfHome="http://m.kaifa.huandengpai.com";
    //正式地址
    public static final String zsHome="http://m.mp.huandengpai.com";

    //qa站
    public static final String qaHome="http://m.qa.huandengpai.com";

    //项目中的请求地址 主页
    public static final String URL = zsUrl;
    //项目中请求地址  子页
    public static final String URLHome = zsHome;


    //检测版本
    public static final String CheckVersion=URLHome+"/apiajax/ppt/app/android/version";

    //PPT文件列表--后面直接接uid的具体值
    public static final String PPT_LIST = URLHome + "/ajaxapi/ppt/app/studio/pptlist/";

    //本地ppt上传
    public static final String UPLOAD_PPT = URLHome + "/apiajax/ppt/app/pptfile/AppStudioCreatePPTFileNode";

    //修改ppt
    public static final String CHANGE_PPT_ONLINE = URLHome + "/apiajax/ppt/app/pptshare/setShareFileSimpleInfo?data=";

    //得到ppt基本信息
    public static final String GETPPT_DETAIL = URLHome + "/apiajax/ppt/app/pptshare/getPPTShareFileSimpleInfo?data=";

    //用户信息
    public static final String USER_Message="http://dev.huandengpai.com/apiajax/ppt/app/pptuser/getAppPPTUserSimpleInfoByUid?data=";
    public static final String VOICE_ICON_LIST = URLHome + "/api/file/images/";

    //上传音频文件
    public static final String VOICE_UPLOAD = URLHome + "/apiajax/ppt/app/pptvocie/AppStudioCreatePPTVoiceNode?";

    //得到音频信息
    public static final String GETVOICE_MESSAGE = URLHome + "/apiajax/ppt/app/pptvocie/getAppStudioPPTVoiceSimpleInfo?data=";

    //得到分享信息
    public static final String GetShareMessage = URLHome + "/api/file/info/";


    //微信登录后面直接接授权成功后的unionid
    public static final String WX_LOGIN=URLHome+"/app/u/joinlogin/weixin/entry?";

    public static final String VoiceStyle_No = "nothing";
    public static final String VoiceStyle_HAVE = "ppt_files_audio";

    public static final String UID = "uid";
    public static final String USER_EMAIL="email";
}
