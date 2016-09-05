package com.sudoku.gfm13.sudokusolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gfm13 on 8/9/2016.
 */
public class Group {
    public List<Square> Members;

    public Group (List<Square> boardState, int minX, int maxX, int minY, int maxY) {
        Members = new ArrayList<Square>(9);
        for (Square square : boardState){
            if (square.X >= minX && square.X <= maxX && square.Y >= minY && square.Y <= maxY) {
                Members.add(square);
            }
        }
    }
}

