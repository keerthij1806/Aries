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

//import com.example.ariess.create_page.pass_value_to_db;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "ValidFragment" }) public class add_task extends Activity {
	
	Button button1;
	String str="Task posted successfully";
	  EditText et1;
	  EditText et2;
	  EditText et3;
	  EditText et4;
	  EditText et5;
	  EditText et6;
	  TextView dateView;
	  int month;
		int day;
		int year;
	    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addt);
              Button b1=(Button)findViewById(R.id.button1);
             
            
              
         et1=(EditText)findViewById(R.id.editText1);
         et2=(EditText)findViewById(R.id.editText2);
         et3=(EditText)findViewById(R.id.editText3);
         et4=(EditText)findViewById(R.id.editText4);
         et5=(EditText)findViewById(R.id.editText5);
         et6=(EditText)findViewById(R.id.editText6);
         
     	

    	b1.setOnClickListener(new View.OnClickListener() {
    	
    	
			public void onClick(View v) {
				// TODO Auto-generated method stub			
		

			        if( et1.getText().toString().length() == 0 )
				    et1.setError( "Please enter project name" );

				else if( et2.getText().toString().length() == 0 )
				    et2.setError( "Please enter client name" );
				
				else if( et3.getText().toString().length() == 0 )
				    et3.setError( "Please enter project start date" );

				else if( et4.getText().toString().length() == 0 )
				    et4.setError( "Please enter project end date" );

				else if( et5.getText().toString().length() == 0 )
				    et5.setError( "Please enter member name" );
				else if (et6.getText().toString().length() == 0 )
			    et6.setError( "Please enter the task" );
				

				else{
				  
					String project_name = et1.getText().toString();
					String client_name = et2.getText().toString();
					String start_date = et3.getText().toString();
					
					String end_date = et4.getText().toString();
					String member_name = et5.getText().toString();
					String task = et6.getText().toString();
				
					try {
						
						 project_name = URLEncoder.encode( project_name, "UTF-8");
						 client_name = URLEncoder.encode(client_name, "UTF-8");
						 start_date = URLEncoder.encode(start_date, "UTF-8");
						 end_date = URLEncoder.encode(end_date, "UTF-8");
						 member_name = URLEncoder.encode( member_name, "UTF-8");
						 task = URLEncoder.encode(task, "UTF-8");
						 
						
						
						String url = "http://172.16.13.166/db/add_task.php?project_name="
								+ project_name.trim() + "&client_name="
								+ client_name.trim() + "&start_date="
								+ start_date.trim() + "&end_date="
								+ end_date.trim() + "&member_name="
								+ member_name.trim()+  "&task="
										+ task.trim();
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
				
				dialog = new ProgressDialog(add_task.this);
				dialog.setTitle("Processing...");
				dialog.setMessage("Please wait.");
				dialog.setCancelable(false);
				dialog.show();
				Toast.makeText(getApplicationContext(), 
						str,Toast.LENGTH_LONG).show();
				Intent var3=new Intent(add_task.this,notification1.class);
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
	         et3=(EditText)view;
	         et4=(EditText)view;
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
	  		   et3.setText(date);
	  		 et4.setText(date);
	  		}
	  	
	  	
	  
	  }
	    public void onStart(){
	     	super.onStart();
	     	
	     	et3=(EditText)findViewById(R.id.editText3);
	     	et4=(EditText)findViewById(R.id.editText4);
	  			   et3.setOnFocusChangeListener(new OnFocusChangeListener(){
	  			   public void onFocusChange(View view, boolean hasfocus){
	  			      if(hasfocus){
	  					         DateDialog dialog=new DateDialog(view);
	  					        FragmentTransaction ft =getFragmentManager().beginTransaction();
	  					        dialog.show(ft, "DatePicker");
	  					        
	  					        
	  					        
	  					        
	  					
	  				}
	  			}
	  			
	  		});
	  			 et4.setOnFocusChangeListener(new OnFocusChangeListener(){
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
