package com.dental.demo.Exception;

public class AppointmentException extends Exception
{

    private int status;

    private String errorMessage;

    public AppointmentException(int status, String errorMessage)
    {
        super(errorMessage);
        this.status = status;
    }

    public AppointmentException(String errorMessage)
    {
        super(errorMessage);
        this.status = 500;
    }

    public int getStatus()
    {
        return status;
    }
}
