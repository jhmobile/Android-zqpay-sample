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
    private TableRow trAccountPay;
    private TableRow trAccountBank;
    private TableRow trUpdatePwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 2018/7/13 在获取用户证联的userId和token的地方 ，设置用户信息
        PaymentManager.getInstance().setUser("2018917", "QFsiIxVJEXE=");
        initView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tr_account_bank://我的银行卡
                // TODO: 2018/7/13  参数：context    类型： Context  描述 ： 上下为对象           示例：MainActivity.this
                PaymentManager.getInstance().gotoBankList(MainActivity.this);
                break;
            case R.id.tr_update_pwd://修改支付密码
                // TODO: 2018/7/13  参数：context    类型： Context  描述 ： 上下为对象           示例：MainActivity.this
                PaymentManager.getInstance().forgetPayPassword(MainActivity.this);
                break;
            case R.id.tr_account_pay://支付
                // TODO: 2018/7/12   参数：context    类型： Context  描述 ： 上下为对象           示例：MainActivity.this
                // TODO: 2018/7/13   参数：orderNo    类型： String   描述 ： 订单号               示例：2018060733456
                // TODO: 2018/7/13   参数：orderTime  类型： long     描述 ： 订单生成的时间，格式为时间戳       示例：1531450606
                // TODO: 2018/7/13   参数：amount     类型： String   描述 ： 订单金额，保留两位小时             示例：56.65
                // TODO: 2018/7/13   参数：desc       类型： String   描述 ： 订单描述             示例： 小熊皮鞋
                // 上下文，定单号，订单生成时间的时间戳，订单金额，订单描述
                PaymentManager.getInstance().pay(MainActivity.this, System.currentTimeMillis() + "", System.currentTimeMillis(), "548.54", "支付")
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
        trAccountPay = (TableRow) findViewById(R.id.tr_account_pay);
        trAccountPay.setOnClickListener(this);
        trAccountBank = (TableRow) findViewById(R.id.tr_account_bank);
        trAccountBank.setOnClickListener(this);
        trUpdatePwd = (TableRow) findViewById(R.id.tr_update_pwd);
        trUpdatePwd.setOnClickListener(this);
    }
}
