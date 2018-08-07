package cyt.com.example.yuan.okhttplibrary;

import java.util.Map;

import cyt.com.example.yuan.okhttplibrary.exector.MyExecutor;
import cyt.com.example.yuan.okhttplibrary.listener.HttpListener;
import cyt.com.example.yuan.okhttplibrary.task.HttpTask;

public class HttpClient {
    private MyExecutor myExecutor;
    private static HttpClient instance;

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null)
                    instance = new HttpClient();
            }
        }
        return instance;
    }

    public HttpClient() {
        this.myExecutor = new MyExecutor();
    }

    public <T> HttpTask<T> executeHttpRequest(String url, Map<String, String> headers, Map<String, String> body, final HttpListener<T> responseListener) {
        HttpTask<T> httpTask = new HttpTask<T>(url, headers, body, responseListener, myExecutor.getMainExector());
        myExecutor.getNetExector().execute(httpTask.getHttpThread());
        return httpTask;
    }

    public <T> HttpTask<T> executeHttpRequest(String url, Map<String, String> body, final HttpListener<T> responseListener) {
        return executeHttpRequest(url, null, body, responseListener);
    }

}
