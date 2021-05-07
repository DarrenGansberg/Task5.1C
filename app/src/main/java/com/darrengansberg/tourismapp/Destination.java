package com.darrengansberg.tourismapp;

import androidx.annotation.NonNull;

//A domain entity class that is used to store and manage data relating to a destination.
public class Destination {

    private final int id;
    private final String title;
    private final String description;
    private int imageId;

    public Destination(int id, @NonNull String title, @NonNull String description, int imageId)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription() { return description; }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }

    public int getImageId()
    {
        return this.imageId;
    }


}
