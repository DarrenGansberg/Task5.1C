package com.darrengansberg.tourismapp;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Vector;

class ApplicationData {

    private static List<Destination> destinations;

    public static Destination getDestination(int position)
    {
        return getPlacesToGo().get(position);
    }

    public static List<Destination> getPlacesToGo(){

        if (destinations == null)
        {
            destinations = new Vector<>();
            AddDestinations(destinations, 1000);
        }
        return destinations;
    }

    public static List<Destination> getTopDestinations()
    {
        return getPlacesToGo();
    }

    private static void AddDestinations(@NonNull List<Destination> destinations, int count)
    {
        for(int i = 1; i <= count; i++)
        {
            Destination d = new Destination(i, "", "" , 0);
            destinations.add(d);
        }
    }
}