/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package horoscopeapi;

/**
 *
 * @author Xiaoerge
 * @version 1.0 Compiled by Java 1.6u33
 */
public class SQLExcpetion extends Exception
{
    public SQLExcpetion()
    {
        super();
    }
    public SQLExcpetion(String message)
    {
        super(message);
    }
    public SQLExcpetion(Throwable cause)
    {
        super(cause);
    }
    public SQLExcpetion(String message, Throwable cause)
    {
        super(message, cause);
    }
}
