package h8.chikey.nosmoke1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
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

public class DeclineMode extends AppCompatActivity {

    private ImageView butprogress,declineButSmoke,buthandbook,declinebuttonsetings,declineButtonSleep;
    private ProgressBar declineProgresSigara2;
    private TextView declineTextTimerInterval,declineTextTodayCount,declineTextInterval,declineTextTimerSmoke,declineTextTodayLost;
    private MediaPlayer player;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    private int sigareteToday;
    private int lost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decline_mode);
        declineTextTimerInterval = findViewById(R.id.declineTextTimerInterval);
        declineProgresSigara2 = findViewById(R.id.declineProgresSigara2);
        butprogress = findViewById(R.id.imageview99);
        declineButSmoke = findViewById(R.id.declineButSmoke);
        buthandbook = findViewById(R.id.progressButhandBook);
        declinebuttonsetings = findViewById(R.id.declinebuttonsetings);
        declineTextTimerSmoke = findViewById(R.id.declineTextTimerSmoke);
        declineTextInterval = findViewById(R.id.declineTextInterval);
        declineTextTodayCount = findViewById(R.id.declineTextTodayCount);
        declineButtonSleep = findViewById(R.id.declineButtonSleep);
        declineTextTodayLost = findViewById(R.id.declineTextTodayLost);

        String interval = getTimerText(Integer.parseInt(readDate("IntervalSettings.txt"))*1000);
        declineTextInterval.setText(interval);
        lost = Integer.parseInt(readDate("SigareteLostDecline.txt"));
        declineTextTodayLost.setText(String.valueOf(lost));
        sigareteToday = Integer.parseInt(readDate("TodayCountDecline.txt"));
        declineTextTodayCount.setText(String.valueOf(sigareteToday));


        player = MediaPlayer.create(this,R.raw.sound);
        imageClick();
    }

    public void imageClick() {
        declineButSmoke.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lost==0){
                            Toast.makeText(DeclineMode.this, "На сегодня хватит", Toast.LENGTH_SHORT).show();
                        }else {
                            lost--;
                            Toast.makeText(DeclineMode.this, "Приятного перекура", Toast.LENGTH_SHORT).show();
                            soundPlay(player);
                            sigareteToday++;
                            startSmokeTimer();
                            deleteDate("TodayCountDecline.txt");
                            saveDate("TodayCountDecline.txt", String.valueOf(sigareteToday));
                            deleteDate("SigareteLostDecline.txt");
                            saveDate("SigareteLostDecline.txt", String.valueOf(lost));
                            declineTextTodayCount.setText(String.valueOf(sigareteToday));
                            declineTextTodayLost.setText(String.valueOf(lost));
                        }
                    }
                }
        );
        declinebuttonsetings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".CastomSetings");
                        startActivity(intent);
                        finish();
                    }
                }
        );
        declineButtonSleep.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(DeclineMode.this);
                        a_builder.setMessage("Вы уверенны?")
                                .setCancelable(true)
                                .setPositiveButton("Да, доброй ночи", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteDate("YesterdayCountDecline.txt");
                                        saveDate("YesterdayCountDecline.txt", String.valueOf(sigareteToday));
                                        deleteDate("TodayCountDecline.txt");
                                        sigareteToday=0;
                                        saveDate("TodayCountDecline.txt", String.valueOf(sigareteToday));
                                        declineTextTodayCount.setText(String.valueOf(sigareteToday));
                                        deleteDate("SigareteLostDecline.txt");
                                        lost =  (Integer.parseInt(readDate("FirstSettings.txt"))*60*60) / Integer.parseInt(readDate("IntervalSettings.txt"));
                                        saveDate("SigareteLostDecline.txt", String.valueOf(lost));
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
        buthandbook.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".HandBook");
                        startActivity(intent);
                    }
                }
        );
        butprogress.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".ProgressView");
                        startActivity(intent);
                    }
                }
        );
    }






    private void startSmokeTimer() {
        long time = 5 * 60 * 1000;
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                declineButSmoke.setVisibility(View.INVISIBLE);
                declineTextTimerSmoke.setText(""+getTimerText((int) millisUntilFinished));
                declineProgresSigara2.setMax((int) (time/1000));
                declineProgresSigara2.setProgress((int) (millisUntilFinished/1000));
                declineProgresSigara2.setSecondaryProgress(declineProgresSigara2.getProgress()+1);
                if (declineProgresSigara2.getProgress() == 10){
                    String push = "До конца перекура осталась:"+declineTextTimerSmoke.getText();
                    showPush(push);
                }
            }
            @Override
            public void onFinish() {
                startIntervalSTimer();
                declineProgresSigara2.setProgress((int) time);
            }
        }.start();
    }

    private void startIntervalSTimer() {
        Toast.makeText(DeclineMode.this,"Пришло время потерпеть",Toast.LENGTH_SHORT).show();
        long time = Long.parseLong(readDate("IntervalSettings.txt"))*1000;
        new CountDownTimer(time, 1000) {
            int plus;
            @Override
            public void onTick(long millisUntilFinished) {
                long timeSec = time/1000;
                plus++;
                declineTextTimerInterval.setText(""+getTimerText((int) millisUntilFinished));
                declineProgresSigara2.setMax((int) timeSec);
                declineProgresSigara2.setProgress(plus);
                declineProgresSigara2.setSecondaryProgress(declineProgresSigara2.getProgress()+10);
                long pushHelp = timeSec /10;
                if (declineProgresSigara2.getProgress() == (timeSec-pushHelp)){
                    String push = "Перекур начнется через"+declineTextTimerInterval.getText();
                    showPush(push);
                }
            }
            @Override
            public void onFinish() {
                int inter = Integer.parseInt(readDate("IntervalSettings.txt"))+6;
                deleteDate("IntervalSettings.txt");
                saveDate("IntervalSettings.txt", String.valueOf(inter));
                declineTextTimerInterval.setText("00:00");
                String interval = getTimerText(Integer.parseInt(readDate("IntervalSettings.txt"))*1000);
                declineTextInterval.setText(interval);
                declineButSmoke.setVisibility(View.VISIBLE);
                declineProgresSigara2.setProgress((int) time);
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
                .setContentTitle("Перекур")
                .setContentText(str);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID,builder.build());
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

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    private String getTimerText(int time) {
        time = time / 1000;

        int hour = time /3600;
        int minutes = (time %3600) / 60;
        int second = time % 60;


        return timeFormat(second,minutes,hour);
    }

    private String timeFormat(int second, int minutes,int hour) {
        return String.format("%02d",hour)+":"+String.format("%02d",minutes)+":"+String.format("%02d",second);
    }

    public void soundPlay(MediaPlayer sound){
        sound.start();
    }
}