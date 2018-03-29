import java.io.*;
import java.util.*;

public class Board implements Comparable<Board>{

    public BoardState[][] getBoard() {
        return board;
    }

    private BoardState [][] board;
    int empty_pos_x;
    int empty_pos_y;
    List <BoardState> legal_moves = new ArrayList<BoardState>();
    ClosedList closedList = new ClosedList();
    int cnt = 0;

    public String path = "";

    public StringBuilder getSb() {
        return sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    OpenList openList = new OpenList();
    StringBuilder sb = new StringBuilder();

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    int heuristicValue;


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

    //for copying
    public Board(Board oldBoard){

        this.board =  new BoardState [3][5];
        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                this.board[i][j] = new BoardState (oldBoard.board[i][j].getAlpha_position(),oldBoard.board[i][j].occupier);
            }
        }

        this.heuristicValue = oldBoard.getHeuristicValue();
        this.empty_pos_x = oldBoard.empty_pos_x;
        this.empty_pos_y = oldBoard.empty_pos_y;
        this.sb = new StringBuilder(oldBoard.sb.toString());
        this.path = oldBoard.path;
        this.cnt = oldBoard.cnt;
    }

    //init by insert string
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

        closedList.add(board);

        Map<BoardState,Integer> tempMap = new HashMap<BoardState, Integer>();

        for (BoardState bs: legal_moves) {

            Board hypotheticalBoard = new Board(board,empty_pos_x,empty_pos_y);

                int swap = bs.alpha_position;
                int new_pos_x = (swap-65)%5;
                int new_pos_y = (swap-65-new_pos_x)/5;
                char new_c = hypotheticalBoard.board[new_pos_y][new_pos_x].getOccupier();
                hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier(new_c);
                hypotheticalBoard.empty_pos_x = new_pos_x;
                hypotheticalBoard.empty_pos_y = new_pos_y;
                hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier('e');

                if(!closedList.containsBoard(hypotheticalBoard.board)) {
                    int score = runHeuristic(hypotheticalBoard.board);
                    hypotheticalBoard.setHeuristicValue(score);
                    tempMap.put(bs, score);
                    openList.add(hypotheticalBoard);
                }
        }

//        Map.Entry<BoardState, Integer> maxEntry = null;
//
//        for (Map.Entry<BoardState, Integer> entry : tempMap.entrySet())
//        {
//            if(maxEntry != null && entry.getValue() == maxEntry.getValue() ){
//                System.out.println("Values are equal between " + entry.getKey().getAlpha_position() + " and " + maxEntry.getKey().alpha_position);
//            }
//            if (maxEntry == null || entry.getValue() <= maxEntry.getValue())
//            {
//                maxEntry = entry;
//            }
//        }
        Object [] bar = openList.openList.toArray();
        System.out.println("heuristic values");
        for(Object bard: bar){
            System.out.print(((Board)bard).getHeuristicValue() +" ");
        }

        Board tempBoard = openList.openList.pollFirst();
        char alpha = tempBoard.getBoard()[tempBoard.empty_pos_y][tempBoard.empty_pos_x].alpha_position;

        //backtracking
//        backtracking(tempBoard);
//        sb.append(alpha);
        System.out.println("Path is here: ");
        System.out.println(sb.toString());
        System.out.println("cnt is here: ");
        System.out.println(cnt);

        return alpha;
    }

    public void autoSearchComplete(){

        closedList.add(board);

        Map<BoardState,Integer> tempMap = new HashMap<BoardState, Integer>();

        for (BoardState bs: legal_moves) {

            Board hypotheticalBoard = new Board(board,empty_pos_x,empty_pos_y);

            int swap = bs.alpha_position;
            int new_pos_x = (swap-65)%5;
            int new_pos_y = (swap-65-new_pos_x)/5;
            char new_c = hypotheticalBoard.board[new_pos_y][new_pos_x].getOccupier();
            hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier(new_c);
            hypotheticalBoard.empty_pos_x = new_pos_x;
            hypotheticalBoard.empty_pos_y = new_pos_y;
            hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier('e');

            hypotheticalBoard.path += this.path;
            hypotheticalBoard.path += bs.alpha_position;
            hypotheticalBoard.cnt = this.cnt+1;

            if(!closedList.containsBoard(hypotheticalBoard.board)) {

                BoardState [][] copyBoardState = new BoardState[3][5];

                for (int i = 0; i<= 2 ;i++) {
                    for (int j = 0; j<= 4 ;j++) {
                        copyBoardState[i][j] = new BoardState(hypotheticalBoard.board[i][j].getAlpha_position(),hypotheticalBoard.board[i][j].occupier);
                    }
                }


                int score = runHeuristic(copyBoardState);
                hypotheticalBoard.setHeuristicValue(score);
                System.out.println("HypotheticalBoard here");
                hypotheticalBoard.printBoard();
                tempMap.put(bs, score);
                openList.add(hypotheticalBoard);
            }
        }


        Object [] bar = openList.openList.toArray();
        System.out.println("heuristic values");
        for(Object bard: bar){
            System.out.print(((Board)bard).getHeuristicValue() +" ");
        }

        Board tempBoard = openList.openList.pollFirst();

        //backtracking
        backtracking(tempBoard);
    }

//    public char autoSearchComplete(){
//
//        closedList.add(board);
//
//        Map<BoardState,Integer> tempMap = new HashMap<BoardState, Integer>();
//
//        for (BoardState bs: legal_moves) {
//
//            Board hypotheticalBoard = new Board(board,empty_pos_x,empty_pos_y);
//
//            int swap = bs.alpha_position;
//            int new_pos_x = (swap-65)%5;
//            int new_pos_y = (swap-65-new_pos_x)/5;
//            char new_c = hypotheticalBoard.board[new_pos_y][new_pos_x].getOccupier();
//            hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier(new_c);
//            hypotheticalBoard.empty_pos_x = new_pos_x;
//            hypotheticalBoard.empty_pos_y = new_pos_y;
//            hypotheticalBoard.board[hypotheticalBoard.empty_pos_y][hypotheticalBoard.empty_pos_x].setOccupier('e');
//            hypotheticalBoard.cnt = cnt++;
//
//            if(!closedList.containsBoard(hypotheticalBoard.board)) {
//                int score = runHeuristic(hypotheticalBoard.board);
//                hypotheticalBoard.setHeuristicValue(score);
//                tempMap.put(bs, score);
//                openList.add(hypotheticalBoard);
//            }
//        }
//
////        Map.Entry<BoardState, Integer> maxEntry = null;
////
////        for (Map.Entry<BoardState, Integer> entry : tempMap.entrySet())
////        {
////            if(maxEntry != null && entry.getValue() == maxEntry.getValue() ){
////                System.out.println("Values are equal between " + entry.getKey().getAlpha_position() + " and " + maxEntry.getKey().alpha_position);
////            }
////            if (maxEntry == null || entry.getValue() <= maxEntry.getValue())
////            {
////                maxEntry = entry;
////            }
////        }
//        Object [] bar = openList.openList.toArray();
//        System.out.println("heuristic values");
//        for(Object bard: bar){
//            System.out.print(((Board)bard).getHeuristicValue() +" ");
//        }
//
//        Board tempBoard = openList.openList.pollFirst();
//        char alpha = tempBoard.getBoard()[tempBoard.empty_pos_y][tempBoard.empty_pos_x].alpha_position;
//
//        //backtracking
//        backtracking(tempBoard);
//        sb.append(alpha);
////        System.out.println("Path is here: ");
////        System.out.println(sb.toString());
//
//        return alpha;
//    }

    public void backtracking(Board oldBoard){
        this.board = oldBoard.board;
        this.empty_pos_y = oldBoard.empty_pos_y;
        this.empty_pos_x = oldBoard.empty_pos_x;
        this.cnt = oldBoard.cnt;
        this.path = oldBoard.path;
        legal_moves.clear();
    }

    public boolean updateBoard (char c){
        for (BoardState bs: legal_moves
                ) {
            if(bs.alpha_position == c){

                int swap = c;
                int new_pos_x = (swap-65)%5;
                int new_pos_y = (swap-65-new_pos_x)/5;
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



    public static int runHeuristic(BoardState [][] b){
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

    public static Integer getClosest(BoardState [][] hypoBoard, int skip_x, int skip_y, int actual_y, char occupier){
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



    public boolean updateBoardComplete (char c){
        for (BoardState bs: legal_moves
                ) {
            if(bs.alpha_position == c){

                int swap = c;
                int new_pos_x = (swap-65)%5;
                int new_pos_y = (swap-65-new_pos_x)/5;
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

    public void printBoard (PrintWriter pw){


        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                pw.printf("%8c",board[i][j].getOccupier());
            }
            pw.println();
        }
    }

    public void printBoardVertical (PrintWriter pw){

        for (int i = 0; i<= 2 ;i++){
            for (int j = 0; j<= 4 ;j++){
                pw.print(board[i][j].getOccupier()+ " ");
            }
        }
    }

    @Override
    public int compareTo(Board board){
        if(this.getHeuristicValue() == board.getHeuristicValue()){
            return 0;
        }else if (this.getHeuristicValue() > board.getHeuristicValue()){
            return 1;
        }else{
            return -1;
        }
    }

    public static void main(String []args){
        Board testHypotheticalBoard = new Board ("r b w r r b b b b r r b w e r");
        System.out.println(testHypotheticalBoard.runHeuristic(testHypotheticalBoard.board));

    }

    public char nextSolution(int i){

        String csvFile = "solutions.txt";
        BufferedReader br = null;
        Scanner scanner = null;
        String line = "";


        try {

            scanner = new Scanner(System.in);
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {

                return line.charAt(i);

            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (scanner != null) {
                    scanner.close();
                }

            } catch (Exception e) {

            }
        }

        return ']';
    }
}
