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

public class MainActivity extends Activity implements View.OnClickListener {

    private TableRow trAccountLogin;
    private TableRow trAccountPay;
    private TableRow trAccountBank;
    private TableRow trUpdatePwd;
    private TableRow trForgetPwd;
    private TableRow trAccountLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tr_account_bank://我的银行卡
                PaymentManager.getInstance().gotoBankList(MainActivity.this);
                break;
            case R.id.tr_update_pwd://修改交易密码
                PaymentManager.getInstance().updatePayPassword(MainActivity.this);
                break;
            case R.id.tr_forget_pwd://忘记交易密码
                PaymentManager.getInstance().forgetPayPassword(MainActivity.this);
                break;
            case R.id.tr_account_login:
                RouteManager.getInstance()
                        .build(AccountRouteURL.LOGIN)
                        .withParams("isInterceptor", false)
                        .go(MainActivity.this, new IRouteCallBack() {
                            @Override
                            public void onSuccess(RouteResponse routeResponse) {
                                //模拟登陆成功，设置userId和token
                                PaymentManager.getInstance().setUser("2018917", "QFsiIxVJEXE=");
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
                // TODO: 2018/7/12   参数：上下文，定单号，订单生成时间的时间戳，订单金额，订单描述
                PaymentManager.getInstance().pay(MainActivity.this, System.currentTimeMillis() + "", System.currentTimeMillis(), "548.546", "支付")
                        .callBack(new PaymentResultCallBack() {
                            @Override
                            public void onPaymentError(String code, String errorMsg, String orderNo) {//支付成功回调
                                ToastUtil.show(MainActivity.this, errorMsg, 0);
                            }

                            @Override
                            public void onPaymentSuccess(String orderNo) {
                                ToastUtil.show(MainActivity.this, "成功", 0);//支付失败回调
                            }
                        });
                break;
        }
    }

    private void initView() {
        trAccountLogin = (TableRow) findViewById(R.id.tr_account_login);
        trAccountLogin.setOnClickListener(this);
        trAccountPay = (TableRow) findViewById(R.id.tr_account_pay);
        trAccountPay.setOnClickListener(this);
        trAccountBank = (TableRow) findViewById(R.id.tr_account_bank);
        trAccountBank.setOnClickListener(this);
        trUpdatePwd = (TableRow) findViewById(R.id.tr_update_pwd);
        trUpdatePwd.setOnClickListener(this);
        trForgetPwd = (TableRow) findViewById(R.id.tr_forget_pwd);
        trForgetPwd.setOnClickListener(this);
        trAccountLogout = (TableRow) findViewById(R.id.tr_account_logout);
        trAccountLogout.setOnClickListener(this);
    }
}
