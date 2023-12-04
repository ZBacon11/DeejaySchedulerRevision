/*
NOTES FROM 10/25
1. Keep backup copies of your data files in case write file messes up
2. two (2) toString() methods
    - one is a regular toString() to print out to screen, toString() -> Bob, Allan, 10/31/23, Afternoon
    - the other is to write to a file, call it toFileString() -> Customer: Bob, DJ: Alan, Data: 10/31/23, Afternoon
3. Build Constructors that read line from file or take it in as parameter and processes it in constructors
    - constructor that takes a single string & splits into parts | parameter = "Bob, DJ, Alan, 10/31/23, Afternoon"
    - constructor for bookings that takes a request & a dj
4. Make a class for bookings (Schedule) and request at the very least

Order suggested we do this:
    1. Menu Dispatch
    2. File IO
    3. Status operations (add third status operation to see waiting list?)
    4. Schedule (can use status to test schedule)
    5. Cancel
    6. Sign Up
    7. Dropout
    8. Quit
*/
import java.time.LocalDateTime;
public class Schedule {
    private LocalDateTime date;

    private String djName;

    private String cName;
    private String timeslot;

    public Schedule(String cName, String djName, LocalDateTime date, String timeslot) {
        this.djName = djName;
        this.date = date;
        this.cName = cName;
        this.timeslot = timeslot;
    }

    public Schedule (String sFromFile) {
        String [] scheduleData = sFromFile.split(",");
//        System.out.println(scheduleData);
        cName = scheduleData[0].strip();
        djName = scheduleData[1].strip();
        date = LocalDateTime.parse(scheduleData[2].strip());
        timeslot = scheduleData[3].strip();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDJName() {
        return djName;
    }

    public void setDJName(String name) {
        djName = name;
    }

    public String getCname() {
        return cName;
    }

    public void setCname(String cname) {
        this.cName = cname;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    @Override
    public String toString() {
        return "Customer: " + cName + ", DJ: " + djName + ", Date: " + date + ", Timeslot: " + timeslot;
    }

    public String toFileString() {
        return cName + ", " + djName + ", " + date + ", " + timeslot;
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
