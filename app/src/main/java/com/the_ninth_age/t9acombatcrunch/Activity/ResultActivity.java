package com.the_ninth_age.t9acombatcrunch.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.the_ninth_age.t9acombatcrunch.R;
import com.the_ninth_age.t9acombatcrunch.Service.Result.CombatOutcome;
import com.the_ninth_age.t9acombatcrunch.Service.SimulatorService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    SimulatorService simulatorService;
    Gson gson;
    Resources res;

    TextView title_combat_result;
    TextView bar_unit1_destroyed_overall;
    TextView bar_unit1_fled_overall;
    TextView bar_combat_continues_overall;
    TextView bar_unit2_fled_overall;
    TextView bar_unit2_destroyed_overall;
    TextView bar_mutual_destruction_overall;
    TextView numbers_unit1_destroyed_overall;
    TextView numbers_unit1_fled_overall;
    TextView numbers_combat_continues_overall;
    TextView numbers_unit2_fled_overall;
    TextView numbers_unit2_destroyed_overall;
    TextView numbers_mutual_destruction_overall;
    Button button_redo_units;

    List<TextView> list_results_title;
    List<LinearLayout> list_results_chart;
    List<TextView> list_results_unit1_destroyed;
    List<TextView> list_results_unit1_fled;
    List<TextView> list_results_combat_continues;
    List<TextView> list_results_unit2_fled;
    List<TextView> list_results_unit2_destroyed;
    List<TextView> list_results_mutual_destruction;

    int rounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        res = getResources();
        gson = new Gson();
        simulatorService = gson.fromJson(getIntent().getStringExtra("simulatorService"), SimulatorService.class);
        rounds = simulatorService.getRoundsRequested();

        //identify interface elements
        title_combat_result = (TextView) findViewById(R.id.title_combat_result);
        bar_unit1_destroyed_overall = (TextView) findViewById(R.id.bar_unit1_destroyed_overall);
        bar_unit1_fled_overall = (TextView) findViewById(R.id.bar_unit1_fled_overall);
        bar_combat_continues_overall = (TextView) findViewById(R.id.bar_combat_continues_overall);
        bar_unit2_fled_overall = (TextView) findViewById(R.id.bar_unit2_fled_overall);
        bar_unit2_destroyed_overall = (TextView) findViewById(R.id.bar_unit2_destroyed_overall);
        bar_mutual_destruction_overall = (TextView) findViewById(R.id.bar_mutual_destruction_overall);
        button_redo_units = (Button) findViewById(R.id.button_redo_units_result);
        numbers_unit1_destroyed_overall = (TextView) findViewById(R.id.numbers_unit1_destroyed_overall);
        numbers_unit1_fled_overall = (TextView) findViewById(R.id.numbers_unit1_fled_overall);
        numbers_combat_continues_overall = (TextView) findViewById(R.id.numbers_combat_continues_overall);
        numbers_unit2_fled_overall = (TextView) findViewById(R.id.numbers_unit2_fled_overall);
        numbers_unit2_destroyed_overall = (TextView) findViewById(R.id.numbers_unit2_destroyed_overall);
        numbers_mutual_destruction_overall = (TextView) findViewById(R.id.numbers_mutual_destruction_overall);

        list_results_title = Arrays.asList((TextView) findViewById(R.id.title_result_1), (TextView) findViewById(R.id.title_result_2), (TextView) findViewById(R.id.title_result_3), (TextView) findViewById(R.id.title_result_4), (TextView) findViewById(R.id.title_result_5), (TextView) findViewById(R.id.title_result_6), (TextView) findViewById(R.id.title_result_7), (TextView) findViewById(R.id.title_result_8), (TextView) findViewById(R.id.title_result_9), (TextView) findViewById(R.id.title_result_10), (TextView) findViewById(R.id.title_result_11), (TextView) findViewById(R.id.title_result_12));
        list_results_chart = Arrays.asList((LinearLayout) findViewById(R.id.chart_result_1), (LinearLayout) findViewById(R.id.chart_result_2), (LinearLayout) findViewById(R.id.chart_result_3), (LinearLayout) findViewById(R.id.chart_result_4), (LinearLayout) findViewById(R.id.chart_result_5), (LinearLayout) findViewById(R.id.chart_result_6), (LinearLayout) findViewById(R.id.chart_result_7), (LinearLayout) findViewById(R.id.chart_result_8), (LinearLayout) findViewById(R.id.chart_result_9), (LinearLayout) findViewById(R.id.chart_result_10), (LinearLayout) findViewById(R.id.chart_result_11), (LinearLayout) findViewById(R.id.chart_result_12));
        list_results_unit1_destroyed = Arrays.asList((TextView) findViewById(R.id.bar_unit1_destroyed_1), (TextView) findViewById(R.id.bar_unit1_destroyed_2), (TextView) findViewById(R.id.bar_unit1_destroyed_3), (TextView) findViewById(R.id.bar_unit1_destroyed_4), (TextView) findViewById(R.id.bar_unit1_destroyed_5), (TextView) findViewById(R.id.bar_unit1_destroyed_6), (TextView) findViewById(R.id.bar_unit1_destroyed_7), (TextView) findViewById(R.id.bar_unit1_destroyed_8), (TextView) findViewById(R.id.bar_unit1_destroyed_9), (TextView) findViewById(R.id.bar_unit1_destroyed_10), (TextView) findViewById(R.id.bar_unit1_destroyed_11), (TextView) findViewById(R.id.bar_unit1_destroyed_12));
        list_results_unit1_fled = Arrays.asList((TextView) findViewById(R.id.bar_unit1_fled_1), (TextView) findViewById(R.id.bar_unit1_fled_2), (TextView) findViewById(R.id.bar_unit1_fled_3), (TextView) findViewById(R.id.bar_unit1_fled_4), (TextView) findViewById(R.id.bar_unit1_fled_5), (TextView) findViewById(R.id.bar_unit1_fled_6), (TextView) findViewById(R.id.bar_unit1_fled_7), (TextView) findViewById(R.id.bar_unit1_fled_8), (TextView) findViewById(R.id.bar_unit1_fled_9), (TextView) findViewById(R.id.bar_unit1_fled_10), (TextView) findViewById(R.id.bar_unit1_fled_11), (TextView) findViewById(R.id.bar_unit1_fled_12));
        list_results_combat_continues = Arrays.asList((TextView) findViewById(R.id.bar_combat_continues_1), (TextView) findViewById(R.id.bar_combat_continues_2), (TextView) findViewById(R.id.bar_combat_continues_3), (TextView) findViewById(R.id.bar_combat_continues_4), (TextView) findViewById(R.id.bar_combat_continues_5), (TextView) findViewById(R.id.bar_combat_continues_6), (TextView) findViewById(R.id.bar_combat_continues_7), (TextView) findViewById(R.id.bar_combat_continues_8), (TextView) findViewById(R.id.bar_combat_continues_9), (TextView) findViewById(R.id.bar_combat_continues_10), (TextView) findViewById(R.id.bar_combat_continues_11), (TextView) findViewById(R.id.bar_combat_continues_12));
        list_results_unit2_fled = Arrays.asList((TextView) findViewById(R.id.bar_unit2_fled_1), (TextView) findViewById(R.id.bar_unit2_fled_2), (TextView) findViewById(R.id.bar_unit2_fled_3), (TextView) findViewById(R.id.bar_unit2_fled_4), (TextView) findViewById(R.id.bar_unit2_fled_5), (TextView) findViewById(R.id.bar_unit2_fled_6), (TextView) findViewById(R.id.bar_unit2_fled_7), (TextView) findViewById(R.id.bar_unit2_fled_8), (TextView) findViewById(R.id.bar_unit2_fled_9), (TextView) findViewById(R.id.bar_unit2_fled_10), (TextView) findViewById(R.id.bar_unit2_fled_11), (TextView) findViewById(R.id.bar_unit2_fled_12));
        list_results_unit2_destroyed = Arrays.asList((TextView) findViewById(R.id.bar_unit2_destroyed_1), (TextView) findViewById(R.id.bar_unit2_destroyed_2), (TextView) findViewById(R.id.bar_unit2_destroyed_3), (TextView) findViewById(R.id.bar_unit2_destroyed_4), (TextView) findViewById(R.id.bar_unit2_destroyed_5), (TextView) findViewById(R.id.bar_unit2_destroyed_6), (TextView) findViewById(R.id.bar_unit2_destroyed_7), (TextView) findViewById(R.id.bar_unit2_destroyed_8), (TextView) findViewById(R.id.bar_unit2_destroyed_9), (TextView) findViewById(R.id.bar_unit2_destroyed_10), (TextView) findViewById(R.id.bar_unit2_destroyed_11), (TextView) findViewById(R.id.bar_unit2_destroyed_12));
        list_results_mutual_destruction = Arrays.asList((TextView) findViewById(R.id.bar_mutual_destruction_1), (TextView) findViewById(R.id.bar_mutual_destruction_2), (TextView) findViewById(R.id.bar_mutual_destruction_3), (TextView) findViewById(R.id.bar_mutual_destruction_4), (TextView) findViewById(R.id.bar_mutual_destruction_5), (TextView) findViewById(R.id.bar_mutual_destruction_6), (TextView) findViewById(R.id.bar_mutual_destruction_7), (TextView) findViewById(R.id.bar_mutual_destruction_8), (TextView) findViewById(R.id.bar_mutual_destruction_9), (TextView) findViewById(R.id.bar_mutual_destruction_10), (TextView) findViewById(R.id.bar_mutual_destruction_11), (TextView) findViewById(R.id.bar_mutual_destruction_12));

        //onClick listener for the button
        button_redo_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatUnitCreation();
            }
        });

        //hide or display result charts for specific combat phases
        for (int i = 0; i < 12; i++) {
            if (i < rounds) {
                TextView title = list_results_title.get(i);
                title.setVisibility(View.VISIBLE);
                title.setText(getString(R.string.combat_phase_no, i + 1));
                list_results_chart.get(i).setVisibility(View.VISIBLE);
            } else {
                list_results_title.get(i).setVisibility(View.GONE);
                list_results_chart.get(i).setVisibility(View.GONE);
            }
        }
        displayResultFullStatus();


    }

    private void displayResultNewOnly() {
        //prepare the values and variables for result
        List<Map<CombatOutcome, Integer>> allOutcomes = simulatorService.runSimulator();
        int combat_continues_overall = allOutcomes.get(rounds - 1).get(CombatOutcome.COMBAT_CONTINUES);
        int unit1_destroyed_overall = 0;
        int unit1_fled_overall = 0;
        int unit2_fled_overall = 0;
        int unit2_destroyed_overall = 0;
        int mutual_destruction_overall = 0;
        //for results of each single round
        for (int i = 0; i < rounds; i++) {
            //add to overall values
            Map<CombatOutcome, Integer> currentRound = allOutcomes.get(i);
            unit1_destroyed_overall += currentRound.get(CombatOutcome.UNIT1_DESTROYED);
            unit1_fled_overall += currentRound.get(CombatOutcome.UNIT1_FLED);
            unit2_fled_overall += currentRound.get(CombatOutcome.UNIT2_FLED);
            unit2_destroyed_overall += currentRound.get(CombatOutcome.UNIT2_DESTROYED);
            mutual_destruction_overall += currentRound.get(CombatOutcome.MUTUAL_DESTRUCTION);
            //display this round
            TextView unit1_destroyed = list_results_unit1_destroyed.get(i);
            unit1_destroyed.setLayoutParams(new LinearLayout.LayoutParams(currentRound.get(CombatOutcome.UNIT1_DESTROYED) * 3,30));
            TextView unit1_fled = list_results_unit1_fled.get(i);
            unit1_fled.setLayoutParams(new LinearLayout.LayoutParams(currentRound.get(CombatOutcome.UNIT1_FLED) * 3,30));
            TextView combat_continues = list_results_combat_continues.get(i);
            combat_continues.setLayoutParams(new LinearLayout.LayoutParams(currentRound.get(CombatOutcome.COMBAT_CONTINUES) * 3,30));
            TextView unit2_fled = list_results_unit2_fled.get(i);
            unit2_fled.setLayoutParams(new LinearLayout.LayoutParams(currentRound.get(CombatOutcome.UNIT2_FLED) * 3,30));
            TextView unit2_destroyed = list_results_unit2_destroyed.get(i);
            unit2_destroyed.setLayoutParams(new LinearLayout.LayoutParams(currentRound.get(CombatOutcome.UNIT2_DESTROYED) * 3,30));
            TextView mutual_destruction = list_results_mutual_destruction.get(i);
            mutual_destruction.setLayoutParams(new LinearLayout.LayoutParams(currentRound.get(CombatOutcome.MUTUAL_DESTRUCTION) * 3,30));
        }
        //display overall values
        bar_unit1_destroyed_overall.setLayoutParams(new LinearLayout.LayoutParams(unit1_destroyed_overall * 3,30));
        bar_unit1_fled_overall.setLayoutParams(new LinearLayout.LayoutParams(unit1_fled_overall * 3,30));
        bar_combat_continues_overall.setLayoutParams(new LinearLayout.LayoutParams(combat_continues_overall * 3,30));
        bar_unit2_destroyed_overall.setLayoutParams(new LinearLayout.LayoutParams(unit2_destroyed_overall * 3,30));
        bar_unit2_fled_overall.setLayoutParams(new LinearLayout.LayoutParams(unit2_fled_overall * 3,30));
        bar_mutual_destruction_overall.setLayoutParams(new LinearLayout.LayoutParams(mutual_destruction_overall * 3,30));
        numbers_unit1_destroyed_overall.setText(unit1_destroyed_overall + "");
        numbers_unit1_fled_overall.setText(unit1_fled_overall + "");
        numbers_combat_continues_overall.setText(combat_continues_overall + "");
        numbers_unit2_fled_overall.setText(unit2_fled_overall + "");
        numbers_unit2_destroyed_overall.setText(unit2_destroyed_overall + "");
        numbers_mutual_destruction_overall.setText(mutual_destruction_overall + "");
    }

    private void displayResultFullStatus() {
        //prepare the values and variables for result
        List<Map<CombatOutcome, Integer>> allOutcomes = simulatorService.runSimulator();
        int combat_continues_overall = allOutcomes.get(rounds - 1).get(CombatOutcome.COMBAT_CONTINUES);
        int unit1_destroyed_overall = 0;
        int unit1_fled_overall = 0;
        int unit2_fled_overall = 0;
        int unit2_destroyed_overall = 0;
        int mutual_destruction_overall = 0;
        //for results of each single round
        for (int i = 0; i < rounds; i++) {
            //add to overall values
            Map<CombatOutcome, Integer> currentRound = allOutcomes.get(i);
            unit1_destroyed_overall += currentRound.get(CombatOutcome.UNIT1_DESTROYED);
            unit1_fled_overall += currentRound.get(CombatOutcome.UNIT1_FLED);
            unit2_fled_overall += currentRound.get(CombatOutcome.UNIT2_FLED);
            unit2_destroyed_overall += currentRound.get(CombatOutcome.UNIT2_DESTROYED);
            mutual_destruction_overall += currentRound.get(CombatOutcome.MUTUAL_DESTRUCTION);
            //display this round
            TextView unit1_destroyed = list_results_unit1_destroyed.get(i);
            unit1_destroyed.setLayoutParams(new LinearLayout.LayoutParams(unit1_destroyed_overall * 3 ,30));
            TextView unit1_fled = list_results_unit1_fled.get(i);
            unit1_fled.setLayoutParams(new LinearLayout.LayoutParams(unit1_fled_overall * 3,30));
            TextView combat_continues = list_results_combat_continues.get(i);
            int combatContinues = simulatorService.getSIMULATIONS() - (unit1_destroyed_overall + unit1_fled_overall + unit2_destroyed_overall + unit2_fled_overall + mutual_destruction_overall);
            combat_continues.setLayoutParams(new LinearLayout.LayoutParams(combatContinues * 3,30));
            TextView unit2_fled = list_results_unit2_fled.get(i);
            unit2_fled.setLayoutParams(new LinearLayout.LayoutParams(unit2_fled_overall * 3,30));
            TextView unit2_destroyed = list_results_unit2_destroyed.get(i);
            unit2_destroyed.setLayoutParams(new LinearLayout.LayoutParams(unit2_destroyed_overall * 3,30));
            TextView mutual_destruction = list_results_mutual_destruction.get(i);
            mutual_destruction.setLayoutParams(new LinearLayout.LayoutParams(mutual_destruction_overall * 3,30));
        }
        //display overall values
        bar_unit1_destroyed_overall.setLayoutParams(new LinearLayout.LayoutParams(unit1_destroyed_overall * 3,30));
        bar_unit1_fled_overall.setLayoutParams(new LinearLayout.LayoutParams(unit1_fled_overall * 3,30));
        bar_combat_continues_overall.setLayoutParams(new LinearLayout.LayoutParams(combat_continues_overall * 3,30));
        bar_unit2_destroyed_overall.setLayoutParams(new LinearLayout.LayoutParams(unit2_destroyed_overall * 3,30));
        bar_unit2_fled_overall.setLayoutParams(new LinearLayout.LayoutParams(unit2_fled_overall * 3,30));
        bar_mutual_destruction_overall.setLayoutParams(new LinearLayout.LayoutParams(mutual_destruction_overall * 3,30));
        numbers_unit1_destroyed_overall.setText(unit1_destroyed_overall + "");
        numbers_unit1_fled_overall.setText(unit1_fled_overall + "");
        numbers_combat_continues_overall.setText(combat_continues_overall + "");
        numbers_unit2_fled_overall.setText(unit2_fled_overall + "");
        numbers_unit2_destroyed_overall.setText(unit2_destroyed_overall + "");
        numbers_mutual_destruction_overall.setText(mutual_destruction_overall + "");
    }

    //launching activities
    private void repeatUnitCreation() {
        Intent intent = new Intent(this, MainActivity.class);
        gson = new Gson();
        String serviceJson = gson.toJson(simulatorService);
        intent.putExtra("simulatorService", serviceJson);
        startActivity(intent);
    }


}
