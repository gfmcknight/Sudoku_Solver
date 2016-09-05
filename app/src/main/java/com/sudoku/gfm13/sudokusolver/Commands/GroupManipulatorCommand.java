package com.sudoku.gfm13.sudokusolver.Commands;

import com.sudoku.gfm13.sudokusolver.Group;

import java.util.List;

/**
 * Created by gfm13 on 8/30/2016.
 */
public interface GroupManipulatorCommand {
    //Commands return true if their execution has lead to progression in the board state
    public boolean Execute(Group group);
    public boolean RequiresRefresh();
}
