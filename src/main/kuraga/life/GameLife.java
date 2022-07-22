package kuraga.life;

public class GameLife {
    private int numTicks;
    private Universe seed;
    private java.util.ArrayList<Universe> generations;

    public GameLife() {}

    public void setNumTicks(int num) {
        if(num < 0) {
            throw new IllegalArgumentException("number of ticks cannot be negative");
        }
        this.numTicks = num;
    }

    public void generateSeed() {
        java.util.ArrayList<java.util.ArrayList<Boolean>> seed = new java.util.ArrayList<>();
        java.util.Random rnd = new java.util.Random();
        final int edge = rnd.nextInt(20) + 1; // from 1 to 20
        for(int i = 0; i < edge; ++i) {
            seed.add(i, new java.util.ArrayList<Boolean>());
            for(int j = 0; j < edge; ++j) {
                seed.get(i).add(j, rnd.nextBoolean());
            }
        }
        this.seed = Universe.generateRandomUniverse();
    }

    public void playGame() {
        generations = new java.util.ArrayList<Universe>();
        generations.add(this.seed);
        for(int TicksRemained = this.numTicks; TicksRemained > 0; --TicksRemained) {
            generations.add(this.generations.get(this.generations.size() - 1).getEvolved());
        }
    }

    public java.util.ArrayList<String> getGenerations() {
        java.util.ArrayList<String> retGenerations = new java.util.ArrayList<>();
        for(Universe generation : this.generations) {
            retGenerations.add(generation.toString());
        }
        return retGenerations;
    }
}
