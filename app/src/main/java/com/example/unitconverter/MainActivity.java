package com.example.unitconverter;

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

    private RadioButton m_temperatureQuantity, m_lengthQuantity, m_weightQuantity;

    //These variables Inflate Spinners with units depending on which unit Radio is selected
    private Spinner m_fromUnit, m_toUnit;

    //These variables keep track of which one unit which is converted to another unit
    //integer Numbers are used which keep track of position of units in spinner
    //so that approperiate calculation can be done
    private int m_firstUnit, m_secondUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_temperatureQuantity = findViewById(R.id.temperature_quantity_radio);
        m_lengthQuantity = findViewById(R.id.length_quantity_radio);
        m_weightQuantity = findViewById(R.id.weight_quantity_radio);
        m_fromUnit = findViewById(R.id.from_unit_type);
        m_toUnit = findViewById(R.id.to_unit_type);

        //Default filling of Spinner
        fillSpinnerWithUnits(R.array.temperature_units);

        m_temperatureQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillSpinnerWithUnits(R.array.temperature_units);
            }
        });

        m_lengthQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillSpinnerWithUnits(R.array.length_units);
            }
        });

        m_weightQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillSpinnerWithUnits(R.array.weight_units);
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
                m_firstUnit = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        m_toUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m_secondUnit = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //The following function takes input and calculates temperature according to user selection
    //Numbers were used to keep track of units, check comment to see what unit that number represents
    private double getTemperatureConversion(double input){
        double converted = input;

        // 0 = Celsius, 1 = Fahrenheit, 2 = Kelvin

        Log.v("Message", "Double: " + input);
        if(m_firstUnit == 0 /*Celsius*/){
            if(m_secondUnit == 1 /*Fahrenheit*/){ converted = (input*9/5) + 32; }
            else if(m_secondUnit == 2 /*Kelvin*/){ converted = input + 273.15; }
        }
        else if(m_firstUnit == 1 /*Fahrenheit*/){
            if(m_secondUnit == 0 /*Celsius*/){ converted = (input - 32) * 5/9; }
            else if(m_secondUnit == 2 /*Kelvin*/){ converted = (input - 32) * 5/9 + 273.15; }
        }
        else if(m_firstUnit == 2 /*Kelvin*/) {
            if(m_secondUnit == 0 /*Celsius*/){ converted = input - 273.15; }
            else if(m_secondUnit == 1 /*Fahrenheit*/){
                converted = (input - 273.15) * 9/5 + 32;
            }
        }

        return converted;
    }

    //The following function takes input and calculates temperature according to user selection
    //Numbers were used to keep track of units, check comment to see what unit that number represents
    private double getLengthConversion(double input){
        double converted = input;

        // 0 = Centimeters, 1 = Inches, 2 = Meters, 3 = Kilometers, 4 = Miles

        if(m_firstUnit == 0 /*Centimeters*/){
            if(m_secondUnit == 1 /*Inches*/){ converted = input / 2.54; }
            else if(m_secondUnit == 2 /*Meters*/){ converted = input / 100.0; }
            else if(m_secondUnit == 3 /*Kilometers*/) { converted = input / 100000.0; }
            else if(m_secondUnit == 4 /*Miles*/){ converted = input / 160900; }
        }
        else if(m_firstUnit == 1 /*Inches*/){
            if(m_secondUnit == 0 /*Centimeters*/){ converted = input * 2.54; }
            else if(m_secondUnit == 2 /*Meters*/){ converted = input / 39.37; }
            else if(m_secondUnit == 3 /*Kilometers*/){ converted = input / 39370; }
            else if(m_secondUnit == 4 /*Miles*/){ converted = input / 63360; }
        }
        else if(m_firstUnit == 2 /*Meters*/){
            if(m_secondUnit == 0 /*Centimeters*/){ converted = input * 100; }
            else if(m_secondUnit == 1 /*Inches*/){ converted = input * 39.37; }
            else if(m_secondUnit == 3 /*Kilometers*/){ converted = input / 1000; }
            else if(m_secondUnit == 4 /*Miles*/){ converted = input / 1609; }
        }
        else if(m_firstUnit == 3 /*Kilometers*/){
            if(m_secondUnit == 0 /*Centimeters*/){ converted = input * 100000; }
            else if(m_secondUnit == 1 /*Inches*/){ converted = input * 39370; }
            else if(m_secondUnit == 2 /*Meters*/){ converted = input * 1000; }
            else if(m_secondUnit == 4 /*Miles*/){ converted = input / 1.609; }
        }
        else if(m_firstUnit == 4 /*Miles*/){
            if(m_secondUnit == 0 /*Centimeters*/){ converted = input * 160934.4; }
            else if(m_secondUnit == 1 /*Inches*/){ converted = input * 63360; }
            else if(m_secondUnit == 2 /*Meters*/){ converted = input * 1609; }
            else if(m_secondUnit == 3 /*Kilometers*/){ converted = input * 1.609; }
        }

        return converted;
    }

    //This function handles conversion of weight units
    private double getWeightConversion(double input){
        double converted = input;

        // 0 = Milligrams, 1 = Grams, 2 = Kilograms, 3 = Pounds, 4 = Tonnes
        if(m_firstUnit == 0 /*Milligrams*/){
            if(m_secondUnit == 1 /*Grams*/){ converted = input / 1000; }
            else if(m_secondUnit == 2 /*Kilograms*/){ converted = input / 1000000; }
            else if(m_secondUnit == 3 /*Pounds*/){ converted = input / 453600; }
            else if(m_secondUnit == 4 /*Tonnes*/){ converted = input / 907184740; }
        }
        else if(m_firstUnit == 1 /*Grams*/){
            if(m_secondUnit == 0 /*Milligrams*/){ converted = input * 1000; }
            else if(m_secondUnit == 2 /*Kilograms*/){ converted = input / 1000; }
            else if(m_secondUnit == 3 /*Pounds*/){ converted = input / 453.6; }
            else if(m_secondUnit == 4 /*Tonnes*/){ converted = input /  907184.74; }
        }
        else if(m_firstUnit == 2 /*Kilograms*/){
            if(m_secondUnit == 0 /*Milligrams*/){ converted = input * 1000000; }
            else if(m_secondUnit == 1 /*Grams*/){ converted = input * 1000; }
            else if(m_secondUnit == 3 /*Pounds*/){ converted = input * 2.205; }
            else if(m_secondUnit == 4 /*Tonnes*/){ converted = input / 1000; }
        }
        else if(m_firstUnit == 3 /*Pounds*/){
            if(m_secondUnit == 0 /*Milligrams*/){ converted = input * 453600; }
            else if(m_secondUnit == 1 /*Grams*/){ converted = input * 453.592; }
            else if(m_secondUnit == 2 /*Kilograms*/){ converted = input / 2.205; }
            else if(m_secondUnit == 4 /*Tonnes*/){ converted = input / 2205; }
        }
        else if(m_firstUnit == 4 /*Tonnes*/){
            if(m_secondUnit == 0 /*Milligrams*/){ converted = input * 1000000000; }
            else if(m_secondUnit == 1 /*Grams*/){ converted = input * 1000000; }
            else if(m_secondUnit == 2 /*Kilograms*/){ converted = input * 1000; }
            else if(m_secondUnit == 3 /*Pounds*/){ converted = input * 2204.62; }
        }

        return converted;
    }

    //This Function is called Every time Calculate Button is pressed
    public void calculate(View view){
        EditText userInput = findViewById(R.id.user_input_text);
        double input = convertUserInputToDouble(userInput.getText().toString());

        String result = "Select Units";

        if(m_temperatureQuantity.isChecked()){
            result = "" + getTemperatureConversion(input);
        }
        else if(m_lengthQuantity.isChecked()){
            result = "" + getLengthConversion(input);
        }
        else if(m_weightQuantity.isChecked()){
            result = "" + getWeightConversion(input);
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