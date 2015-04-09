package osu.ceti.persuasionapi.core.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringHelper {

	/**
	 * Check if the given input string contains a value
	 * @param stringToVerify
	 * @return true if the string contains value; false if null or empty
	 */
	public static boolean isNotEmpty(String stringToVerify) {
		return (stringToVerify != null) && (stringToVerify.length() > 0);
	}
	
	/**
	 * Check if the given input string is null or contains an empty value
	 * @param stringToVerify
	 * @return true if the string contains value; false if null or empty
	 */
	public static boolean isEmpty(String stringToVerify) {
		return !isNotEmpty(stringToVerify);
	}
	
	/**
	 * Converts a date object to String of the format yyyy-MM-dd HH:mm:ss
	 * @param dateToBeConverted
	 * @return converted date string
	 */
	public static String dateToString(Date dateToBeConverted) {
		return getStringFormatter().format(dateToBeConverted);
	}
	
	/**
	 * Converts a given date string of the format yyyy-MM-dd HH:mm:ss to a date object
	 * @param dateString
	 * @return converted date object
	 * @throws ParseException
	 */
	public static Date toDateFromCharString(String dateString) throws ParseException {
		return getStringFormatter().parse(dateString);
	}
	
	/**
	 * Returns a date formatter for the string format yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	private static DateFormat getStringFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * Converts a given throwable object's stack trace into a string
	 * @param thorwable
	 * @return converted string stack trace
	 */
	public static String stackTraceToString(Throwable thorwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		thorwable.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
	}
}
