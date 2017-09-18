package com.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class JavaSmsApi {

	//查账户信息的http地址
    private static String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";

    //智能匹配模板发送接口的http地址
    private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

    //模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

    //发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";

    //绑定主叫、被叫关系的接口http地址
    private static String URI_SEND_BIND = "https://call.yunpian.com/v2/call/bind.json";

    //解绑主叫、被叫关系的接口http地址
    private static String URI_SEND_UNBIND = "https://call.yunpian.com/v2/call/unbind.json";

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
    //修改为您的apikey.apikey可在官网（http://www.yunpian.com)登录后获取
    private static String apikey = "dc6710821f8bbf7616a64dbcfcb8cf1c";
    
    

    public static boolean sendMessage(String mobile,String member,String device,String message) throws IOException, URISyntaxException{

        /**************** 查账户信息调用示例 *****************/
        System.out.println(JavaSmsApi.getUserInfo(apikey));

        /**************** 使用智能匹配模板接口发短信(推荐) *****************/
        //设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String text = "【无线巡更网站】"+member+"，巡更任务("+device+")：\n"+message;
        //发短信调用示例
        String result = JavaSmsApi.sendSms(apikey, text, mobile);
        if(result == "")
        	return false;
        System.out.println(result);
        return true;
    }
    
    
    
    
    
    
    
    
    /* 单条短信发送,智能匹配短信模板
    *
    * @param apikey 成功注册后登录云片官网,进入后台可查看
    * @param text   需要使用已审核通过的模板或者默认模板
    * @param mobile 接收的手机号,仅支持单号码发送
    * @return json格式字符串
    */
   public static String singleSend(String apikey, String text, String mobile) {
       Map<String, String> params = new HashMap<String, String>();//请求参数集合
       params.put("apikey", apikey);
       params.put("text", text);
       params.put("mobile", mobile);
       return post("https://sms.yunpian.com/v2/sms/single_send.json", params);//请自行使用post方式请求,可使用Apache HttpClient
   }

    /**
    * 取账户信息
    *
    * @return json格式字符串
    * @throws java.io.IOException
    */

    public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return post(URI_GET_USER_INFO, params);
    }

    /**
    * 智能匹配模板接口发短信
    *
    * @param apikey apikey
    * @param text   　短信内容
    * @param mobile 　接受的手机号
    * @return json格式字符串
    * @throws IOException
    */

    public static String sendSms(String apikey, String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }

    /**
    * 通过模板发送短信(不推荐)
    *
    * @param apikey    apikey
    * @param tpl_id    　模板id
    * @param tpl_value 　模板变量值
    * @param mobile    　接受的手机号
    * @return json格式字符串
    * @throws IOException
    */

    public static String tplSendSms(long tpl_id, String tpl_value, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
    * 通过接口发送语音验证码
    * @param apikey apikey
    * @param mobile 接收的手机号
    * @param code   验证码
    * @return
    */

    public static String sendVoice(String apikey, String mobile, String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("mobile", mobile);
        params.put("code", code);
        return post(URI_SEND_VOICE, params);
    }

    /**
    * 通过接口绑定主被叫号码
    * @param apikey apikey
    * @param from 主叫
    * @param to   被叫
    * @param duration 有效时长，单位：秒
    * @return
    */

    public static String bindCall(String apikey, String from, String to , Integer duration ) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("from", from);
        params.put("to", to);
        params.put("duration", String.valueOf(duration));
        return post(URI_SEND_BIND, params);
    }

    /**
    * 通过接口解绑绑定主被叫号码
    * @param apikey apikey
    * @param from 主叫
    * @param to   被叫
    * @return
    */
    public static String unbindCall(String apikey, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("from", from);
        params.put("to", to);
        return post(URI_SEND_UNBIND, params);
    }

    /**
    * 基于HttpClient 4.3的通用POST方法
    *
    * @param url       提交的URL
    * @param paramsMap 提交<参数，值>Map
    * @return 提交响应
    */

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
            try {
                HttpPost method = new HttpPost(url);
                if (paramsMap != null) {
                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                        NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                        paramList.add(pair);
                    }
                    method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
                }
                response = client.execute(method);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseText = EntityUtils.toString(entity, ENCODING);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return responseText;
        }
}
