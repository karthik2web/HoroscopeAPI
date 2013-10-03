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
public enum HoroscopeSign 
{   
    Aries("3/21-4/19","♈"),
    Taurus("4/20-5/20","♉"),
    Gemini("5/21-6/21","♊"),
    Cancer("6/22-7/22","♋"),
    Leo("7/23-8/22","♌"),
    Virgo("8/23-9/22","♍"),
    Libra("9/23-10/22","♎"),
    Scorpio("10/23-11/21","♏"),
    Sagittarius("11/22-12/21","♐"),
    Capricorn("12/22-01/19","♑"),
    Aquarius("01/20-02/18","♒"),
    Pisces("02/19-03/20","♓");
    
    private String infoh;
    private String symbol;
    
    private HoroscopeSign(String s, String s2)
    {
        infoh = s;
        symbol = s2;
    }
    /**
     * Get a date range for current Horoscope sign in String format. eg 9/23-10/22
     * @return Date range String
     */
    protected String getInfo()
    {
        return infoh;
    }
    /**
     * <p>Get the Special symbol for current Horoscope sign in String format. eg ♎</p>
     * <p>Note that the symbol is Non-ASCII. It may not display correctly</p>
     * @return Special symbol as String
     */
    protected String getSymbol()
    {
        return symbol;
    }
}
