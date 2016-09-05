package com.sudoku.gfm13.sudokusolver.Commands;

import com.sudoku.gfm13.sudokusolver.Group;
import com.sudoku.gfm13.sudokusolver.Square;

/**
 * Created by gfm13 on 8/30/2016.
 */
public class UniqueCandidatesCommand implements GroupManipulatorCommand {

    public boolean RequiresRefresh() { return true; }

    public boolean Execute (Group group)
    {
        boolean progressMade = false;

        for (int i = 0; i < 10; ++i)
        {
            int numberOfCandidate = 0;
            Square uniqueCandidate = null;
            for (Square square : group.Members) {
                if (square.PossibleValues.contains(i))
                {
                    numberOfCandidate++;
                    uniqueCandidate = square;
                }
            }

            if (numberOfCandidate == 1) {
                progressMade = true;
                uniqueCandidate.SubmitValue(i);
            }
        }

        return  progressMade;
    }
}
