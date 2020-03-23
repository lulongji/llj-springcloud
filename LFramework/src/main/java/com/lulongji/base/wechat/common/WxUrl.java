package com.lulongji.base.wechat.common;

/**
 * @Description:微信接口调用接口常量
 * @Author: lulongji
 * @Date: Created in 18:44 2018/11/12
 */
public class WxUrl {


    public static class common {

        public static final String WX_AES = "aes";

        public common() {
        }
    }

    public static class wxrest {

        /**
         * 获取access_token
         */
        public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

        /**
         * 获取用户信息
         */
        public static final String USER_URL_1 = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        public static final String USER_URL_2 = " https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

        /**
         * 二次授权
         */
        public static final String AUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";


        public static final String WX_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

        /**
         * 创建菜单
         */
        public static final String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

        /**
         * 查询菜单
         */
        public static final String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

        /**
         * 请求下载多媒体文件
         */
        public static final String MEDIA_FILE_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=[ACCESS_TOKEN]&media_id=[MEDIA_ID]";

        /**
         * 客服接口-发消息 using
         */
        public static final String SEND_KF_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=[ACCESS_TOKEN]";

        /**
         * 新增其他类型永久素材  媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）  using
         */
        public static final String ADD_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=[ACCESS_TOKEN]&type=[TYPE]";


        public wxrest() {
        }
    }


    public static class wxopen {
        /**
         * 微信公众号登录授权入口URL
         */
        public static final String AUTH_ENTRY_URL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=[COMPONENT_APPID]&pre_auth_code=[PRE_AUTH_CODE]&redirect_uri=[REDIRECT_URI]";

        /**
         * 获取第三方平台component_access_token
         */
        public static final String COMPONENT_ACCESS_TOKEN_URI = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";

        /**
         * 获取预授权码pre_auth_code
         */
        public static final String PRE_AUTH_CODE_URI = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=[COMPONENT_ACCESS_TOKEN]";

        /**
         * 使用授权码换取公众号或小程序的接口调用凭据和授权信息
         */
        public static final String API_QUERY_AUTH = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=[COMPONENT_ACCESS_TOKEN]";

        /**
         * 获取authorizerAccessToken
         */
        public static final String AUTHORIZER_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=[COMPONENT_ACCESS_TOKEN]";


        /**
         * 获取授权方的公众号帐号基本信息URL
         */
        public static final String API_GET_AUTHORIZER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=[COMPONENT_ACCESS_TOKEN]";


        public wxopen() {

        }

    }


}
