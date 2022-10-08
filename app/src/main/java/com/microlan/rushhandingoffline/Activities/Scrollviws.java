package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.microlan.rushhandingoffline.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Scrollviws extends AppCompatActivity {
    private ScrollView scrollView;
    private Button btn;
    public static Bitmap bitScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollviws);



        scrollView = findViewById(R.id.scroll);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bitScroll = getBitmapFromView(scrollView, scrollView.getChildAt(0).getHeight(), scrollView.getChildAt(0).getWidth());
                saveBitmap(bitScroll);
                Intent intent = new Intent(Scrollviws.this,PreviewActivity.class);
               // intent.putExtra("bitScroll",bitScroll);
                startActivity(intent);
            }
        });

    }

    //create bitmap from the ScrollView
    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public void saveBitmap(Bitmap bitmap) {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + ".jpeg";
        File imagePath = new File(mPath);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(),imagePath.getAbsolutePath()+"", Toast.LENGTH_LONG).show();
            Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

}
