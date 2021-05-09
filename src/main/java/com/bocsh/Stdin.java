package com.bocsh;

import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Stdin {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    public static void main(String args[]) throws Exception {

        String url = "http://192.168.31.147:8099/req/exec";
        System.out.println("开始连接 " + url + " ...");
        String pwd = post(url,"pwd").replace("\n","");

        System.out.println("连接成功！");
        while(true){

            BufferedReader stdin =new BufferedReader(new InputStreamReader(System.in));
            System.out.print("["+pwd + "]$");
            String cmd = stdin.readLine();
            if(cmd.equals("")){
                //System.out.println("12121");
            }
            else{
                if(cmd.startsWith("cd")){ //目录切换命令
                    String res = post(url,"cd " + pwd + ";" + cmd + ";pwd");
                    if(res.indexOf(":cd:")<0){
                        pwd = res.replace("\n","");
                    }
                    else{
                        System.out.print(res);
                    }
                }
                else{
                    System.out.print(post(url, "cd " + pwd + ";" + cmd));
                }

            }

        }

    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
