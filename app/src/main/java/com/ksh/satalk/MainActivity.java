package com.ksh.satalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ksh.satalk.Fragment.PeopleFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, new PeopleFragment()).commit();
    }
}
