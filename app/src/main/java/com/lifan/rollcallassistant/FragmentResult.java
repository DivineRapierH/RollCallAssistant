package com.lifan.rollcallassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        //写入学生信息
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

}
