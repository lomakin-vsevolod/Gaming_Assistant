package com.by.lomakin.gaming_assistant.api;

/**
 * Created by Nuclear on 17.12.2016.
 */

public class GiantBombApiConstants {
    private final static String KEY = "0e49c312dd130fc948ae8c117bf9dc34cb19a80f";

    private final static String API_URL = "http://www.giantbomb.com/api/";
    private final static String API_KEY = "api_key=";
    private final static String FORMAT = "format=";
    private final static String FIELD_LIST = "field_list=";
    private final static String LIMIT = "limit=";
    private final static String OFFSET = "offset=";
    private final static String FILTER = "filter=";

    private final static String API_KEY_VALUE = API_KEY + KEY;

    private final static String FORMAT_JSON = FORMAT + "json";
    private final static String FORMAT_XML = FORMAT + "xml";

    private final static String LIMIT_VALUE = LIMIT + "100";

    private final static String API_GAMES = API_URL + "games/?";
    private final static String FIELD_LIST_GAMES = FIELD_LIST + "id,name,api_detail_url,image";
    public final static String GAMES_URL = API_GAMES + API_KEY_VALUE + "&" + FORMAT_JSON + "&" + LIMIT_VALUE + "&" + FIELD_LIST_GAMES + "&" + FILTER;

}
