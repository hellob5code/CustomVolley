package com.nguyenvanquan7826.demo.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

public class StringRequestCache extends StringRequest {

    private boolean isCache = true;

    public StringRequestCache(int method, String url, boolean isCache, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        setShouldCache(isCache);
    }

    public StringRequestCache(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(method, url, true, listener, errorListener);
    }

    public StringRequestCache(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, true, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        if (isCache) {
            return Response.success(jsonString, HttpHeaderParserForCache.parseIgnoreCacheHeaders(response));
        } else {
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}
