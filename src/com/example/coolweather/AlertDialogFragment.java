package com.example.coolweather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;



public class AlertDialogFragment extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_title);
		builder.setMessage(getActivity().getString(R.string.failed_request));
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		return dialog;
	}

	

}
