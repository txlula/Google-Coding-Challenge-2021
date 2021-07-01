package com.google;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String name;
    private Video video;

    public VideoPlaylist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
