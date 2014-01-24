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
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class AVPlayerActivity extends Activity implements OnPreparedListener, MediaController.MediaPlayerControl{
//		http://stackoverflow.com/questions/3747139/how-can-i-show-a-mediacontroller-while-playing-audio-in-android
//	http://stackoverflow.com/questions/16680432/android-mediacontroller-position
	
//if video, add wakelock
	private MediaPlayer mediaPlayer;
	private MediaController mediaController;
	private Handler handler = new Handler();
	private ArrayList<Track> trackList;
	private Playlist currentPlaylist;
	private VideoView mVideoView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avplayer);
		mVideoView = (VideoView)findViewById(R.id.videoView1);
		
		Intent i = getIntent();
		trackList = i.getParcelableArrayListExtra(Track.klass);
		currentPlaylist = new Playlist(trackList, i.getIntExtra("pos",0));
		
	    mediaPlayer = new MediaPlayer();
	    mediaPlayer.setOnPreparedListener(this);

	    mediaController = new MediaController(this);
	    mVideoView.setMediaController(mediaController);
	    mVideoView.setVisibility(VideoView.INVISIBLE);
	    
//	    set controls for next and previous
	    mediaController.setPrevNextListeners(new View.OnClickListener() {
			@Override
			public void onClick(View next) {
				prepareToStart(currentPlaylist.getNext());
			}
		}, new View.OnClickListener() {
			@Override
			public void onClick(View prev) {
				prepareToStart(currentPlaylist.getPrevious());
			}
		});
	    
	   prepareToStart(currentPlaylist.getCurrent());
	}
	
	private void prepareToStart(Track dataSource){
		try {
			if(dataSource.isVideo()){
				mediaController.setMediaPlayer(mVideoView);
			    mVideoView.setVisibility(VideoView.VISIBLE);
				mVideoView.setVideoPath(dataSource.getPath());
				mVideoView.start();
			}
			else{
				mediaPlayer.reset();
				mVideoView.setVisibility(VideoView.INVISIBLE);
				mediaPlayer.setDataSource(dataSource.getPath());
				mediaPlayer.prepare();
				mediaPlayer.start();
			}
			((TextView)findViewById(R.id.now_playing_text)).setText(currentPlaylist.getCurrent().getTitle());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	 public void onPrepared(MediaPlayer mediaPlayer) {
	    mediaController.setMediaPlayer(this);
	    mediaController.setAnchorView(findViewById(R.id.main_audio_view));
	    handler.post(new Runnable() {
	      public void run() {
	        mediaController.setEnabled(true);
	        mediaController.show();
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
