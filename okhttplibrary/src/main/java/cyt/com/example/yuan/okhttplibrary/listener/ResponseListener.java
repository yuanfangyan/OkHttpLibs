package cyt.com.example.yuan.okhttplibrary.listener;

public interface ResponseListener<T> {
    void responseSuccess(String url, T t);

    void responseFile(String url, String err);
}
