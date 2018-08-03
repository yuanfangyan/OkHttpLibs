package cyt.com.example.yuan.okhttplibrary.http;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpManage {

    public static OkHttpClient createHttpClient(boolean isLog) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        setRequestTime(builder);
        if (isLog) {
            builder.addInterceptor(createInterceptor());
        }
        return builder.build();
    }

    /**
     * 设置超时
     *
     * @param builder
     */
    public static void setRequestTime(OkHttpClient.Builder builder) {
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);
    }

    /**
     * 设置拦截
     *
     * @return
     */
    public static Interceptor createInterceptor() {
        LogInterceptor interceptor = new LogInterceptor();
        return interceptor;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static Request createGetRequest(String url) {
        return createGetRequest(url, null);
    }

    public static Request createGetRequest(String url, Map<String, String> headers) {
        return createRequest(url, headers, null);
    }

    /**
     * post请求
     *
     * @param url
     * @param headers
     * @param body
     * @return
     */
    public static Request createPostRequest(String url, Map<String, String> headers, RequestBody body) {
        return createRequest(url, headers, body);
    }

    public static Request createPostRequest(String url, RequestBody body) {
        return createPostRequest(url, null, body);
    }

    /**
     * 创建Request对象
     *
     * @param url
     * @param headers
     * @param body
     * @return
     */
    public static Request createRequest(String url, Map<String, String> headers, RequestBody body) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (body != null) {
            builder.post(body);
        }
        if (headers != null) {
            Set<Map.Entry<String, String>> set = headers.entrySet();
            for (Map.Entry<String, String> entry : set) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 创建Call对象
     *
     * @param client
     * @param request
     * @return
     */
    public static Call createCall(OkHttpClient client, Request request) {
        return client.newCall(request);
    }

    /***
     *
     * 异步
     * @param call
     * @param callback
     */
    public static void executeAsyncCalls(Call call, Callback callback) {
        call.enqueue(callback);
    }

    /**
     * 同步
     *
     * @param call
     * @return
     * @throws IOException
     */
    public static Response executeSynchronCalls(Call call) throws IOException {
        Response response = call.execute();
        return response;
    }
}
