package com.lib.zhifubao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

public class AliPay {

	// 商户PID
	public static final String PARTNER = "2088511239479533";
	// 商户收款账号
	public static final String SELLER = "1836601457@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANNYEukdH0GIcp5pImcRezZeKb3CtpoTCpdstua3odfI2erfPxJaFGxWvnwE0ges5hJRpGtO4ORNyCoyNKdri18tm9OhfDJJrWcDoW4fWCaZkEeNnjXUHA6Cev6Y1r0ret4krbF2+Vz5qQdJsYlyDpJinvjnGTXxrX8ynITSESsNAgMBAAECgYAyvGEFz3TycYQ6nTiiD6NJoP9aS8U0Zb/ULEgYSRs0R0ZxSRjGGhPvEj/2W93j89Djsu/KxwvcIwQbhSP40SuKxRaphRQdtUoKpm+QMXA6I6ARLTBu0QiGD/ojjjUGle9vQbY9Kp+Y7AIZdH2H4bhu1Rh+ulWuug40zEU/rw5DYQJBAPAZdNAzzcRhY5ikmqLNIaFsscBsBO0Oth0Oiae72566mMd/PxE9mmeNnhfq9GsRd9o5273wXBj4ru5LQVKrzBkCQQDhVxxaPwYS+islLeQNBwPuKURkEnH2/iE+auLViBUp6t3f6kVoqokv/Ujm8XOZ+wc1uvL4sj8scNEz7mYJgnUVAkEAg3asy0NSK3DXw8B9Gx8OhwCo4x9CIzqm5IoNPVZTDjpFZRZ7RclhPcoBAj+XzPgnk8mSVBHDm7iur7Ns9QM0IQJAQcQBs1kHdcxrgStWjnLIs955Zld3yWU74JKjZzyTKKuyW6Js5XI4HbhnaXd4jK0V2pmYRfsHsvmuJODkCMx5TQJAaVs3V3IwYsqjXYPNZsl4RwD05W631IYsw9q5SBOpYmdGUm2DmPGezIyM7vbo4ilr6I7eabPcVmwYiTDp13SGZg==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTWBLpHR9BiHKeaSJnEXs2Xim9wraaEwqXbLbmt6HXyNnq3z8SWhRsVr58BNIHrOYSUaRrTuDkTcgqMjSna4tfLZvToXwySa1nA6FuH1gmmZBHjZ411BwOgnr+mNa9K3reJK2xdvlc+akHSbGJcg6SYp745xk18a1/MpyE0hErDQIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	Context mContext;
	Activity payActivity;
	private Handler mHandler;

	public AliPay(Context mContext, Activity payActivity, Handler mHandler) {
		super();
		this.mContext = mContext;
		this.payActivity = payActivity;
		this.mHandler = mHandler;
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(PayInfo PayInfo) {
		// 订单
		String orderInfo = getOrderInfo(PayInfo);
		
		// 对订单做RSA 签名
		String sign = sign(orderInfo, PayInfo.getPrivate_key());
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
		//final String payInfos = "_input_charset=\"utf-8\"&body=\"暂无资料\"&it_b_pay=\"30m\"&notify_url=\"api/order/notify_alipay/api/balance/test_alipay_succ\"&out_trade_no=\"2016071722093937329\"&partner=\"2088511239479533\"&payment_type=\"1\"&return_url=\"http://www.52jiabao.com/api/balance/test_alipay_succ\"&seller_id=\"1836601457@qq.com\"&service=\"mobile.securitypay.pay\"&subject=\"暂无资料\"&total_fee=\"0.03\"&sign=\"MyCkR6k7eXlW%2FbvXev8SABBZPp4l3kmiQE8bQBu%2Fdh9QXkDNipVvNIwvSxlb8eVDfB98UFijZtWhtkm%2BN73zfWkxinTyLDSHkpZEcKUCjdo6YdaWL3UuyvqzEi0uE7%2FWQyDvuedX2TiSVSLceeV%2BkcSWvbGnra46MjqSVkz5HHs%3D\"&sign_type=\"RSA\"";

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(payActivity);
				if (alipay.checkAccountIfExist()) {
					String result = alipay.pay(payInfo);
					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 40001;
//					 msg.obj = result;
					mHandler.sendMessage(msg);
				}
				// 调用支付接口，获取支付结果

			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(payActivity);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(payActivity);
		String version = payTask.getVersion();
		Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(PayInfo payInfo) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + payInfo.getPartner() + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + payInfo.getSeller_id() + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + payInfo.getTrade_no() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + payInfo.getSubject() + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + payInfo.getBody() + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + payInfo.getTotal_fee() + "\"";

		// 测试用 支付0.01元
		// orderInfo += "&total_fee=" + "\"" + 0.01 + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + payInfo.getNotify_url() + "\"";

		// 服务接口名称， 固定值
		 orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		 orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"" + payInfo.getNotify_url() + "\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content, String RSA_PRIVATE) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
