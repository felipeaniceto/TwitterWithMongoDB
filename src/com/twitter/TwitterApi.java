package com.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author felipe Aniceto
 * @author Mirlane Ribeiro
 */

public class TwitterApi {
    
    public static final String CREATED_AT = "created_at";
    public static final String FROM_USER = "from_user";
    public static final String FROM_USER_ID = "from_user_id";
    public static final String GEO_LOCATION = "geo_location";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ID = "id";
    public static final String ISO_LANGUAGE_CODE = "iso_language_code";
    public static final String LOCATION = "location";
    public static final String PLACE = "place";
    public static final String PROFILE_IMAGE_URL = "profile_image_url";
    public static final String SOURCE = "source";
    public static final String TEXT = "text";
    public static final String TO_USER = "to_user";
    public static final String TO_USER_ID = "to_user_id";

    private static TwitterApi instance;
    private Query query = new Query();
    private static Twitter twitter;

    static {
        instance = new TwitterApi();
        twitter = new TwitterFactory().getInstance();
    }

    private TwitterApi() {
    }

    public static TwitterApi getInstance() {
        return instance;
    }

    public void setQuery(String q, int rpp) {
        query.setQuery(q);
        query.setRpp(rpp);
    }

    public List<Tweet> getTweets() {
        try {
            return searchTwetts().getTweets();
        } catch (TwitterException ex) {
            Logger.getLogger(TwitterApi.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<Tweet>();
        }
    }

    private QueryResult searchTwetts() throws TwitterException {
        return twitter.search(query);
    }
}
