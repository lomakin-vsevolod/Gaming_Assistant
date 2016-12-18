package com.by.lomakin.gaming_assistant.bo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nuclear on 18.12.2016.
 */

public class Game {

    @SerializedName("id")
    private int id;
    @SerializedName("api_detail_url")
    private String apiDetailUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private Image image;

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

    public class Image{

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

}
