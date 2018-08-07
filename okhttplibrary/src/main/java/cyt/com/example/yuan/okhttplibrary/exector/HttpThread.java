package cyt.com.example.yuan.okhttplibrary.exector;

import android.os.Process;

import com.google.gson.Gson;

import java.io.IOException;

import cyt.com.example.yuan.okhttplibrary.http.HttpRequest;
import cyt.com.example.yuan.okhttplibrary.task.HttpTask;
import cyt.com.example.yuan.okhttplibrary.utils.GsonUtils;
import okhttp3.Response;

public class HttpThread<T> implements Runnable {
    private HttpTask<T> httpTask;

    public HttpThread(HttpTask<T> httpTask) {
        this.httpTask = httpTask;
    }

    @Override
    public void run() {
        //设置优先级
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        try {
            HttpRequest request = new HttpRequest(httpTask);
            Response response = request.executeSynchronization();
            Gson gson=new Gson();
            if (response.isSuccessful()){
                httpTask.success((T) GsonUtils.toBean(gson,response.body().string()));
            }else{
               httpTask.fail("请求失败");
            }
        } catch (IOException e) {
            httpTask.fail(e.getMessage());
            e.printStackTrace();
        }
    }
}
