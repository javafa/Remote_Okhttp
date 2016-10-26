package com.kodonho.android.remote_okhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
    1. gradle에 okhttp 라이브러리 추가
    2. manifest 에 INTERNET 퍼미션 추가
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String key = "4c425976676b6f643437665377554c";
        String serviceName = "SeoulRoadNameInfo";
        int begin = 1;
        int end = 5;

        String url = "http://openapi.seoul.go.kr:8088/"+key+"/json/"+serviceName+"/"+begin+"/"+end+"/";

        callHttp(url);
    }

    public void callHttp(String url){
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                try{
                    result = getData(params[0]);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.e("Result",result);
            }
        }.execute(url);
    }

    // OKHTTP 사용하기
    public String getData(String url) throws IOException{
        // 1. OKHttp 클라이언트를 생성하고
        OkHttpClient client = new OkHttpClient();
        // 2. 요청정보를 세팅
        Request request = new Request.Builder()
                .url(url)
                .build();
        // 3. 응답객체를 통해 요청을 받는다
        Response response = client.newCall(request).execute();

        return response.body().string(); // string 함수를 body 내용을 문자로 변환해 준다
                                          // *주의 : toString() 이 아니다
    }
}
