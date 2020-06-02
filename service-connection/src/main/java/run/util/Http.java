package run.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.util.TextUtils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Http {
    private static String sessionId = "";

    public static void main(String[] args) {
        Map<String,Object> params = new HashMap<>(){{
            put("username","admin");
            put("password","admin");
        }};
        String res1 = doJsonPost("http://39.104.240.180:8080/loginWeb",params);
        System.out.println("==================================");
        System.out.println(res1);
        System.out.println("==================================");
        System.out.println(sessionId);
        get("http://39.104.240.180:8080/getLoginUser");
    }

    public static void get(String urlStr) {
        try {
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Cookie", sessionId);

            // 返回结果-字节输入流转换成字符输入流，控制台输出字符
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //发送JSON字符串 如果成功则返回成功标识。
    public static String doJsonPost(String urlPath, Object param) {
        String Json = JSON.toJSONString(param);

        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                System.out.println("doJsonPost: conn"+conn.getResponseCode());
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }

            //获得session信息
            String session_value = conn.getHeaderField("Set-Cookie");
            System.out.println("session_value : "+session_value);
            String[] id = session_value.split(";");
            //保存session信息
            sessionId = id[0];

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
