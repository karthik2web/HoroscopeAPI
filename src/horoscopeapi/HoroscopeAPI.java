/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package horoscopeapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 *
 * @author Xiaoerge
 * @version 1.0 Compiled by Java 1.6u33
 */
public final class HoroscopeAPI 
{
    private final String url;
    private final int id;
    private final String[][] horoscope_reading;
    private final String[] zodiac_reading;
    private final boolean isPaid;
    private final Date expiration;
    private long lastmod;
    
    /**
     * Create a new instance of HoroscopeAPI where id is user_id.
     * 
     * <pre>
     * {@code
     * Code example
     *            try 
            {
                HoroscopeAPI api = new HoroscopeAPI(123456789);
            } 
            catch (MalformedURLException ex) 
            {
                ex.printStackTrace();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            } 
            catch (SQLExcpetion ex) 
            {
                ex.printStackTrace();
            }
     * }
     * </pre>
     * @param id 
     */
    public HoroscopeAPI(int id) throws MalformedURLException, IOException, SQLExcpetion
    {
        this.url = "http://www.xiaoergeblessed.com/API/HoroscopeAPI/HoroscopeAPI.php";
        this.id = id;       
        horoscope_reading = new String[12][6];
        zodiac_reading = new String[12];
        isPaid = false;
        expiration = new Date(System.currentTimeMillis());
        lastmod = 0;
        getReading();
    }
    /**
     * Get a reading for zodiac sign.
     * <pre>
     * {@code
     * Code example
     *            try 
            {
                HoroscopeAPI api = new HoroscopeAPI(123456789);
                String reading = api.getZodiacReading(ZodiacSign.Dragon, ZodiacReading.Daily_Zodiac);
            } 
            catch (MalformedURLException ex) 
            {
                ex.printStackTrace();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            } 
            catch (SQLExcpetion ex) 
            {
                ex.printStackTrace();
            }
     * }
     * </pre>
     * @param sign Rat, Ox, Tiger, Rabbit, Dragon, Snake, Horse, Sheep, Monkey, Rooster, Dog, Pig
     * @param reading Daily_Zodiac
     * @return Zodiac_Reading String
     */
    public final String getZodiacReading(ZodiacSign sign, ZodiacReading reading)
    {
        return zodiac_reading[sign.ordinal()];
    }
    /**
     * Get a reading for horoscope sign. 
     * <pre>
     * {@code
     * Code example
     *            try 
            {
                HoroscopeAPI api = new HoroscopeAPI(123456789);
                String reading = api.getHoroscopeReading(HoroscopeSign.Libra, HoroscopeReading.Love);
            } 
            catch (MalformedURLException ex) 
            {
                ex.printStackTrace();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            } 
            catch (SQLExcpetion ex) 
            {
                ex.printStackTrace();
            }
     * }
     * </pre>
     * @param sign Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces;
     * @param reading Daily_Horoscope, Weekly_Horoscope, Monthly_Horoscope, Love, Career, Wellness
     * @return Horoscope_Reading String
     */
    public final String getHoroscopeReading(HoroscopeSign sign, HoroscopeReading reading)
    {
        return horoscope_reading[sign.ordinal()][reading.ordinal()];
    }
    private final void getReading() throws MalformedURLException, IOException, SQLExcpetion
    {
        HttpURLConnection con = (HttpURLConnection) 
                    new URL(url+"?"+"id="+id).openConnection();
            
        con.setRequestProperty("User-Agent", "HoroscopeAPI");

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String str = "";
        for (int i = 0; i < horoscope_reading.length; i++)
        {                
            for (int j = 0; j < horoscope_reading[i].length; j++)
            {
                str = reader.readLine();

                if (str.contains("ERROR"))
                {
                    throw new SQLExcpetion(reader.readLine()); 
                }
                else
                {
                    horoscope_reading[i][j] = str;
                }
            }
        }//done grabbing horoscope readings

        //reading zodiac
        for (int i = 0; i < zodiac_reading.length; i++)
        {
            str = reader.readLine();

            if (str.contains("ERROR"))
            {
                throw new SQLExcpetion(reader.readLine()); 
            }
            else
            {
                zodiac_reading[i] = str;
            }
        }
        try
        {
            //php returns seconds
            String s = reader.readLine();
            lastmod = Long.parseLong(s)*1000;
        }
        catch(Exception ex){lastmod = 0;}
        reader.close();
    }
    /**
     * Get developer info 
     * <pre>
     * {@code
     * Code example
     *            try 
            {
                HoroscopeAPI api = new HoroscopeAPI(123456789);
                String info = api.getDeveloperInfo();
            } 
            catch (MalformedURLException ex) 
            {
                ex.printStackTrace();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            } 
            catch (SQLExcpetion ex) 
            {
                ex.printStackTrace();
            }
     * }
     * </pre>
     * @return Developer information String
     */
    public final String getDeveloperInfo()
    {
        String developer = "Xiaoerge";
        String appname = "HoroscopeAPI";
        String appversion = "1.0";
        String supportemail = "horoscope@xiaoergeblessed.com";
        String website = "http://www.xiaoergeblessed.com";
        
        return developer+"\n"
                +appname+"\n"
                +appversion+"\n"
                +supportemail+"\n"+
                website;
    }
    /**
     * Returns the value of the <code>last-modified</code> header field.
     * The result is the number of milliseconds since January 1, 1970.
     *
     * @return  the date the resource referenced by this
     *          <code>HoroscopeAPI</code> was last modified, or 0 if not known.  
     */
    public final long getLastModified()
    {
        return lastmod;
    }
    /**
     * Get a date range for current Horoscope sign in String format. eg 9/23-10/22
     * @return Date range String
     */
    public final String getSignInfo(HoroscopeSign hsign)
    {
        return hsign.getInfo();
    }
    /**
     * Get a year list for current Zodiac sign in String format. eg 1976, 1988, 2000, 2012
     * @return Year list as String
     */
    public final String getSignInfo(ZodiacSign zsign)
    {
        return zsign.getInfo();
    }
    /**
     * <p>Get the Special symbol for current Horoscope sign in String format. eg ♎</p>
     * <p>Note that the symbol is Non-ASCII. It may not display correctly</p>
     * @return Special symbol as String
     */
    public final String getSignSymbol(HoroscopeSign hsign)
    {
        return hsign.getSymbol();
    }
    /**
     * <p>Get the Chinese symbol for current Zodiac sign in String format. eg 龙</p>
     * <p>Note that the symbol is Non-ASCII. It may not display correctly</p>
     * @return Chinese symbol as String
     */
    public final String getSignSymbol(ZodiacSign zsign)
    {
        return zsign.getSymbol();
    }
    public static void main(String[] args) throws Exception
    {
        HoroscopeAPI api = new HoroscopeAPI(100);
        System.out.println(api.getHoroscopeReading(HoroscopeSign.Virgo, HoroscopeReading.Career));
    }
}
