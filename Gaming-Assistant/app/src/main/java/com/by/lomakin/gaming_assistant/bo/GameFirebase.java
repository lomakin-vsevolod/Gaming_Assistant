package com.by.lomakin.gaming_assistant.bo;

import java.util.List;

/**
 * Created by Nuclear on 20.12.2016.
 */

public class GameFirebase {
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    private String gameId;

    public List<String> getCategories() {
        return categories;
    }

    public String getGameId() {
        return gameId;
    }

    private List<String> categories;

}
