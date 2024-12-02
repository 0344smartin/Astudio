package com.example.audioplayer;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AudioPlayer extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY; // Hace que el servicio se reinicie si el sistema lo cierra
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null; // Retorna null porque es un Started Service y no necesita comunicación directa
    }
}
