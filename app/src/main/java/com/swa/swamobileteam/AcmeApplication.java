package com.swa.swamobileteam;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.swa.swamobileteam.di.ApplicationComponent;
import com.swa.swamobileteam.di.DaggerApplicationComponent;
import com.swa.swamobileteam.transportApi.TransportApi;
import com.swa.swamobileteam.transportApi.authentication.LoginRequestParams;
import com.swa.swamobileteam.transportApi.authentication.LoginResponse;
import com.swa.swamobileteam.transportApi.deliveries.DeliveriesParams;
import com.swa.swamobileteam.transportApi.deliveries.DeliveryScheduleResponse;
import com.swa.swamobileteam.utils.NotLoggingTree;
import com.swa.swamobileteam.utils.constants.AuthorizationConstants;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class AcmeApplication extends DaggerApplication implements HasSupportFragmentInjector {
    
    private static ApplicationComponent appComponent;
    private RefWatcher refWatcher;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new NotLoggingTree());
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);

        // Отключить вызов exceptions после отписки от observable
        RxJavaPlugins.setErrorHandler(throwable -> Timber.e(throwable.getMessage()));

        // test
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.tag("okHttp").d(message)).setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AuthorizationConstants.acmeApiUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
        TransportApi api = retrofit.create(TransportApi.class);

        String testToken = "Token 4605c9fd2066bd83c2206bf50c8ed3e95fba005e";

        api.getSchedule(10, 0, testToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<DeliveryScheduleResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("did subscribe");
                    }

                    @Override
                    public void onSuccess(DeliveryScheduleResponse deliveryScheduleResponse) {
                        System.out.println("success, number of items " + deliveryScheduleResponse.getResults().size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("failure");
                        e.printStackTrace();
                    }
                });
    }

    public static RefWatcher getRefWatcher(Context context) {
        AcmeApplication application = (AcmeApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerApplicationComponent.builder().application(this).build();
        return appComponent;
    }
}
