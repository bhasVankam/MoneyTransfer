package exception;

public abstract class UserDefinedException extends RuntimeException
{

    UserDefinedException(String message)
    {
        super(message);
    }


    public abstract Integer getStatus();
}
