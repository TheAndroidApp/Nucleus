package adapter;

/**
 * Created by Anand.M.P on 2/22/2016.
 */

import com.nucleus.app.FullScreenViewActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewImageAdapter extends BaseAdapter {

    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;

    public GridViewImageAdapter(Activity activity, ArrayList<String> filePaths, int imageWidth) {
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }

        // Get screen dimensions, scale the image accordingly.
        Bitmap image = decodeFile(_filePaths.get(position), imageWidth, imageWidth);

        //Set image in the ImageView
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
        imageView.setImageBitmap(image);

        // Image view click listener
        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    // Grid view image click listener. Full screen activity is started on selecting an image.
    class OnImageClickListener implements OnClickListener {

        int _postion;

        // Constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        // Launch full screen activity when an image is selected from GridView.
        @Override
        public void onClick(View v) {
            Intent i = new Intent(_activity, FullScreenViewActivity.class);
            i.putExtra("position", _postion);
            _activity.startActivity(i);
        }
    }

    /*
     * Resize the actual image size to fit in the screen.
     * First the image is decoded and the required scale is obtained.
     * Then the scaled image is decoded & returned.
     */
    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try {
            File f = new File(filePath);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, options);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;

            while (options.outWidth / scale / 2 >= REQUIRED_WIDTH && options.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale;

            return BitmapFactory.decodeStream(new FileInputStream(f), null, options2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
