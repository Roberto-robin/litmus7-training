import java.util.regex.Pattern;

public class ValidationUtil {

 
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$"); 
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"); 


    public static boolean isValidEmail(String email) 
    {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) 
    {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidJoinDate(String date) 
    {
        return date != null && DATE_PATTERN.matcher(date).matches();
    }

}
