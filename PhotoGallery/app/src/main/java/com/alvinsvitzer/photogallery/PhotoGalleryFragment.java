package com.alvinsvitzer.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by Alvin on 2/23/16.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = PhotoGalleryFragment.class.getSimpleName();
    private RecyclerView mPhotoRecyclerView;


    public static Fragment newInstance(){
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        new FetchItemsTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        return v;
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,Void>{

        protected Void doInBackground(Void... params){

            try {
                String result = new FlickrFetchr().getUrlString("https://www.google.com");
                Log.i(TAG, "fetched contents of URL: " + result);
            }catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: " + ioe);
            }

            return null;
        }
    }
}
