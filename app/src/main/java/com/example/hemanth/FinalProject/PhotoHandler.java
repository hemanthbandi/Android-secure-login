package com.example.hemanth.FinalProject;
/**
 * Created by hemanth on 06/30/2017.
 */

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;


public class PhotoHandler implements PictureCallback {

    private  Context context;
  Camera camera;
    public PhotoHandler(Context context, Camera camera)
    {
        this.context = context;
        this.camera=camera;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.e("Hema_LOG:","ON PICTURE TAKEN");
        File pictureFileDir = getDir();
        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Log.e("Hema_LOG:", "Can't create directory to save image.");
            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        String photoFile = "Picture.jpg";
        String filename = pictureFileDir.getPath() + File.separator + photoFile;
        Log.e("Hema_LOG:", "Cretae Image directory AT="+filename);

        File pictureFile = new File(filename);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
            Toast.makeText(context, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();

           camera.release();
        } catch (Exception error) {

            error.printStackTrace();
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "CameraAPIDemo");
    }
}