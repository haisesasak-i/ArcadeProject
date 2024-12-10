package com.example.semesterproject.BattleShip;
import com.example.semesterproject.TicTakToe.RowColumnException;
import java.util.ArrayList;
import java.util.Random;

public class BattleShip {
    //attributes
    private String [][] gridfShips;
    private ArrayList<Coordinates> gridCoordinates;

    int remainingShips;
    int hits;
    int misses;

    //constructor
    public BattleShip(){
        gridfShips = new String[5][5];
        {
            gridCoordinates = new ArrayList<>();
            for(int i =0;i<gridfShips.length;i++){
                for(int j =0;j<gridfShips[i].length;j++){
                    gridCoordinates.add(new Coordinates(i,j));
                }
            }
        }

        remainingShips = 0;
        hits = 0;
        misses = 0;
    }
    //getter
    public String[][] getGridfShips() {
        return gridfShips;
    }

    public ArrayList<Coordinates> getgridCoordinates() {
        return gridCoordinates;
    }

    public int getRemainingShips() {
        return remainingShips;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }
    //setter

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setRemainingShips(int remainingShips) {
        this.remainingShips = remainingShips;
    }

    public void initialShipGrid(){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                gridfShips[i][j] = "";
            }
        }
        this.setHits(0);
        this.setMisses(0);
        this.remainingShips = 0;
        this.placeShip();
    }
    private void placeShip(){
        Random randomShipPosition = new Random();
        for(int i =0;i<gridfShips.length;i++){
            while (true) {
                int shipPosition = randomShipPosition.nextInt(this.gridCoordinates.size());
                int row = this.gridCoordinates.get(shipPosition).getX();
                int column = this.gridCoordinates.get(shipPosition).getY();
                if (gridfShips[row][column].isEmpty()) {
                    gridfShips[row][column] = "Ship";
                    remainingShips++;
                    break;
                }
            }
        }
    }
    public boolean processGuess(int row , int columns) throws  RowColumnException{
        if(row>this.gridfShips.length-1||columns>gridfShips[row].length-1){
            throw new RowColumnException("\"Invalid input: Row "+row+", Column "+columns+" is out of bounds for a grid of "+this.gridfShips.length+"x"+this.gridfShips[0].length+".\"\n");
        }
        if(gridfShips[row][columns].equals("Ship")){
            gridfShips[row][columns] = "Hit";
            remainingShips--;
            hits++;
            return true;
        }
            gridfShips[row][columns] = "Miss";
            misses++;

            return false;

    }
    public boolean isGameOver(){
        return  remainingShips==0;
    }

    public void restartGame(){
        this.initialShipGrid();

    }
    public  int [] hintSystem(){
        for(int i =0;i<gridfShips.length;i++){
            for(int j =0;j<gridfShips[i].length;j++){
                if(gridfShips[i][j].equals("Ship")){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }


}
