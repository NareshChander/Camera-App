package Android.CompetencyCheckpoint3_Task3;

import android.app.Activity;
import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;

public class Nikon extends Activity
{
    int code = 200;
    String path;
    ImageView image;
    private static final int RQUEST_CODE = 200;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        image = (ImageView) findViewById(R.id.imgView);
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = null;
        try
            {
                file = createFile();
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
        catch(IOException e)
            {
                e.printStackTrace();
            }
        startActivityForResult(photoIntent, RQUEST_CODE);
    }
    
    private File createFile() throws IOException
        {
            String name = "Image";
            File album = getAlbumName();
            File imageFile = File.createTempFile(name, ".jpeg", album);
            path = imageFile.getAbsolutePath();
            return imageFile;
        }
    
    private File getAlbumName()
        {
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraAppPhotos");
            if(!dir.exists())
                {
                    dir.mkdir();
                } 
            return dir;
        } 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result)
        {
        if(requestCode == RQUEST_CODE)
        {
            Bundle extras = result.getExtras();
            Bitmap bmap = (Bitmap) extras.get("data");
            image.setImageBitmap(bmap);
        }
//        if(resultCode == RESULT_OK)
//            {
//                this.galleryPic();
//            }
        }
    private void galleryPic()
        {
           Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
           File file = new File(path);
           Uri u = Uri.fromFile(file);
           media.setData(u);
           this.sendBroadcast(media);
       }   
}
