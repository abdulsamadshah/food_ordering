package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.microlan.rushhandingoffline.R;

import java.io.File;
import java.io.FileOutputStream;

public class PreviewActivity extends AppCompatActivity {

    private ImageView imageView;
    Button sharebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        imageView = findViewById(R.id.img);
        sharebtn = findViewById(R.id.sharebtn);

       // String bitScroll=getIntent().getStringExtra("bitScroll");
        imageView.setImageBitmap(Scrollviws.bitScroll);
        //View content = findViewById(R.id.full_image_view);
        imageView.setDrawingCacheEnabled(true);


        Bitmap bitmap = imageView.getDrawingCache();
        File root = Environment.getExternalStorageDirectory();
        final File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bitmap bm = ((android.graphics.drawable.BitmapDrawable) imageView.getDrawable()).getBitmap();
               // try {
                 //   java.io.File file = new java.io.File(getExternalCacheDir() + "/image.jpg");
                   // java.io.OutputStream out = new java.io.FileOutputStream(file);
                    //bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    //out.flush();
                    //out.close();
                //} catch (Exception e)
                //{ System.out.println(e.toString()); }
                //Intent iten = new Intent(android.content.Intent.ACTION_SEND);
                //iten.setType("*/*");
                //iten.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new java.io.File(getExternalCacheDir() + "/image.jpg")));
                //startActivity(Intent.createChooser(iten, "Send image"));


/*
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share,"Share via"));
*/

            }
        });

    }
}