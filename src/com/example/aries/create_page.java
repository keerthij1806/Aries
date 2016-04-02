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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class create_page extends Activity {
	String[] presidents;
	  Spinner sp;
	  EditText e1;
	  EditText e2;
	  EditText e3;
	 // EditText e4;
	  EditText e5;
	  EditText e6;
	  static final String str="Account created successfully";
	  
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.xml2);
	        Button b1=(Button)findViewById(R.id.submit);
	         e1=(EditText)findViewById(R.id.editText1);
	         e2=(EditText)findViewById(R.id.editText2);
	         e3=(EditText)findViewById(R.id.editText3);
	         e5=(EditText)findViewById(R.id.editText5);
	         e6=(EditText)findViewById(R.id.editText6);
	         presidents = getResources().getStringArray(R.array.presidents_array);
	         final Spinner s1 = (Spinner) findViewById(R.id.spinner1);
	         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        	        android.R.layout.simple_spinner_dropdown_item, presidents);

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
					    e1.setError( "First name is required!" );

					else if( e2.getText().toString().length() == 0 )
					    e2.setError( "Last name is required!" );

					else if( e3.getText().toString().length() == 0 )
					    e3.setError( "4 digit company id is required!" );
					
					else if (s1.getSelectedItem().toString().trim().equals("choose one")) {
		                Toast.makeText(create_page.this, "choose one",
		                        Toast.LENGTH_SHORT).show();}

					else if( e5.getText().toString().length() == 0 )
					    e5.setError( "Username is required!" );

					else if( e6.getText().toString().length() == 0 )
					    e6.setError( "Password is required!" );
					
					else{
					String firstname_value = e1.getText().toString();
					String lastname_value = e2.getText().toString();
					String companyid = e3.getText().toString();
					String designation = s1.getSelectedItem().toString();
					String user_value = e5.getText().toString();
					String pass_value = e6.getText().toString();
					try {firstname_value = URLEncoder.encode(firstname_value, "UTF-8");
						lastname_value = URLEncoder.encode(lastname_value, "UTF-8");
						companyid = URLEncoder.encode(companyid, "UTF-8");
						designation = URLEncoder.encode(designation, "UTF-8");
						user_value = URLEncoder.encode(user_value, "UTF-8");
						pass_value = URLEncoder.encode(pass_value, "UTF-8");
						
						
						String url = "http://172.16.13.191/db/create_acc.php?firstname="
								+ firstname_value.trim() + "&lastname="
								+ lastname_value.trim() + "&companyid="
								+ companyid.trim()+ "&designation="
								+ designation.trim()+"&username="
								+ user_value.trim() + "&password="
								+ pass_value.trim() ;
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
				
				dialog = new ProgressDialog(create_page.this);
				dialog.setTitle("Processing...");
				dialog.setMessage("Please wait.");
				dialog.setCancelable(false);
				dialog.show();
				Toast.makeText(getApplicationContext(), 
						str,Toast.LENGTH_LONG).show();
				Intent var3=new Intent(create_page.this,MainActivity.class);
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
