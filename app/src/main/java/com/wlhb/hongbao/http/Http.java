package com.wlhb.hongbao.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.litesuits.common.assist.Network;
import com.litesuits.common.utils.MD5Util;
import com.wlhb.hongbao.app.App;
import com.wlhb.hongbao.http.converter.FastJsonConverterFactory;
import com.wlhb.hongbao.http.converter.ToStringConverterFactory;
import com.wlhb.hongbao.utils.HexDump;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2018/4/19/019.
 */

public class Http {

    private static APIService service;
    private static OkHttpClient client;
    private static final String TAG = "Http";

    public static APIService getService() {

        if (client == null) {
            initClient();
        }

        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();
            service = retrofit.create(APIService.class);
        }

        return service;
    }

    private static void initClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (App.isDebug) {
            //开启日志拦截器
            setLoggingInterceptor(builder);
        }
        setCookieJar(builder);
        setStethoInterceptor(builder);
        setHeaderInterceptor(builder);
        setParamsInterceptor(builder);
        setCacheDirectory(builder);
        setCacheInterceptor(builder);
        setTimeout(builder);
        client = builder.build();
    }

    /**
     * Stetho抓包
     *
     * @param builder
     */
    private static void setStethoInterceptor(OkHttpClient.Builder builder) {
        if (builder != null) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
    }

    /**
     * 设置超时和重试
     *
     * @param builder
     */
    private static void setTimeout(OkHttpClient.Builder builder) {
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
    }

    /**
     * 设置缓存路径
     *
     * @param builder
     */
    public static void setCacheDirectory(OkHttpClient.Builder builder) {
        //设置缓存路径
        Context context = App.getContext();
        if (context != null) {
            String path = context.getCacheDir().getPath();
            File httpCacheDirectory = new File(path, "responses");
            Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            builder.cache(cache);
        }
    }

    /**
     * 设置CookieJar
     *
     * @param builder
     */
    private static void setCookieJar(OkHttpClient.Builder builder) {
        Context context = App.getContext();
        if (context != null) {
            ClearableCookieJar cookieJar = new PersistentCookieJar(
                    new SetCookieCache(),
                    new SharedPrefsCookiePersistor(context));
            builder.cookieJar(cookieJar);
        }
    }

    /**
     * 缓存拦截器
     *
     * @param builder
     */
    private static void setCacheInterceptor(OkHttpClient.Builder builder) {
        if (builder != null) {
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Context context = App.getContext();
                    if (context != null) {
                        if (!Network.isAvailable(context)) {
                            request = request.newBuilder()
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .build();
                        }

                        Response response = chain.proceed(request);
                        if (Network.isAvailable(context)) {
                            int maxAge = 0;
                            // 有网络时,设置缓存超时时间0个小时
                            response.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + maxAge)
                                    .removeHeader("Pragma")
                                    .build();
                        } else {
                            // 无网络时,设置超时为4周
                            int maxStale = 60 * 60 * 24 * 3;
                            response.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .removeHeader("Pragma")
                                    .build();
                        }
                        return response;
                    } else {
                        //默认当作有网络处理
                        int maxAge = 0;
                        Response response = chain.proceed(request);
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("Pragma")
                                .build();
                        return response;
                    }
                }
            };
            builder.addInterceptor(cacheInterceptor);
        }
    }

    /**
     * 公共参数拦截器
     *
     * @param builder
     */
    private static void setParamsInterceptor(OkHttpClient.Builder builder) {
        if (builder != null) {
            Interceptor paramsInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Headers headers = originalRequest.headers();
                    String method = originalRequest.method();
                    RequestBody body = originalRequest.body();
                    RequestBody newBody = body;

                    //公共参数
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            //                            .addQueryParameter("platform", "android-" + Build.VERSION.SDK_INT)
                            //                            .addQueryParameter("version_name", App.versionName)
                            //                            .addQueryParameter("version_code", String.valueOf(App.versionCode))
                            .build();

                    //新请求
                    Request request = originalRequest.newBuilder()
                            .headers(headers)
                            .method(method, newBody)
                            .url(modifiedUrl)
                            .build();

                    //添加参数
                    if (method.equalsIgnoreCase("POST")) {
                        MediaType mediaType = body.contentType();
                        String type = mediaType.toString();
                        Log.d(TAG, "intercept: " + type);
                        if ("application/x-www-form-urlencoded".equals(type)) {
                            okio.Buffer buffer = new okio.Buffer();
                            body.writeTo(buffer);
                            String bodyStr = buffer.readUtf8();
                            Log.d(TAG, "intercept: " + bodyStr);
                            if (bodyStr.length() > 0) {
                                //解码
                                String decodedBody = URLDecoder.decode(bodyStr, "utf-8");
                                List<String> keyValues = new ArrayList<>();
                                String[] split = decodedBody.split("&");
                                Collections.addAll(keyValues, split);
                                //计算签名
                                String signature = SignUtils.getSignature(keyValues);
                                if (signature != null) {
                                    Log.d(TAG, "intercept: " + signature);
                                    //添加签名
                                    bodyStr += "&signature=" + signature;
                                    newBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), bodyStr);
                                }
                            }
                            request = originalRequest.newBuilder()
                                    .headers(headers)
                                    .method(method, newBody)
                                    .url(modifiedUrl)
                                    .build();

                        }

                    }
                    else if (method.equalsIgnoreCase("GET")) {
                        //                        //添加签名
                        Set<String> nameSet = modifiedUrl.queryParameterNames();
                        ArrayList<String> nameList = new ArrayList<>();
                        nameList.addAll(nameSet);
                        Collections.sort(nameList);

                        StringBuilder buffer = new StringBuilder();
                        for (int i = 0; i < nameList.size(); i++) {
                            buffer.append(nameList.get(i)).append("=").append(modifiedUrl.queryParameterValues(nameList.get(i)) != null &&
                                    modifiedUrl.queryParameterValues(nameList.get(i)).size() > 0 ? modifiedUrl.queryParameterValues(nameList.get(i)).get(0) : "").append("&");
                        }
                        String signatur = buffer.toString();
                        if (TextUtils.isEmpty(signatur)){
                            modifiedUrl = modifiedUrl.newBuilder()
                                    .addQueryParameter("signature", HexDump.toHexString(MD5Util.md5("$!#HeQi1o0")))
                                    .build();
                        }else {
                            modifiedUrl = modifiedUrl.newBuilder()
                                    .addQueryParameter("signature", HexDump.toHexString(MD5Util.md5(buffer.toString().substring(0, buffer.toString().length()-1)+"$!#HeQi1o0")))
                                    .build();
                        }

                        request = request.newBuilder().url(modifiedUrl).build();
                    }
                    return chain.proceed(request);
                }
            };
            builder.addInterceptor(paramsInterceptor);
        }
    }

    /**
     * 头部拦截器
     *
     * @param builder
     */
    private static void setHeaderInterceptor(OkHttpClient.Builder builder) {
        if (builder != null) {
            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .header("AppType", "TPOS")
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .method(originalRequest.method(), originalRequest.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            };
            builder.addInterceptor(headerInterceptor);
        }
    }

    /**
     * 日志拦截器
     *
     * @param builder
     */
    private static void setLoggingInterceptor(OkHttpClient.Builder builder) {
        if (builder != null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
    }

    /**
     * GET请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static <T> void get(String url, Map<String, String> params, final RequestCallback<T> callback) {
        if (client == null) {
            initClient();
        }
        final WeakReference<RequestCallback<T>> weakReference = new WeakReference<>(callback);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                urlBuilder.addQueryParameter(key, value);
            }
        }
        HttpUrl httpUrl = urlBuilder.build();
        final Request request = new Request.Builder()
                .url(httpUrl)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                RequestCallback<T> callback1 = weakReference.get();
                if (callback1 != null) {
                    int code = -1;
                    String error = e.getLocalizedMessage();
                    callback1.onFailure(code, error);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                RequestCallback<T> callback1 = weakReference.get();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    T t = (T) body.string();
                    if (callback1 != null) {
                        callback1.onSuccess(t);
                    }
                } else {
                    int code = response.code();
                    String error = response.message();
                    if (callback1 != null) {
                        callback1.onFailure(code, error);
                    }
                }
            }
        });
    }

    /**
     * POST请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static <T> void post(String url, Map<String, String> params, final RequestCallback<T> callback) {
        if (client == null) {
            initClient();
        }

        final WeakReference<RequestCallback<T>> weakReference = new WeakReference<>(callback);
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                bodyBuilder.add(key, value);
            }
        }
        FormBody formBody = bodyBuilder.build();

        Request request = new Request.Builder()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .url(url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                RequestCallback<T> callback1 = weakReference.get();
                if (callback1 != null) {
                    int code = -1;
                    String error = e.getLocalizedMessage();
                    callback1.onFailure(code, error);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                RequestCallback<T> callback1 = weakReference.get();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    T t = (T) body.string();
                    if (callback1 != null) {
                        callback1.onSuccess(t);
                    }
                } else {
                    int code = response.code();
                    String error = response.message();
                    if (callback1 != null) {
                        callback1.onFailure(code, error);
                    }
                }
            }
        });
    }
}


