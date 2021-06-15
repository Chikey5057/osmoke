package h8.chikey.nosmoke1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProgressView extends AppCompatActivity {


    private ImageView progressButChasi,progressButhandBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        progressButChasi = findViewById(R.id.progressButChasi);
        progressButhandBook = findViewById(R.id.progressButhandBook);

        imageClick();
    }

    private void imageClick() {
        progressButhandBook.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".HandBook");
                        startActivity(intent);
                        finish();
                    }
                }
        );
        progressButChasi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".DeclineMode");
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}