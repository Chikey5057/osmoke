package h8.chikey.nosmoke1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class HabitMode extends AppCompatActivity {

    private TextView sigcount1, sigcount2, timertext;
    private ImageView butsmoke, buttonsleep;
    private MediaPlayer play;
    private ProgressBar sigara;
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";


    int sigareteYesterday;
    int sigareteToday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_mode);
        sigcount1 = findViewById(R.id.sigcount1);
        sigcount2 = findViewById(R.id.sigcount2);
        timertext = findViewById(R.id.timertext);
        butsmoke = findViewById(R.id.butsmoke);
        buttonsleep = findViewById(R.id.buttonsleep);
        sigara = findViewById(R.id.sigara);


        sigareteYesterday= Integer.parseInt(readDate("YesterdayCountHabit.txt"));
        sigcount1.setText(String.valueOf(sigareteYesterday));
        sigareteToday= Integer.parseInt(readDate("TodayCountHabit.txt"));
        sigcount2.setText(String.valueOf(sigareteToday));



        play = MediaPlayer.create(this,R.raw.sound);
        imageClick();

    }



    public void imageClick() {
        butsmoke.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HabitMode.this,"Приятного перекура",Toast.LENGTH_SHORT).show();
                        soundPlay(play);
                        startTimer();
                        sigareteToday++;
                        deleteDate("TodayCountHabit.txt");
                        saveDate("TodayCountHabit.txt", String.valueOf(sigareteToday));
                        sigcount2.setText(String.valueOf(sigareteToday));
                    }
                }
        );
        buttonsleep.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(HabitMode.this);
                        a_builder.setMessage("Вы уверенны?")
                                .setCancelable(true)
                                .setPositiveButton("Да, доброй ночи", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteDate("YesterdayCountHabit.txt");
                                       saveDate("YesterdayCountHabit.txt", String.valueOf(sigareteToday));
                                        deleteDate("TodayCountHabit.txt");
                                        sigareteToday=0;
                                        saveDate("TodayCountHabit.txt", String.valueOf(sigareteToday));
                                        sigcount2.setText(String.valueOf(sigareteToday));
                                       finish();
                                    }
                                })
                                .setNegativeButton("Нет, попозже", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        ;
                        AlertDialog alertDialog = a_builder.create();
                        alertDialog.setTitle("Ложусь спать");
                        alertDialog.show();
                    }
                }
        );
    }

    private void startTimer() {
        long time = 5*60*1000;
        new CountDownTimer(time, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                timertext.setText(""+getTimerText((int) millisUntilFinished));
                butsmoke.setVisibility(View.INVISIBLE);
                sigara.setMax((int) (time/1000));
                sigara.setProgress((int) (millisUntilFinished/1000));
                sigara.setSecondaryProgress(sigara.getProgress()+10);
                if (sigara.getProgress() == 1*60){
                    String push = "До конца перекура осталась:"+timertext.getText();
                    showPush(push);
                }
            }

            @Override
            public void onFinish() {
                timertext.setText("Перекур");
                butsmoke.setVisibility(View.VISIBLE);
                sigara.setProgress((int) time);
            }
        }.start();
    }


    public void showPush(String str){
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getApplicationContext(),HabitMode.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.logo)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle("Перекур заканчивается")
                .setContentText(str);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID,builder.build());
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    private String getTimerText(int time) {

        int rounded = time;

        int second = (rounded / 1000) %60;
        int minutes = (rounded / 1000) /60;


        return timeFormat(second,minutes);
    }

    private String timeFormat(int second, int minutes) {
        return String.format("%02d",minutes)+":"+String.format("%02d",second);
    }


    public void soundPlay(MediaPlayer sound){
        sound.start();
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