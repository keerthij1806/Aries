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

public class add_project extends Activity {
	String team[];
	  EditText e1;
	  EditText e2;
	  EditText e3;
	  EditText e4;
	  EditText e5;
	  EditText e6;
	  static final String str="Posted successfully";
	  
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.addp);
	        Button b1=(Button)findViewById(R.id.button1);
	         e1=(EditText)findViewById(R.id.editText1);
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
	      

	        b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if( e1.getText().toString().length() == 0 )
					    e1.setError( "Project name is required!" );

					else if( e2.getText().toString().length() == 0 )
					    e2.setError( "Project number is required!" );

					else if( e3.getText().toString().length() == 0 )
					    e3.setError( "Client name is required!" );

					else if( e4.getText().toString().length() == 0 )
					    e4.setError( "Client number is required!" );

					else if(s1.getSelectedItem().toString().trim().equals("choose one")) {
		                Toast.makeText(add_project.this, "choose one",
		                        Toast.LENGTH_SHORT).show();}

					else if( e6.getText().toString().length() == 0 )
					    e6.setError( "Team lead is required!" );
					
					else{
					String project_name = e1.getText().toString();
					String project_number= e2.getText().toString();
					String client_name = e3.getText().toString();
					String client_number = e4.getText().toString();
					String team_name = s1.getSelectedItem().toString();
					String team_lead = e6.getText().toString();
					try {project_name = URLEncoder.encode(project_name, "UTF-8");
					project_number = URLEncoder.encode(project_number, "UTF-8");
					client_name = URLEncoder.encode(client_name, "UTF-8");
					client_number = URLEncoder.encode(client_number, "UTF-8");
					team_name = URLEncoder.encode(team_name, "UTF-8");
					team_lead = URLEncoder.encode(team_lead, "UTF-8");
						
						
						String url = "http://172.16.13.191/db/add_project.php?project_name="
								+ project_name.trim() + "&project_number="
								+ project_number.trim() + "&client_name="
								+ client_name.trim() + "&client_number="
								+ client_number.trim() + "&team_name="
								+ team_name.trim() + "&team_lead="
								+ team_lead.trim();
						System.out.println(url);
						pass_value_to_db get = new  pass_value_to_db();
						get.execute(new String[] { url });
					}
					catch (UnsupportedEncodingException e) {
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
				
				dialog = new ProgressDialog(add_project.this);
				dialog.setTitle("Processing...");
				dialog.setMessage("Please wait.");
				dialog.setCancelable(false);
				dialog.show();
				Toast.makeText(getApplicationContext(), 
						str,Toast.LENGTH_LONG).show();
				Intent var3=new Intent(add_project.this,notification.class);
				
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
