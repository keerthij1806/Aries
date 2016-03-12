package com.example.aries;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.   graphics.   Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class MainContent extends Activity {

	String data = "";
	TableLayout tl;
	TableRow tr;
	TextView label;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewp);

		tl = (TableLayout) findViewById(R.id.maintable);

		final GetDataFromDB1 getdb = new GetDataFromDB1();
		new Thread(new Runnable() {
			public void run() {
				data = getdb.getDataFromDB1();
				System.out.println(data);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ArrayList<Users1> users = parseJSON(data);
						addData(users);						
					}
				});
				
			}
		}).start();
	}

	public ArrayList<Users1> parseJSON(String result) {
		ArrayList<Users1> users = new ArrayList<Users1>();
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				Users1 user = new Users1();
				user.setId(json_data.getInt("id"));
				user.setpsdate(json_data.getString("start_date"));
				user.setpedate(json_data.getString("end_date"));
				user.settask(json_data.getString("task"));
				

				
				users.add(user);
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());  
		}
		return users;
	}

	void addHeader(){
		/** Create a TableRow dynamically **/
		tr = new TableRow(this);

		/** Creating a TextView to add to the row **/
		label = new TextView(this);
		label.setText("Start date");
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label.setPadding(5, 5, 5, 5);
		label.setBackgroundColor(Color.MAGENTA);
		LinearLayout Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label,params);
		tr.addView((View)Ll); // Adding textView to tablerow.

		/** Creating Qty Button **/
		TextView label2 = new TextView(this);
		label2.setText("End date");
		label2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label2.setPadding(5, 5, 5, 5);
		label2.setBackgroundColor(Color.MAGENTA);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label2,params);
		tr.addView((View)Ll); // Adding textview to tablerow.

		TextView label3 = new TextView(this);
		label3.setText("Task");
		label3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label3.setPadding(5, 5, 5, 5);
		label3.setBackgroundColor(Color.MAGENTA);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label3,params);
		tr.addView((View)Ll); // Adding textview to tablerow.

		 // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void addData(ArrayList<Users1> users) {

		addHeader();
		
		for (Iterator i = users.iterator(); i.hasNext();) {

			Users1 p = (Users1) i.next();

			/** Create a TableRow dynamically **/
			tr = new TableRow(this);

			/** Creating a TextView to add to the row **/
			label = new TextView(this);
			label.setText(p.getpsdate());
			label.setId(p.getId());
			label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			label.setPadding(5, 5, 5, 5);
			label.setBackgroundColor(Color.GRAY);
			LinearLayout Ll = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(label,params);
			tr.addView((View)Ll); // Adding textView to tablerow.

			/** Creating Qty Button **/
			TextView label2 = new TextView(this);
			label2.setText(p.getpedate());
			label2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			label2.setPadding(5, 5, 5, 5);
			label2.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(label2,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			
			TextView label3 = new TextView(this);
			label3.setText(p.gettask());
			label3.setId(p.getId());
			label3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			label3.setPadding(5, 5, 5, 5);
			label3.setBackgroundColor(Color.GRAY);
			LinearLayout Ll1 = new LinearLayout(this);
			LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params1.setMargins(5, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll1.addView(label3,params1);
			tr.addView((View)Ll1); // Adding textView to tablerow.

			 // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
		}
	}
}
