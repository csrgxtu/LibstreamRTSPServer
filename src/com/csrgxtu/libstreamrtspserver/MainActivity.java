package com.csrgxtu.libstreamrtspserver;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;

public class MainActivity extends Activity {

  private final static String TAG = "MainActivity";

  private SurfaceView mSurfaceView;
  
  /*@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }*/

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mSurfaceView = (SurfaceView) findViewById(R.id.surface);
    
    // Sets the port of the RTSP server to 1234
    Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    editor.putString(RtspServer.KEY_PORT, String.valueOf(1234));
    editor.commit();

    // Configures the SessionBuilder
    /*SessionBuilder.getInstance()
    .setSurfaceView(mSurfaceView)
    .setPreviewOrientation(90)
    .setContext(getApplicationContext())
    .setAudioEncoder(SessionBuilder.AUDIO_NONE)
    .setVideoEncoder(SessionBuilder.VIDEO_H264);*/
    SessionBuilder.getInstance()    
    .setSurfaceView(mSurfaceView)
    .setContext(getApplicationContext())
    .setAudioEncoder(SessionBuilder.AUDIO_AAC)
    .setVideoEncoder(SessionBuilder.VIDEO_H264);
    
    // Starts the RTSP server
    //this.startService(new Intent(this,RtspServer.class));
    this.startService(new Intent(this,RtspServer.class));

  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  public void onPuse() {
    this.stopService(new Intent(this, RtspServer.class));
  }
  
  public void onDestroy() {
    this.stopService(new Intent(this,RtspServer.class));
  }
  

}
