package com.swa.swamobileteam.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swa.swamobileteam.AcmeApplication;
import com.swa.swamobileteam.di.AppScope;
import com.swa.swamobileteam.transportApi.TransportApiClient;
import com.swa.swamobileteam.utils.constants.AuthorizationConstants;
import com.swa.swamobileteam.utils.cryptoManager.Encrypter;
import com.swa.swamobileteam.utils.cryptoManager.KeyStorage;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class ApplicationModule {
    private final static String TRANSCRYPT_SHARED_PREFERENCES = "com.swa.swamobileteam.prefs";

    @AppScope
    @Provides
    public TransportApiClient provideTransportApiClient(Retrofit retrofit) {
        return new TransportApiClient(retrofit);
    }

    @AppScope
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJavaCallAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(AuthorizationConstants.acmeApiUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @AppScope
    @Provides
    public OkHttpClient provideRetrofitOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @AppScope
    @Provides
    public GsonConverterFactory provideGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        return GsonConverterFactory.create(gson);
    }

    @AppScope
    @Provides
    public RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @AppScope
    @Provides
    public HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Timber.tag("okHttp").d(message)).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @AppScope
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(TRANSCRYPT_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @AppScope
    @Provides
    public KeyStorage provideKeyStorage(Context context, SharedPreferences preferences) {
        return new KeyStorage(context, preferences);
    }

    @AppScope
    @Provides
    public Encrypter provideEncrypter() {
        return new Encrypter();
    }

    @Module
    public interface Declarations {
        @AppScope
        @Binds
        Context bindContext(AcmeApplication application);
    }
}
