package com.example.creitiive.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        // returns original request that you can work with
        Request originalRequest = chain.request();

        Request newRequest = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .build();

        //initiate the HTTP work. This call invokes the request and returns the response as per the request.
        Response response = chain.proceed(newRequest);

        return response;
    }
}
