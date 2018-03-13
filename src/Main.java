import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String csvFile = "input.txt";
        BufferedReader br = null;
        Scanner scanner = null;
        PrintWriter pw = null;
        String line = "";
        String cvsSplitBy = "`";
        String gamemode = null;
        String gamemodeMessage = "Select a Game Mode : automatic | manual";

        try {
            pw = new PrintWriter(new PrintStream("Output.txt"));
            scanner = new Scanner(System.in);
            br = new BufferedReader(new FileReader(csvFile));
            while(gamemode == null || (!gamemode.equals("automatic")&&!gamemode.equals("manual"))){
                System.out.println(gamemodeMessage);
                gamemode = scanner.nextLine();
                if(!gamemode.equals("automatic")&&!gamemode.equals("manual")){
                    System.out.print( "Error Try Again - ");
                }
            }
            while ((line = br.readLine()) != null) {
                Board game = new Board(line);
                pw.println("\n\n New Game:");
                game.printBoard(pw);
                pw.println("\n Trace is as follows: \n\n");
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
                        pw.println("  " + nextPosition);
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
            try {
                if (scanner != null) {
                    scanner.close();
                }
                if (pw != null) {
                    pw.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
