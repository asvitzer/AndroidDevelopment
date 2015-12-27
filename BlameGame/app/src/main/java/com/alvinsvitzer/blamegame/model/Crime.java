package com.alvinsvitzer.blamegame.model;

import java.util.UUID;

/**
 * Created by Alvin on 12/27/15.
 */
public class Crime {

    private UUID mId;

    private String mTitle;

    public Crime(){

        mId = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
