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
                .initialize(this, "jh31cff83f764e1c2e", "e05b875b70361b99f36dfb6a23730e93");//appkey，appsecret

    }

}
