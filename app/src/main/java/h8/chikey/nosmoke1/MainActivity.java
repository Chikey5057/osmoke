package h8.chikey.nosmoke1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private ImageView butPrompt,buthabit,butdecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butPrompt = findViewById(R.id.butPrompt);
        buthabit = findViewById(R.id.buthabit);
        butdecline = findViewById(R.id.butdecline);


        if (Integer.parseInt(readDate("Start.txt"))==0) {
            startApp();
        }
        imageClick();
    }

    public void imageClick(){
        butPrompt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        a_builder.setMessage("Как пользоваться")
                        .setCancelable(true)
                        .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = a_builder.create();
                        alertDialog.setTitle("Инструкция");
                        alertDialog.show();
                    }
                }
        );
        buthabit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".HabitMode");
                        startActivity(intent);
                    }
                }
        );
        butdecline.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int date = Integer.parseInt(readDate("FirstSettings.txt"));
                        if(date == 0) {
                            Intent intent = new Intent(".CastomSetings");
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(".DeclineMode");
                            startActivity(intent);
                        }
                    }
                }
        );
    }

    private void startApp() {
        deleteDate("FirstSettings.txt");
        saveDate("FirstSettings.txt", String.valueOf(0));
        deleteDate("IntervalSettings.txt");
        saveDate("IntervalSettings.txt", String.valueOf(0));
        deleteDate("SigareteLostDecline.txt");
        saveDate("SigareteLostDecline.txt", String.valueOf(0));
        deleteDate("TodayCountDecline.txt");
        saveDate("TodayCountDecline.txt", String.valueOf(0));
        deleteDate("TodayCountHabit.txt");
        saveDate("TodayCountHabit.txt", String.valueOf(0));
        deleteDate("YesterdayCountDecline.txt");
        saveDate("YesterdayCountDecline.txt", String.valueOf(0));
        deleteDate("YesterdayCountHabit.txt");
        saveDate("YesterdayCountHabit.txt", String.valueOf(0));
        deleteDate("Start.txt");
        saveDate("Start.txt", String.valueOf(1));
    }

    public void saveDate(String fileName,String date){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput(String.valueOf(fileName),MODE_PRIVATE)));
            bufferedWriter.write(date);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readDate(String fileName){
        int line = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            line= Integer.parseInt(bufferedReader.readLine());
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return String.valueOf(line);
    }

    public boolean deleteDate(String filename){
        File file = new File(getFilesDir(),filename);
        return file.delete();
    }

}