package com.course.validation;

public class DoubleValidator {
    public static boolean isValid(String source)
    {
        try {
            double result = Double.parseDouble(source);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }
}
