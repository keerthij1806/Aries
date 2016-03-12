package com.example.aries;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class first_page extends Activity {
	  String[] web = {
	            "Add project",
	            "Add task",
	            "View project",
	            "View task",
	            "Add team",
	            "Update task",
	            "View team",
	            "Attach files"
	 
	    } ;
	 int[] imageId = {
	            R.drawable.addproject,
	            R.drawable.addtask,
	            R.drawable.viewproject,
	            R.drawable.viewtask,
	            R.drawable.addteam,
	            R.drawable.taskup,
	            R.drawable.viewteam,
	            R.drawable.attach,
	            
	 
	    };
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml1);
        customGrid adapter = new customGrid(first_page.this, web, imageId);
        GridView gv=(GridView)findViewById(R.id.gridView1);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				CharSequence text="Now loading " + web[position]+"...";
				Toast.makeText(first_page.this, text, Toast.LENGTH_SHORT).show();
				
				if(position==0){
					Intent a=new Intent(v.getContext(),add_project.class);
					startActivity(a);
				}
				if(position==1){
					Intent b=new Intent(v.getContext(),add_task.class);
					startActivity(b);
				}
				if(position==2){
					Intent c=new Intent(v.getContext(),view_project.class);
					startActivity(c);
				}
				if(position==3){
					Intent d=new Intent(v.getContext(),view_task.class);
					startActivity(d);
				}
				if(position==4){
					Intent e=new Intent(v.getContext(),addteam_details.class);
					startActivity(e);
				}
				if(position==5){
					Intent f=new Intent(v.getContext(),task_update.class);
					startActivity(f);
				}
				if(position==6){
					Intent g=new Intent(v.getContext(),team_details.class);
					startActivity(g);
				}
				if(position==7){
					Intent h=new Intent(v.getContext(),vc.class);
					startActivity(h);
				}
						  
				// TODO Auto-generated method stub
				
			}
        	
        	
		});
           }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
