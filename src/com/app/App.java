package com.app;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongodb;
import com.mongodb.Search;
import com.twitter.TwitterApi;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.Tweet;

public class App {

    /**
     *
     * @author felipe Aniceto
     * @author Mirlane Ribeiro
     */
    private static final String SEARCH = "Siena 'Palio Weekend'";

    public static void main(String[] args) {
        Mongodb.getInstance().createConnection();
        Mongodb.getInstance().removeAll();
        searchTwetts(SEARCH);
        insertTwetts();
        find();
    }

    private static void searchTwetts(String search) {
        TwitterApi.getInstance().setQuery(search, 1000);
    }

    private static void insertTwetts() {
        List<Tweet> tweets = TwitterApi.getInstance().getTweets();
        for (Tweet tweet : tweets) {
            BasicDBObject bdbo = new BasicDBObject();
            bdbo.put(TwitterApi.CREATED_AT, tweet.getCreatedAt().toString());
            bdbo.put(TwitterApi.FROM_USER, tweet.getFromUser());
            bdbo.put(TwitterApi.FROM_USER_ID, tweet.getFromUserId());
            GeoLocation location = tweet.getGeoLocation();
            if (location != null) {
                bdbo.put(TwitterApi.GEO_LOCATION, new BasicDBObject(TwitterApi.LATITUDE, tweet.getGeoLocation().getLatitude()).append(TwitterApi.LONGITUDE, tweet.getGeoLocation().getLongitude()));
            } else {
                bdbo.put(TwitterApi.GEO_LOCATION, null);
            }
            bdbo.put(TwitterApi.ID, tweet.getId());
            bdbo.put(TwitterApi.ISO_LANGUAGE_CODE, tweet.getIsoLanguageCode());
            bdbo.put(TwitterApi.LOCATION, tweet.getLocation());
            bdbo.put(TwitterApi.PLACE, tweet.getPlace());
            bdbo.put(TwitterApi.PROFILE_IMAGE_URL, tweet.getProfileImageUrl());
            bdbo.put(TwitterApi.SOURCE, tweet.getSource());
            bdbo.put(TwitterApi.TEXT, tweet.getText());
            bdbo.put(TwitterApi.TO_USER, tweet.getToUser());
            bdbo.put(TwitterApi.TO_USER_ID, tweet.getToUserId());
            Mongodb.getInstance().insert(bdbo);
        }
    }

    private static void find() {
        Search.countTweets();
        Search.findAllTwetts();
        Search.selectDistinct(TwitterApi.FROM_USER);
        Search.selectUserAndText();
        Search.selectTextByKey(TwitterApi.FROM_USER, "pontanegrafiat");
    }
}