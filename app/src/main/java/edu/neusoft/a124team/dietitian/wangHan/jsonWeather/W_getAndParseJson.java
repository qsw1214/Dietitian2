package edu.neusoft.a124team.dietitian.wangHan.jsonWeather;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hansen on 2016/6/11.
 */
public class W_getAndParseJson {
    private String url="http://www.weather.com.cn/adat/cityinfo/101070201.html";
    public static final int PARSESUCCWSS=0x2001;
    private Handler handler;
    public W_getAndParseJson(Handler handler) {
        // TODO Auto-generated constructor stub
        this.handler=handler;
    }
    /**
     * 获取网络上的JSON
     */
    public void getJsonFromInternet () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode()==200) {
                        InputStream inputStream=conn.getInputStream();  //创建一个输入流
                        List<W_weather> listWeather=parseJson(inputStream);
                        if (listWeather.size()>0) {
                            Message msg=new Message();
                            msg.what=PARSESUCCWSS;//通知UI线程Json解析完成
                            msg.obj=listWeather;//将解析出的数据传递给UI线程
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 解析json格式的输入流转换成List
     * @param inputStream
     * @return List
     */

    protected List<W_weather> parseJson(InputStream inputStream) {
        // TODO Auto-generated method stub
        List<W_weather>listWeather = new ArrayList<W_weather>();
        byte [] jsonBytes = convertIsToByteArray(inputStream);  //将输入流转换为字节数组
        String json = new String(jsonBytes);
        System.out.println("JSON内容："+json);
        try {
            JSONObject jsonArray = new JSONObject(json);  //实例化
            //    for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jObject=jsonArray.getJSONObject("weatherinfo");

            String city = jObject.getString("city");
            String temp1 = jObject.getString("temp1");
            String temp2 = jObject.getString("temp2");
            String wather = jObject.getString("weather");
            String ptime = jObject.getString("ptime");

                /*int id=jObject.getInt("id");
                String title=jObject.getString("title");
                long time=jObject.getLong("publishDate");*/
            W_weather news=new W_weather(city, temp1,temp2,wather,ptime);
            listWeather.add(news);
            // }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listWeather;
    }
    /**
     * 将输入流转化成ByteArray
     * @param inputStream
     * @return ByteArray
     */
    private byte[] convertIsToByteArray(InputStream inputStream) {
        // TODO Auto-generated method stub
        ByteArrayOutputStream baos=new ByteArrayOutputStream(); //字节数组的输出流
        byte buffer[]=new byte[1024];
        int length=0;
        try {
            while ((length=inputStream.read(buffer))!=-1) {
                baos.write(buffer, 0, length);
            }
            inputStream.close();//关闭输入流
            baos.flush();//刷新缓存
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

}
