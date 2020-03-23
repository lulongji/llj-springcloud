package com.lulongji.base.wechat.util.aes;

public class Test {
	public static void main(String[] args) throws Exception {
		// 需要加密的明文
		String encodingAesKey = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		String token = "9795efa8e1854496b2db065db55e8581";
		String timestamp = "1452077479";
		String nonce = "1484520289";
		String appId = "wx64571873bdaca0f9";
		String msgSignature = "234b336f34145a1a5b22a9152c49dc6a7d9712e9";

		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		// <xml> <AppId><![CDATA[wx64571873bdaca0f9]]></AppId>
		// <Encrypt><![CDATA[rXJ+2V7XvtKARWYOgIQecyY/TGQRFH6ymKpISi1enAUnDYEe0GVB93zbA5cTWrZSUxRJn6o009AeA+uaoXXRt+dCmgs+WjxTCMa1Iif3HmaEeXiJIvl0JOpgOLS/Zf/U7LWQguprHp7//VuoUad9bm3hszMRQ0MCreMmd1H5BdzZ0lGxhC8rpNeScNzCqDxfji42C2+J2JnSHtJUSkh3I5Fgxm133zCN6rTx2DoZnczRAUXBarxvB3wXt/TGRdg2N4hqhqHFYvZ5BQkE0Up4SPVW9NhE79dzaALcVrFZEAMBSlXa2kRufaGnGXGEWP0nWs/aOTzxqQbt0385sfATvRSKNqD0FQk3wzrQk0YS8thG76vnPhweUU5wzdzIO+W0g320i2wxGdUlHUDUYFPWgq9FkMhtaUerRfKIfHEadn0tQreWU6xUSg/F5M3OqK8fsX/hHpHPqCfV+aiB2IlonQ==]]></Encrypt></xml>
		// 密文
		String encrypt = "6LCMKCEZgvZI+cY6elrZ9xFBigHpnP3pTKDZnnU0KYm4tu+CbI7+6XsSKxjwwwi7GBF5N2WagGHJokxzvVdqMoAWaVg/uFPNWHftnYxDnHG0ZN9VOrlYtAc7NpxPmecy8JOOokkPqVex1Ha1WE7n6KPqlSwGjey5nJvOU9B95VSMEggi8+JdU8MHs9pVnm5ADJJQcEcP98adQ0DvxY9phKW2u70BqGoDLOHy0WC3UHT+MeQWYOFQVhef9Hf3H5yms2Gb4RJHCZdq3FiXhL7Qa/ZpGF4jw9FWLw5ZJyx52RZm1PbYId1n4qyIeRW52Anx2rFm/VeDypThr6FTgcDqfZ5IYBnK1/LaPMMSL//vVSwP7spcsW2O/ajdZojWs9zojzQahgKUuHo8iMT3e7qgh2/Z7ehH9HrSMkmH1YAjB3HVJ7zvA64ifpfqwl6aJQaHPDeJmA6+En55I3LibdnZ8g==";
		String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		String fromXML = String.format(format, encrypt);

		// 第三方收到公众号平台发送的消息
		String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		System.out.println("解密后明文: " + result2);
	}
}
