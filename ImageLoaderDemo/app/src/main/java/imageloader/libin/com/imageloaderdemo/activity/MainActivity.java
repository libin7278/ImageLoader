package imageloader.libin.com.imageloaderdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import imageloader.libin.com.imageloaderdemo.R;

import static imageloader.libin.com.imageloaderdemo.config.imageconfig.IMG_NAME_C;

public class MainActivity extends AppCompatActivity {
    Button btn_big_img ;
    Button btn_viewpager ;
    Button btn_round ;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_big_img = (Button) findViewById(R.id.btn_big_img);
        btn_viewpager = (Button) findViewById(R.id.btn_viewpager);
        btn_round = (Button) findViewById(R.id.btn_round);

        copyFile();

        setonClick();
    }

    private void setonClick() {
        btn_big_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BigImageActivity.class));
            }
        });

        btn_viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewpagerActivity.class));
            }
        });

        btn_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SimpleActivity.class));
            }
        });

    }

    private void copyFile() {
        InputStream is;
        try {
            is = getAssets().open(IMG_NAME_C);
            FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), IMG_NAME_C));
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,"图片存储失败",Toast.LENGTH_SHORT).show();
        }
    }

    public native String stringFromJNI();
}
