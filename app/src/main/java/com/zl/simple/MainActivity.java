package com.zl.simple;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinhui365.router.core.IRouteCallBack;
import com.jinhui365.router.core.RouteManager;
import com.jinhui365.router.core.RouteResponse;
import com.zqpay.zl.interceptor.AccountRouteURL;
import com.zqpay.zl.interfac.BalanceResultCallBack;
import com.zqpay.zl.interfac.PaymentResultCallBack;
import com.zqpay.zl.manager.UserManager;
import com.zqpay.zl.paymentsdk.PaymentManager;
import com.zqpay.zl.util.ToastUtil;
import com.zqpay.zl.view.tabrow.TableRow;

public class MainActivity extends Activity implements View.OnClickListener {
    private TableRow trAccountPay;
    private TableRow trAccountBank;
    private TableRow trUpdatePwd;
    private TableRow trBalance;
    private TableRow trBalancePage;
    private TableRow trLogout;

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
                PaymentManager.getInstance().updatePayPassword(MainActivity.this);
                break;
            case R.id.tr_account_pay://支付
                // TODO: 2018/7/12   参数：context    类型： Context  描述 ： 上下为对象           示例：MainActivity.this
                // TODO: 2018/7/13   参数：orderNo    类型： String   描述 ： 订单号               示例：2018060733456
                // TODO: 2018/7/13   参数：orderTime  类型： long     描述 ： 订单生成的时间，格式为时间戳       示例：1531450606
                // TODO: 2018/7/13   参数：instuId    类型： String   描述 ： 订单的商户号                      示例：12345
                // TODO: 2018/7/13   参数：instuName  类型： String   描述 ： 订单的商户名称                    示例：玛雅哈商店
                // TODO: 2018/7/13   参数：amount     类型： String   描述 ： 订单金额，保留两位小时             示例：56.65
                // TODO: 2018/7/13   参数：desc       类型： String   描述 ： 订单描述             示例： 这是一笔主动发起的祝福
                // 上下文，定单号，订单生成时间的时间戳，订单金额，订单描述
                PaymentManager.getInstance().pay(MainActivity.this, System.currentTimeMillis() + "", System.currentTimeMillis(),
                        "B00000627", "玛雅哈商店", "5048.54", "这是一笔主动发起的祝福这是一笔主动发起的祝福这是一笔主动发起的祝福这是一笔主动发起的祝福")
                        .callBack(new PaymentResultCallBack() {
                            @Override
                            public void onPaymentError(String code, String errorMsg, String orderNo) {//支付成功回调

                                ToastUtil.show(MainActivity.this, orderNo + ":" + errorMsg, 0);
                            }

                            @Override
                            public void onPaymentSuccess(String orderNo) {
                                ToastUtil.show(MainActivity.this, orderNo + ":成功", 0);//支付失败回调
                            }
                        });
                break;
            case R.id.tr_balance://余额
               PaymentManager.getInstance().getBalanceData(new BalanceResultCallBack() {
                   @Override
                   public void getBalance(double v) {
                       ToastUtil.show(MainActivity.this,  "当前账户余额:"+v, 0);//支付失败回调
                   }
               });
                break;
            case R.id.tr_balance_page://余额页面
                PaymentManager.getInstance().gotoBalanceDetail(MainActivity.this);
                break;
            case R.id.tr_logout:
                PaymentManager.getInstance().cleanInfo(MainActivity.this);
                break;
        }
    }

    private void initView() {
        trAccountPay = findViewById(R.id.tr_account_pay);
        trAccountPay.setOnClickListener(this);
        trAccountBank = findViewById(R.id.tr_account_bank);
        trAccountBank.setOnClickListener(this);
        trUpdatePwd = findViewById(R.id.tr_update_pwd);
        trUpdatePwd.setOnClickListener(this);
        trBalance = findViewById(R.id.tr_balance);
        trBalance.setOnClickListener(this);
        trBalancePage = findViewById(R.id.tr_balance_page);
        trBalancePage.setOnClickListener(this);
        trLogout = findViewById(R.id.tr_logout);
        trLogout.setOnClickListener(this);
    }
}
