package Handler;

public class AppException extends RuntimeException{

    int statusCode;

    public AppException(int statusCode, String message){
        super(message);
        this.statusCode=statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
