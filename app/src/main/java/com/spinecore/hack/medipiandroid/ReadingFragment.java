package com.spinecore.hack.medipiandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spinecore.hack.medipiandroid.domain.MediPiReading;
import com.spinecore.hack.medipiandroid.store.MediPiStorageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 * {@link GridLayoutManager}.
 */
public class ReadingFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected ReadingAdaptor mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<MediPiReading> mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reading, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerReadingView);

        setRecyclerViewLayoutManager();

        mAdapter = new ReadingAdaptor(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     */
    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        Context ctx = getActivity();
        mDataset = new ArrayList<MediPiReading>();
        mDataset.add(new MediPiReading("Blood Pressure", Float.toString(
                MediPiStorageUtils.getFloatPreference(ctx,MediPiStorageUtils.BP_READING,120))));
        mDataset.add(new MediPiReading("Oximeter", Float.toString(
                MediPiStorageUtils.getFloatPreference(ctx,MediPiStorageUtils.OX_READING,99))));
        mDataset.add(new MediPiReading("Weight", Float.toString(
                MediPiStorageUtils.getFloatPreference(ctx,MediPiStorageUtils.WEIGHT_READING,70))));
    }

}