package com.example.aries;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class notification1 extends Activity {
   EditText ed1,ed2;
   String[] notify;
   String str="Notification sent";
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.notify);
      
      ed1=(EditText)findViewById(R.id.ed1);
      ed2=(EditText)findViewById(R.id.ed2);
      notify = getResources().getStringArray(R.array.notify);
      final Spinner s1 = (Spinner) findViewById(R.id.spinner1);
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
  	        android.R.layout.simple_spinner_dropdown_item, notify);

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


      Button b1=(Button)findViewById(R.id.b1);
      
      b1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String title=ed1.getText().toString().trim();
            String subject = s1.getSelectedItem().toString().trim();
            String body=ed2.getText().toString().trim();
            
            if( ed1.getText().toString().length() == 0 )
			    ed1.setError( "Please enter notification name!" );
            else if (s1.getSelectedItem().toString().trim().equals("choose one")) {
                Toast.makeText(notification1.this, "choose one",
                        Toast.LENGTH_SHORT).show();}

			else if( ed2.getText().toString().length() == 0 )
			    ed2.setError( "Please enter notification body!" );
			else{
            NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify=new Notification(R.drawable.ic_launcher,title,System.currentTimeMillis());
			Toast.makeText(getApplicationContext(), 
					str,Toast.LENGTH_LONG).show();
			Intent var=new Intent(notification1.this,first_page.class);
            startActivity(var);
            Intent var1=new Intent(notification1.this,view_task.class);
            //startActivity(var);
            
            PendingIntent p=PendingIntent.getActivity(getApplicationContext(), 0,var1, 0);
			
            var.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notify.setLatestEventInfo(getApplicationContext(),subject,body, p);
            notif.notify(0, notify);
         }
         }
      });
   }
   
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
   
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      
      int id = item.getItemId();
      
      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}