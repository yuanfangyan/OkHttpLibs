package cyt.com.example.yuan.okhttplibrary.http;

import java.util.Map;
import java.util.Set;

import cyt.com.example.yuan.okhttplibrary.listener.ResponseListener;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpRequest<T> {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> bodys;
    private MediaType from_type = MediaType.parse("");
    private RequestBody requestBody;
    private ResponseListener<T> responseListener;

    public HttpRequest(String url, Map<String, String> headers, Map<String, String> bodys, ResponseListener<T> responseListener) {
        this.url = url;
        this.headers = headers;
        this.bodys = bodys;
        this.responseListener = responseListener;
        requestBody = RequestBody.create(from_type, getPostBody());
    }

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

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    private Request createRequest() {
        return OkHttpManage.createPostRequest(getUrl(), getHeaders(), getRequestBody());
    }

    public ResponseListener<T> getResponseListener() {
        return this.responseListener;
    }

}
