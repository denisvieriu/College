package Exception;

public class WordContainsException extends Exception
{
    // Parameterless Constructor
    public WordContainsException() {}

    // Constructor that accepts a message
    public WordContainsException(String message)
    {
        super(message);
    }
}