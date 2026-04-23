package errors;

public class InvalidDateException extends Throwable{
	private static final long serialVersionUID = -8964630019480870654L;
	// error message variable
    private String error;

    // constructor with no parameters that sets a default error message and a constructor with a string parameter to set a custom error message
    public InvalidDateException() {
        this.error = "Invalid date format. Please enter the date in the format YYYY-MM-DD.";
    }

    // constructor with a string parameter to set a custom error message based on the user's input
    public InvalidDateException(String error) {
        this.error = error;
    }

    // Override the getMessage method to return the error message when the exception is caught
    @Override
    public String getMessage() {
        return error;
    }
}
