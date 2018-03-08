package com.example.hemanth.FinalProject;

// Receiver of the Application
/**
 * Created by hemanth on 6/30/2017.
 */
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class Admin extends DeviceAdminReceiver {
    private final static String DEBUG_TAG = "MakePhotoActivity";
    private Camera camera;
    private int cameraId = 0;
    Context context;

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        Log.e("Hema_LOG:","Entered into wrong password");
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showToast(context, "Sample Device Admin: pw failed");
        try {
           // Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            // Vibrate for 2 seconds
            //v.vibrate(2000);
            cameraId = findFrontFacingCamera();
            camera = Camera.open(cameraId);
            SurfaceTexture st = new SurfaceTexture(MODE_PRIVATE);
            camera.setPreviewTexture(st);
            camera.startPreview();
            Log.e("Hema_Log:", "PREVIEW DOWN");
            camera.takePicture(null, null, new PhotoHandler(context, camera));
            Log.e("Hema_LOG:", "CAMREA DONE");
            context.getSharedPreferences("mail_id", MODE_PRIVATE);
            DatabaseHandler handler = new DatabaseHandler(context);
            String fromEmail = "alertlogin559@gmail.com";
            String fromPassword = "Deepu@2017";
            String toEmails= handler.getContact();
            String emailSubject = "Alert";
            String emailBody = "Hello Admin," +
                    "Some one else tried you unlock your phone. Catch him in the picture";
             String c= "com.example.hemanth.FinalProject.Main2Activity";
            Class<?> myClass = Class.forName(c);
            Activity obj = (Activity) myClass.newInstance();
            Log.e("Hema_LOG:","About to send mail to ="+ handler.getContact());
            new SendMailTask(obj).execute(fromEmail,fromPassword,toEmails,emailSubject,emailBody);
        }catch (Exception e)
        {
            Log.e("hema_LOG:","SOMETHING WENT WRONG:="+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, "Welcome Device Owner");
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }


}
