package com.example.unitconverted;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioButton m_temperatureUnit, m_lengthUnit;
    private Spinner m_fromUnit, m_toUnit;

    //These variables keep track of which one unit which is converted to another unit
    private String m_firstUnit, m_secondUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_temperatureUnit = findViewById(R.id.temperature_unit_radio);
        m_lengthUnit = findViewById(R.id.length_unit_radio);
        m_fromUnit = findViewById(R.id.from_unit_type);
        m_toUnit = findViewById(R.id.to_unit_type);

        m_temperatureUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillSpinnerWithUnits(R.array.temperature_units);
            }
        });

        m_lengthUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillSpinnerWithUnits(R.array.length_units);
            }
        });
    }

    //This function will set sub_units of Different Units in SpinnerViews
    private void fillSpinnerWithUnits(int unitStringArrayResId){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, unitStringArrayResId, R.layout.my_spinner_style
            );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        m_fromUnit.setAdapter(adapter);
        m_toUnit.setAdapter(adapter);

        m_fromUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m_firstUnit = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        m_toUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m_secondUnit = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //The following function takes input and calculates temperature according to user selection
    private double getTemperatureConversion(double input){
        double converted = input;
        Log.v("Message", "Double: " + input);
        if(m_firstUnit.equals("Celsius")){
            if(m_secondUnit.equals("Fahrenheit")){ converted = (input*9/5) + 32; }
            else if(m_secondUnit.equals("Kelvin")){ converted = input + 273.15; }
        }
        else if(m_firstUnit.equals("Fahrenheit")){
            if(m_secondUnit.equals("Celsius")){ converted = (input - 32) * 5/9; }
            else if(m_secondUnit.equals("Kelvin")){ converted = (input - 32) * 5/9 + 273.15; }
        }
        else {
            if(m_secondUnit.equals("Celsius")){ converted = input - 273.15; }
            else if(m_secondUnit.equals("Fahrenheit")){ converted = (input - 237.15) * 9/5 + 32; }
        }
        return converted;
    }

    //This Function is called Every time Calculate Button is pressed
    public void calculate(View view){
        EditText userInput = findViewById(R.id.user_input_text);
        double input = convertUserInputToDouble(userInput.getText().toString());

        String result = "Select Units";

        if(m_temperatureUnit.isChecked()){
            result = "" + getTemperatureConversion(input);
        }
        else if(m_lengthUnit.isChecked()){
            result = "Length Units";
        }

        displayResult(result);
    }

    //This function displays result of calculated quantity
    private void displayResult(String result){
        TextView resultView = findViewById(R.id.display_result_view);
        resultView.setText(result);
    }

    //This function will convert user input string to double and returns it
    private double convertUserInputToDouble(String userInput){
        double input = 0.0;
        if(!userInput.isEmpty()){
            input = Double.parseDouble(userInput);
        }

        return input;
    }
}