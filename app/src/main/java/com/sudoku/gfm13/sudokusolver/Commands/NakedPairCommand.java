package com.sudoku.gfm13.sudokusolver.Commands;

import com.sudoku.gfm13.sudokusolver.Group;
import com.sudoku.gfm13.sudokusolver.Square;

/**
 * Created by gfm13 on 8/31/2016.
 */
public class NakedPairCommand implements GroupManipulatorCommand {
    public boolean RequiresRefresh(){
        return false;
    }

    public boolean Execute(Group group){
        boolean progressMade = false;

        for (Square squareA : group.Members) {
            if (squareA.PossibleValues.size() == 2)
            {
                for (Square squareB: group.Members) {
                    if (squareA != squareB && squareB.PossibleValues.size() == 2
                            && squareB.PossibleValues.containsAll(squareA.PossibleValues)) {
                        for (Square squareC : group.Members) {
                            if (squareC != squareA && squareC != squareB) {
                                progressMade = squareC.RemoveCandidate(squareA.PossibleValues.get(0)) || progressMade;
                                progressMade = squareC.RemoveCandidate(squareA.PossibleValues.get(1)) || progressMade;
                            }
                        }
                    }
                }
            }
        }

        return progressMade;
    }
}
