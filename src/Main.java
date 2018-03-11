import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String csvFile = "input.txt";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "`";
        String gamemode;

        try {
            Scanner scanner = new Scanner(System.in);
            br = new BufferedReader(new FileReader(csvFile));
            gamemode = scanner.nextLine();
            while ((line = br.readLine()) != null) {
                Board game = new Board(line);
                game.printBoard();

                boolean isLegal = false;
                boolean isWinning = false;
                while(!isWinning){
                    game.showLegalMoves();
                    if(gamemode.equals("manual")) {
                        while (!isLegal) {
                            //manual version
                            isLegal = game.updateBoard(scanner.nextLine().charAt(0));
                        }
                    }else if (gamemode.equals("automatic")){
                        char nextPosition = game.autoSearch();
                        System.out.println("Next position chosen by computer is: " + nextPosition);
                        scanner.nextLine();
                        game.updateBoard(nextPosition);
                    }


                    game.printBoard();
                    isLegal= false;
                    isWinning = game.isWinning();
                }
                System.out.println("\n\n You win!!!!!!!!!!!!!!!!!!!!! \n\n\n");

                System.out.println("\n\n Start New Game! Press Enter \n\n\n");
                scanner.nextLine();


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
