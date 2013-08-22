package gupta.app.assignmentmanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		Button addAssignment = (Button) findViewById (R.id.addTaskButton);
		Button viewLastDatabaseItem = (Button) findViewById (R.id.viewAllTaskButton);
		
		addAssignment.setOnClickListener(new OnClickListener() 
		{
			public void onClick (View view) {
				addTask(view);
			}
		});
		
		viewLastDatabaseItem.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				viewLastTask(view);
			}
		});
	}

	protected void viewLastTask(View view) {
		Intent intent = new Intent(this, ViewTask.class);
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	
	public void addTask(View view) {
		Intent intent = new Intent(this, AddTask.class);
		startActivity(intent);
	}

}
