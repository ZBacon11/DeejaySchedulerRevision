import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class DJ {
    private String djName;

    private List<Schedule> bookings;

    public DJ(String name) {
        djName = name.toLowerCase();
        bookings = new ArrayList<>();
        try (Scanner fileReader = new Scanner (new File("Schedule.txt"))) {
            fileReader.nextLine(); // skips first line
            while (fileReader.hasNext()) {
                Schedule s = new Schedule(fileReader.nextLine());
                if (s.getDJName().equals(djName)) {
                    bookings.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
    }

    public String getDJName() {
        return djName;
    }

    public List<Schedule> djDateStatus(LocalDateTime date) {
        List<Schedule> result = new ArrayList<>();
        for (Schedule x : bookings) {
            if (date.equals(x.getDate())) {
                result.add(x);
            }
        }
        return result;
    }

    public boolean isAvailable(LocalDateTime date, String timeslot) {
        List<Schedule> dateBookings = djDateStatus(date);
        for (Schedule x : dateBookings) {
            if (timeslot.equals(x.getTimeslot())) {
                return false;
            }
        }
        return true;
    }


    public List<Schedule> djStatus () {
        return bookings;
    }

    @Override
    public String toString() {
        return "DJ: " + djName + "\nCurrent Bookings: \n" + bookings;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
