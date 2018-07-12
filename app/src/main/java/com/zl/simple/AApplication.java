package com.zl.simple;

import android.app.Application;
import com.zqpay.zl.paymentsdk.PaymentManager;

public class AApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentManager.getInstance()
                .create()
                .setUser("2018917", "QFsiIxVJEXE=")
                .setIsProduction(false)
                .initialize(this, "jh23fe7badc465548b", "29e4ceded0ef44839a7293ec7fd4b3e7");
    }

}
