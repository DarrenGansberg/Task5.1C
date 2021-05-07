package com.darrengansberg.tourismapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/*Class that defines a fragment that is used to view a destinations information */
public class ViewDestinationFragment extends Fragment {

    private static final String ARG_PARAM1 = "destinationId";

    //identifier for the destination
    private int mDestinationId;

    public ViewDestinationFragment() {
        // Required empty public constructor
    }

    //param: destinationId
    //description: Identifier for the destination to be viewed.
    public static ViewDestinationFragment newInstance(int destinationId) {
        ViewDestinationFragment fragment = new ViewDestinationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, destinationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDestinationId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_destination, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Destination d = ApplicationData.getDestination(mDestinationId);
        updateTextView(view.findViewById(R.id.DestinationTitleView), d.getTitle());
        updateTextView(view.findViewById(R.id.DestinationDescriptionView), d.getDescription());
        updateImageView(view.findViewById(R.id.DestinationImageView), d.getImageId());

    }

    protected void updateTextView(@NonNull AppCompatTextView view, @NonNull String value)
    {
        if (!value.equals(""))
        {
            view.setText(value);
        }
    }

    protected void updateImageView(@NonNull AppCompatImageView view, int imageId)
    {
        Drawable d = null;
        if (imageId == 0)
        {
            d = AppCompatResources.getDrawable(getContext(), R.drawable.view_destination_default_image);
        }
        if (d != null)
        {
            view.setImageDrawable(d);
        }
    }
}