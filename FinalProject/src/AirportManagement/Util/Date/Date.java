package AirportManagement.Util.Date ;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
public class Date {
    static public String getDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);
        return dateTimeString ;
    }
    static public String formatDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null; // Handle null values gracefully
        }
        
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
    public static boolean isAfterOrEqual(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false; // Consider null or empty strings as invalid
        }

        try {
            // Parse the input dateString to LocalDateTime for comparison
            LocalDateTime inputDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String currentDateString = getDate(); // Get the current date as a string
            LocalDateTime currentDateTime = LocalDateTime.parse(currentDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Check if the input date is after or equal to the current time
            boolean result = !inputDateTime.isBefore(currentDateTime); // input is not before current time
            return result;
        } catch (Exception e) {
            // Handle invalid date format
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd HH:mm:ss");
        }
    }
}
