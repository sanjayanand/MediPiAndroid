package com.spinecore.hack.medipiandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spinecore.hack.medipiandroid.domain.MediPiReading;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ReadingAdaptor extends RecyclerView.Adapter<ReadingAdaptor.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<MediPiReading> mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView readingType;
        private final TextView reading;

        public ViewHolder(View v) {
            super(v);
            readingType = (TextView) v.findViewById(R.id.readingType);
            reading = (TextView) v.findViewById(R.id.reading);
        }

        public TextView getReadingType() {
            return readingType;
        }
        public TextView getReading() {
            return reading;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public ReadingAdaptor(List<MediPiReading> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reading_row, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getReadingType().setText(mDataSet.get(position).getDisplayName());
        viewHolder.getReading().setText(mDataSet.get(position).getReading());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}