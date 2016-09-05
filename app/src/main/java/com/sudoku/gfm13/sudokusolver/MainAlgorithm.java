package com.sudoku.gfm13.sudokusolver;

import com.sudoku.gfm13.sudokusolver.Commands.BlockRowInteractionCommand;
import com.sudoku.gfm13.sudokusolver.Commands.EliminateImpossibleCommand;
import com.sudoku.gfm13.sudokusolver.Commands.GroupManipulatorCommand;
import com.sudoku.gfm13.sudokusolver.Commands.NakedPairCommand;
import com.sudoku.gfm13.sudokusolver.Commands.SoleCandidatesCommand;
import com.sudoku.gfm13.sudokusolver.Commands.UniqueCandidatesCommand;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gfm13 on 8/8/2016.
 */
public class MainAlgorithm {

    public List<Square> BoardState;
    public List<Group> Groups;
    private MainAlgorithm solution;

    public MainAlgorithm(int[][] boardState) {
        BoardState = new ArrayList<>(81);
        Groups = new ArrayList<>(27);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                BoardState.add(new Square(i, j, boardState[i][j]));
            }
        }

        for (int i = 0; i < 9; i++) {
            Groups.add(new Group (BoardState, i, i, 0, 8));
            Groups.add(new Group (BoardState, 0, 8, i, i));
            Groups.add(new Group (BoardState, minimumXForBox(i), minimumXForBox(i) + 2,
                    minimumYForBox(i), minimumYForBox(i) + 2));
        }
    }

    public boolean AttemptSolve()
    {
        boolean progressMade = true;
        while (progressMade) {
            progressMade = false;
            progressMade = enactCommand(new EliminateImpossibleCommand()) || progressMade;
            if (checkForInvalidSquares()) {
                return false;
            }
            progressMade = enactCommand(new SoleCandidatesCommand()) || progressMade;
            progressMade = enactCommand(new UniqueCandidatesCommand()) || progressMade;
            progressMade = enactCommand(new NakedPairCommand()) || progressMade;
            progressMade = enactCommand(new BlockRowInteractionCommand(Groups)) || progressMade;
        }

        Square guessSquare = getLeastOptionsSquare();
        if (guessSquare == null) {
            return true;
        }
        else {
            List<MainAlgorithm> searchStack = new LinkedList<>();

            for (Integer i : guessSquare.PossibleValues) {
                int[][] changedBoardState = GetBoardState();
                changedBoardState[guessSquare.X][guessSquare.Y] = i;
                searchStack.add(new MainAlgorithm(changedBoardState));
            }
            for (MainAlgorithm a : searchStack) {
                if (a.AttemptSolve()){
                    solution = a;
                    return true;
                }
            }
            return false;
        }
    }

    public int [][] GetBoardState()
    {
        if (solution == null) {
            int[][] answer = new int[9][9];
            for (Square square : BoardState) {
                answer[square.X][square.Y] = square.Value;
            }
            return answer;
        }
        else {
            return solution.GetBoardState();
        }
    }

    int minimumXForBox (int i){ return (i % 3) * 3; }
    int minimumYForBox(int i){ return ((int)(i / 3)) * 3; }

    private boolean enactCommand(GroupManipulatorCommand command){
        boolean progressMade = false;

        for (Group group : Groups) {
            if (command.Execute(group)){
                progressMade = true;

                if (command.RequiresRefresh()) {
                    enactCommand(new EliminateImpossibleCommand());
                }
            }
        }

        return progressMade;
    }

    private boolean checkForInvalidSquares()
    {
        for (Square square : BoardState) {
            if (square.PossibleValues.size() == 0 && square.SolvedSquare() == false) {
                return true;
            }
        }
        return  false;
    }

    private Square getLeastOptionsSquare(){
        Square answer = null;
        for (Square square: BoardState) {
            if (!square.SolvedSquare() && ((answer == null) || square.PossibleValues.size() < answer.PossibleValues.size())){
                answer = square;
            }
        }
        return answer;
    }
}
