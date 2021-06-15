package h8.chikey.nosmoke1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CastomSetings extends AppCompatActivity {

    private EditText editSigareteCount, editHourCount;
    private ImageView setingsButHelp,setingsSetSetings,setingsButRevert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_castom_setings);
        editHourCount = findViewById(R.id.editHourCount);
        editSigareteCount = findViewById(R.id.editSigareteCount);
        setingsButHelp = findViewById(R.id.setingsButHelp);
        setingsSetSetings = findViewById(R.id.setingsSetSetings);
        setingsButRevert = findViewById(R.id.setingsButRevert);

        imageClick();
    }

    private void imageClick() {
        setingsSetSetings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hourCout = String.valueOf(editHourCount.getText());
                        String sigareteCount = String.valueOf(editSigareteCount.getText());

                        if (hourCout.isEmpty() && sigareteCount.isEmpty()) {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(CastomSetings.this);
                            a_builder.setMessage("Настройки не указаны. Вы уверенны")
                                    .setCancelable(true)
                                    .setPositiveButton("Да,не указывать", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("Нет, указать ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                            ;
                            AlertDialog alertDialog = a_builder.create();
                            alertDialog.setTitle("Настройки не указаны");
                            alertDialog.show();
                        }else {
                            Toast.makeText(CastomSetings.this,"Настрйоки заданны",Toast.LENGTH_SHORT).show();
                            int interval = (Integer.parseInt(hourCout) * 60 * 60) / Integer.parseInt(sigareteCount);
                            deleteDate("FirstSettings.txt");
                            deleteDate("IntervalSettings.txt");
                            saveDate("FirstSettings.txt", hourCout);
                            saveDate("IntervalSettings.txt", String.valueOf(interval));
                            finish();
                        }
                    }
                }
        );
        setingsButHelp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(CastomSetings.this);
                        a_builder.setMessage("Инструкция")
                                .setCancelable(true)
                                .setPositiveButton("Да, доброй ночи", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = a_builder.create();
                        alertDialog.setTitle("Настройки");
                        alertDialog.show();
                    }
                }
        );
        setingsButRevert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(CastomSetings.this);
                        a_builder.setMessage("Вы уверенны что хотите сбросить все настройки?")
                                .setCancelable(true)
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteDate("Start.txt");
                                        saveDate("Start.txt", String.valueOf(0));
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        ;
                        AlertDialog alertDialog = a_builder.create();
                        alertDialog.setTitle("СБРОС");
                        alertDialog.show();
                    }
                }
        );


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