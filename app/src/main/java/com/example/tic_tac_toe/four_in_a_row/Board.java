package com.example.tic_tac_toe.four_in_a_row;

public class Board {

    private int numberOfColumns;
    private int numberOfRows;
    private BoardLogic boardLogic;
    public boolean winCondition;
    public Cell[][] cells;

    public enum Turn {
        RED_PLAYER, YELLOW_PLAYER
    }

    public Turn turn;

    public Board( int rows , int cols) {
        numberOfRows = rows;
        numberOfColumns = cols;
        cells = new Cell[rows][cols];
        reset();
    }

    public void reset() {
        winCondition = false;
        turn = Turn.RED_PLAYER;
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                cells [row][col] = new Cell();
            }
        }
    }

  /*  public Object clone()  {
        Board newBoard = new Board(this.numberOfRows, this.numberOfColumns );
        for (int i = 0; i< this.numberOfRows; i++) {
            newBoard.cells[i] = this.cells[i].clone();
        }
        return newBoard;
    }*/

    public int lastAvailableRow(int col) {
        for (int row = numberOfRows - 1; row >= 0; row--) {
            if (cells[row][col].empty) {
                return row;
            }
        }
        return -1;
    }

    public void occupyCell( int row , int col) {

        cells[row][col].setPlayer(turn);
    }

    public void toggleTurn() {
        if (turn == Turn.RED_PLAYER) {
            turn = Turn.YELLOW_PLAYER;
        } else {
            turn = Turn.RED_PLAYER;
        }
    }

    public boolean checkForWin() {
        boardLogic = new BoardLogic(turn , cells , numberOfRows , numberOfColumns);

        if(boardLogic.checkForWin()){
            winCondition = true;
        }
        return  boardLogic.checkForWin();
    }
    public boolean noWinner(){
        for (int i = 0 ; i < numberOfColumns ; i++ ){
            if (lastAvailableRow(i) != -1){
                return false;
            }
        }
        return true;
    }

}
