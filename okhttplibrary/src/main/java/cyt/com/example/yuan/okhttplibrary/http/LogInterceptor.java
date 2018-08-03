package cyt.com.example.yuan.okhttplibrary.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);

        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");

        MediaType contentType = responseBody.contentType();

        if (contentType != null) charset = contentType.charset(Charset.forName("UTF-8"));

        RequestBody body = request.body();
        String jsonParams = "";
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            Map<String, String> formMap = new HashMap<>();
            // 从 formBody 中拿到请求参数，放入 formMap 中
            for (int i = 0; i < formBody.size(); i++) {
                formMap.put(formBody.name(i), formBody.value(i));
            }
            Gson gson = new Gson();
            jsonParams = gson.toJson(formMap);
        }
        Log.e("httpLog请求参数", jsonParams + "\n请求方式：" + request.method() + "\n请求url" + request.url() + "\n接口回调" + buffer.clone().readString(charset));
        return response;
    }
}
