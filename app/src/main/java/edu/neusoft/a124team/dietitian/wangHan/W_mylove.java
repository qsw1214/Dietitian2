package edu.neusoft.a124team.dietitian.wangHan;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import edu.neusoft.a124team.dietitian.R;

/**
 * 我的收藏版块
 */

public class W_mylove extends AppCompatActivity {

    LinearLayout camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_activity_adapter);
        setTitle("我的收藏");


        camera = (LinearLayout) findViewById(R.id.w_btn_adapter_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);*/
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(intent);

            }
        });
    }
}
