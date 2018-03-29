import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {



        Scanner scanner = null;
        PrintWriter pw = null;
        String gamemode = null;

        String gamemodeMessage = "Select a Game Mode : automatic | manual | complete | solutions";


        try {
            scanner = new Scanner(System.in);
            while (gamemode == null || (!gamemode.equals("automatic") && !gamemode.equals("manual") && !gamemode.equals("solutions") && !gamemode.equals("complete"))) {
                System.out.println(gamemodeMessage);
                gamemode = scanner.nextLine();
                if (!gamemode.equals("automatic") && !gamemode.equals("manual") && !gamemode.equals("complete") && !gamemode.equals("solutions")) {
                    System.out.print("Error Try Again - ");
                }
            }

            for (int i = 1; i < 5; i++) {
                runInputs("input" + i + ".txt", "output" + i + ".txt", gamemode);
            }

        } catch(Exception e){
            System.out.println("what happened?");
        }

    }


    public static void runInputs(String inputName, String outputName, String gamemode) {

        String csvFile = inputName;
        BufferedReader br = null;
        Scanner scanner = null;
        PrintWriter pw = null;
        StringBuilder mainSb = new StringBuilder();
        String line = "";
        String cvsSplitBy = "`";
        char nextPosition;
        int i = 0;
        long totaltime= 0;

        try {
            pw = new PrintWriter(new PrintStream(outputName));
            scanner = new Scanner(System.in);
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {

                StringBuilder sb = new StringBuilder();
                long timeStart  = System.currentTimeMillis();

                Board game = new Board(line);
                if(gamemode.equals("complete")){}
                else {
                    pw.println("\n\n New Game:");
                    game.printBoard(pw);
                    pw.println("\n Trace is as follows: \n\n");
                    System.out.println("\n Trace is as follows: \n\n");
                }
                boolean isLegal = false;
                boolean isWinning = false;
                while(!isWinning){
                    game.showLegalMoves();
                    if(gamemode.equals("manual")) {
                        while (!isLegal) {
                            //manual version
                            isLegal = game.updateBoard(scanner.nextLine().charAt(0));
                        }
                        game.printBoard();
                    }else if(gamemode.equals("solutions")) {
                        while (!isLegal) {
                            //manual version
                            isLegal = game.updateBoard(game.nextSolution(i));
                            i++;
                        }
                        game.printBoard();
                    }else if (gamemode.equals("automatic")){
                        nextPosition = game.autoSearch();
                        System.out.println("Next position chosen by computer is: " + nextPosition);
                        pw.println("  " + nextPosition);
                        scanner.nextLine();
                        game.updateBoard(nextPosition);
                        game.printBoard();
                    }else if (gamemode.equals("complete")){
                        game.autoSearchComplete();

                    }


                    isLegal= false;
                    isWinning = game.isWinning();
                }
//                System.out.println("Solution is: " + game.path);
                game.printBoard();
                long endStart  = System.currentTimeMillis();
                long currTime = endStart-timeStart;
                totaltime += currTime;
                pw.println(game.path);
                pw.println((currTime) + "ms");
//                System.out.println("(endStart-timeStart) + "ms");
//                System.out.println("Solution is: " + sb.toString());
//                System.out.println("\n\n You win!!!!!!!!!!!!!!!!!!!!! \n\n\n");

                System.out.println("\n\n Start New Game! Press Enter \n\n\n");
                if(gamemode.equals("automatic")||gamemode.equals("manual")) {
                    scanner.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.println("totaltime: "+ totaltime+ "ms");
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
