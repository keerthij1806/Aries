package com.example.aries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



public class team_details extends Activity {

	//Button topost;
	String final_data;
	
    
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list1);
                
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        connect();
       
       
       
				// TODO Auto-generated method stub
				//Intent intent = new Intent(list.this, OtherActivity.class);
				

			
    }

	private void connect() {
		// TODO Auto-generated method stub
		  final String final_data;
		    List<String> r = new ArrayList<String>();
		    ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,r);
		    final ListView list=(ListView)findViewById(R.id.listView1);
		   

	       
		    
		        try {
		            DefaultHttpClient client = new DefaultHttpClient();
		            HttpGet request = new HttpGet("http://172.16.13.191/db/selectall1.php");
		            HttpResponse response = client.execute(request);
		            HttpEntity entity=response.getEntity();
		            final_data=EntityUtils.toString(entity);
		            Log.e("STRING", final_data);
		            
		            
		            try {
		            
		    JSONArray json=new JSONArray(final_data);
		    for(int i=0;i<json.length(); i++)
		    {
		    JSONObject obj=json.getJSONObject(i);
		    String name=obj.getString("final_data");
		   
		  
		    Log.e("STRING", final_data);
			   
		    r.add(name);
		   
		    list.setAdapter(adapter);
		    }		           		      
		    
		              } catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    }
		         
		        } catch (ClientProtocolException e) {
		            Log.d("HTTPCLIENT", e.getLocalizedMessage());
		        } catch (IOException e) {
		            Log.d("HTTPCLIENT", e.getLocalizedMessage());
		        }
		     
	}

}
      		      
	