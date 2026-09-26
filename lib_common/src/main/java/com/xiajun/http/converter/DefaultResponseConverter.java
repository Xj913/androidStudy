package com.xiajun.http.converter;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

public class DefaultResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public DefaultResponseConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return new Gson().fromJson(tempStr, type);
    }
}