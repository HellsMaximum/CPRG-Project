package errors;

public class ISBNException extends Throwable{
    // error message variable
    private String error;

    // constructor with no parameters that sets a default error message and a constructor with a string parameter to set a custom error message
    public ISBNException() {
        this.error = "ISBN must be 13 characters long and only contain numeric characters.";
    }

    // constructor with a string parameter to set a custom error message based on the user's input
    public ISBNException(String error) {
        this.error = error;
    }

    // Override the getMessage method to return the error message when the exception is caught
    @Override
    public String getMessage() {
        return error;
    }
}
