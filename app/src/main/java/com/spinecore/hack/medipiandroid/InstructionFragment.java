package com.spinecore.hack.medipiandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstructionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;

    public static final String ARG_READING_TYPE="readingType";
    public static final String ARG_INSTRUCTION_STEP="instructionStep";

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected QuestionnaireAdaptor mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<String> mDataset;
    public InstructionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @return A new instance of fragment InstructionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InstructionFragment newInstance() {
        InstructionFragment fragment = new InstructionFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        Bundle args = getArguments();
        ImageView textfieldImage = rootView.findViewById(R.id.instruction_image);
        TextView textfieldInstruction = rootView.findViewById(R.id.instruction_step);
        if (args != null) {
            String readingType = args.getString(ARG_READING_TYPE);
            if (readingType == "oximeter") {
                String[] instructions = getResources().getStringArray(R.array.oximeter_instructions);
                String[] images = getResources().getStringArray(R.array.oximeter_instruction_images);
                int resource = getResources().getIdentifier("com.spinecore.hack.medipiandroid:drawable/"+images[args.getInt(ARG_INSTRUCTION_STEP)], null, null);
                textfieldImage.setImageResource(resource);
                textfieldInstruction.setText(instructions[args.getInt(ARG_INSTRUCTION_STEP)]);
            }
            else if (readingType == "weight") {
                String[] instructions = getResources().getStringArray(R.array.weight_instructions);
                String[] images = getResources().getStringArray(R.array.weight_instruction_images);
                int resource = getResources().getIdentifier("com.spinecore.hack.medipiandroid:drawable/"+images[args.getInt(ARG_INSTRUCTION_STEP)], null, null);
                textfieldImage.setImageResource(resource);
                textfieldInstruction.setText(instructions[args.getInt(ARG_INSTRUCTION_STEP)]);
            }
            else if (readingType == "bp") {
                String[] instructions = getResources().getStringArray(R.array.bp_instructions);
                String[] images = getResources().getStringArray(R.array.bp_instruction_images);
                int resource = getResources().getIdentifier("com.spinecore.hack.medipiandroid:drawable/"+images[args.getInt(ARG_INSTRUCTION_STEP)], null, null);
                textfieldImage.setImageResource(resource);
                textfieldInstruction.setText(instructions[args.getInt(ARG_INSTRUCTION_STEP)]);
            }
        }

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
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
