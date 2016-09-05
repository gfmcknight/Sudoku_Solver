package com.sudoku.gfm13.sudokusolver.Commands;

import com.sudoku.gfm13.sudokusolver.Group;
import com.sudoku.gfm13.sudokusolver.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 9/4/2016.
 */
public class BlockRowInteractionCommand implements GroupManipulatorCommand{
    List<Group> groups;



    public BlockRowInteractionCommand (List<Group> allGroups){
        groups = allGroups;
    }

    public boolean RequiresRefresh() { return false;}

    public boolean Execute (Group group){
        boolean progressMade = false;

        for (int i = 1; i < 10; i++) {
            List<Square> squaresWithCandidate = new ArrayList<Square>();
            for (Square square : group.Members) {
                if (square.PossibleValues.contains(i)){
                    squaresWithCandidate.add(square);
                }
            }

            if (squaresWithCandidate.size() > 1 && squaresWithCandidate.size() <= 3) {
                for (Group alternateGroup : groups) {
                    if (alternateGroup != group && alternateGroup.Members.containsAll(squaresWithCandidate)) {
                        for (Square square : alternateGroup.Members) {
                            if (!squaresWithCandidate.contains(square)){
                                progressMade = square.RemoveCandidate(i) || progressMade;
                            }
                        }
                    }
                }

            }
        }

        return progressMade;
    }

}
