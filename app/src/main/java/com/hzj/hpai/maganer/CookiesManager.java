package com.hzj.hpai.maganer;

import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by zx on 2016/12/26.
 */

public class CookiesManager implements CookieJar {
//    private Context context;
//
//    public CookiesManager(Context mcontext) {
//        this.context = mcontext;
//    }

    private final PersistentCookieStore cookieStore = new PersistentCookieStore(BaseApplication.getContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            cookieStore.add(url,cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie>cookies=cookieStore.get(url);
        return cookies;
    }
}
