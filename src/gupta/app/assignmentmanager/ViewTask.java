package gupta.app.assignmentmanager;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ViewTask extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);
		TextView viewLastTask = (TextView) findViewById(R.id.lastTask);
		TaskDB db = new TaskDB(this);
		List<Task> lastTask = db.getAllTasks();
		String allTaskTitle = "";
		for (int i = 0; i < lastTask.size(); i++) {
			allTaskTitle = allTaskTitle + lastTask.get(i).getTitle() + "|||";
		}
		viewLastTask.setText(allTaskTitle);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_task, menu);
		return true;
	}

}
