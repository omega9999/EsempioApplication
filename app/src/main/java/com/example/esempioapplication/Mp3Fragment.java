package com.example.esempioapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

/**
 * http://www.tutorialspoint.com/android/android_mediaplayer.htm
 */
public class Mp3Fragment extends Fragment {

    private Button b1, b2, b3, b4;
    private ImageView iv;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1, tx2, tx3;

    public static int oneTimeOnly = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_mp3, container, false);


        b1 = root.findViewById(R.id.button);
        b2 = root.findViewById(R.id.button2);
        b3 = root.findViewById(R.id.button3);
        b4 = root.findViewById(R.id.button4);
        iv = root.findViewById(R.id.imageView);

        seekbar = root.findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        tx1 = root.findViewById(R.id.textView2);
        tx2 = root.findViewById(R.id.textView3);
        tx3 = root.findViewById(R.id.textView4);
        tx3.setText(R.string.song_name);
        b2.setEnabled(false);



        b3.setOnClickListener(v -> {
            Toast.makeText(getActivity().getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
            mediaPlayer.start();

            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();

            if (oneTimeOnly == 0) {
                seekbar.setMax((int) finalTime);
                oneTimeOnly = 1;
            }

            tx2.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    finalTime)))
            );

            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    startTime)))
            );

            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(UpdateSongTime, 100);
            b2.setEnabled(true);
            b3.setEnabled(false);
        });

        b2.setOnClickListener(v -> {
            Toast.makeText(getActivity().getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            b2.setEnabled(false);
            b3.setEnabled(true);
        });

        b1.setOnClickListener(v -> {
            int temp = (int) startTime;

            if ((temp + forwardTime) <= finalTime) {
                startTime = startTime + forwardTime;
                mediaPlayer.seekTo((int) startTime);
                Toast.makeText(getActivity().getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
            }
        });

        b4.setOnClickListener(v -> {
            int temp = (int) startTime;

            if ((temp - backwardTime) > 0) {
                startTime = startTime - backwardTime;
                mediaPlayer.seekTo((int) startTime);
                Toast.makeText(getActivity().getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.inno_nazionale_italia);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
}
