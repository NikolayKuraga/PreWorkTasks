package kuraga.life;

public class Init {
    public static void main(String[] args) {
        while(true) {
            java.util.Scanner sc = new java.util.Scanner(System.in);
            System.out.print("   Enter natural number of ticks or \"$quit\": ");
            if(!sc.hasNextInt()) {
                if(sc.nextLine().compareTo("$quit") == 0) {
                    break;
                }
                System.out.println("\n   !!! Enter NUMBER or SMALLER NUMBER !!!\n");
                continue;
            }
            GameLife game = new GameLife();
            try {
                game.setNumTicks(sc.nextInt());
            } catch(IllegalArgumentException e) {
                System.out.println("\n   !!! Enter NATURAL number !!!\n");
                continue;
            }
            game.generateSeed();
            game.playGame();
            java.util.ArrayList<String> generations = game.getGenerations();
            System.out.println("\n\tSeed:\n" + generations.get(0));
            for(int i = 1; i < game.getGenerations().size(); ++i) {
                System.out.println("-||-------------------||-");
                System.out.println("\n\tGeneration #" + i + "\n" + generations.get(i));
            }
        }
    }
}
