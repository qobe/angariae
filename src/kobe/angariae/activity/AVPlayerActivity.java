package kobe.angariae.activity;

import java.io.IOException;
import java.util.ArrayList;

import kobe.angariae.Playlist;
import kobe.angariae.R;
import kobe.angariae.Track;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.TextView;

public class AVPlayerActivity extends Activity implements OnPreparedListener, MediaController.MediaPlayerControl{
//		http://stackoverflow.com/questions/3747139/how-can-i-show-a-mediacontroller-while-playing-audio-in-android
//	http://stackoverflow.com/questions/16680432/android-mediacontroller-position
	
//if video, add wakelock
	private static final String TAG = "AudioPlayer";
	public static final String AUDIO_FILE_NAME = "audioFileName";
	private MediaPlayer mediaPlayer;
	private MediaController mediaController;
	private String audioFile;
	private Handler handler = new Handler();
	private ArrayList<Track> trackList;
	private Playlist currentPlaylist;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avplayer);
		Intent i = getIntent();
		trackList = i.getParcelableArrayListExtra(Track.klass);
		currentPlaylist = new Playlist(trackList, i.getIntExtra("pos",0));
		
		
//		((TextView)findViewById(R.id.now_playing_text)).setText(currentPlaylist.getCurrent().getTitle());
		TextView t = (TextView)findViewById(R.id.now_playing_text);
		audioFile = currentPlaylist.getCurrent().getTitle();
		t.setText(audioFile);
		
	    mediaPlayer = new MediaPlayer();
	    mediaPlayer.setOnPreparedListener(this);

	    mediaController = new MediaController(this);
	    audioFile = currentPlaylist.getCurrent().getPath();
	    try {
	    	
	      mediaPlayer.setDataSource(audioFile);
	      mediaPlayer.prepare();
	      mediaPlayer.start();
	    } catch (IOException e) {
	      Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
	    }
	}
	
	@Override
	 public void onPrepared(MediaPlayer mediaPlayer) {
	    Log.d(TAG, "onPrepared");
	    mediaController.setMediaPlayer(this);
	    mediaController.setAnchorView(findViewById(R.id.main_audio_view));

	    handler.post(new Runnable() {
	      public void run() {
	        mediaController.setEnabled(true);
	        mediaController.show();
	        Log.d(TAG, ((Boolean)mediaController.isShowing()).toString());
	      }
	    });
	  }
	
	@Override
	protected void onStop() {
	    super.onStop();
	    mediaController.hide();
	    mediaPlayer.stop();
	    mediaPlayer.release();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//the MediaController will hide after 3 seconds - tap the screen to make it appear again
		mediaController.show();
		return false;
	}	
	
	@Override
	public boolean canPause() {
		return true;
	}
	@Override
	public boolean canSeekBackward() {
		return true;
	}
	@Override
	public boolean canSeekForward() {
		return true;
	}
	@Override
	public int getAudioSessionId() {
		return 0;
	}
	@Override
	public int getBufferPercentage() {
		return 0;
	}
	@Override
	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}
	@Override
	public int getDuration() {
		return mediaPlayer.getDuration();
	}
	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}
	@Override
	public void pause() {
		mediaPlayer.pause();
	}
	@Override
	public void seekTo(int i) {
		mediaPlayer.seekTo(i);
	}
	@Override
	public void start() {
		mediaPlayer.start();
	}
}
