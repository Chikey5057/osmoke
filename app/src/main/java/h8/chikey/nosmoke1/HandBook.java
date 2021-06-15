package h8.chikey.nosmoke1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HandBook extends AppCompatActivity {

    private ImageView handBookButDeclineMode,handBookButProgress,imageView31,imageView32,imageView33
            ,imageView34,imageView35,imageView36,imageView37,imageView38;
    private LinearLayout imageContener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_book);

        handBookButDeclineMode = findViewById(R.id.handBookButDeclineMode);
        handBookButProgress = findViewById(R.id.handBookButProgress);
        imageView31 = findViewById(R.id.imageView31);
        imageView32 = findViewById(R.id.imageView32);
        imageView33 = findViewById(R.id.imageView33);
        imageView34 = findViewById(R.id.imageView34);
        imageView35 = findViewById(R.id.imageView35);
        imageView36 = findViewById(R.id.imageView36);
        imageView37 = findViewById(R.id.imageView37);
        imageView38 = findViewById(R.id.imageView38);

        imageContener = findViewById(R.id.imageContener);



        imageClick();
    }

    private void imageClick() {
        handBookButDeclineMode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".DeclineMode");
                        startActivity(intent);
                        finish();
                    }
                }
        );
        handBookButProgress.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".ProgressView");
                        startActivity(intent);
                        finish();
                    }
                }
        );
        imageView31.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text1);
                    }
                }
        );
        imageView32.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text2);
                    }
                }
        );
        imageView33.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text3);
                    }
                }
        );
        imageView34.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text4);
                    }
                }
        );
        imageView35.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text5);
                    }
                }
        );
        imageView36.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text6);
                    }
                }
        );
        imageView37.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text7);
                    }
                }
        );
        imageView38.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageContener.setBackgroundResource(R.drawable.text8);
                    }
                }
        );
    }
}