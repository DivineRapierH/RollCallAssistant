package com.lifan.rollcallassistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentResult.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentResult extends Fragment {
    protected RollCall rollCall;
    private View view;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View oview = inflater.inflate(R.layout.fragment_result, container, false);
        view = oview;
        return oview;
    }


    public void set(){
        rollCall=(RollCall)getActivity();

        //初始化控件
        TextView row1_Id = view.findViewById(R.id.row1_id);
        TextView row2_Id = view.findViewById(R.id.row2_id);
        TextView row3_Id = view.findViewById(R.id.row3_id);
        TextView row4_Id = view.findViewById(R.id.row4_id);
        TextView row5_Id = view.findViewById(R.id.row5_id);
        TextView row1_Name = view.findViewById(R.id.row1_name);
        TextView row2_Name = view.findViewById(R.id.row2_name);
        TextView row3_Name = view.findViewById(R.id.row3_name);
        TextView row4_Name = view.findViewById(R.id.row4_name);
        TextView row5_Name = view.findViewById(R.id.row5_name);
        TextView row1_Absence = view.findViewById(R.id.row1_absence);
        TextView row2_Absence = view.findViewById(R.id.row2_absence);
        TextView row3_Absence = view.findViewById(R.id.row3_absence);
        TextView row4_Absence = view.findViewById(R.id.row4_absence);
        TextView row5_Absence = view.findViewById(R.id.row5_absence);
        TextView row1_Answer = view.findViewById(R.id.row1_answer);
        TextView row2_Answer = view.findViewById(R.id.row2_answer);
        TextView row3_Answer = view.findViewById(R.id.row3_answer);
        TextView row4_Answer = view.findViewById(R.id.row4_answer);
        TextView row5_Answer = view.findViewById(R.id.row5_answer);
        TextView row1_Average = view.findViewById(R.id.row1_average);
        TextView row2_Average = view.findViewById(R.id.row2_average);
        TextView row3_Average = view.findViewById(R.id.row3_average);
        TextView row4_Average = view.findViewById(R.id.row4_average);
        TextView row5_Average = view.findViewById(R.id.row5_average);

        row1_Id.setText(rollCall.SavedClass.Students[0].getStuId());
        row2_Id.setText(rollCall.SavedClass.Students[1].getStuId());
        row3_Id.setText(rollCall.SavedClass.Students[2].getStuId());
        row4_Id.setText(rollCall.SavedClass.Students[3].getStuId());
        row5_Id.setText(rollCall.SavedClass.Students[4].getStuId());
        row1_Name.setText(rollCall.SavedClass.Students[0].getStuName());
        row2_Name.setText(rollCall.SavedClass.Students[1].getStuName());
        row3_Name.setText(rollCall.SavedClass.Students[2].getStuName());
        row4_Name.setText(rollCall.SavedClass.Students[3].getStuName());
        row5_Name.setText(rollCall.SavedClass.Students[4].getStuName());

        row1_Absence.setText(Integer.toString(rollCall.SavedClass.Students[0].getStuAbsence()));
        row2_Absence.setText(Integer.toString(rollCall.SavedClass.Students[1].getStuAbsence()));
        row3_Absence.setText(Integer.toString(rollCall.SavedClass.Students[2].getStuAbsence()));
        row4_Absence.setText(Integer.toString(rollCall.SavedClass.Students[3].getStuAbsence()));
        row5_Absence.setText(Integer.toString(rollCall.SavedClass.Students[4].getStuAbsence()));
        row1_Answer.setText(Integer.toString(rollCall.SavedClass.Students[0].getStuAnswer()));
        row2_Answer.setText(Integer.toString(rollCall.SavedClass.Students[1].getStuAnswer()));
        row3_Answer.setText(Integer.toString(rollCall.SavedClass.Students[2].getStuAnswer()));
        row4_Answer.setText(Integer.toString(rollCall.SavedClass.Students[3].getStuAnswer()));
        row5_Answer.setText(Integer.toString(rollCall.SavedClass.Students[4].getStuAnswer()));
        String str1=""+rollCall.SavedClass.Students[0].getStuAverage();
        String str2=""+rollCall.SavedClass.Students[1].getStuAverage();
        String str3=""+rollCall.SavedClass.Students[2].getStuAverage();
        String str4=""+rollCall.SavedClass.Students[3].getStuAverage();
        String str5=""+rollCall.SavedClass.Students[4].getStuAverage();
        row1_Average.setText(str1);
        row2_Average.setText(str2);
        row3_Average.setText(str3);
        row4_Average.setText(str4);
        row5_Average.setText(str5);
    }




    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentResult() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentResult.
     *//*
    // TODO: Rename and change types and number of parameters
    public static FragmentResult newInstance(String param1, String param2) {
        FragmentResult fragment = new FragmentResult();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
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

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
