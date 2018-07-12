package com.zl.simple;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinhui365.router.core.IRouteCallBack;
import com.jinhui365.router.core.RouteManager;
import com.jinhui365.router.core.RouteResponse;
import com.zqpay.zl.interceptor.AccountRouteURL;
import com.zqpay.zl.interfac.PaymentResultCallBack;
import com.zqpay.zl.manager.UserManager;
import com.zqpay.zl.paymentsdk.PaymentManager;
import com.zqpay.zl.util.ToastUtil;
import com.zqpay.zl.view.tabrow.TableRow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.tr_account_bank)
    TableRow trAccountBank;
    @BindView(R.id.tr_update_pwd)
    TableRow trUpdatePwd;
    @BindView(R.id.tr_forget_pwd)
    TableRow trForgetPwd;
    @BindView(R.id.tr_account_login)
    TableRow trAccountLogin;
    @BindView(R.id.tr_account_logout)
    TableRow trAccountLogout;
    @BindView(R.id.tr_account_pay)
    TableRow trAccountPay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tr_account_bank, R.id.tr_update_pwd, R.id.tr_forget_pwd, R.id.tr_account_login, R.id.tr_account_logout, R.id.tr_account_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tr_account_bank:
                PaymentManager.getInstance().gotoBankList(MainActivity.this);
                break;
            case R.id.tr_update_pwd:
                PaymentManager.getInstance().updatePayPassword(MainActivity.this);
                break;
            case R.id.tr_forget_pwd:
                PaymentManager.getInstance().forgetPayPassword(MainActivity.this);
                break;
            case R.id.tr_account_login:
                RouteManager.getInstance()
                        .build(AccountRouteURL.LOGIN)
                        .withParams("isInterceptor", false)
                        .go(MainActivity.this, new IRouteCallBack() {
                            @Override
                            public void onSuccess(RouteResponse routeResponse) {
                                PaymentManager.getInstance()
                                        .create()
                                        .setUser("2018917", "QFsiIxVJEXE=");
                            }

                            @Override
                            public void onFail(RouteResponse routeResponse) {

                            }
                        });
                break;
            case R.id.tr_account_logout:
                UserManager.sharedInstance().logOut(MainActivity.this);
                break;
            case R.id.tr_account_pay:
                PaymentManager.getInstance().pay(MainActivity.this, System.currentTimeMillis()+"", System.currentTimeMillis(), "548.546", "支付")
                        .callBack(new PaymentResultCallBack() {
                            @Override
                            public void onPaymentError(String code, String errorMsg, String orderNo) {
                                ToastUtil.show(MainActivity.this, errorMsg, 0);
                            }

                            @Override
                            public void onPaymentSuccess(String orderNo) {
                                ToastUtil.show(MainActivity.this, "成功", 0);
                            }
                        });
                break;
        }
    }
}
