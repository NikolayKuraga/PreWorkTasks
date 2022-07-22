package kuraga.metro;

public final class Path {
    private final Station[] stations;
    private final int changes;

    public Path() {
        this.stations = new Station[0];
        this.changes = 0;
    }

    public Path(Station[] stations, int changes) {
        this.stations = stations;
        this.changes = changes;
    }

    public String[] getStationsNamesArray() {
        java.util.List<String> names = new java.util.ArrayList<>();
        for(Station station : stations) {
            names.add(station.getName());
        }
        return names.toArray(new String[0]);
    }

    public boolean isEmpty() {
        return this.stations.length == 0;
    }

    public int getChanges() {
        return this.changes;
    }
}
