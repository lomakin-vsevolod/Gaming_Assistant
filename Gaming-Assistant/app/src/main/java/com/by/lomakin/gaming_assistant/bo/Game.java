package com.by.lomakin.gaming_assistant.bo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nuclear on 18.12.2016.
 */

public class Game {

    public Game(int id, String apiDetailUrl, String name, Image image, String deck, List<Developer> developers ) {
        this.id = id;
        this.apiDetailUrl = apiDetailUrl;
        this.name = name;
        this.image = image;
        this.deck = deck;
        this.developers = developers;
    }

    public Game() {

    }

    @SerializedName("id")
    private int id;
    @SerializedName("api_detail_url")
    private String apiDetailUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private Image image;
    @SerializedName("deck")
    private String deck;
    @SerializedName("developers")
    private List<Developer> developers;


    public int getId() {
        return id;
    }

    public String getApiDetailUrl() {
        return apiDetailUrl;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public String getDeck() {
        return deck;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public class Image {

        @SerializedName("icon_url")
        private String iconUrl;
        @SerializedName("medium_url")
        private String mediumUrl;
        @SerializedName("screen_url")
        private String screenUrl;
        @SerializedName("small_url")
        private String smallUrl;
        @SerializedName("super_url")
        private String superUrl;
        @SerializedName("thumb_url")
        private String thumbUrl;
        @SerializedName("tiny_url")
        private String tinyUrl;

        public String getIconUrl() {
            return iconUrl;
        }

        public String getMediumUrl() {
            return mediumUrl;
        }

        public String getScreenUrl() {
            return screenUrl;
        }

        public String getSmallUrl() {
            return smallUrl;
        }

        public String getSuperUrl() {
            return superUrl;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public String getTinyUrl() {
            return tinyUrl;
        }

    }

    public class Developer {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
