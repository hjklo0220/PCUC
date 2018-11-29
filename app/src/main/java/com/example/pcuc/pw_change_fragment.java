package com.example.pcuc;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class pw_change_fragment  extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.pw_change_fragment,container, false);

        final Button pw_val = (Button) rootView.findViewById(R.id.oldpw_button);

        pw_val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity activity = (MainActivity) getActivity();

                //rootView.setVisibility(View.GONE);

                EditText oldpw_check = (EditText) rootView.findViewById(R.id.oldpw_check);



                TextView valcheck_text = (TextView) rootView.findViewById(R.id.valcheck_text);


                String PW = oldpw_check.getText().toString();

                pw_change activity_pw_change = (pw_change) getActivity();
                if(activity_pw_change.pwFragmentChange(PW)){

                    oldpw_check.setVisibility(View.GONE);
                    pw_val.setVisibility(View.GONE);
                    valcheck_text.setVisibility(View.GONE);


                }








            }

        });

        return rootView;
    }
}