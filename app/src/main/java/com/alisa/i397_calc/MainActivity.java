package com.alisa.i397_calc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.EnumMap;

import static com.alisa.i397_calc.R.string.dflt;
import static com.alisa.i397_calc.R.string.error;

public class MainActivity extends AppCompatActivity {

    //screen
    private EditText screen;

    //  operation buttons
    private Button btnSum;
    private Button btnSub;
    private Button btnMlt;
    private Button btnDiv;



    // map for storing user input
    private EnumMap<ExpElement, Object> input = new EnumMap<ExpElement, Object>(ExpElement.class);

    private MathOperation operation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (EditText) findViewById(R.id.txtResult);

        if (savedInstanceState != null){
            input.put(ExpElement.FIRST_OPERAND, savedInstanceState.getString("fop"));
            input.put(ExpElement.OPERATOR, savedInstanceState.getString("oper"));
            input.put(ExpElement.SECOND_OPERAND, savedInstanceState.getString("sop"));
            operation = (MathOperation) savedInstanceState.getSerializable("mathop");

        }
        screen.setText(dflt);
        btnSum = (Button) findViewById(R.id.buttonSum);
        btnSub = (Button) findViewById(R.id.buttonSub);
        btnMlt = (Button) findViewById(R.id. buttonMlt);
        btnDiv = (Button) findViewById(R.id. buttonDiv);





        btnSum.setTag(MathOperation.SUM);
        btnSub.setTag(MathOperation.SUB);
        btnMlt.setTag(MathOperation.MLT);
        btnDiv.setTag(MathOperation.DIV);


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        String fop =  (String)input.get(ExpElement.FIRST_OPERAND);
        String oper =  (String)input.get(ExpElement.OPERATOR);
        String sop =  (String )input.get(ExpElement.SECOND_OPERAND);
        savedInstanceState.putString ("fop", fop);
        savedInstanceState.putString ("oper", oper);
        savedInstanceState.putString("sop", sop);
        savedInstanceState.putSerializable("mathop", operation);

    }

    public void buttonClick (View view){
        switch (view.getId()){
            // any math operation button
            case R.id.buttonSum:
            case R.id.buttonSub:
            case R.id.buttonMlt:
            case R.id.buttonDiv: {

                operation = (MathOperation) view.getTag(); //returns SUM, SUB, MLT or DIV

                if (!input.containsKey(ExpElement.OPERATOR)) {
                    if (!input.containsKey(ExpElement.FIRST_OPERAND)){
                        input.put(ExpElement.FIRST_OPERAND, screen.getText()); // if first operand isn't assigned, assign current screen value
                    }
                    input.put(ExpElement.OPERATOR, operation); // if operator isn't assigned, assign current
                } else if (!input.containsKey(ExpElement.SECOND_OPERAND)) {
                    doTheMath();
                }
                break;
            }

            case R.id.buttonC:{
                screen.setText(dflt);
                input.clear();
                break;
            }

            case R.id.buttonEq:{
                if (input.containsKey(ExpElement.FIRST_OPERAND) && input.containsKey(ExpElement.OPERATOR)) {
                    doTheMath();
                    input.remove(ExpElement.OPERATOR);
                }
                break;
            }

            // all numeric buttons
            default:{
                Button b = (Button)view;
                // in case of new input, or if operand was just stored in memory, start from scratch
                if (screen.getText().toString().equals("0") ||
                            (input.containsKey(ExpElement.FIRST_OPERAND) &&
                            getDouble(screen.getText())== getDouble (input.get(ExpElement.FIRST_OPERAND))))
                    screen.setText(( b.getText()).toString());

                else {
                    // is there is any input that is not stored as operand, continue concatenating to current string
                    screen.setText(screen.getText() + (b.getText()).toString());
                }
            }
        }
    }

    private void doTheMath() {
        input.put(ExpElement.SECOND_OPERAND, screen.getText()); // assign current screen value to second operand
        calculate(); // do the math
        input.put(ExpElement.OPERATOR, operation);  // stores operator of last clicked operation button
        input.remove(ExpElement.SECOND_OPERAND); // clears "used" operand
    }

    private void calculate() {
        MathOperation previousOperand = (MathOperation) input.get (ExpElement.OPERATOR);
        double result;
        double a = getDouble(input.get(ExpElement.FIRST_OPERAND));
        double b = getDouble(input.get(ExpElement.SECOND_OPERAND));

        switch (previousOperand){
            case SUM:
                result = a+b;
                break;
            case SUB:
                 result = a - b;
                break;
            case MLT:
                result = a*b;
                break;
            case DIV:
                result= a/b;
                break;
            default:
                result = 0;
        }

        // if result is integer, cut the ".0" part
        if (result % 1 == 0){
            screen.setText(String.valueOf((int) result));
        } else {
            screen.setText(String.valueOf(result));
        }

        input.put(ExpElement.FIRST_OPERAND, result); // replace first operand with current result
    }

    private double getDouble(Object screenInput) {
        double result;
        try{
           result = Double.valueOf(screenInput.toString()).doubleValue();
        } catch (Exception e){
            screen.setText(error);
            e. printStackTrace();
            result = 0;
        }
        return result;


    }


}






/*

    private EditText screen; // displays current inout and results
    private double operand1; //stores number that was on screen before operator btn pressed
    private double operand2;
    private String operation; // hold current operator +-*//*

    private ButtonClickListener btnClick;
    private String lastOperator = "x"; // previousOperator, stored until is replased by another operator +-*//*
=
    private double intermResult = 0; // holds the result of last "equal" operation - broken, now. check!
    private String lastSymbol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (EditText) findViewById(R.id.txtResult);

        if (savedInstanceState != null){
            operand1 =savedInstanceState.getDouble("operand1");
            operand2 =savedInstanceState.getDouble("operand2");
            operation = savedInstanceState.getString("operation");
            lastOperator = savedInstanceState.getString("lastOperator");
            intermResult = savedInstanceState.getDouble("intermResult");
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
        savedInstanceState.putDouble("operand1", operand1);
        savedInstanceState.putDouble("operand2", operand2);
        savedInstanceState.putString("operation", operation);
        savedInstanceState.putString("lastOperator", lastOperator);
        savedInstanceState.putDouble("intermResult", intermResult);
        savedInstanceState.putString ("lastSymbol", lastSymbol);
    }



    public void mMath(String operator){

        operand1 = Double.parseDouble (String.valueOf(screen.getText()));
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

        operand2 =  Double.parseDouble (screen.getText().toString());
        double result =0;

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
}*/
