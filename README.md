# 支付SDK 集成文档

### 当前最新版本：v1.1.2

### SDK包含的业务功能

1. 绑定银行卡(未提供调起API，在首次支付等环节由SDK调起))
2. 收银台支付（提供调起和回调API）
3. 银行卡管理（提供调起API）
4. 修改支付密码、忘记支付密码（提供调起API）

### 非原生页面如何使用SDK

使用非原生技术的App内页面（如H5、React Native、Weex等）如需调起SDK，需在App内部通过原生与非原生通信的API，封装SDK接口后供非原生页面调用即可。
如果需要回调，可以在非原生页面约定好回调接口，由原生模块收到SDK回调后调用非原生页面的约定接口即可实现回调。


### SDK交互流程图

![结构图](https://github.com/jhmobile/Android-zqpay-sample/blob/master/app/image/structure.png)

### 第一步：根项目
##### 在项目的build.gradle中添加mavn地址：
<pre><code>
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
</code></pre>

### 第二步:主项目moudle（app目录下）

##### 在主项目的build.gradle中添加sdk对应引用：
<pre><code>
configurations.all {
    resolutionStrategy {
        //可根据buildToolsVersion版本酌情指定版本，不能低于24.0.0
        force 'com.android.support:support-annotations:24.0.0'
        force 'com.android.support:recyclerview-v7:24.0.0'
    }
}
dependencies{
    ......
    compile ('com.zqpay.zl:zqpay:1.1.1',{
    exclude group: 'com.android.support', module: 'support-v4'
    })
    //可根据buildToolsVersion版本酌情指定版本，不能低于24.0.0
   compile 'com.android.support:appcompat-v7:26.1.0'
}
</code></pre>

### 第三步：换肤

#### 1，color.xml中添加如下代码

###### 可定义sdk导航栏，按钮和文字主题颜色值，如果需要改变就直接把下面的color定义添加到color文件中，根据需要改变颜色内容

![color.xml](https://github.com/jhmobile/Android-zqpay-sample/blob/master/app/image/color.png)

#### 2，在mipmap中添加对应的导航栏返回和关闭图片，图片的命名如下：

###### 返回图片：zqpay_bar_ic_back.png
###### 关闭图片：zqpay_bar_ic_close

##### 注：如果不需要进行换肤操作，以上两步可以忽略

### 第四步：API调用

#### 1，在application调用初始化
<pre><code>
PaymentManager.getInstance()
        .setIsProduction(false)//设置当前用于的环境
        .initialize(context,"appKey","appSecret");

</code></pre>

<p>
setIsProduction(boolean isProduction)
</p>

|参数|类型|描述|是否可为空|示例|
|:-:|:-:|:-:|:-:|:-:| 
|isProduction|boolean|表示当前app运行的环境：true  生产即线上环境   false 测试环境|否|false|
 
<p>
initialize(Context context，String appkey，String appSecret)
</P>

|参数|类型|描述|是否可为空|示例|
|:-:|:-:|:-:|:-:|:-:| 
|appkey|String|sdk对当前应用的唯一标示账户，有sdk提供方给出|否|jh23fe7badc465548b|
|appSecret|String|sdk对当前应用的唯一标示密钥，有sdk提供方给出|否|29e4ceded0ef44839a7293ec7fd4b3e7| 
  
#### 2，设置用户信息
<pre><code>
PaymentManager.getInstance().setUser("userId","token")

</code></pre>

<p>
setUser(String userId, String token)
</P>

|参数|类型|描述|是否可为空|示例|
|:-:|:-:|:-:|:-:| :-:| 
|userId|String|用户通过调用证联接口获取到的当前用户唯一标示|否|2018917|
|token|String|用户通过调用证联接口获取到的userId的密钥|否|QFsiIxVJEXE=|

#### 3， 支付
<pre><code>
PaymentManager.getInstance().pay(context,orderNo, orderTime,instuId,instuName, amount, desc)
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

</code></pre>

<P>
PaymentManager.getInstance().pay(Context context, String orderNo, String orderTime, String amount,                   String desc).callBack(PaymentResultCallBack callBack）
</p>

|参数|类型|描述|是否可为空|示例|
|:-:|:-:|:-:|:-:|:-:| 
|orderNo|String|订单号，当前订单的唯一标示|否|2018090422345|
|orderTime|long|订单生成的时间，格式：时间戳|否|1531450606|
|instuId|String|订单对应的商户号|否|B1000543466777|
|instuName|String|订单对应的商户名称|否|雅马哈商城|
|amount|String|订单支付的金额，格式：两位小数|否|45.57|
|desc|String|订单描述信息|是|雅马哈商城支付订单|
|callBack|PaymentResultCallBack|支付结果回调接口|否|参考sample代码|

#### 3，我的银行卡
<pre><code>
PaymentManager.getInstance().gotoBankList(context);

</code></pre>

#### 4，修改交易密码
<pre><code>
PaymentManager.getInstance().updatePayPassword(context);

</code></pre>


#### 支付结果code码描述

|code|描述|
|:-:|:-:|
|0|成功|
|-1|失败|

#### 混淆

如果代码进行了混淆，请把sample下面的混淆文件复制到项目的混淆文件中