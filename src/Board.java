import java.util.*;

public class Board {

    private BoardState [][] board;
    int empty_pos_x;
    int empty_pos_y;
    List <BoardState> legal_moves = new ArrayList<BoardState>();



    public Board(BoardState [][] b, int empty_x,int empty_y) {

        empty_pos_x = empty_x;
        empty_pos_y = empty_y;

        board =  new BoardState [3][5];

            for(int i = 0; i< b.length ; i++){
                for(int j = 0; j< b[0].length ; j++){
                    this.board[i][j] = new BoardState (b[i][j].getAlpha_position(),b[i][j].getOccupier());
                }
            }
    }

    public Board(String line) {
        initBoard(line);
    }

    private void initBoard (String line){

        String[]inputs = line.split(" ");

        System.out.println(Arrays.toString(inputs));

        board =  new BoardState [3][5];
        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                board[i][j] = new BoardState ( (char)(65+ (i*5)+j) ,inputs [(i*5)+j].charAt(0));
                if(inputs [(i*5)+j].charAt(0) == 'e'){
                    empty_pos_y = i;
                    empty_pos_x = j;
                }
            }
        }
    }

    public char autoSearch(){
        Map<BoardState,Integer> tempMap = new HashMap<BoardState, Integer>();

        for (BoardState bs: legal_moves) {

            Board hypotheticalBoard = new Board(board,empty_pos_x,empty_pos_y);

//            System.out.println("\t\t\t\t\t\t\t\t\t\t\t" + bs.alpha_position + " " + bs.getOccupier());

                int swap = bs.alpha_position;
                int new_pos_x = (swap-65)%5;
                int new_pos_y = (swap-65-new_pos_x)/5;
                char new_c = hypotheticalBoard.board[new_pos_y][new_pos_x].getOccupier();
                hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier(new_c);
                hypotheticalBoard.empty_pos_x = new_pos_x;
                hypotheticalBoard.empty_pos_y = new_pos_y;
                hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier('e');

//                hypotheticalBoard.printBoard("\t\t\t\t\t\t\t\t\t\t\t");
                int score = runHeuristic(hypotheticalBoard.board);
                tempMap.put(bs, score);
//            System.out.println("\t\t\t\t\t\\t\t\t\t\t\t" +bs.alpha_position + " has a score " + score);
        }

        Map.Entry<BoardState, Integer> maxEntry = null;

        for (Map.Entry<BoardState, Integer> entry : tempMap.entrySet())
        {
            if(maxEntry != null && entry.getValue() == maxEntry.getValue() ){
                System.out.println("Values are equal between " + entry.getKey().getAlpha_position() + " and " + maxEntry.getKey().alpha_position);
            }
            if (maxEntry == null || entry.getValue() <= maxEntry.getValue())
            {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey().alpha_position;
    }

    public int runHeuristic(BoardState [][] b){
        int sum=0;

        Integer sumTop=0;
        Integer sumBottom=0;
        //first looping
        for (int j = 0; j<= 4 ;j++){
            if(b[0][j].getOccupier() != b[2][j].getOccupier()){
                continue;
            }else{
                b[0][j].setOccupier('i');
                b[2][j].setOccupier('i');
            }
        }
        //second looping
        for (int j = 0; j<= 4 ;j++){
            if(b[0][j].getOccupier() != b[2][j].getOccupier() && b[0][j].getOccupier() != 'i'){
                if(b[2][j].getOccupier() != 'e'){
                    sumTop = getClosest(b,j,2,0,b[2][j].getOccupier());
                }
                if(b[0][j].getOccupier() != 'e') {
                    sumBottom = getClosest(b, j, 0,2, b[0][j].getOccupier());
                }
            }
            if(sumTop != null){
                sum += sumTop;
            }
            if(sumBottom != null){
                sum += sumBottom;
            }
            sumTop = 0;
            sumBottom = 0;
        }

        return sum;
    }

    public Integer getClosest(BoardState [][] hypoBoard, int skip_x, int skip_y, int actual_y, char occupier){
        Integer sumDistance = null;

        for (int i = 0; i<= 2 ;i++) {
            for (int j = 0; j<= 4 ;j++) {
                if((i != skip_y || j != skip_x) && hypoBoard[i][j].getOccupier() == occupier ){

                    //finds smallest distance
                    int sumDistanceTemp = Math.abs(i-actual_y) + Math.abs(j-skip_x);
                    if(sumDistance == null || sumDistanceTemp < sumDistance ){
                        sumDistance = sumDistanceTemp;
                    }
                }
            }
        }
        return sumDistance;

    }


    public boolean updateBoard (char c){
        for (BoardState bs: legal_moves
             ) {
            if(bs.alpha_position == c){

                int swap = c;
                System.out.println (empty_pos_y  + " " + empty_pos_x   +" " +swap);
                int new_pos_x = (swap-65)%5;
                int new_pos_y = (swap-65-new_pos_x)/5;
                System.out.println (new_pos_y  + " " + new_pos_x   +" " +swap);
                char new_c = board[new_pos_y][new_pos_x].getOccupier();
                board[empty_pos_y][empty_pos_x].setOccupier(new_c);
                empty_pos_x = new_pos_x;
                empty_pos_y = new_pos_y;
                board[empty_pos_y][empty_pos_x].setOccupier('e');
                legal_moves.clear();
                return true;
            }
        }

        System.out.println("Illegal move");
        return false;
    }

    public void showLegalMoves(){
        List<String> print_legal_moves = new ArrayList<String>();

        if (empty_pos_y > 0) {
            print_legal_moves.add("<" + board[empty_pos_y - 1][empty_pos_x].getAlpha_position() + "," + board[empty_pos_y - 1][empty_pos_x].getOccupier() + ">");
            legal_moves.add(board[empty_pos_y - 1][empty_pos_x]);
        }
        if (empty_pos_y < 2) {
            print_legal_moves.add("<" + board[empty_pos_y + 1][empty_pos_x].getAlpha_position() + "," + board[empty_pos_y + 1][empty_pos_x].getOccupier() + ">");
            legal_moves.add(board[empty_pos_y + 1][empty_pos_x]);
        }
        if (empty_pos_x > 0) {
            print_legal_moves.add("<" + board[empty_pos_y][empty_pos_x - 1].getAlpha_position() + "," + board[empty_pos_y][empty_pos_x - 1].getOccupier() + ">");
            legal_moves.add(board[empty_pos_y][empty_pos_x-1]);
        }
        if (empty_pos_x < 4) {
            print_legal_moves.add("<" + board[empty_pos_y][empty_pos_x + 1].getAlpha_position() + "," + board[empty_pos_y][empty_pos_x + 1].getOccupier() + ">");
            legal_moves.add(board[empty_pos_y][empty_pos_x + 1]);
        }
        System.out.println(Arrays.toString(print_legal_moves.toArray()));
    }

    public boolean isWinning (){
        boolean winning = true;
        for (int j = 0; j<= 4 ;j++){
           if(board[0][j].getOccupier() != board[2][j].getOccupier())
                   return false;
        }
        return winning;
    }

    public void printBoard (String s){

        for (int i = 0; i<= 2 ;i++){
            System.out.print(s);
            for (int j = 0; j<= 4 ;j++){
                System.out.printf("%8c",board[i][j].getOccupier());
            }
            System.out.println();
        }
    }

    public void printBoard (){

        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                System.out.printf("%8c",board[i][j].getOccupier());
            }
            System.out.println();
        }
    }

    public static void main(String []args){
        Board testHypotheticalBoard = new Board ("r b w r r b b b b r r b w e r");
        System.out.println(testHypotheticalBoard.runHeuristic(testHypotheticalBoard.board));

    }
}
