package kobe.angariae.activity;

import java.util.ArrayList;

import kobe.angariae.Playlist;
import kobe.angariae.Track;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;

public class AVPlayerActivity extends Activity implements OnPreparedListener, MediaController.MediaPlayerControl{
//		http://stackoverflow.com/questions/3747139/how-can-i-show-a-mediacontroller-while-playing-audio-in-android
//	http://stackoverflow.com/questions/16680432/android-mediacontroller-position
	private static final String TAG = "AudioPlayer";
	public static final String AUDIO_FILE_NAME = "audioFileName";
	private MediaPlayer mediaPlayer;
	private MediaController mediaController;
	private String audioFile;
	private Handler handler = new Handler();
	private ArrayList<Track> trackList;
	private Playlist currentPlaylist;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		trackList = i.getParcelableArrayListExtra(Track.klass);
		currentPlaylist = new Playlist(trackList, i.getIntExtra("pos",0));
	}
	
	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getAudioSessionId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	@Override
	public void seekTo(int arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
}
