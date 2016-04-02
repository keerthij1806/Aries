package com.example.aries;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
 
public class GetDataFromDB1 {
 
    public String getDataFromDB1() {
        try {
 
            HttpPost httppost;
            HttpClient httpclient;
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(
                    "http://172.16.13.191/db/GetUsers1.php"); // change this to your URL.....
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost,
                    responseHandler);
             
            return response.trim();
 
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            return "error";
        }
    }
}