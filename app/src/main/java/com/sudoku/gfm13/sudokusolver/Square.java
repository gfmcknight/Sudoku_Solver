package com.sudoku.gfm13.sudokusolver;


import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by gfm13 on 8/8/2016.
 */
public class Square {
    public int X;
    public int Y;
    public int Value;
    public List<Integer> PossibleValues;


    public Square (int x, int y, int value) {
        X = x;
        Y = y;
        Value = value;
        PossibleValues = new LinkedList();
        if (value == 0){
            for (int i = 1; i < 10; i++)
            {
                PossibleValues.add(i);
            }
        }
    }

    public boolean SolvedSquare() {
        return Value != 0;
    }
    //Returns true if the elimination has helped progress the board state
    public boolean RemoveCandidate(int candidate)
    {
        if (PossibleValues.contains(candidate)) {
            PossibleValues.remove(PossibleValues.indexOf(candidate));
            return true;
        }
        return false;
    }

    public void SubmitValue(int value)
    {
        if (PossibleValues.contains(value) && !SolvedSquare()) {
            Value = value;
            PossibleValues.clear();
        }
    }
}
