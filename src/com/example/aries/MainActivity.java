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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	String str1 = "Correct password";
	String str2 = "Incorrect password";
	Intent var4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// setContentView(R.layout.test);
		Button b1 = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		Button b3 = (Button) findViewById(R.id.button3);

		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText username = (EditText) findViewById(R.id.editText1);
				EditText password = (EditText) findViewById(R.id.editText2);
				if (username.getText().toString().length() == 0)
					username.setError("username name is required!");
				else if (password.getText().toString().length() == 0)
					password.setError("Password is required!");

				else {

					String user_value = username.getText().toString();
					String pass_value = password.getText().toString();
					try {

						user_value = URLEncoder.encode(user_value, "UTF-8");
						pass_value = URLEncoder.encode(pass_value, "UTF-8");

						String url = "http://172.16.13.191/db/login.php?username="
								+ user_value.trim()
								+ "&password="
								+ pass_value.trim();

						System.out.println(url);
						pass_value_to_db get = new pass_value_to_db();
						get.execute(new String[] { url });
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});


b2.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View v) {
EditText username = (EditText)findViewById(R.id.editText1);
EditText password = (EditText)findViewById(R.id.editText2);	
username.setText("");
password.setText("");

// TODO Auto-generated method stub

}
});
b3.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View v) {
Intent var2=new Intent(MainActivity.this,create_page.class);

startActivity(var2);

// TODO Auto-generated method stub

}
});




}


	private class pass_value_to_db extends AsyncTask<String, Void, String> {

		ProgressDialog dialog;
		Intent var4;

		@Override
		protected void onPreExecute() {

			dialog = new ProgressDialog(MainActivity.this);
			dialog.setTitle("Processing...");
			dialog.setMessage("Please wait.");
			dialog.setCancelable(false);
			dialog.show();

		
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

			if (result.equals("0")) {

				// *******************************************************

				Toast.makeText(getApplicationContext(),
						" Invalid username or password...", Toast.LENGTH_LONG).show();
			} else {
				var4 = new Intent(MainActivity.this, first_page.class);
				startActivity(var4);
			}

			if (dialog != null)
				dialog.dismiss();

		}
	}

}
