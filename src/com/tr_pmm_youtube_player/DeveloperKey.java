// Copyright 2012 Google Inc. All Rights Reserved.

package com.tr_pmm_youtube_player;

/**
 * Static container class for holding a reference to your YouTube Developer Key.
 */
public class DeveloperKey {

  /**
   * Please replace this with a valid API key which is enabled for the 
   * YouTube Data API v3 service. Go to the 
   * <a href="https://code.google.com/apis/console/">Google APIs Console</a> to
   * register a new developer key.
   */
  private static final String DEVELOPER_KEY = "AIzaSyCGrFN9tpj3YBbzKhZM468VrntFeuHzlEA";

  public String getDeveloperKey(){
	  return DEVELOPER_KEY;
  }
}
