
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
class WaitList {
    private String customer;

    private LocalDateTime date;

    private String timeslot;

    public WaitList(String sFromFile) {
        String [] waitlist = sFromFile.split(",");
        customer = waitlist[0].strip();
        date = LocalDateTime.parse(waitlist[1].strip());
        timeslot = waitlist[2].strip();
    }

    public WaitList(String wCustomer, LocalDateTime wDate, String wTimeslot) {
        customer = wCustomer;
        date = wDate;
        timeslot = wTimeslot;
    }

    public String getCustomer() {
        return customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void checkWaitlist(String dj) {
        List<WaitList> waitLists = Main.readWaitinglist();
        List<WaitList> wList = new ArrayList<>(waitLists);
        List<Schedule> schedules = Main.readSchedule();
        DJ newDJ = new DJ(dj);
        for (WaitList w : waitLists) {
            List<String> availability = new ArrayList<>();
            List<Schedule> checkDate = newDJ.djDateStatus(w.getDate());
            if (checkDate.isEmpty()) {
                Schedule s = new Schedule(w.getCustomer(), dj, w.getDate(), w.getTimeslot());
                wList.remove(w);
                schedules.add(s);
                break;
            } else {
                if (checkDate.size() >= 3) {
                    break;
                }
                for (Schedule x : checkDate) {
                    if (w.getTimeslot().equals(x.getTimeslot())) {
                        availability.add("Yes");
                    } else {
                        availability.add("No");
                    }
                }
            }

            if (!availability.contains("Yes")) {
                wList.remove(w);
                Schedule s = new Schedule(w.getCustomer(), dj, w.getDate(), w.getTimeslot());
                schedules.add(s);
            }
        }
        Main.updateWaitList(wList);
        Main.updateSchedule(schedules);
    }

    public String toFileString() {
        return customer + ", " + date + ", " + timeslot;
    }
}