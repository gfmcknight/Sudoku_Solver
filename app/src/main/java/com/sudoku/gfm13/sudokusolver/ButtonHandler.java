package com.sudoku.gfm13.sudokusolver;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.AlignmentSpan;
import android.widget.Button;

/** 
 * Created by gfm13 on 8/30/2016.
 */
public class ButtonHandler {
    public int X;
    public int Y;
    public int Value;
    public String ButtonText;
    public Button Handled;

    Drawable standardBackground;
    Drawable selectedBackground;

    public ButtonHandler(int x, int y, Button button, Drawable selectedBackground)
    {
        X = x;
        Y = y;
        Value = 0;
        ButtonText = "";
        Handled = button;
        standardBackground = button.getBackground();
        this.selectedBackground = selectedBackground;
    }

    public void CycleValue ()
    {
        SetValue((Value + 1) % 10);
    }

    public void SetValue (int newValue)
    {
        Value = newValue;
        if (Value > 0) {
            ButtonText = Integer.toString(Value);
        }
        else {
            ButtonText = "";
        }
        Handled.setText(ButtonText);
        Handled.setTextColor(Color.BLACK);
        Handled.setBackground(standardBackground);
    }

    public void SuggestValue(int value){
        if (Value == 0 && value > 0) {
            Handled.setText(Integer.toString(value));
            Handled.setTextColor(Color.GRAY);
        }
    }

    public  void Select(){
        Handled.setTextColor(Color.BLUE);
        Handled.setBackground(selectedBackground);
    }

    public void Deselect(){
        if (Value > 0) {
            Handled.setTextColor(Color.BLACK);
        }
        else {
            Handled.setTextColor(Color.DKGRAY);
        }
        Handled.setBackground(standardBackground);
    }
}
