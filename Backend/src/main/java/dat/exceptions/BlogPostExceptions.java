package dat.exceptions;

public class BlogPostExceptions extends Exception {

    private final int statusCode;


    public BlogPostExceptions(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }


    public int getStatusCode() {
        return statusCode;
    }


    public String getErrorMessage() {
        return "Error: " + super.getMessage() + " | Status Code: " + getStatusCode();
    }


}
