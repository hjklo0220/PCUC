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
import android.widget.Toast;

public class pw_change_fragment2 extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.pw_change_fragment2,container,false);


        Button newpw_button = (Button)rootView.findViewById(R.id.newpw_button);

        newpw_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText newpw,newpw_check;

                newpw = (EditText)rootView.findViewById(R.id.newpw);
                newpw_check=(EditText)rootView.findViewById(R.id.newpw_check);

                if(newpw.getText().toString().equals(newpw_check.getText().toString())){

                    pw_change activity_pw_change = (pw_change) getActivity();

                    assert activity_pw_change != null;
                    boolean login_result = activity_pw_change.pw_change(newpw.getText().toString());

                    if(login_result) activity_pw_change.finish();


                }

                else{
                    Toast.makeText(rootView.getContext(), "입력한 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return rootView;
    }


}
