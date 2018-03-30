package com.hzj.hpai.networkrequests;

import android.widget.Toast;

import com.hzj.hpai.maganer.BaseApplication;
import com.hzj.hpai.tool.NetworkHelper;
import com.hzj.hpai.tool.UrlTool;
import com.hzj.hpai.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.action;

/**
 * @author zxm
 * @2015年4月1日
 * @descripte 发送网络请求
 */
public class SendActtionTool {


    /**
     * 2015年4月1日
     *
     * @param url      带参数字段的 url xx.com?name=a&psd=chuagn123
     * @param tag      请求标签
     * @param action   业务指令信号
     * @param listener 回调业务
     */
    public static void post(final String url, final Object tag,
                            final Object action, final CommonListener listener) {
        post(url, listener, UrlTool.getMapParams());
    }

    /**
     * @param url      网络地址
     * @param //tag    请求标签 建议传入activity?fragment的引用，方便页面销毁时取消该页面内的网络请求
     * @param //action 业务指令信号
     * @param listener 回调
     * @param params   参数字段
     */
    public static void post(final String url, final CommonListener listener,
                            Map<String, String> params) {

        // LogUtils.t("head:U-I", PreferencesUtils.getPreferences(Constants.U_I));
        //  LogUtils.t("url", url + "?" + UrlTool.getParamsString(params));
        OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                listener.onStart(action);
                super.onBefore(request, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
            }

            @Override
            public void onAfter(int id) {
                listener.onFinish(action);
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (!NetworkHelper.isNetworkAvailable(BaseApplication.getContext())) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                }
//                }

                listener.onException(action, e.toString());
            }

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                // saveHeader(response);
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onResponse(String response, int id) {
//                LogUtils.t("onResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    // 请求成功
                    if (object.optInt("status", -1) == 1) {
                        listener.onSuccess(action, object);
//                        listener.onSuccess(action, object.optJSONObject(Constants.DATA));
                        // 请求失败
                    } else {
                        listener.onFaile(action,
                                object.optString("message", ""));
                    }
                } catch (JSONException e) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试---服务器出现异常---onResponse1", Toast.LENGTH_SHORT).show();
//                    Utils.toast("无法连接网络，请检查网络设置后重试---服务器出现异常---onResponse1");
                    // 服务器出现异常
                    listener.onException(action, e.toString());
                    e.printStackTrace();
                }
                listener.onFinish(action);
            }
        });
    }

    //提交一系列文件
    public static void postFiles(final String url, final CommonListener listener, final Map<String, File> fileMap, final Map<String, String> params, final Object action) {
        String string = OkHttpUtils.post().files("file", fileMap).url(url).tag(action).params(params).toString();
        LogUtils.d("postFile-------" + string);
        OkHttpUtils.post().files("file", fileMap).url(url).tag(action).params(params).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                listener.onStart(action);
                super.onBefore(request, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
            }

            @Override
            public void onAfter(int id) {
                listener.onFinish(action);
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (!NetworkHelper.isNetworkAvailable(BaseApplication.getContext())) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                }
//                }

                listener.onException(action, e.toString());
            }

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                // saveHeader(response);
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onResponse(String response, int id) {
//                LogUtils.t("onResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    // 请求成功
                    if (object.optInt("status", -1) == 200) {
                        listener.onSuccess(action, object);
//                        listener.onSuccess(action, object.optJSONObject(Constants.DATA));
                        // 请求失败
                    } else {
                        listener.onFaile(action,
                                object.optString("message", ""));
                    }
                } catch (JSONException e) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试---服务器出现异常---onResponse1", Toast.LENGTH_SHORT).show();
//                    Utils.toast("无法连接网络，请检查网络设置后重试---服务器出现异常---onResponse1");
                    // 服务器出现异常
                    listener.onException(action, e.toString());
                    e.printStackTrace();
                }
                listener.onFinish(action);
            }
        });

    }

    //上传单个文件
    public static void postFile(final String url, final CommonListener listener, final Map<String, String> params, final String fileName,
                                final File file, final Object action) {

        // LogUtils.t("head:U-I", PreferencesUtils.getPreferences(Constants.U_I));
        //  LogUtils.t("url", url + "?" + UrlTool.getParamsString(params));
        PostFormBuilder builder = new PostFormBuilder();
        OkHttpUtils.post().url(url).tag(action).params(params).addFile("file", fileName, file).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                listener.onStart(action);
                super.onBefore(request, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
            }

            @Override
            public void onAfter(int id) {
                listener.onFinish(action);
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (!NetworkHelper.isNetworkAvailable(BaseApplication.getContext())) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                }
//                }

                listener.onException(action, e.toString());
            }

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                // saveHeader(response);
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onResponse(String response, int id) {
//                LogUtils.t("onResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    // 请求成功
                    if (object.optInt("status", -1) == 200) {
                        listener.onSuccess(action, object);
//                        listener.onSuccess(action, object.optJSONObject(Constants.DATA));
                        // 请求失败
                    } else {
                        listener.onFaile(action,
                                object.optString("message", ""));
                    }
                } catch (JSONException e) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试---服务器出现异常---onResponse1", Toast.LENGTH_SHORT).show();
//                    Utils.toast("无法连接网络，请检查网络设置后重试---服务器出现异常---onResponse1");
                    // 服务器出现异常
                    listener.onException(action, e.toString());
                    e.printStackTrace();
                }
                listener.onFinish(action);
            }
        });
    }


    /**
     * 2015年4月1日
     *
     * @param url      带参数字段的 url xx.com?name=a&psd=chuagn123
     * @param tag      请求标签
     * @param action   业务指令信号
     * @param listener 回调业务
     */
    public static void get(final String url, final Object tag,
                           final Object action, final CommonListener listener) {
        get(url, tag, action, listener, UrlTool.getMapParams());
    }


    /**
     * @param url
     * @param tag      请求标签
     * @param action
     * @param listener
     * @param params   请求参数
     */
    public static void get(final String url, final Object tag,
                           final Object action, final CommonListener listener
            , Map<String, String> params) {
//        LogUtils.t("head:U-I", PreferencesUtils.getPreferences(Constants.U_I));
//        LogUtils.t("url", url + "?" + UrlTool.getParamsString(params));
        OkHttpUtils.get().url(url).tag(tag).params(params).
                build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                listener.onStart(action);
                super.onBefore(request, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
            }

            @Override
            public void onAfter(int id) {
                listener.onFinish(action);
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onException(action, e.toString());
                if (!NetworkHelper.isNetworkAvailable(BaseApplication.getContext())) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                    //Utils.toast("无法连接网络，请检查网络设置后重试");
                }
            }

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                //saveHeader(response);
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onResponse(String response, int id) {
//                LogUtils.t("onResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    // 请求成功
                    if (object.optInt("status", -1) == 1) {
                        listener.onSuccess(action, object);
//                        listener.onSuccess(action, object.optJSONObject(Constants.DATA));
                        // 请求失败
                    } else {
                        listener.onFaile(action,
                                object.optString("message", ""));
                    }
                } catch (JSONException e) {
                    listener.onException(action, e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 保存头信息
     *
     * @param response
     */
//    private static void saveHeader(Response response) {
//        if (response == null || TextUtils.isEmpty(response.header(Constants.U_I)))
//            return;
//        PreferencesUtils.savePreferences(Constants.U_I, response.header(Constants.U_I));
//        LogUtils.t("u_i: ", response.header(Constants.U_I));
//    }

    /**
     * 根据标签取消请求
     *
     * @param tag 取消请求的标签
     */
    public static void cancelTag(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }


    public static void downLoader(String url, FileCallBack callBack) {
        OkHttpUtils.get().url(url).build().execute(callBack);
    }


    public static void getJson(final String url, String urldetail, final Object action, final CommonListener listener) {

        long timeMillis = System.currentTimeMillis();
        // LogUtils.d(url + urldetail);

        OkHttpUtils.get().url(url + urldetail).tag(action).build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                listener.onStart(action);
                super.onBefore(request, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
            }

            @Override
            public void onAfter(int id) {
                listener.onFinish(action);
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onException(action, e.toString());
                if (!NetworkHelper.isNetworkAvailable(BaseApplication.getContext())) {
                    Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                    //Utils.toast("无法连接网络，请检查网络设置后重试");
                }
            }

            @Override
            public String parseNetworkResponse(Response response, int id) throws IOException {
                //saveHeader(response);
                return super.parseNetworkResponse(response, id);
            }

            @Override
            public void onResponse(String response, int id) {
//                LogUtils.t("onResponse", response);
                try {
                    JSONObject object = new JSONObject(response);
                    listener.onSuccess(action, object);
                } catch (JSONException e) {
                    listener.onException(action, e.toString());
                    e.printStackTrace();
                }
            }
        });

    }

    public static void postJson(final String url, final String jsonStr, final Object action, final CommonListener listener) {
        OkHttpUtils.post()
                .url(url + jsonStr).tag(action)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .content(jsonStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        listener.onStart(action);
                        super.onBefore(request, id);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        listener.onFinish(action);
                        super.onAfter(id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onException(action, e.toString());
                        if (!NetworkHelper.isNetworkAvailable(BaseApplication.getContext())) {
                            Toast.makeText(BaseApplication.getContext(), "无法连接网络，请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                            //Utils.toast("无法连接网络，请检查网络设置后重试");
                        }
                    }

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws IOException {
                        //saveHeader(response);
                        return super.parseNetworkResponse(response, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                LogUtils.t("onResponse", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            // 请求成功
                            listener.onSuccess(action, object);
                        } catch (JSONException e) {
                            listener.onException(action, e.toString());
                            e.printStackTrace();
                        }
                    }
                });
    }

}
