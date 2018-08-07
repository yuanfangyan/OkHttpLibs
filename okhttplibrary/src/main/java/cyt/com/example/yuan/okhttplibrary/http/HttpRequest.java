package cyt.com.example.yuan.okhttplibrary.http;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cyt.com.example.yuan.okhttplibrary.task.HttpTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest<T> {

    private HttpTask httpTask;

    public HttpRequest(HttpTask httpTask) {
        this.httpTask = httpTask;
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(new LogInterceptor())
                .build();
    }

    private Request createRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(httpTask.getUrl());
        if (httpTask.getHeaders() != null) {
            Set<Map.Entry<String, String>> set = httpTask.getHeaders().entrySet();
            for (Map.Entry<String, String> entry : set) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        builder.post(httpTask.getRequestBody());

        return builder.build();
    }


    private Call createCall() {
        return createHttpClient().newCall(createRequest());
    }

    /**
     * 异步
     *
     * @param callback
     */
    public void executeAsynchronous(Callback callback) {
        createCall().enqueue(callback);
    }

    /**
     * 同步
     *
     * @throws IOException
     */
    public Response executeSynchronization() throws IOException {
        Response response = createCall().execute();
        return response;
    }
}
