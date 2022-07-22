package kuraga.metro;

public final class Station {
    private final String name;
    private final java.util.Set<String> colors;

//    public Station() {
//        this.name = "null";
//        this.colors = new java.util.HashSet<String>();
//    }

    public Station(String name, String... colors) {
        if(name == null) {
            throw new IllegalArgumentException("\n   !!! Passed null as name for station !!!\n");
        }
        this.name = name;
        this.colors = new java.util.HashSet<String>();
        this.colors.addAll(java.util.Arrays.asList(colors));
    }

    // colors do not affect equality
    @Override
    public boolean equals(Object obj) {
        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return obj == this || this.name.equals(((Station) obj).name);
    }

    // colors do not affect hash
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public String[] getColorsArray() {
        return colors.toArray(new String[0]);
    }
}
