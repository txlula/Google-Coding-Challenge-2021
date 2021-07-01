package com.google;

import java.util.*;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private boolean isPlaying;
  private boolean isPaused;
  private String playingTitle;
  private String playingID;
  private List<String> playingTags;
  private ArrayList<VideoPlaylist> playlists;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    this.playlists = new ArrayList<>();
    this.isPlaying = false;
    this.isPaused = false;
  }

  public ArrayList<VideoPlaylist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(ArrayList<VideoPlaylist> playlists) {
    this.playlists = playlists;
  }

  public List<String> getPlayingTags() {
    return playingTags;
  }

  public void setPlayingTags(List<String> playingTags) {
    this.playingTags = playingTags;
  }

  public String getPlayingID() {
    return playingID;
  }

  public void setPlayingID(String playingID) {
    this.playingID = playingID;
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setPaused(boolean paused) {
    isPaused = paused;
  }

  public boolean isPlaying() {
    return isPlaying;
  }

  public void setPlaying(boolean playing) {
    isPlaying = playing;
  }

  public String getPlayingTitle() {
    return playingTitle;
  }

  public void setPlayingTitle(String playingTitle) {
    this.playingTitle = playingTitle;
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> library = videoLibrary.getVideos();
    List<Video> sortedLibrary = library.stream().sorted(Comparator.comparing(Video :: getTitle)).collect(Collectors.toList());

    for (int i = 0; i < videoLibrary.getVideos().size(); i++){
      System.out.println(sortedLibrary.get(i).getTitle() + " (" + sortedLibrary.get(i).getVideoId() + ") "
              + sortedLibrary.get(i).getTags().toString().replaceAll(",", "")
      );
    }
  }

  public void playVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if (video == null){
      System.out.println("Cannot play video: Video does not exist");
    }
    else {
      if (isPlaying){
        this.stopVideo();
      }
      else if (isPaused){
        this.setPaused(false);
      }
      String videoTitle = video.getTitle();
      this.setPlaying(true);
      this.setPlayingTitle(videoTitle);
      this.setPlayingID(video.getVideoId());
      this.setPlayingTags(video.getTags());
      System.out.println("Playing video: " + videoTitle);
    }
  }

  public void stopVideo() {
    if (this.isPlaying){
      System.out.println("Stopping video: " + this.getPlayingTitle());
      this.setPlaying(false);
      this.setPaused(false);
    }
    else if (this.isPaused){
      this.setPaused(false);
    }
    else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    Random rand = new Random();
    int randomNumber = rand.nextInt(5);
    String videoID = videoLibrary.getVideos().get(randomNumber).getVideoId();
    if (isPlaying) {
      this.stopVideo();
    }
    this.playVideo(videoID);
  }

  public void pauseVideo() {
    if (isPlaying && !isPaused){
      System.out.println("Pausing video: " + this.getPlayingTitle());
      this.setPaused(true);
    }
    else if (isPaused && isPlaying) {
      System.out.println("Video already paused: " + this.getPlayingTitle());
    }
    else {
      this.setPaused(false);
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {
    if (!isPaused && isPlaying) {
      System.out.println("Cannot continue video: Video is not paused");
    }
    else if (!isPlaying){
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else {
      System.out.println("Continuing video: " + this.getPlayingTitle());
      this.setPaused(false);
    }
  }

  public void showPlaying() {
    if (!isPlaying){
      System.out.println("No video is currently playing");
    }
    else if (isPaused){
      System.out.println("Currently playing: " + this.getPlayingTitle() + " (" + this.getPlayingID()
              + ") " + this.getPlayingTags().toString().replaceAll(",", "")
              + " - PAUSED"
      );
    }
    else if(!isPaused) {
      System.out.println("Currently playing: " + this.getPlayingTitle() + " (" + this.getPlayingID()
              + ") " + this.getPlayingTags().toString().replaceAll(",", "")
      );
    }
  }

  public void createPlaylist(String playlistName) {
    boolean isSame = false;

    for (VideoPlaylist playlist: this.playlists){
      String result = playlist.getName().toLowerCase();
      if (result.matches(playlistName.toLowerCase())){
          isSame = true;
      }
      else {
        isSame = false;
      }
    }

    if (!isSame){
      VideoPlaylist createdPlaylist = new VideoPlaylist(playlistName);
      this.playlists.add(createdPlaylist);
      System.out.println("Successfully created new playlist: " + playlistName);
    }
    else {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    boolean isMatch = false;

    for (VideoPlaylist playlist: this.playlists){
      String result = playlist.getName().toLowerCase();
      if (result.matches(playlistName.toLowerCase()))
        if ((playlist.getVideo().getVideoId().equals(videoId)) || playlist.getVideo() != null) {
          isMatch = true;
        } else {
          playlist.setVideo(videoLibrary.getVideo(videoId));
          isMatch = false;
        }
    }

    if (!isMatch){
      System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
    }
    else {
      System.out.println("Cannot add video to " + playlistName + ": Video already added");
    }
  }

  public void showAllPlaylists() {
    if (this.playlists == null){
      System.out.println("No playlists exist yet");
    }
    else {
      System.out.println("Showing all playlists:");
      for (VideoPlaylist playlist: this.playlists){
        System.out.println(playlist.getName());
      }
    }
  }

  public void showPlaylist(String playlistName) {
    boolean isSame = false;

    for (VideoPlaylist playlist: this.playlists){
      String result = playlist.getName().toLowerCase();
      if (result.matches(playlistName.toLowerCase())){
        isSame = true;
        String videoId = playlist.getVideo().getVideoId();
      }
      else {
        isSame = false;
      }
    }

    if (!isSame){

    }
    else {
      System.out.println("Showing playlist: " + playlistName);
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    System.out.println("Removed video from " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
  }

  public void clearPlaylist(String playlistName) {
    System.out.println("Successfully removed all videos from " + playlistName);
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("Deleted playlist: " + playlistName);
  }

  public void searchVideos(String searchTerm) {
    Scanner scanner = new Scanner(System.in);
    List<Video> library = videoLibrary.getVideos();
    List<Video> sortedLibrary = library.stream().sorted(Comparator.comparing(Video :: getTitle)).collect(Collectors.toList());

    boolean noResult;
    for (int i = 0; i < videoLibrary.getVideos().size(); i++){
      String title = sortedLibrary.get(i).getTitle();
      if (title.toLowerCase().contains(searchTerm.toLowerCase())){
        noResult = false;
      }
      else{
        noResult = true;
      }
    }

    if (noResult = false){
      System.out.println("Here are the results for " + searchTerm + ":");

      for (int i = 0; i < videoLibrary.getVideos().size(); i++){
        String title = sortedLibrary.get(i).getTitle();
        if (title.toLowerCase().contains(searchTerm.toLowerCase())){
          System.out.println(i+1 + ") " + sortedLibrary.get(i).getTitle() + " (" + sortedLibrary.get(i).getVideoId() + ") "
                  + sortedLibrary.get(i).getTags().toString().replaceAll(",", "")
          );
        }
      }

      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");

      String answer = scanner.nextLine();
      answer = answer.replaceAll("[a-zA-Z]","");
      if (answer.matches("[0-9]")){
        for (int i = 0; i < videoLibrary.getVideos().size(); i++){
          String title = sortedLibrary.get(i).getTitle();
          if (title.toLowerCase().contains(searchTerm.toLowerCase())){
            if (Integer.parseInt(answer) == i + 1){
              System.out.println("Playing video: " + sortedLibrary.get(i));
            }
          }
        }
      }
      else {
        System.out.println("Playing video");
      }
    }
    else {
      System.out.println("No search results for " + searchTerm);
    }
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> library = videoLibrary.getVideos();
    List<Video> sortedLibrary = library.stream().sorted(Comparator.comparing(Video :: getTitle)).collect(Collectors.toList());

    System.out.println("Here are the results for " + videoTag + ":");

    for (int i = 0; i < videoLibrary.getVideos().size(); i++){
      List<String> tags = sortedLibrary.get(i).getTags();
      for (String tag : tags) {
        if (tag.toLowerCase().matches(videoTag)) {
          System.out.println(i + 1 + ") " + sortedLibrary.get(i).getTitle() + " (" + sortedLibrary.get(i).getVideoId() + ") "
                  + sortedLibrary.get(i).getTags().toString().replaceAll(",", "")
          );
        }
      }
    }

    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}