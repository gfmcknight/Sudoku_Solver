package com.sudoku.gfm13.sudokusolver.Commands;

import com.sudoku.gfm13.sudokusolver.Group;
import com.sudoku.gfm13.sudokusolver.Square;

/**
 * Created by gfm13 on 8/30/2016.
 */
public class SoleCandidatesCommand implements GroupManipulatorCommand {

    public boolean RequiresRefresh() { return true; }

    public boolean Execute (Group group)
    {
        boolean progressMade = false;

        for (Square square : group.Members) {
            if (square.PossibleValues.size() == 1 && !square.SolvedSquare()){
                square.SubmitValue(square.PossibleValues.get(0));
                progressMade = true;
            }
        }

        return progressMade;
    }
}
