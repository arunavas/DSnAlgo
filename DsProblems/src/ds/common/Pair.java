package ds.common;

public class Pair <F, S> {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void first(F first) {
        this.first = first;
    }
    public void second(S second) {
        this.second = second;
    }
    public F first() {
        return this.first;
    }
    public S second() {
        return this.second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
