package com.sudoku.gfm13.sudokusolver.Commands;

import com.sudoku.gfm13.sudokusolver.Group;
import com.sudoku.gfm13.sudokusolver.Square;

/**
 * Created by gfm13 on 8/30/2016.
 */
public class EliminateImpossibleCommand implements GroupManipulatorCommand {

    public boolean RequiresRefresh()
    {
        return false;
    }

    public boolean Execute (Group group){
        boolean progressMade = false;

        for (Square squareA : group.Members) {
            if (squareA.SolvedSquare()) {
                for (Square squareB: group.Members) {
                    progressMade = squareB.RemoveCandidate(squareA.Value) || progressMade;
                }
            }
        }
        return progressMade;
    }

}
