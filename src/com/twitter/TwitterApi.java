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

public class TwitterApi {

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
