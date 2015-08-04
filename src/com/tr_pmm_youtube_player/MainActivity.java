package com.tr_pmm_youtube_player;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager.Query;
import android.content.Intent;
import android.widget.AdapterView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private Intent intent;
	private ListView list;
	private EditText editText;
	private List<String> videoTitle;
	private List<String> imageId;
	private List<String> videoId;
	private DeveloperKey developerKey;
	private static final long NUMBER_OF_VIDEOS_RETURNED = 5;
	private static YouTube youtube;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intent = new Intent(this, TestActivity.class);
		videoTitle = new ArrayList<String>();
		videoId = new ArrayList<String>();
		imageId = new ArrayList<String>();
		developerKey = new DeveloperKey();
		getData();
		CustomList adapter = new CustomList(MainActivity.this, videoTitle, imageId);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent.putExtra("video_id", videoId.get(position));
				intent.putExtra("video_title", videoTitle.get(position));
				intent.putExtra("dev_key", developerKey.getDeveloperKey());
				startActivity(intent);
			}
		});
		editText = (EditText) findViewById(R.id.editText1);
		editText.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_UP) {
					inputQuery = editText.getText().toString();
					videoTitle.clear();
					videoId.clear();
					imageId.clear();

					getData();
					CustomList adapter = new CustomList(MainActivity.this, videoTitle, imageId);
					list.setAdapter(adapter);
				}
				return false;
			}
		});
	}

	private void getData() {
		youtube = new YouTube.Builder(new NetHttpTransport(),new JacksonFactory(), new HttpRequestInitializer() {

					@Override
					public void initialize(HttpRequest arg0) throws IOException {
						// TODO Auto-generated method stub

					}
				}).setApplicationName("TR_PMM_YOUTUBE_PLAYER").build();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			String query = getInputQuery();

			YouTube.Search.List search = youtube.search().list("id,snippet");
			String apiKey = developerKey.getDeveloperKey();
			search.setKey(apiKey);
			search.setQ(query);
			search.setType("video");
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if (searchResultList != null) {
				prettyPrint(searchResultList.iterator(), query);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String inputQuery = "";
	private String getInputQuery() throws IOException {
		if (inputQuery.length() < 1) {
			inputQuery = "DOTA 2 2014";
		}
		return inputQuery;
	}

	private void prettyPrint(Iterator<SearchResult> iteratorSearchResults,
			String query) {
		if (!iteratorSearchResults.hasNext()) {
			System.out.println(" There aren't any results for your query.");
		}

		while (iteratorSearchResults.hasNext()) {
			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();
			if (rId.getKind().equals("youtube#video")) {
				Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails()
						.getDefault();
				imageId.add("http://img.youtube.com/vi/" + rId.getVideoId()
						+ "/2.jpg");
				videoId.add(rId.getVideoId());
				videoTitle.add(singleVideo.getSnippet().getTitle());
			}
		}
	}

}
