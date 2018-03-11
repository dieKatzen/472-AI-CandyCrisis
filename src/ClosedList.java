import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClosedList {
    Set<BoardState[][]> closedList;

    public Set<BoardState[][]> getClosedList() {
        return closedList;
    }

    public void setClosedList(Set<BoardState[][]> closedList) {
        this.closedList = closedList;
    }

    public ClosedList() {
        closedList = new HashSet<BoardState[][]>();
    }


    public void add (BoardState [][] bs){
        if(!this.containsBoard(bs)){
            this.closedList.add(bs);
        }
    }

    public boolean containsBoard (BoardState [][] bs){

        Iterator cli = closedList.iterator();
        boolean tempBool = true;

        while(cli.hasNext()){
            BoardState [][] clibs = (BoardState [][])cli.next();
            for (int i = 0; i<= 2 ;i++){
                for (int j = 0; j<= 4 ;j++){
                    if(clibs[i][j].getOccupier()!= bs[i][j].getOccupier()){
                        tempBool = false;
                    }
                }
            }
            if(tempBool){
                return true;
            }
        }
        return false;
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


