import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class OpenList {
    SortedSet<BoardState[][]> openList;


    public OpenList() {
        this.openList = new TreeSet<BoardState[][]>(new Comparator<BoardState[][]>(){
                @Override
                public int compare(BoardState[][]bs1,BoardState[][] bs2) {
                return Board.runHeuristic(bs1) - Board.runHeuristic(bs2);
                }
        });
    }

    public void add (BoardState [][] bs){
        System.out.println(openList.size());
        if(!this.containsBoard(bs)){
            System.out.println("adding");
            openList.add(copyBS(bs));
        }
    }

    public BoardState[][] copyBS(BoardState[][]bs){
        BoardState [][] bsnew = new BoardState[3][5];

        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                bsnew[i][j]= new BoardState(bs[i][j].getOccupier());
            }
        }
        return bsnew;
    }

    public boolean containsBoard (BoardState [][] bs){

        Iterator cli = openList.iterator();

        boolean tempBool;

        while(cli.hasNext()){
            tempBool = true;

            BoardState [][] clibs = (BoardState [][])cli.next();
            if(sameBoard(clibs,bs)){
                return true;
            };
        }
        return false;
    }

    public boolean sameBoard(BoardState [][] bs1,BoardState [][]bs2){
        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                if(bs1[i][j].getOccupier()!= bs2[i][j].getOccupier()){
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
