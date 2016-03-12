package com.example.aries;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class task_update extends Activity {
	  EditText e1;
	  EditText e2;
	  EditText e3;
	  EditText e4;
	  EditText e5;
	  EditText e6;
	  TextView dateView;
	String str="Updated successfully ";
	 int month;
		int day;
		int year;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_update);
        Button b1=(Button)findViewById(R.id.button1);
        e1=(EditText)findViewById(R.id.editText1);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        e5=(EditText)findViewById(R.id.editText5);
        e6=(EditText)findViewById(R.id.editText6);
        b1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if( e1.getText().toString().length() == 0 )
				    e1.setError( "Project name is required!" );

				else if( e2.getText().toString().length() == 0 )
				    e2.setError( "Task assigned is required!" );

				else if( e3.getText().toString().length() == 0 )
				    e3.setError( "Actual start date is required!" );

				else if( e4.getText().toString().length() == 0 )
				    e4.setError( "Actual end date is required!" );

				else if( e5.getText().toString().length() == 0 )
				    e5.setError( "Hours worked is required!" );

				else if( e6.getText().toString().length() == 0 )
				    e6.setError( "Percent of work completed is required!" );
				else{
					String project_name = e1.getText().toString();
					String task= e2.getText().toString();
					String start_date = e3.getText().toString();
					String end_date = e4.getText().toString();
					String hours = e5.getText().toString();
					String percent = e6.getText().toString();
					try {project_name = URLEncoder.encode(project_name, "UTF-8");
					task = URLEncoder.encode(task, "UTF-8");
					start_date = URLEncoder.encode(start_date, "UTF-8");
					end_date = URLEncoder.encode(end_date, "UTF-8");
					hours = URLEncoder.encode(hours, "UTF-8");
					percent = URLEncoder.encode(percent, "UTF-8");
						
						
						String url = "http://172.16.13.166/db/task_update.php?project_name="
								+ project_name.trim() + "&task="
								+ task.trim() + "&start_date="
								+ start_date.trim() + "&end_date="
								+ end_date.trim() + "&hours="
								+ hours.trim() + "&percent="
								+ percent.trim();
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
				
				dialog = new ProgressDialog(task_update.this);
				dialog.setTitle("Processing...");
				dialog.setMessage("Please wait.");
				dialog.setCancelable(false);
				dialog.show();
				Toast.makeText(getApplicationContext(), 
						str,Toast.LENGTH_LONG).show();
				Intent var3=new Intent(task_update.this,vc.class);
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

	    @SuppressLint("ValidFragment") 
	      private class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	        
	         public DateDialog(View view){
	         e3=(EditText)view;
	         e4=(EditText)view;
	      }
	      public Dialog onCreateDialog(Bundle savedInstanceState) {
	    

	// Use the current date as the default date in the dialog
	  		final Calendar c = Calendar.getInstance();
	  		int year = c.get(Calendar.YEAR);
	  		int month = c.get(Calendar.MONTH);
	  		int day = c.get(Calendar.DAY_OF_MONTH);
	  		// Create a new instance of DatePickerDialog and return it
	  		return new DatePickerDialog(getActivity(), this, year, month, day);
	         

	  	}
	  	
	  	public void onDateSet(DatePicker view, int year, int month, int day) {
	  		   //show to the selected date in the text box
	  		   String date=year+"-"+(month+1)+"-"+day;
	  		   e3.setText(date);
	  		 e4.setText(date);
	  		}
	  	
	  	
	  
	  }
	    public void onStart(){
	     	super.onStart();
	     	
	     	e3=(EditText)findViewById(R.id.editText3);
	     	e4=(EditText)findViewById(R.id.editText4);
	  			   e3.setOnFocusChangeListener(new OnFocusChangeListener(){
	  			   public void onFocusChange(View view, boolean hasfocus){
	  			      if(hasfocus){
	  					         DateDialog dialog=new DateDialog(view);
	  					        FragmentTransaction ft =getFragmentManager().beginTransaction();
	  					        dialog.show(ft, "DatePicker");
	  					        
	  					        
	  					        
	  					        
	  					
	  				}
	  			}
	  			
	  		});
	  			 e4.setOnFocusChangeListener(new OnFocusChangeListener(){
		  			   public void onFocusChange(View view, boolean hasfocus){
		  			      if(hasfocus){
		  					         DateDialog dialog=new DateDialog(view);
		  					        FragmentTransaction ft =getFragmentManager().beginTransaction();
		  					        dialog.show(ft, "DatePicker");
		  					        
		  					        
		  					        
		  					        
		  					
		  				}
		  			}
		  			
		  		});
	  			   
	  			   
	  }
	  
	    
	    
	    


}

				