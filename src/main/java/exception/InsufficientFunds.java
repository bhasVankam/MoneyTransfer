package exception;

import org.eclipse.jetty.http.HttpStatus;

public class InsufficientFunds extends UserDefinedException
{

    public InsufficientFunds(String message)
    {
        super(message);
    }


    public Integer getStatus()
    {
        return HttpStatus.BAD_REQUEST_400;
    }
}
