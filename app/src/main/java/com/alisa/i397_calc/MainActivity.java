package com.alisa.i397_calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private EditText screen;
    private float numberBefore; //stores number that wqas on screen before operator pressed
    private String operation;
    private ButtonClickListener btnClick;
    private String lastOperand = "x";
    private float intermResult = 0; // holds the result of last "equal" operation


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (EditText) findViewById(R.id.txtResult);
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

    public void mMath(String operator){

        numberBefore = Float.parseFloat (String.valueOf(screen.getText()));
        operation = operator;
        screen.setText("0");
    }


    public void getKeyboard(String number){

        String currentScreen = screen.getText().toString();
         if (currentScreen.equals("0")){
             currentScreen = "";
         }
         if ( lastOperand.equals("=")){
             currentScreen = "";
             numberBefore = intermResult;
             lastOperand = "x";

         }
        currentScreen += number;
        screen.setText(currentScreen);
    }

    public void mResult (){

        float numberAfter =  Float.parseFloat (screen.getText().toString());
        float result =0;

        if (operation.equals("+")){
            result = numberBefore + numberAfter;

        }
        if (operation.equals("-")){
            result = numberBefore - numberAfter;

        }
        if (operation.equals("*")){
            result = numberBefore * numberAfter;
        }
        if (operation.equals("/")){
            result = numberBefore / numberAfter;
        }
        intermResult = result;
        screen.setText(String.valueOf(result));
    }



    private class ButtonClickListener implements OnClickListener{
        public void onClick (View v){
            switch (v.getId()){
                case R.id.buttonC:
                    screen.setText ("0");
                    numberBefore = 0;
                    operation = "";
                    break;
                case R.id.buttonSum:
                    mMath ("+");
                    lastOperand= "+";
                    break;
                case R.id.buttonSub:
                    mMath ("-");
                    lastOperand= "-";
                    break;
                case R.id.buttonMlt:
                    mMath ("*");
                    lastOperand= "*";
                    break;
                case R.id.buttonDiv:
                    mMath("/");
                    lastOperand = "/";
                    break;
                case R.id.buttonEq:
                    mResult();
                    lastOperand= "=";
                    break;
                default:
                    String number = ((Button) v). getText().toString();
                    getKeyboard(number);
                    break;



            }
        }
    }


}


