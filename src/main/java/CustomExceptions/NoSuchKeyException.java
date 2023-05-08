package CustomExceptions;

public class NoSuchKeyException extends RuntimeException{

    public NoSuchKeyException(){
        super("There is no such key in the map !");
    }
}
