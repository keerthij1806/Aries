package com.example.aries;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class addteam_details extends Activity {
	String[] team;
	  EditText e2;
	  EditText e3;
	  EditText e4;
	  EditText e5;
	  EditText e6;
	String str="Team added successfully ";
		protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d);
        Button b1=(Button)findViewById(R.id.button1);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        e5=(EditText)findViewById(R.id.editText5);
        e6=(EditText)findViewById(R.id.editText6);
        team = getResources().getStringArray(R.array.team_array);
        final Spinner s1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
       	        android.R.layout.simple_spinner_dropdown_item, team);

       	    s1.setAdapter(adapter);
       	    s1.setOnItemSelectedListener(new OnItemSelectedListener() {
       	      @Override
       	      public void onItemSelected(AdapterView<?> arg0, View arg1,
       	          int arg2, long arg3) {
       	       
       	      }
       	      @Override
       	      public void onNothingSelected(AdapterView<?> arg0) {
       	      }
       	    });
     

        b1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(s1.getSelectedItem().toString().trim().equals("choose one")) {
	                Toast.makeText(addteam_details.this, "choose one",
	                        Toast.LENGTH_SHORT).show();}

				else if( e2.getText().toString().length() == 0 )
				    e2.setError( "Team leader name is required!" );

				else if( e3.getText().toString().length() == 0 )
				    e3.setError( "Member1 name is required!" );

				else if( e4.getText().toString().length() == 0 )
				    e4.setError( "Member2 name is required!" );

				else if( e5.getText().toString().length() == 0 )
				    e5.setError( "Member3 name is required!" );

				else if( e6.getText().toString().length() == 0 )
				    e6.setError( "Member4 name is required!" );
				else{
					String team_name = s1.getSelectedItem().toString();
					String team_lead= e2.getText().toString();
					String member1 = e3.getText().toString();
					String member2 = e4.getText().toString();
					String member3= e5.getText().toString();
					String member4 = e6.getText().toString();
					try {team_name = URLEncoder.encode(team_name, "UTF-8");
					team_lead = URLEncoder.encode(team_lead, "UTF-8");
					member1 = URLEncoder.encode(member1, "UTF-8");
					member2 = URLEncoder.encode(member2, "UTF-8");
					member3 = URLEncoder.encode(member3, "UTF-8");
					member4 = URLEncoder.encode(member4, "UTF-8");
						
						
						String url = "http://172.16.13.191/db/add_team.php?team_name="
								+ team_name.trim() + "&team_lead="
								+ team_lead.trim() + "&member1="
								+ member1.trim() + "&member2="
								+ member2.trim() + "&member3="
								+ member3.trim() + "&member4="
								+ member4.trim();
						System.out.println(url);
						pass_value_to_db get = new  pass_value_to_db();
						get.execute(new String[] { url });
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
			});
	        
	 }
	 private class pass_value_to_db extends AsyncTask<String, Void, String> {

			ProgressDialog dialog;

			@Override
			protected void onPreExecute() {
				
				dialog = new ProgressDialog(addteam_details.this);
				dialog.setTitle("Processing...");
				dialog.setMessage("Please wait.");
				dialog.setCancelable(false);
				dialog.show();
				Toast.makeText(getApplicationContext(), 
						str,Toast.LENGTH_LONG).show();
				Intent var3=new Intent(addteam_details.this,first_page.class);
			    startActivity(var3);// TODO Auto-generated method stub
			}

			@Override
			protected String doInBackground(String... urls) {
				String result = "";
				for (String url : urls) {
					InputStream is = null;
					try {

						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(url);
						HttpResponse response = httpclient.execute(httppost);
						int status = response.getStatusLine().getStatusCode();
						Log.d("KG", "status=" + status);

						if (status == 200) {
							HttpEntity entity = response.getEntity();
							is = entity.getContent();
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(is, "iso-8859-1"), 8);
							String line = "";
							while ((line = reader.readLine()) != null) {
								result += line;
							}
							is.close();

							Log.v("KG", result);

						}
					} catch (Exception ex) {
						Log.e("Error", ex.toString());
					}
				}
				return result;
			}

			protected void onPostExecute(String result) {
				Log.v("KG", "output=" + result);
				result = result.trim(); //

				if (result.equals("false")) {

					// *******************************************************

					Toast.makeText(getApplicationContext(),
							" Please try again later...", Toast.LENGTH_LONG).show();
				} else {
					System.out.println(result);

				}

				if (dialog != null)
					dialog.dismiss();

			}
		}

}
