package com.wytv.cc.mytvapp.Utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.wytv.cc.mytvapp.R;

public class MediaUtils {

    private volatile static MediaUtils instance;

    public static MediaUtils getInstance() {
        if (instance == null) {
            synchronized (MediaUtils.class) {
                if (instance == null) {
                    instance = new MediaUtils();
                }
            }
        }
        return instance;
    }
   private boolean  playMediaSound ;

   public void play(Context context){
       if (!playMediaSound) {
           playMediaSound = true;
           MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.warn);
           if (mediaPlayer != null){
               mediaPlayer.start();
               mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       playMediaSound = false;
                   }
               });
           }else {
               playMediaSound = false;
           }

       }
   }
}
