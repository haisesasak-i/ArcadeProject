package com.example.semesterproject.TicTakToe;

public class TicTakToe {

    //attributes
    private String symbol;
    private  String [][] board;
    private String currentPlayer;
    private String winner;


    public TicTakToe(){
        this.symbol = "";
        this.board = new String[3][3];
        this.winner = null;
        this.currentPlayer="";
    }
    //Getter and setter

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
    public String[][] getBoard() {
        return board;
    }
    public void initializeBoard(String currentPlayer){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
        this.currentPlayer = currentPlayer;
        this.winner =null;
    }
    public boolean makeMove(int row, int column) throws RowColumnException {
        if(row >= 3 || column >= 3 || row < 0 || column < 0){
            throw new RowColumnException("Invalid Rows and columns");

        }
        if(board[row][column].isEmpty()) {
            board[row][column] = this.currentPlayer;
            return true;
        }
        else
            return false;
    }
    public void switchPlayer(){
        if(this.currentPlayer.equals("X"))
            this.currentPlayer = "O";
        else
            this.currentPlayer = "X";
    }
    public boolean checkWinner(){
       //rows check
        for(int i =0;i<3;i++){
            if(board[i][0].equals(this.currentPlayer)&&board[i][1].equals(this.currentPlayer)&&board[i][2].equals(this.currentPlayer)) {
                this.winner =this.currentPlayer;
                return true;
            }


        }
        //columns check
        for(int j=0;j<3;j++){
            if(board[0][j].equals(this.currentPlayer)&&board[1][j].equals(this.currentPlayer)&&board[2][j].equals(this.currentPlayer)) {
                this.winner =this.currentPlayer;
                return true;
            }
        }
        //diagonal check
        if ((board[0][0].equals(this.currentPlayer) &&
                board[1][1].equals(this.currentPlayer) &&
                board[2][2].equals(this.currentPlayer)) ||
                (board[0][2].equals(this.currentPlayer) &&
                        board[1][1].equals(this.currentPlayer) &&
                        board[2][0].equals(this.currentPlayer))) {
           this.winner =this.currentPlayer;
            return true;
        }

        return false;


    }
    public boolean checkTie(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j].equals("")){
                    return false;
                }
            }

        }
        return  !(this.checkWinner());
    }
    public boolean checkForEmptyRowAndColumn(int row , int column){
        return board[row][column].isEmpty();
    }
    public void resetGame(String userPlayer){
        this.initializeBoard(userPlayer);
    }


}
