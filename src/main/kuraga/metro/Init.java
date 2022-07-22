package kuraga.metro;

public class Init {
    private final static String start = "F";
    private final static String finish = "A";

    public static void main(String[] args) {
        Metro metro = new Metro();
        metro.addStation("A", "red");
        metro.addStation("B", "red", "black");
        metro.addStation("C", "red", "green");
        metro.addStation("D", "red", "blue");
        metro.addStation("E", "red", "green");
        metro.addStation("F", "red", "black");
        metro.addStation("G", "black");
        metro.addStation("H", "black");
        metro.addStation("J", "black", "green", "blue");
        metro.addStation("K", "green");
        metro.addStation("L", "green", "blue");
        metro.addStation("M", "green");
        metro.addStation("N", "blue");
        metro.addStation("O", "blue");

        // red line
        metro.linkStations("A", "B");
        metro.linkStations("B", "C");
        metro.linkStations("C", "D");
        metro.linkStations("D", "E");
        metro.linkStations("E", "F");

        // black line
        metro.linkStations("B", "H");
        metro.linkStations("H", "J");
        metro.linkStations("J", "F");
        metro.linkStations("F", "G");

        // blue line
        metro.linkStations("N", "L");
        metro.linkStations("L", "D");
        metro.linkStations("D", "J");
        metro.linkStations("J", "O");

        // green line
        metro.linkStations("C", "J");
        metro.linkStations("J", "E");
        metro.linkStations("E", "M");
        metro.linkStations("M", "L");
        metro.linkStations("L", "K");
        metro.linkStations("K", "C");

        Path path = metro.findShortestPath(Init.start, Init.finish);
        System.out.println("   Path from " + Init.start + " to " + Init.finish + ":");
        if(path.isEmpty()) {
            System.out.println("There is no any path");
            return;
        }

        String[] pathArray = path.getStationsNamesArray();
        for(int i = 0; i < pathArray.length; ++i) {
            System.out.print(pathArray[i]);
            if(i < pathArray.length - 1) {
                System.out.print(" | -> | ");
            }
        }
        System.out.println("\n" + path.getChanges());
    }
}
