package com.example.hemanth.FinalProject;
/**
 * Created by hemanth on 06/30/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SendMailTask extends AsyncTask {

	private ProgressDialog statusDialog;
	private Activity sendMailActivity;
	//Context context;

	public SendMailTask(Activity activity) {
		this.sendMailActivity = activity;

	}

	protected void onPreExecute() {

	}

	@Override
	protected Object doInBackground(Object... args) {
		try {
			Log.e("Juno_LOG:","INSIDE GAMILTASK");
			GMail androidEmail = new GMail(args[0].toString(),
					args[1].toString(), args[2].toString(), args[3].toString(), args[4].toString());
			androidEmail.createEmailMessage();
			androidEmail.sendEmail();
			Log.e("Juno_LOG:","MESSAGE="+ "MAil has bern sent");


		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onProgressUpdate(Object... values) {


	}

	@Override
	public void onPostExecute(Object result) {

	}
}

