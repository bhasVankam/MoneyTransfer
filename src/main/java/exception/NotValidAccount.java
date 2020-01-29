package exception;

import org.eclipse.jetty.http.HttpStatus;

public class NotValidAccount extends UserDefinedException
{

    public NotValidAccount(String message)
    {
        super(message);
    }


    public Integer getStatus()
    {
        return HttpStatus.NOT_FOUND_404;
    }
}
