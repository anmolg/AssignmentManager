package gupta.app.assignmentmanager.fragment;

import gupta.app.assignmentmanager.R;
import gupta.app.assignmentmanager.task.ViewTask;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TaskOptionsFragment extends DialogFragment{
	
	private int taskPosition;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		taskPosition = getArguments().getInt("position");
		
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.task_options)
        .setItems(R.array.task_menu, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch(which) {
				case 0:
					((ViewTask) getActivity()).deleteTask(taskPosition);
					break;
				
				case 1:
					((ViewTask) getActivity()).getToModifyTask(taskPosition);
					break;
				}
			}
		});
        
               
        // Create the AlertDialog object and return it
        return builder.create();
    }


}
