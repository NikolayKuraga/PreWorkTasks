package kuraga.life;

public class Universe {
    private static final int maxEdge = 20;
    private final int height;
    private final int width;
    private final java.util.ArrayList<java.util.ArrayList<Boolean>> cells;

    // create dead universe with 1x1 cells
    public Universe() {
        this.cells = new java.util.ArrayList<>();
        this.cells.add(new java.util.ArrayList<Boolean>());
        this.cells.get(0).add(Boolean.FALSE);
        this.height = this.cells.size();
        this.width = this.cells.get(0).size();
    }

    // doesn't create new instance of "seed", makes shallow copy
    private Universe(java.util.ArrayList<java.util.ArrayList<Boolean>> seed) {
        if(seed == null) {
            throw new NullPointerException("null seed");
        }
        this.cells = seed;
        this.height = this.cells.size();
        this.width = this.cells.get(0).size();
    }

//    // makes deep copy of "seed"
//    private Universe(java.util.ArrayList<java.util.ArrayList<Boolean>> seed) {
//        this.cells = new java.util.ArrayList<java.util.ArrayList<Boolean>>();
//        for(java.util.ArrayList<Boolean> row : seed) {
//            this.cells.add(new java.util.ArrayList<Boolean>());
//            for(Boolean cellStatus : row) {
//                this.cells.get(this.cells.size() - 1).add(cellStatus);
//            }
//        }
//    }

    public static Universe generateRandomUniverse() {
        java.util.ArrayList<java.util.ArrayList<Boolean>> seed = new java.util.ArrayList<>();
        java.util.Random rnd = new java.util.Random();
        final int edge = rnd.nextInt(Universe.maxEdge) + 1;
        for(int i = 0; i < edge; ++i) {
            seed.add(i, new java.util.ArrayList<Boolean>());
            for(int j = 0; j < edge; ++j) {
                seed.get(i).add(j, rnd.nextBoolean());
            }
        }
        return new Universe(seed);
    }

//    public int getHeight() {
//        return this.height;
//    }
//
//    public int getWidth() {
//        return this.width;
//    }

    public void checkCoordinates(int y, int x) {
        if(y < 1 || x < 1) {
            throw new IllegalArgumentException("coordinate of cell must be bigger than 0");
        }
        if(y > this.height || x > this.width) {
            throw new IllegalArgumentException("passed coordinate of cell is out of bounds");
        }
    }

    public boolean isAlive(int y, int x) {
        checkCoordinates(y, x);
        return this.cells.get(y - 1).get(x - 1).booleanValue();
    }

    public boolean willBeAlive(int y, int x) {
        checkCoordinates(y, x);
        // the worst part of the code
        int aliveNeighbors = 0;
        if(y + 1 <= this.height && isAlive(y + 1, x)) {
            ++aliveNeighbors;
        }
        if(y + 1 <= this.height && x + 1 <= this.width && isAlive(y + 1, x + 1)) {
            ++aliveNeighbors;
        }
        if(x + 1 <= this.width && isAlive(y, x + 1)) {
            ++aliveNeighbors;
        }
        if(y - 1 > 0 && x + 1 <= this.width && isAlive(y - 1, x + 1)) {
            ++aliveNeighbors;
        }
        if(y - 1 > 0 && isAlive(y - 1, x)) {
            ++aliveNeighbors;
        }
        if(y - 1 > 0 && x - 1 > 0 && isAlive(y - 1, x - 1)) {
            ++aliveNeighbors;
        }
        if(x - 1 > 0 && isAlive(y, x - 1)) {
            ++aliveNeighbors;
        }
        if(y + 1 <= this.height && x - 1 > 0 && isAlive(y + 1, x - 1)) {
            ++aliveNeighbors;
        }

        if(isAlive(y, x)) {
            return aliveNeighbors == 2 || aliveNeighbors == 3;
        }
        return aliveNeighbors == 3;
    }

    public Universe getEvolved() {
        java.util.ArrayList<java.util.ArrayList<Boolean>> newCells = new java.util.ArrayList<>();
        for(int i = 0; i < this.height; ++i) {
            newCells.add(new java.util.ArrayList<Boolean>());
            for(int j = 0; j < this.width; ++j) {
                newCells.get(i).add(this.willBeAlive(i + 1, j + 1));
            }
        }
        return new Universe(newCells);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(java.util.ArrayList<Boolean> row : this.cells) {
            for(Boolean cell : row) {
                str.append(cell ? '@' : '.').append(' ');
            }
            str.append('\n');
        }
        return str.toString();
    }
}
