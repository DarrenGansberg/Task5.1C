package com.darrengansberg.tourismapp;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Vector;

//Class that is used as a data context to supply the application with its data.
//Would be rebuilt to allow for real data to be handled by the app.
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