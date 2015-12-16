package com.alisa.i397_nicecalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.EnumMap;

import static com.alisa.i397_nicecalc.R.string.dflt;
import static com.alisa.i397_nicecalc.R.string.error;

public class MainActivity extends AppCompatActivity {

    //screen
    private EditText screen;

    //  operation buttons
    private Button btnSum;
    private Button btnSub;
    private Button btnMlt;
    private Button btnDiv;
    private Button btnEq;

    double value = 0;
    String operation = "";
    boolean operationPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (EditText) findViewById(R.id.txtResult);

        if (savedInstanceState != null){
            value =savedInstanceState.getDouble("value");
            operation = savedInstanceState.getString("operation");
            operationPressed = savedInstanceState.getBoolean("pressed");
        }
        screen.setText(dflt);
        btnSum = (Button) findViewById(R.id.buttonSum);
        btnSub = (Button) findViewById(R.id.buttonSub);
        btnMlt = (Button) findViewById(R.id.buttonMlt);
        btnDiv = (Button) findViewById(R.id.buttonDiv);
        btnEq = (Button) findViewById(R.id.buttonEq);




    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("value", value);
        savedInstanceState.putString("operation", operation);
        savedInstanceState.putBoolean("pressed", operationPressed);

    }

    public void buttonClick (View view){
        switch (view.getId()){
            // any math operation button
            case R.id.buttonSum:
            case R.id.buttonSub:
            case R.id.buttonMlt:
            case R.id.buttonDiv: {
                Button b = (Button)view;

                 if (value != 0){
                     btnEq.performClick();
                     operationPressed = true;
                     operation = b.getText().toString();
                 } else {
                     operationPressed = true;
                     operation = b.getText().toString();
                     value = getDouble(screen.getText());
                 }


               break;
            }


            case R.id.buttonC:{
                screen.setText(dflt);
                value = 0;
                operation = "";
                operationPressed = false;


                break;
            }

            case R.id.buttonEq:{
                switch (operation){

                    case "+":{
                        screen.setText (String.valueOf(value + getDouble (screen.getText())));
                        break;
                    }
                    case "-":{
                        screen.setText (String.valueOf(value - getDouble (screen.getText())));
                        break;
                    }
                    case "*":{
                        screen.setText (String.valueOf(value * getDouble (screen.getText())));
                        break;
                    }
                    case "/":{
                        screen.setText (String.valueOf(value / getDouble (screen.getText())));
                        break;
                    }
                    default:
                        break;
                }// end eq switch
                value = getDouble(screen.getText());
                operation = "";
                operationPressed = true;



                break;
            }

            // all numeric buttons
            default:{

                Button b = (Button)view;

                // in case of new input, start from scratch
                if ((String.valueOf(screen.getText()).equals(getResources().getString(R.string.dflt))) || (operationPressed == true)) {
                    operationPressed = false;
                    screen.setText("");
                }
                screen.setText(screen.getText() + (b.getText()).toString());


            }
        }// end button switch
    }



    private double getDouble(Object screenInput) {
        double result;
        try{
           result = Double.valueOf(screenInput.toString());
        } catch (Exception e){
            screen.setText(error);
            e. printStackTrace();
            result = 0;
        }
        return result;


    }


}




