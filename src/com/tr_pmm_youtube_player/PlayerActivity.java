package com.tr_pmm_youtube_player;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class PlayerActivity extends FragmentActivity implements
		YouTubePlayer.OnInitializedListener {

	static private final String DEVELOPER_KEY = "AIzaSyCGrFN9tpj3YBbzKhZM468VrntFeuHzlEA";
	private String VIDEO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
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
		player.loadVideo(VIDEO);
	}
}