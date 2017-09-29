package com.spinecore.hack.medipiandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private static ArrayList<List<String>> oximeterInstructions;
    static
    {
        oximeterInstructions = new ArrayList<List<String>>();
        List<String> oximeterIndividualInstruction1 =
                new ArrayList<String>(Arrays.asList("someImage1.jpg", "Instructions for the use of the Nonin 9560 Bluetooth Pulse Oximeter."));
        List<String> oximeterIndividualInstruction2 =
                new ArrayList<String>(Arrays.asList("someImage2.jpg", "1. Press start button on MediPi to commence reading."));
        List<String> oximeterIndividualInstruction3 =
                new ArrayList<String>(Arrays.asList("someImage3.jpg", "2. Insert finger into Nonin device with nail side up until the fingertip touches the built in stop guide. This switches on the device. For best results, keep device at your heart or chest level." ));
        List<String> oximeterIndividualInstruction4 =
                new ArrayList<String>(Arrays.asList("someImage4.jpg", "3. Wait until MediPi display shows the measurement has been taken. THIS MAY TAKE SOME TIME (about 30 seconds). The LED display on the Nonin device will flash and MediPi will indicate it is 'Downloading'"));
        List<String> oximeterIndividualInstruction5 =
                new ArrayList<String>(Arrays.asList("someImage5.jpg", "4. Remove your finger and press ‘next’/’back’ on MediPi."));
        oximeterInstructions.add(oximeterIndividualInstruction1);
        oximeterInstructions.add(oximeterIndividualInstruction2);
        oximeterInstructions.add(oximeterIndividualInstruction3);
        oximeterInstructions.add(oximeterIndividualInstruction4);
        oximeterInstructions.add(oximeterIndividualInstruction5);
    }


    private static final HashMap<String,ArrayList<List<String>>> allInstructionMap;
    static
    {
        allInstructionMap = new HashMap<String,ArrayList<List<String>>>();
        allInstructionMap.put("oximeter", oximeterInstructions);
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
        TextView textfieldImage = rootView.findViewById(R.id.instruction_image);
        TextView textfieldInstruction = rootView.findViewById(R.id.instruction_step);
        if (args != null) {
            String readingType = args.getString(ARG_READING_TYPE);
            if (readingType == "oximeter") {
                String[] instructions = getResources().getStringArray(R.array.oximeter_instructions);
                String[] images = getResources().getStringArray(R.array.oximeter_instruction_images);
                textfieldImage.setText(images[args.getInt(ARG_INSTRUCTION_STEP)]);
                textfieldInstruction.setText(instructions[args.getInt(ARG_INSTRUCTION_STEP)]);
            }
            else if (readingType == "weight") {
                String[] instructions = getResources().getStringArray(R.array.weight_instructions);
                String[] images = getResources().getStringArray(R.array.weight_instruction_images);
                textfieldImage.setText(images[args.getInt(ARG_INSTRUCTION_STEP)]);
                textfieldInstruction.setText(instructions[args.getInt(ARG_INSTRUCTION_STEP)]);
            }
            else if (readingType == "bp") {
                String[] instructions = getResources().getStringArray(R.array.bp_instructions);
                String[] images = getResources().getStringArray(R.array.bp_instruction_images);
                textfieldImage.setText(images[args.getInt(ARG_INSTRUCTION_STEP)]);
                textfieldInstruction.setText(instructions[args.getInt(ARG_INSTRUCTION_STEP)]);
            }
            //ArrayList<List<String>> instructions = allInstructionMap.get(args.getString(ARG_READING_TYPE));
            System.out.println("Hello world");
            //textfield.setText(Integer.toString(args.getInt(ARG_OBJECT)));
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
