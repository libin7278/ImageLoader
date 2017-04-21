package imageloader.libin.com.imageloaderdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import imageloader.libin.com.imageloaderdemo.R;

public class MainActivity extends AppCompatActivity {
    Button btn_big_img ;
    Button btn_viewpager ;
    Button btn_round ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_big_img = (Button) findViewById(R.id.btn_big_img);
        btn_viewpager = (Button) findViewById(R.id.btn_viewpager);
        btn_round = (Button) findViewById(R.id.btn_round);

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
                startActivity(new Intent(MainActivity.this, RoundActivity.class));
            }
        });

    }
}
