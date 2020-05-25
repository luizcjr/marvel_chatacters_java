package com.luiz.marvelcharacters.di;

import com.luiz.marvelcharacters.api.constants.Constants;
import com.luiz.marvelcharacters.api.services.ApiDataSource;
import com.luiz.marvelcharacters.api.services.ApiRepository;
import com.luiz.marvelcharacters.util.Utils;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    private static final int CONNECTION_TIMEOUT = 20 * 1000;

    private Long ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis() / 1000L);

    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {

        Request newRequest = chain.request();

        HttpUrl url = newRequest.url()
                .newBuilder()
                .addQueryParameter("apikey", Constants.API_KEY)
                .addQueryParameter("ts", ts.toString().toLowerCase())
                .addQueryParameter("hash", Utils.md5(ts.toString().toLowerCase() + Constants.PRIVATE_KEY + Constants.API_KEY).toLowerCase())
                .build();

        newRequest = newRequest.newBuilder().url(url).build();

        return chain.proceed(newRequest);
    }).connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build();

    @Provides
    ApiDataSource provideApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiDataSource.class);
    }

    @Provides
    ApiRepository providesService() {
        return new ApiRepository();
    }
}
