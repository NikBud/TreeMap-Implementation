package CustomExceptions;

public class EmptyMapException extends RuntimeException{

    public EmptyMapException(){
        super("This map has no elements !");
    }
}
