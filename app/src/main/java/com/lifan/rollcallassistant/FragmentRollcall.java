package com.lifan.rollcallassistant;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentRollcall extends Fragment {

    protected RollCall rollCall;
    private View view;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View oview = inflater.inflate(R.layout.fragment_rollcall, container, false);
        view = oview;
        return oview;
    }

    public void setName(int flag){
        rollCall=(RollCall)getActivity();
        TextView textName = view.findViewById(R.id.textName);
        textName.setText(rollCall.SavedClass.Students[flag].getStuName());
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
