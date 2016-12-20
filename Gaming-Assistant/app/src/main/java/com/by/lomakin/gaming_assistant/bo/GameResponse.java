package com.by.lomakin.gaming_assistant.bo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nuclear on 20.12.2016.
 */

public class GameResponse {
    @SerializedName("error")
    private String error;
    @SerializedName("limit")
    private int limit;
    @SerializedName("offset")
    private int offset;
    @SerializedName("number_of_page_results")
    private int numberOfPageResults;
    @SerializedName("number_of_total_results")
    private int numberOfTotalResults;
    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("results")
    private Game results;

    public String getError() {
        return error;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getNumberOfPageResults() {
        return numberOfPageResults;
    }

    public int getNumberOfTotalResults() {
        return numberOfTotalResults;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Game getResults() {
        return results;
    }
}
