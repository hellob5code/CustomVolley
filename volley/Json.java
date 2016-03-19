package com.otvina.ictusocial.volley;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Json {

    public static final int POST = Request.Method.POST;
    public static final int GET = Request.Method.GET;

    public static final String APP_ID = "475681656679";
    public static final String APP_SECRET = "LsO189xl1p5b5673t87pQ05w6d3k9KeP";

    public static final String URL_NOTIFY = "http://www.nguyenvanquan7826.com/mobile/tkb/notify.php";

    public static final String DOMAIN = "http://ictu.kingdark.org/";
    public static final String HOME_SCHOOL = DOMAIN + "api.php?";
    public static final String HOME_SERVICE = "http://www.nguyenvanquan7826.com/mobile/ictusocial/";

    public static final String URL_LOGIN = HOME_SCHOOL + "api=login-app";
    public static final String URL_USER_INFO = HOME_SCHOOL + "api=get&path=user";
    public static final String URL_TKB = HOME_SCHOOL + "api=get&path=student-time-table";
    public static final String URL_NEWS = HOME_SCHOOL + "api=get&path=news";
    public static final String URL_SCORE = HOME_SCHOOL + "api=get&path=student-mark";
    public static final String URK_LIST_TEACHER = HOME_SCHOOL + "api=get&path=teacher-list";
    public static final String URL_EXAM = HOME_SCHOOL + "api=get&path=student-exam-list";
    public static final String URL_NEWS_DETAIL = HOME_SCHOOL + "api=get&path=news-detail&newsid=";

    public static final String URL_SERVICE = HOME_SERVICE + "?get=service";
    public static final String URL_CONTACT = HOME_SERVICE + "?get=contact";
    public static final String URL_KHOA_BOMON = HOME_SERVICE + "?get=khoabomon";
    public static final String URL_CONTACT_SERVICE = HOME_SERVICE + "?get=contact&service=";


    public static final String API_KEY = "api";
    public static final String PATH_KEY = "path";
    public static final String TOCKEN_KEY = "access-token";

    private String url;
    private int method = GET;
    private boolean isCache = true;
    private int timeout = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    private HashMap<String, String> parmar = new HashMap<>();

    public Json setUrl(String url) {
        this.url = url;
        return this;
    }

    public Json setMethod(int method) {
        this.method = method;
        return this;
    }

    public Json setIsCache(boolean isCache) {
        this.isCache = isCache;
        return this;
    }

    public Json setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public Json setParmar(HashMap<String, String> parmar) {
        this.parmar = parmar;
        return this;
    }

    public Json put(String key, String value) {
        parmar.put(key, value);
        return this;
    }

    private Context context;

    public static Json init(Context context) {
        return new Json(context);
    }

    private Json(Context context) {
        this.context = context;
    }

    public static Gson getGson() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
    }

    public void runPost() {
        runPost(null);
    }

    public void runPost(String tag) {
        setMethod(POST).run(tag);
    }

    public void run() {
        run(null);
    }

    public void run(final String tag) {
        boolean haveCache = false;
        if (isCache) {
            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(url);
            if (entry != null) {
                try {
                    String json = new String(entry.data, "UTF-8");
                    if (listener != null) {
                        haveCache = true;
                        System.out.println("json from cache");
                        listener.onFinishLoadJson(null, json, tag);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!haveCache) {
            StringRequestCache stringRequest = new StringRequestCache(method, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                if (listener != null) {
                                    System.out.println("json from success");
                                    listener.onFinishLoadJson(null, response, tag);
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("from error");
                            if (listener != null) {
                                listener.onFinishLoadJson(VolleyErrorHelper.getMessage(error, context), null, tag);
                            }
                            error.printStackTrace();
                        }
                    }) {
                @Override
                public Map<String, String> getParams() {
                    if (parmar == null) {
                        return new HashMap<>();
                    } else {
                        return parmar;
                    }
                }
            };

            stringRequest.setShouldCache(isCache);

            if (timeout > 0) {
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        timeout,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            }
            if (tag != null) {
                AppController.getInstance().addToRequestQueue(stringRequest, tag);
            } else {
                AppController.getInstance().addToRequestQueue(stringRequest);
            }
        }
    }

    public void cancel(String tag) {
        AppController.getInstance().getRequestQueue().cancelAll(tag);
    }

    public Json setListener(JsonListener listener) {
        this.listener = listener;
        return this;
    }

    private JsonListener listener;

    public interface JsonListener {
        void onFinishLoadJson(String error, String json, String tag);
    }
}

