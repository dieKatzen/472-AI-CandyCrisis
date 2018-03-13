import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClosedList {
    HashSet<BoardState[][]> closedList;

    public Set<BoardState[][]> getClosedList() {
        return closedList;
    }

    public void setClosedList(HashSet<BoardState[][]> closedList) {
        this.closedList = closedList;
    }

    public ClosedList() {
        closedList = new HashSet<BoardState[][]>();
    }


    public void add (BoardState [][] bs){
        System.out.println(closedList.size());
        if(!this.containsBoard(bs)){
            System.out.println("adding");
            closedList.add(copyBS(bs));
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

        Iterator cli = closedList.iterator();

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

//    @Override
//    public boolean equals(Object obj) {
//        BoardState [][]bs = (BoardState[][])obj;
//        Iterator cli = closedList.iterator();
//
//        while(cli.hasNext()){
//            BoardState [][] clibs = (BoardState [][])cli.next();
//            for (int i = 0; i<= 2 ;i++){
//                for (int j = 0; j<= 4 ;j++){
//                    if(clibs[i][j].getOccupier()!= bs[i][j].getOccupier()){
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
}


