package CustomExceptions;

public class NoComparableImplementedException extends RuntimeException {

    public NoComparableImplementedException(){
        super("Class you use as a key in the map should implement Comparable interface or you should provide Comparator!");
    }
}
