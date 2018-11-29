package com.example.pcuc;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class pw_change extends AppCompatActivity {

    pw_change_fragment pw_change_fragment;
    pw_change_fragment2 pw_change_fragment2;

    String ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_change);

        pw_change_fragment = new pw_change_fragment();


        SharedPreferences getPre2 = getSharedPreferences("setting",MODE_PRIVATE);
        ID = getPre2.getString("ID","");



        pw_change_fragment2 = new pw_change_fragment2();
        //pw_change_fragment = new pw_change_fragment();
    }


    public boolean pwFragmentChange(String PW){


        if("".equalsIgnoreCase(ID) ){
            Toast.makeText(this, "비 정상적인 접근입니다.", Toast.LENGTH_LONG).show();
            return false;
        }
        else{

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("name", ID);
            paramMap.put("country",PW);
            CustomThread thread2 = new CustomThread(paramMap, "login.php");

            thread2.start();
            try {
                thread2.join();
            } catch (InterruptedException ignored) {
            }
            String result = thread2.Getresult();
            boolean login_result = !Objects.equals(result, "");
            String message = login_result? "인증 완료":"비밀번호를 확인해 주세요.";
            thread2.interrupt();

            Toast.makeText(this,message, Toast.LENGTH_LONG).show();


            if(login_result){
                getSupportFragmentManager().beginTransaction().replace(R.id.pw_change_mainFragment,pw_change_fragment2).commit();
                return true;
            }

            else{

                return false;
            }



        }



    }



    public void toastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean pw_change(String pw){

        SharedPreferences getPre = getSharedPreferences("setting",MODE_PRIVATE);
        String old_pw = getPre.getString("PW","");
        String id = getPre.getString("ID","");

        SharedPreferences.Editor e_getPre = getPre.edit();
        //e_getPre.putBoolean("Auto_Login_enabled",false);
        //e_getPre.putString("PW","");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", id);
        paramMap.put("country",pw);
        paramMap.put("old_country",old_pw);
        System.out.println(id);
        System.out.println(pw);
        System.out.println(old_pw);
        CustomThread thread2 = new CustomThread(paramMap, "update.php");

        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException ignored) {
        }
        String result = thread2.Getresult();
        boolean login_result = Objects.equals(result, "success");
        String message = login_result? "비밀번호 성공":"알수없는 오류가 발생하였습니다.\n프로그램을 재실행 해주십시오.";
        toastMsg(message);
        thread2.interrupt();
        if(login_result) {
            e_getPre.putString("PW",pw);
            return e_getPre.commit();
        }
        else return false;
        //return login_result;

    }

}
