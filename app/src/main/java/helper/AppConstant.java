package helper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Anand.M.P on 2/22/2016.
 */
public class AppConstant {

    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    // SD card image directory
//    public static final String PHOTO_ALBUM = "";
    public static String PHOTO_ALBUM = "DCIM/Camera";

    public static String TAB_HEADING="Gallery";

//    public static final String PHOTO_ALBUM2 = "Nucleus/Compressed Images";
//    public static final String PHOTO_ALBUM3 = "Nucleus/Received Images";


    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
            "png");
}
