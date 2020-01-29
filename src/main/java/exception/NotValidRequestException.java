package exception;

import org.eclipse.jetty.http.HttpStatus;

public class NotValidRequestException extends UserDefinedException
{

    public NotValidRequestException(String message)
    {
        super(message);
    }


    public Integer getStatus()
    {
        return HttpStatus.BAD_REQUEST_400;
    }
}
