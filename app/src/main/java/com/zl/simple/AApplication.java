package com.zl.simple;

import android.app.Application;
import com.zqpay.zl.paymentsdk.PaymentManager;

public class AApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2018/7/13 SDK初始化
        PaymentManager.getInstance()
                .setIsProduction(false)//设置运行环境，true:线上生产环境    false:测试环境
                .initialize(this, "jh23fe7badc465548b", "29e4ceded0ef44839a7293ec7fd4b3e7");//appkey，appsecret

    }

}
