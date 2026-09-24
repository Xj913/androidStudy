package com.style.http.converter;

import androidx.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * ResponseBody转换器
 */

public class ResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {

    @Override
    public ResponseBody convert(@NonNull ResponseBody value) {
        return value;
    }
}
