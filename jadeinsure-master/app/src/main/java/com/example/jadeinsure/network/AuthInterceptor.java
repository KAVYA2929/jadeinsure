package com.example.jadeinsure.network;

import android.text.TextUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String token = SessionManager.getInstance().getToken();

        if (TextUtils.isEmpty(token)) {
            return chain.proceed(original);
        }

        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer " + token);

        Request request = builder.build();
        return chain.proceed(request);
    }
}