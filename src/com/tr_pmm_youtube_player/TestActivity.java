package com.tr_pmm_youtube_player;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import android.os.Bundle;
import android.widget.Toast;

public class TestActivity extends YouTubeBaseActivity implements OnInitializedListener {
	static private final String DEVELOPER_KEY = "AIzaSyCGrFN9tpj3YBbzKhZM468VrntFeuHzlEA";
	private String VIDEO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		this.VIDEO = getIntent().getStringExtra("video_id");
		String videoTitle = getIntent().getStringExtra("video_title");
		this.setTitle(videoTitle);
		YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtubeplayerview);
		youTubeView.initialize(DEVELOPER_KEY, this);
	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult error) {
		Toast.makeText(this, "Oh no! " + error.toString(), Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onInitializationSuccess(Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo(VIDEO);
		}
	}

}
