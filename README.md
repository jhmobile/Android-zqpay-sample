# 支付SDK 集成文档

## 测试：
appkey：   jh23fe7badc465548b
appSecret：29e4ceded0ef44839a7293ec7fd4b3e7

## 第一步：根项目
在项目的build.gradle中添加mavn地址：
allprojects {
    repositories {
        ......
       maven {
           url "https://jitpack.io"
       }
        maven {
            url "http://mvn.gemantic.com:19909/content/repositories/releases"
        }
    }
}
## 第二步:主项目moudle（app目录下）
在主项目的build.gradle中添加sdk对应引用：

configurations.all {
    resolutionStrategy {
        force 'com.android.support:support-annotations:24.0.0'
        force 'com.android.support:recyclerview-v7:24.0.0'
    }
}
dependencies{
    ......
    compile ('com.zqpay.zl:zqpay:1.0.1',{
    exclude group: 'com.android.support', module: 'support-v4'
    })
    //可根据buildToolsVersion版本酌情指定版本，不能低于24.0.0
   compile 'com.android.support:appcompat-v7:26.1.0'
}
## 第三步：资源配置
#### 1，color.xml中添加如下代码

<color name="zqpay_theme">#ff9800</color>
<color name="zqpay_theme_title">#ff9000</color>

#### 2，mipmap,把sample中对于的图片拷贝过去，文件名为：ic_dlx

## 第四步：API调用

#### 1，在application调用初始化

PaymentManager.getInstance()
        .setIsProduction(false)
        .initialize(context,"appKey","appSecret");


#### 2，设置用户信息
PaymentManager.getInstance().setUser("userId","token")

#### 3， 支付

PaymentManager.getInstance().pay(context, "订单号", 订单生成时间的时间戳, "订单金额", "订单描述")
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

 3，我的银行卡

PaymentManager.getInstance().gotoBankList(context);
4， 忘记交易密码

PaymentManager.getInstance().forgetPayPassword(context);
5，修改交易密码

PaymentManager.getInstance().updatePayPassword(context);
 相关参数说明：

1，设置用户信息   setUser(String params1, String params2)

params1：获取到的用户在证联的userid
params2：获取到的用户在证联的token
setUser方法  如果用户登录成功，在application里进行调用，否则，需要在用户获取到证联相关uswrId和token的时候再次调用

2，设置环境    setIsProduction(boolean params)

 params： true：生产 false：测试

setIsProduction   设置当前应用的环境

3，初始化    initialize(Context context，String appkey，String appSecret)

context:   上下文对象

appkey     sdk给出的当前平台的appkey

appSecret  sdk给出的当前平台的appSecret

参数表格：

名字   类型   解释   示例



