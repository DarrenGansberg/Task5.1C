package com.darrengansberg.tourismapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

//Class used for the Browse Destinations Fragment. The Browse Destinations Fragment
//is a portion of the UI that displays a list of places to go, in a vertical list, along with a
//set of top destinations, in a horizontal list of destinations. Each list is scrollable by the
//user.
public class BrowseDestinationsFragment extends Fragment {

    //A listener that is used to invoke a callback that handles
    //clicking/touch/activating a place to go that is displayed in the
    //the list of places to go.
    private DestinationItemClickedListener destinationClickedListener;

    public BrowseDestinationsFragment() {
        // Required empty public constructor
    }

    public static BrowseDestinationsFragment newInstance() {
        return new BrowseDestinationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_destinations, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();

        //Setup the top destinations recycler view
        RecyclerView topDestView = view.findViewById(R.id.top_destination_recycler_view);
        LinearLayoutManager topDestinationsLayoutManager = new LinearLayoutManager(context);
        topDestinationsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        topDestView.setLayoutManager(topDestinationsLayoutManager);
        TopDestinationsRecyclerAdapter topDestinationsAdapter =
                new TopDestinationsRecyclerAdapter(ApplicationData.getTopDestinations());
        topDestView.setAdapter(topDestinationsAdapter);

        //Setup the places to go recycler view
        RecyclerView myView = view.findViewById(R.id.places_to_go_recycler_view);
        PlacesToGoRecyclerViewAdapter placesToGoAdapter = new PlacesToGoRecyclerViewAdapter(ApplicationData.getPlacesToGo());
        placesToGoAdapter.setItemClickedListener(destinationClickedListener);
        myView.setAdapter(placesToGoAdapter);
        myView.setLayoutManager(new LinearLayoutManager(context));

        //Setup click handlers for scroll buttons
        AppCompatButton leftScrollButton = view.findViewById(R.id.top_destination_scroll_left_button);
        leftScrollButton.setOnClickListener(new DestinationScrollButtonClickListener(topDestView));
        AppCompatButton rightScrollButton = view.findViewById(R.id.top_destination_scroll_right_button);
        rightScrollButton.setOnClickListener(new DestinationScrollButtonClickListener(topDestView));


    }

    private class DestinationScrollButtonClickListener implements View.OnClickListener{

        private RecyclerView topDestRecyclerView;

        public DestinationScrollButtonClickListener(@NonNull RecyclerView topDestRecyclerView)
        {
            this.topDestRecyclerView = topDestRecyclerView;
        }

        @Override
        public void onClick(View v) {

            AppCompatImageView image = getActivity().findViewById(R.id.top_destination_image);
            //only scroll if we can actually retrieve an destination image
            if (image != null)
            {
                int pixelsToScrollX = 0;
                int pixelsToScrollY = 0;
                switch(v.getId())
                {
                    case R.id.top_destination_scroll_left_button:
                        pixelsToScrollX = image.getWidth();
                        break;
                    case R.id.top_destination_scroll_right_button:
                        pixelsToScrollX = -(image.getWidth());
                        break;
                    default:
                        throw new IllegalArgumentException("Unrecognised button");
                }
                topDestRecyclerView.smoothScrollBy(pixelsToScrollX, pixelsToScrollY);
            }
        }
    }

    public void setDestinationClickedListener(DestinationItemClickedListener listener){

        this.destinationClickedListener = listener;
    }

    protected void destinationClicked(int destinationId)
    {
        if (this.destinationClickedListener != null)
        {
            this.destinationClickedListener.onDestinationClicked(destinationId);
        }
    }

    private class PlacesToGoRecyclerViewAdapter extends RecyclerView.Adapter<PlacesToGoRecyclerViewAdapter.ViewHolder> {

        private List<Destination> data;
        private DestinationItemClickedListener itemClickedListener;

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final LinearLayoutCompat placeToGoContainer;
            private final AppCompatTextView titleView;
            private final AppCompatTextView descriptionView;
            private final AppCompatImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                placeToGoContainer = itemView.findViewById(R.id.place_to_go_container);
                titleView = itemView.findViewById(R.id.place_to_go_title_view);
                descriptionView = itemView.findViewById(R.id.place_to_go_description_view);
                imageView = itemView.findViewById(R.id.places_to_go_image_view);
            }

            public AppCompatTextView getTitleView() {
                return titleView;
            }

            public AppCompatTextView getDescriptionView() {
                return descriptionView;
            }

            public AppCompatImageView getImageView() {
                return imageView;
            }

            public LinearLayoutCompat getPlaceToGoContainer() {
                return placeToGoContainer;
            }
        }

        public PlacesToGoRecyclerViewAdapter(@NonNull List<Destination> data) {

            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.place_to_go_container, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Destination destination = data.get(position);
            updateTitleView(holder, destination.getTitle());
            updateDescriptionView(holder, destination.getDescription());
            updatedImageView(holder, destination.getImageId());
            View.OnClickListener viewListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    destinationClicked(destination.getId());
                }
            };
            holder.getPlaceToGoContainer().setOnClickListener(viewListener);
        }

        private void updatedImageView(ViewHolder holder, int imageId) {
            Drawable destImage = null;
            if (imageId == 0)
            {
                imageId = R.drawable.destination_default_image;
                destImage = AppCompatResources.getDrawable(getContext(), imageId);
            }

            if (destImage != null)
            {
                holder.getImageView().setImageDrawable(destImage);
            }
        }

        private void updateTitleView(@NonNull ViewHolder holder, String title)
        {
            if (title.equals(""))
            {
                title = "[Destination Title]";
            }
            holder.getTitleView().setText(title);
        }

        private void updateDescriptionView(@NonNull ViewHolder holder, String description)
        {
            if (description.equals(""))
            {
                description = getString(R.string.lorem_ipsum);
            }
            holder.getDescriptionView().setText(description);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setItemClickedListener(DestinationItemClickedListener listener) {
            this.itemClickedListener = listener;
        }
    }

    private class TopDestinationsRecyclerAdapter extends RecyclerView.Adapter<TopDestinationsRecyclerAdapter.ViewHolder> {

        private final List<Destination> data;

        public TopDestinationsRecyclerAdapter(@NonNull List<Destination> topDestinations)
        {
            this.data = topDestinations;
        }

        private class ViewHolder extends RecyclerView.ViewHolder{

            private AppCompatImageView imageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.top_destination_image);
            }

            public AppCompatImageView getImageView()
            {
                return imageView;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //This step constructs the view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.topdestination_container, parent, false);
            view.setId(R.id.top_destination_image);
            //Then wrap it and return in in the ViewHolder object.
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TopDestinationsRecyclerAdapter.ViewHolder holder,
                                     int position) {
            Destination destination = data.get(position);
            updatedImageView(holder, destination.getImageId());
        }

        private void updatedImageView(TopDestinationsRecyclerAdapter.ViewHolder holder, int imageId) {
            Drawable destImage = null;
            if (imageId == 0)
            {
                imageId = R.drawable.top_destination_default_image;
                destImage = AppCompatResources.getDrawable(getContext(), imageId);
            }

            if (destImage != null)
            {
                holder.getImageView().setImageDrawable(destImage);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }
}