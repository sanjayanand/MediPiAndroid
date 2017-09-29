package com.spinecore.hack.medipiandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;


/**
         * A simple {@link Fragment} subclass.
         * Activities that contain this fragment must implement the
         * {@link BlankFragment.OnFragmentInteractionListener} interface
         * to handle interaction events.
         * Use the {@link BlankFragment#newInstance} factory method to
         * create an instance of this fragment.
         */
        public class BlankFragment extends Fragment {
            // TODO: Rename parameter arguments, choose names that match
            // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
            private static final String ARG_READING_TYPE = "readingType";
            private static final HashMap<String,String> readingTypeMap;
            static
            {
                readingTypeMap = new HashMap<String,String>();
                readingTypeMap.put("oximeter","Oximeter Reading");
                readingTypeMap.put("weight","Weight Reading");
                readingTypeMap.put("bp", "Blood Pressure");
                readingTypeMap.put("thermometer","Thermometer Reading");
            }
            // TODO: Rename and change types of parameters
            private String readingType;

            private OnFragmentInteractionListener mListener;

            public BlankFragment() {
                // Required empty public constructor
            }

            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param readingType Parameter 1.
             * @return A new instance of fragment BlankFragment.
             */
            // TODO: Rename and change types and number of parameters
            public static BlankFragment newInstance(String readingType) {
                BlankFragment fragment = new BlankFragment();
                Bundle args = new Bundle();
                args.putString(ARG_READING_TYPE, readingType);
                fragment.setArguments(args);
                return fragment;
            }

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if (getArguments() != null) {
                    readingType = getArguments().getString(ARG_READING_TYPE);
                }

            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_blank, container, false);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                //Fragment instruction = new InstructionFragment();
                //ft.add(R.id.instruction_fragment, instruction);
                //ft.commit();

                //View view = inflater.inflate(R.layout.fragment_blank, container, false);
                TextView title = (TextView) view.findViewById(R.id.reading_title);
                title.setText(readingTypeMap.get(readingType));
                return view;
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
