package cyt.com.example.yuan.okhttplibrary.task;

import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import cyt.com.example.yuan.okhttplibrary.exector.HttpThread;
import cyt.com.example.yuan.okhttplibrary.listener.HttpListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HttpTask<T> {
    private static String url;
    private static Map<String, String> headers;
    private Executor mainExecutor;
    private static Map<String, String> bodys;
    private HttpThread<T> httpThread;

    public HttpThread<T> getHttpThread() {
        return httpThread;
    }

    private MediaType from_type = MediaType.parse("application/x-www-form-urlencoded");
    private static RequestBody requestBody;
    private HttpListener<T> responseListener;

    public HttpTask(String url, Map<String, String> headers, Map<String, String> bodys, HttpListener<T> responseListener, Executor mainExecutor) {
        this.url = url;
        this.headers = headers;
        this.bodys = bodys;
        this.responseListener = responseListener;
        this.mainExecutor = mainExecutor;
        requestBody = RequestBody.create(from_type, getPostBody());
        httpThread = new HttpThread<>(this);
    }

    @Nullable
    private String getPostBody() {
        if (bodys == null) {
            return null;
        } else {
            Set<Map.Entry<String, String>> set = bodys.entrySet();
            StringBuffer stringBuffer = new StringBuffer();
            int size = 0;
            for (Map.Entry<String, String> entry : set) {
                if (size != 0) {
                    stringBuffer.append("&");
                }
                size++;
                stringBuffer.append(entry.getKey());
                stringBuffer.append("=");
                stringBuffer.append(entry.getValue());
            }
            return stringBuffer.toString();
        }

    }

    public static String getUrl() {
        return url;
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    public static RequestBody getRequestBody() {
        return requestBody;
    }


    public void success(final T t) {
        if (responseListener == null) {
            return;
        }
        mainExecutor.execute(new Runnable() {
            @Override
            public void run() {
                responseListener.responseSuccess(getUrl(), t);
            }
        });
    }

    public void fail(final String err) {
        if (responseListener == null) {
            return;
        }
        mainExecutor.execute(new Runnable() {
            @Override
            public void run() {
                responseListener.responseFail(getUrl(), err);
            }
        });
    }
}
