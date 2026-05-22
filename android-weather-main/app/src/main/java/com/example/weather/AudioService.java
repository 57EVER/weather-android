package com.example.weather;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AudioService extends Service implements MediaPlayer.OnCompletionListener{

    private final IBinder binder = new AudioBinder();
    //用于播放音乐等媒体资源
    private MediaPlayer mediaPlayer;

    public AudioService() {
        super();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(AudioService.this.getClass().getName(),"执行onCreate()");
        if (mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(this,R.raw.music);
            mediaPlayer.setOnCompletionListener(this);
        }
        mediaPlayer.start();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        stopSelf();
    }

    @Override
    public void onDestroy(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        stopForeground(true);

        Log.d(AudioService.this.getClass().getName(),"执行onDestroy()");
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void play() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    //为了和Activity交互，我们需要定义一个Binder对象
    class AudioBinder extends Binder {

        //返回Service对象
        AudioService getService(){
            return AudioService.this;
        }
    }
}