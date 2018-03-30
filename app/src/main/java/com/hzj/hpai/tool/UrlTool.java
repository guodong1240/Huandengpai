package com.hzj.hpai.tool;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


public class UrlTool {
    /**
     * 2015年2月10日
     *
     * @param values key value 键值对 传入
     * @return 服务器请求的参数
     */
//    public static RequestParams getParams(String... values) {
//        RequestParams params = new RequestParams();
//        String string = "";
//        for (int i = 0; i < values.length; i += 2) {
//            params.addQueryStringParameter(values[i], values[i + 1]);
//            string = string + values[i] + "," + values[i + 1] + ",";
//        }
//        LogUtils.t("RequestParams," + values[0], string);
//        return params;
//    }
//    public static Map<String, String> getMapParams(String... values) {
//        Map<String, String> map = new HashMap<>();
//        for (int i = 0; i < values.length; i += 2) {
//            map.put(values[i], values[i + 1]);
//        }
//        return map;
//    }
    public static Map<String, String> getMapParams(String... values) {
        SortedMap<String, String> map = new TreeMap<>();

        for (int i = 0; i < values.length; i += 2) {
            map.put(values[i], values[i + 1]);
        }
        //zx map.put(Constants.TIME_STAMP, String.valueOf(System.currentTimeMillis()));
       //zx  map.put(Constants.RANDOM_STR, RandomUtil.getRandomString(32));
//        LogUtils.t("md5-sign",getParamsString(map) + "&key=" + MD5Util.DIDI_KEY);
        // zx String sign = createSign(map, MD5Util.DIDI_KEY);
//        LogUtils.t("sign",sign);
        // URLEncoder编码，空格转为%20
//        for (int i = 0; i < values.length; i += 2) {
//            map.put(values[i], getURLCoder(values[i + 1]));
//        }
      //  map.put(Constants.SIGN, sign.toUpperCase());

        return map;
    }

    //    public static RequestParams getPostParams(String... values) {
//        RequestParams params = new RequestParams();
//        String string = "";
//        for (int i = 0; i < values.length; i += 2) {
//            params.addBodyParameter(values[i], values[i + 1]);
//            string = string + values[i] + "," + values[i + 1] + ",";
//        }
//        LogUtils.t("PostRequestParams," + values[0], string);
//        return params;
//    }
//
    public static String getParamsString(Map<String, String> map) {
        String string = "";
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                string = string + entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        return string;
    }
//
//    //
//
//    public static RequestParams getParams(String url, RequestParams requestParams) {
//        List<KeyValue> list = null;
////        requestParams.getQueryStringParams();
//        RequestParams params = new RequestParams(url);
//        for (int i = 0; i < list.size(); i++) {
////            params.addQueryStringParameter(list.get(i).key, list.get(i).getValueStr());
//        }
//        return params;
//    }

    public static String createSign(SortedMap<String, String> packageParams, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
       // zx  String sign = MD5Util.getMD5(sb.toString()).toUpperCase();
        return sb.toString();

    }
}
