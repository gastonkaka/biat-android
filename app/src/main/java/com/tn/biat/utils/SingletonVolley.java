package com.tn.biat.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.tn.biat.Biat;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by mednaceur on 25/04/2017.
 */

public class SingletonVolley {
    private static SingletonVolley mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private SingletonVolley() {

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        HttpCookie cookie = new HttpCookie("lang", "fr");
        cookie.setPath("/");
        cookie.setVersion(0);
        try {
            cookieManager.getCookieStore().add(new URI(DataApplication.URL_BASE), cookie);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }



        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }
    public static synchronized SingletonVolley getInstance() {
        if (mInstance == null) {
            mInstance = new SingletonVolley();
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(Biat.getAppContext(),new HurlStack());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
