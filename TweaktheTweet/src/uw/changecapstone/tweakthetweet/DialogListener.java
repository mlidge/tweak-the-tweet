package uw.changecapstone.tweakthetweet;

import android.app.DialogFragment;
/*
 * DialogListener is an interface for activities
 * that allows responses to selections in dialog boxes
 *  
 */
public interface DialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
	
}
