package com.example.voxiaassistant;

public interface ResposeCallback {
    void onResponce(String response);
    void onError(Throwable throwable);
}
