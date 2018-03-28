import java.util.*;

public class OpenList {
    TreeSet<Board> openList = new TreeSet();


    public OpenList() {
        this.openList = new TreeSet<Board>();
    }

    public void add (Board board){
        if(!this.containsBoard(board)){
            openList.add(copyBS(board));
        }
    }

    public Board copyBS(Board oldBoard){
        Board newBoard = new Board(oldBoard);
        return newBoard;
    }

    public boolean containsBoard (Board board){

        Iterator cli = openList.iterator();

        boolean tempBool;

        while(cli.hasNext()){
            tempBool = true;

            Board clibs = (Board)cli.next();
            if(sameBoard(clibs,board)){
                return true;
            };
        }
        return false;
    }

    public boolean sameBoard(Board b1,Board b2){
        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                if(b1.getBoard()[i][j].getOccupier()!= b2.getBoard()[i][j].getOccupier()){
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard (BoardState[][]bs){

        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                System.out.printf("%8c",bs[i][j].getOccupier());
            }
            System.out.println();
        }
    }



    public static void main (String [] args){

    }
}
