package kuraga.metro;

public class Metro {
    private final java.util.Map<String, Station> stationByName;
    private final java.util.Map<Station, java.util.Set<Station>> adjListStation;

    public Metro() {
        this.stationByName = new java.util.HashMap<String, Station>();
        this.adjListStation = new java.util.HashMap<Station, java.util.Set<Station>>();
    }

    public boolean containsStationWithName(String name) {
        return this.stationByName.containsKey(name);
    }

    public void addStation(String name, String... colors) {
        if (this.containsStationWithName(name)) {
            throw new RuntimeException("\n   !!! Metro already contains station with this name: $"
                    + name
                    + "$ !!!\n");
        }
        if (name.equals("null")) {
            throw new RuntimeException("\n   !!! Unacceptable name for station: $"
                    + name
                    + "$ !!!\n");
        }
        Station station = new Station(name, colors);
        this.stationByName.put(name, station);
        this.adjListStation.put(station, new java.util.HashSet<Station>());
    }

    private Station getStationByName(String name) {
        return this.stationByName.get(name);
    }

    public String[] getStationsWithColorArray(String color) {
        java.util.ArrayList<String> names = new java.util.ArrayList<>();
        for (Station station : adjListStation.keySet()) {
            if (java.util.Arrays.asList(station.getColorsArray()).contains(color)) {
                names.add(station.getName());
            }
        }
        return names.toArray(new String[0]);
    }

    public String[] getStationColorsArray(String name) {
        java.util.ArrayList<String> colors = new java.util.ArrayList<>();
        if (this.stationByName.containsKey(name)) {
            colors.addAll(java.util.List.of(this.getStationByName(name).getColorsArray()));
        }
        return colors.toArray(new String[0]);
    }

    public void linkStations(String st1, String st2) {
        if (!this.containsStationWithName(st1) || !this.containsStationWithName(st2)) {
            throw new RuntimeException("\n   !!! There is not such station in metro -- cannot link !!!\n");
        }
        java.util.Set<String> set = new java.util.HashSet<>(java.util.List.of(this.getStationColorsArray(st1)));
        set.retainAll(java.util.List.of(this.getStationColorsArray(st2)));
        if (set.isEmpty()) {
            throw new RuntimeException("\n   !!! Given stations have different colors !!!\n");
        }
        this.adjListStation.get(this.getStationByName(st1)).add(this.getStationByName(st2));
        this.adjListStation.get(this.getStationByName(st2)).add(this.getStationByName(st1));
    }

    private Station[] getStationNeighbors(Station station) {
        return this.adjListStation.get(station).toArray(new Station[0]);
    }

    private java.util.Set<Station> getStationNeighborsSet(Station station) {
        return new java.util.HashSet<Station>(java.util.List.of(this.getStationNeighbors(station)));
    }

    public String[] getStationNeighborsNamesArray(String name) {
        Station[] stations = this.getStationNeighbors(this.getStationByName(name));
        String[] names = new String[stations.length];
        for (int i = 0; i < stations.length; ++i) {
            names[i] = stations[i].getName();
        }
        return names;
    }

    public Path findShortestPath(String start, String finish) {
        Station startStation = this.getStationByName(start);
        Station finishStation = this.getStationByName(finish);
        if(startStation.equals(finishStation)) {
            return new Path(java.util.List.of(startStation).toArray(new Station[0]), 0);
        }
        if(this.getStationNeighborsSet(startStation).contains(finishStation)) {
            return new Path(java.util.List.of(startStation, finishStation).toArray(new Station[0]), 0);
        }

        class Node {
            private final Station station;
            private Node ancestor;

            public Node(Station station) {
                this.station = station;
                this.ancestor = null;
            }

            public void setAncestor(Node ancestor) {
                this.ancestor = ancestor;
            }

            public Node getAncestor() {
                return this.ancestor;
            }

            public Station getStation() {
                return this.station;
            }

            // ancestor does not affect equality
            @Override
            public boolean equals(Object obj) {
                if(obj == null || this.getClass() != obj.getClass()) {
                    return false;
                }
                return obj == this || this.station.equals(((Node) obj).getStation());
            }

            // ancestor does not affect hash
            @Override
            public int hashCode() {
                return station.hashCode();
            }
        }

        Node startNode = new Node(this.getStationByName(start));
        Node finishNode = new Node(this.getStationByName(finish));

        java.util.Queue<Node> queue = new java.util.ArrayDeque<>(java.util.List.of(startNode));
        java.util.Set<Node> passedNodes = new java.util.HashSet<>(java.util.List.of(startNode));

        while(!queue.isEmpty()) {
            Node curNode = queue.remove();
            for(Station neighbor : java.util.List.of(this.getStationNeighbors(curNode.getStation()))) {
                Node descNode = new Node(neighbor);
                if(passedNodes.contains(descNode)) {
                    continue;
                } passedNodes.add(descNode);
                if(!descNode.equals(finishNode)) {
                    descNode.setAncestor(curNode);
                    queue.add(descNode);
                    continue;
                }
                finishNode.setAncestor(curNode);
                queue.clear();
                break;
            }
        }

        if(finishNode.getAncestor() == null) {
            return new Path();
        }

        int changes = 0;
        Node lstLstNode = finishNode;
        Node lstNode = finishNode;
        Node curNode = finishNode.getAncestor();

        java.util.List<Station> stations = new java.util.ArrayList<>(java.util.List.of(lstNode.getStation()));
        do {
            stations.add(curNode.getStation());

            System.out.println(lstLstNode.getStation() + " " + curNode.getStation());
            java.util.Set<String> colors = new java.util.HashSet<>(java.util.List.of(lstLstNode.getStation().getColorsArray()));
            colors.retainAll(java.util.List.of(lstNode.getStation().getColorsArray()));
            colors.retainAll(java.util.List.of(curNode.getStation().getColorsArray()));
            if(colors.isEmpty()) {
                ++changes;
            }

            lstLstNode = lstNode;
            lstNode = curNode;
            curNode = curNode.getAncestor();
        } while(curNode != null);

        java.util.Collections.reverse(stations);
        return new Path(stations.toArray(new Station[0]), changes);
    }
}
