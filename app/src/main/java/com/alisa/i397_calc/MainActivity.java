package com.alisa.i397_calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private EditText screen; // displays current inout and results
    private float operand1; //stores number that was on screen before operator btn pressed
    private float operand2;
    private String operation; // hold current operator +-*/
    private ButtonClickListener btnClick;
    private String lastOperator = "x"; // previousOperator, stored until is replased by another operator +-*/=
    private float intermResult = 0; // holds the result of last "equal" operation - broken, now. check!
    private String lastSymbol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (EditText) findViewById(R.id.txtResult);

        if (savedInstanceState != null){
            operand1 =savedInstanceState.getFloat("operand1");
            operand2 =savedInstanceState.getFloat("operand2");
            operation = savedInstanceState.getString("operation");
            lastOperator = savedInstanceState.getString("lastOperator");
            intermResult = savedInstanceState.getFloat("intermResult");
            lastSymbol = savedInstanceState.getString ("lastSymbol");

        }
        screen.setText("0");
        btnClick = new ButtonClickListener();
        int idList[] = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                        R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                        R.id.buttonSub, R.id.buttonSum, R.id.buttonMlt, R.id.buttonDiv,
                        R.id.buttonEq, R.id.buttonC};

        for(int id : idList){
            View v = (View)findViewById(id);
            v.setOnClickListener(btnClick);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putFloat("operand1", operand1);
        savedInstanceState.putFloat("operand2", operand2);
        savedInstanceState.putString("operation", operation);
        savedInstanceState.putString("lastOperator", lastOperator);
        savedInstanceState.putFloat("intermResult", intermResult);
        savedInstanceState.putString ("lastSymbol", lastSymbol);
    }



    public void mMath(String operator){

        operand1 = Float.parseFloat (String.valueOf(screen.getText()));
        operation = operator;

    }


    public void getKeyboard(String number){

        String currentScreen = screen.getText().toString();
         if (currentScreen.equals("0") || lastSymbol.equals("+")|| lastSymbol.equals("-")|| lastSymbol.equals("*")|| lastSymbol.equals("/")){
             currentScreen = "";
         }
         if ( lastOperator.equals("=")){
             currentScreen = "";
             operand1 = intermResult;
             lastOperator = "x";
         }
        currentScreen += number;
        screen.setText(currentScreen);
    }


    public void mResult (){

        operand2 =  Float.parseFloat (screen.getText().toString());
        float result =0;

        lastOperator = operation;
        if (operation.equals("+")){
            result = operand1 + operand2;
        }
        if (operation.equals("-")){
            result = operand1 - operand2;
        }
        if (operation.equals("*")){
            result = operand1 * operand2;
        }
        if (operation.equals("/")){
            result = operand1 / operand2;
        }

        intermResult = result;
        operand1 = result;
        operand2 = 0;
        screen.setText(String.valueOf(result));
    }


    private class ButtonClickListener implements OnClickListener{

        public void onClick (View v){

            switch (v.getId()){
                case R.id.buttonC:
                    screen.setText ("0");
                    operand1 = 0;
                    operand2 = 0;
                    operation = "";
                    intermResult = 0;
                    lastOperator = "x";
                    intermResult = 0;
                    lastSymbol = "";
                    break;
                case R.id.buttonSum:
                    mMath ("+");
                    break;
                case R.id.buttonSub:
                    mMath ("-");
                    break;
                case R.id.buttonMlt:
                    mMath ("*");
                    break;
                case R.id.buttonDiv:
                    mMath("/");
                    break;
                case R.id.buttonEq:
                    mResult();
                    lastOperator = "=";
                    break;
                default:
                    String number = ((Button) v). getText().toString();
                    getKeyboard(number);
                    break;
            }
            lastSymbol= ((Button) v). getText().toString();
        }
    }
}