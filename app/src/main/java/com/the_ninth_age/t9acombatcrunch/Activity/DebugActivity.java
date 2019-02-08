package com.the_ninth_age.t9acombatcrunch.Activity;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.the_ninth_age.t9acombatcrunch.R;
import com.the_ninth_age.t9acombatcrunch.Service.Result.Outcome;
import com.the_ninth_age.t9acombatcrunch.Service.SimulatorService;

import java.util.List;

public class DebugActivity extends AppCompatActivity {

    SimulatorService simulatorService;
    Gson gson;
    Resources res;

    TextView text_debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        res = getResources();
        gson = new Gson();
        simulatorService = gson.fromJson(getIntent().getStringExtra("simulatorService"), SimulatorService.class);
        text_debug = (TextView) findViewById(R.id.text_debug);

        Outcome result = simulatorService.runDebugCombat();
        List<String> lines = result.getCombatDescription();
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(line + "\n");
        }
        text_debug.setText(stringBuilder.toString());

    }
}
