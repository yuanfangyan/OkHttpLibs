package cyt.com.example.yuan.okhttplibrary.exector;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyExecutor {
    private Executor mainExector;
    private Executor netExector;

    public MyExecutor() {
        this(new MainExector(), Executors.newFixedThreadPool(3));
    }

    public MyExecutor(Executor mainExector, Executor netExector) {
        this.mainExector = mainExector;
        this.netExector = netExector;
    }

    static class MainExector implements Executor {
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }

    public Executor getMainExector() {
        return mainExector;
    }

    public Executor getNetExector() {
        return netExector;
    }
}
