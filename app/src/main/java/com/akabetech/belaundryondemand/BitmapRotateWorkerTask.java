package com.akabetech.belaundryondemand;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by andreyyoshuamanik on 10/16/16.
 * To make an app
 */
public class BitmapRotateWorkerTask extends AsyncTask<Void, Void, Bitmap> {
    public interface DidRotateListener {
        void getBitmap(Bitmap bitmap);
    }

    DidRotateListener listener;
    private WeakReference<Bitmap> rotateBitmap;
    private Context context;
    Uri imageUri;
    public BitmapRotateWorkerTask(DidRotateListener listener, Context context, Uri imageUri) {
        this.listener = listener;
        this.context = context;
        this.imageUri = imageUri;
    }

    protected void onPreExecute() {
        //if you want to show progress dialog
    }


    @Override
    protected Bitmap doInBackground(Void ...voids) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        int imageRotation = getCameraPhotoOrientation(context, imageUri, filePath);
        Matrix matrix = new Matrix();
        matrix.postRotate(imageRotation);
//        try {
////            rotateBitmap = new WeakReference<>(MediaStore.Images.Media.getBitmap(context.getContentResolver(),imageUri));
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filePath, options);
//
//            options.inSampleSize = calculateInSampleSize(options, 400, 400);
//            options.inJustDecodeBounds = false;
//            rotateBitmap = new WeakReference<>(BitmapFactory.decodeFile(filePath, options));
//            rotateBitmap = new WeakReference<>(Bitmap.createBitmap(rotateBitmap.get(), 0, 0,rotateBitmap.get().getWidth(), rotateBitmap.get().getHeight(), matrix, true));
//            return rotateBitmap.get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, 400, 400);
        options.inJustDecodeBounds = false;
        rotateBitmap = new WeakReference<>(BitmapFactory.decodeFile(filePath, options));
        rotateBitmap = new WeakReference<>(Bitmap.createBitmap(rotateBitmap.get(), 0, 0,rotateBitmap.get().getWidth(), rotateBitmap.get().getHeight(), matrix, true));
        return rotateBitmap.get();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        //dismiss progress dialog
        Log.i("INFO", result.toString());
        if (result != null) {

            rotateBitmap.clear();
            listener.getBitmap(result);
        }
    }

    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}