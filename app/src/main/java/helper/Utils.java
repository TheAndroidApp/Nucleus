package helper;

/**
 * Created by Anand.M.P on 2/22/2016.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

public class Utils {
    private Context _context;
    //private int tabPosition = getSelectedTabPosition();

    // Constructor
    public Utils(Context context) {
        this._context = context;
    }

    // Reading file paths from SDCard
    public ArrayList<String> getFilePaths() {

        ArrayList<String> filePaths = new ArrayList<String>();
        File directory = new File(android.os.Environment.getExternalStorageDirectory() + File.separator + AppConstant.PHOTO_ALBUM);

        Log.v("Current Album", AppConstant.PHOTO_ALBUM);

        // Check if it is a valid Directory.
        if (directory.isDirectory()) {
            // Get the list of file paths into an array.
            File[] listFiles = directory.listFiles();

            // If the directory is not empty, do this.
            if (listFiles.length > 0) {

                // Loop through all files
                for (int i = 0; i < listFiles.length; i++) {

                    // Get file path
                    String filePath = listFiles[i].getAbsolutePath();

                    // Check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                        Log.v("Current Album", filePath);
                        filePaths.add(filePath);
                    }
                }
            } else {
                // Image directory is empty
                Toast.makeText(_context, AppConstant.PHOTO_ALBUM + " is empty. Please load some images in it !",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            // If a valid directory is not available, show alerts to user.
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Error!");
            alert.setMessage(android.os.Environment.getExternalStorageDirectory() + File.separator + AppConstant.PHOTO_ALBUM
                    + " directory path is not valid! Please set the image directory name AppConstant.java class");
            alert.setPositiveButton("OK", null);
            alert.show();
        }

        return filePaths;
    }

    // To check for supported file extensions. Supported file extension are jpg, jpeg, png.
    private boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1), filePath.length());

        //checks if the extensions are the same.
        if (AppConstant.FILE_EXTN.contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;
    }

    /*
     * To get the screen width.
     */
    public int getScreenWidth() {

        int columnWidth;
        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}