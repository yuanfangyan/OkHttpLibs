package cyt.com.example.yuan.okhttplibrary.listener;

public interface HttpListener<T> {
    void responseSuccess(String url, T t);

    void responseFail(String url, String err);
}
