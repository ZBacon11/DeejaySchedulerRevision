import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
      String city = "DefaultCity"; 

      if(args.length > 0){
        city = args[0];
      }
      
        menu(city);

//        Schedule s = new Schedule("BJ, Tim, 10/27/23, Late Night");
//        writeSchedule(s);
    }

    //functions
    public static void menu(String city) {
        Scanner scan = new Scanner(System.in);
        System.out.println("=== MENU ===");
        System.out.println("What are you looking to do? (Enter either Schedule, Cancel, Signup, Dropout, DJ Status, Date Status). Enter Quit to stop.");
        String response = scan.nextLine().toLowerCase();
        switchCase(response, city);
    }

    // Added breaks to switch so only one case occurs at a time
    private static void switchCase(String response, String city) {// doesn't handle incorrect input
        Scanner scan = new Scanner(System.in);
        switch (response) {
            case ("schedule"):
                // enter schedule logic
                System.out.println("Enter customer name: ");
                String scheduleCustomer = scan.nextLine().toLowerCase();
                System.out.println("Enter date and time (MM/DD/YY HH): ");
                String scheduleDateString = scan.nextLine().toLowerCase();
                LocalDateTime scheduleDate = LocalDateTime.parse(scheduleDateString, DateTimeFormatter.ofPattern("MM/dd/yy HH"));
                System.out.println("Enter timeslot (Afternoon, Early Evening, Late Night): ");
                String scheduleTimeslot = scan.nextLine().toLowerCase();
                schedule(scheduleCustomer, scheduleDate, scheduleTimeslot);
                System.out.println("What are you looking to do? (Enter either Schedule, Cancel, Signup, Dropout, DJ Status, Date Status). Enter Quit to stop.");
                String x5 = scan.nextLine();
                x5 = x5.toLowerCase();
                if (!x5.equals("quit")) {
                    switchCase(x5, "");
                } else {
                    break;
                }
            case ("cancel"):
                // enter cancel logic
                System.out.println("Enter customer name: ");
                String cancelCustomer = scan.nextLine().toLowerCase();
                System.out.println("Enter date and time (MM/DD/YY HH): ");
                String cancelDateString = scan.nextLine().toLowerCase();
                LocalDateTime cancelDate = LocalDateTime.parse(cancelDateString, DateTimeFormatter.ofPattern("MM/dd/yy HH"));
                System.out.println("Enter timeslot (Afternoon, Early Evening, Late Night): ");
                String cancelTimeslot = scan.nextLine().toLowerCase();
                cancel(cancelCustomer, cancelDate, cancelTimeslot);
                System.out.println("What are you looking to do? (Enter either Schedule, Cancel, Signup, Dropout, DJ Status, Date Status). Enter Quit to stop.");
                String x4 = scan.nextLine();
                x4 = x4.toLowerCase();
                if (x4.equals("quit")) {
                    System.exit(0);
                } else {
                    switchCase(x4, "");
                }
            case ("signup"):
                List<String> djs = readDJName("");
                List<WaitList> waitLists = readWaitinglist("");
                List<Schedule> schedules = readSchedule("");
                System.out.println("Enter the name of the DJ you are signing up: ");
                String newDJ = scan.nextLine().toLowerCase();
                djs.add(newDJ);
                updateDj(djs);
                for (WaitList x : waitLists) {
                    x.checkWaitlist(newDJ);
                }
                System.out.println("What are you looking to do? (Enter either Schedule, Cancel, Signup, Dropout, DJ Status, Date Status). Enter Quit to stop.");
                String x3 = scan.nextLine().toLowerCase();
                if (x3.equals("quit")) {
                    System.exit(0);
                } else {
                    switchCase(x3, "");
                }

            case ("dropout"):
                // dropout logic
                System.out.println("Enter the Dj you would like to drop");
                String the_dj = scan.nextLine().toLowerCase();
                DJ the_dj1 = new DJ(the_dj);
                List<Schedule> djstatus = the_dj1.djStatus();
                List<Schedule> true_bookings = readSchedule("");
                List<String> djlist = readDJName("");
                djlist.remove(the_dj.toLowerCase());
                List<Schedule> removes = new ArrayList<>();
                for (Schedule s : true_bookings) {
                    if (s.getDJName().equals(the_dj)) {
                        removes.add(s);
                    }
                }
                for (Schedule ss : removes) {
                    true_bookings.remove(ss);
                }
                updateSchedule(true_bookings, "");
                updateDj(djlist);
                List<WaitList> droppedCustomers = new ArrayList<>();
                for (Schedule s : djstatus) {
                    schedule_dropout(s.getCname(),s.getDate(),s.getTimeslot());
                };
                System.out.println("What are you looking to do? (Enter either Schedule, Cancel, Signup, Dropout, DJ Status, Date Status). Enter Quit to stop.");
                String x = scan.nextLine();
                x = x.toLowerCase();
                if (x.equals("quit")) {
                    System.exit(0);
                } else {
                    switchCase(x, "");
                }
            case ("dj status"):
                // enter dj status logic
                System.out.println("Type in DJ name: ");
                String djName = scan.nextLine();
                DJ dj = new DJ(djName);
                List<Schedule> djStatus = dj.djStatus();
                if (djStatus.isEmpty()) {
                    System.out.println(djName + " does not have any bookings.");
                } else {
                    System.out.println(djName + " Status:\n" + djStatus);
                }
                System.out.println("What are you looking to do? (Enter either Schedule, Cancel, Signup, Dropout, DJ Status, Date Status). Enter Quit to stop.");
                String x2 = scan.nextLine();
                x2 = x2.toLowerCase();
                if (x2.equals("quit")) {
                    System.exit(0);
                } else {
                    switchCase(x2, "");
                }
            case ("date status"):
                // enter date status logic
                System.out.println("Type in Date (USE MM/DD/YY)");
                String date = scan.nextLine();
                List<Schedule> dateList = dateStatus(LocalDateTime.parse(date));
                if (dateList.isEmpty()) {
                    System.out.println("There are no dates booked for " + date);
                } else {
                    System.out.println(date + " Bookings:\n" + dateList);
                }
                System.out.println("What are you looking to do? (Enter either Schedule, Cancel, Signup, Dropout, DJ Status, Date Status). Enter Quit to stop.");
                String x1 = scan.nextLine();
                x1 = x1.toLowerCase();
                if (x1.equals("quit")) {
                    System.exit(0);
                } else {
                    switchCase(x1, "");
                }
            case ("quit"):
                System.exit(0);
            default:
                System.out.println("Sorry that's not a correct input, try again!");
                String new_response = scan.nextLine().toLowerCase();
                switchCase(new_response, "");
                if (new_response.equals("quit")) {
                    System.exit(0);
                } else {
                    switchCase(new_response, "");
                }
                // enter quit logic, could use default needs testing
        }
    }

    // modified read methods to return the files contents as an array, might have to change to deque
    public static List<String> readDJName(String city) { // equivalent to Start()
        List<String> result = new ArrayList<>();
        try (Scanner fileReader = new Scanner(new File(city + "DeejayName.txt"))) {
            while (fileReader.hasNext()) {
                result.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        return result;
    }

    public static List<Schedule> readSchedule(String city) { // are we keeping track of List<Schedule> or are we to just read it from the file each time since we already have it
        List<Schedule> list = new ArrayList<>();
        try (Scanner fileReader = new Scanner(new File(city + "Schedule.txt"))) {
            fileReader.nextLine(); // skips first line
            while (fileReader.hasNext()) {
                Schedule s = new Schedule(fileReader.nextLine());
                list.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        return list;
    }

    public static List<WaitList> readWaitinglist(String city) {
        List<WaitList> list = new ArrayList<>();
        try (Scanner fileReader = new Scanner(new File(city + "WaitList.txt"))) {
            fileReader.nextLine(); // skips first line
            while (fileReader.hasNext()) {
                // Might have to be modified depending on how waiting list format is
                WaitList w = new WaitList(fileReader.nextLine());
                list.add(w);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        return list;
    }

    public static void scheduleStatus() {
        try (Scanner fileReader = new Scanner(new File("Schedule.txt"))) {
            fileReader.nextLine(); // skips first line
            while (fileReader.hasNext()) {
                Schedule s = new Schedule(fileReader.nextLine());
                System.out.println(s.toString());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
    }

    public static List<Schedule> dateStatus(LocalDateTime date) {
        List<Schedule> result = new ArrayList<Schedule>();
        try (Scanner fileReader = new Scanner(new File("Schedule.txt"))) {
            fileReader.nextLine(); // skips first line
            while (fileReader.hasNext()) {
                Schedule s = new Schedule(fileReader.nextLine());
                if (s.getDate().equals(date)) {
                    result.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        return result;
    }

    // Returns all the bookings for a specific DJ basically DJ status in a method
    public static List<Schedule> DjSchedule(String Dj) {
        List<Schedule> list = readSchedule("");
        List<Schedule> list1 = new ArrayList<>();
        Dj = Dj.toLowerCase();
        int count = 0;
        try (Scanner fileReader = new Scanner(new File("Schedule.txt"))) {
            fileReader.nextLine(); // skips first line
            while (fileReader.hasNext()) {
                Schedule s = new Schedule(fileReader.nextLine());
                if (s.getDJName().equals(Dj)) {
                    list1.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        return list1;
    }

    // Used to make sure that a DJ doesn't have conflicting date and timeslot
    public static Boolean Check(List<Schedule> list, Schedule s) {
        for (Schedule x : list) {
            if (s.getDate().equals(x.getDate()) && s.getTimeslot().equals(x.getTimeslot())) {
                return false;
            }
        }
        return true;
    }

    public static void waitListStatus() { // unfinished
        try (Scanner fileReader = new Scanner(new File("WaitList.txt"))) {
            fileReader.nextLine(); // skips first line
            while (fileReader.hasNext()) {
                System.out.println(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
    }

    // original write schedule takes file input
    public static void writeSchedule(Schedule newS, String s) { //
        try {
            FileWriter fileWriter = new FileWriter(s, true);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(newS.toFileString());

            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error Occurred: \n" + e.getMessage());
            System.exit(0);
        }
    }

    public static void writeWaitList(String customer, LocalDateTime date, String timeslot) {
        try {
            FileWriter fileWriter = new FileWriter("WaitList.txt", true);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(customer + ", " + date + ", " + timeslot);

            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error Occurred: \n" + e.getMessage());
            System.exit(0);
        }
    }

    // Handles "manipulation" of Schedule and WaitList txt(Granted their full of schedules)
    public static void updateSchedule(List<Schedule> list, String city) {
        try {
            File file = new File(city + "Schedule.txt");
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(city + "Schedule.txt", true);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Format - CustomerName, DJName, Date, Timeslot (Afternoon, Early Evening, or Late Night)");

            for (Schedule ss : list) {
                printWriter.println(ss.toFileString());
            }

            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error Occurred: \n" + e.getMessage());
            System.exit(0);
        }
    }

    public static void updateWaitList(List<WaitList> list, String city) {
        try {
            File file = new File(city + "WaitList.txt");
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(city + "WaitList.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Format - Date (MM/DD/YY) , Timeslot (Afternoon, Early Evening, or Late Night)");
            for (WaitList x : list) {
                printWriter.println(x.toFileString());
            }

            printWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error Occurred: \n" + e.getMessage());
            System.exit(0);
        }
    }

    // manipulation of DeejayName
    public static void updateDj(List<String> djs) {
        try {
            File file = new File("DeejayName.txt");
            file.delete();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter("DeejayName.txt", true);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (String s : djs) {
                printWriter.println(s);
            }

            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error Occurred: \n" + e.getMessage());
            System.exit(0);
        }
    }

    // Changed to accommodate file input, writes text into file of choice testing
    public static void write_to_file(String s, String text) { //
        try {
            FileWriter fileWriter = new FileWriter(text, true);

            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(s);

            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error Occurred: \n" + e.getMessage());
            System.exit(0);
        }
    }

    public static void cancel(String customer, LocalDateTime date, String timeslot) {
        List<Schedule> bookings = readSchedule("");
        List<WaitList> waitLists = readWaitinglist("");
        List<Schedule> bList = new ArrayList<>(bookings);
        List<WaitList> wList = new ArrayList<>(waitLists);
        String djName = "";
        for (Schedule x : bookings) {
            String bookedCustomer = x.getCname().toLowerCase();
            LocalDateTime bookedDate = x.getDate();
            String bookedTimeslot = x.getTimeslot().toLowerCase();

            if ((customer.equals(bookedCustomer) && date.equals(bookedDate) && timeslot.equals(bookedTimeslot))) {
                bList.remove(x);
                djName = x.getDJName();
            }
        }

        for (WaitList y : waitLists) {
            if (date.equals(y.getDate()) && timeslot.equals(y.getTimeslot())) {
                wList.remove(y);
                Schedule s = new Schedule(y.getCustomer(), djName, y.getDate(), y.getTimeslot());
                bList.add(s);
            }
        }
        updateWaitList(wList, "");
        updateSchedule(bList, "");
    }

    public static void schedule(String customer, LocalDateTime date, String timeslot) {
        List<String> djList = readDJName("");
        int minBookings = Integer.MAX_VALUE;
        String selectedDJ = "";

        for (String x : djList) {
            DJ currentDJ = new DJ(x);
            List<Schedule> currentDJBookings = currentDJ.djStatus();
            int numOfBookings = currentDJBookings.size();

            System.out.println("DJ: " + x + ", Bookings: " + numOfBookings);


            boolean conflict = false;

            for (Schedule sched : currentDJBookings) {
                if (date.equals(sched.getDate()) && timeslot.equals(sched.getTimeslot())) {
                    conflict = true;
                    break;
                }
            }

            System.out.println("Conflict: " + conflict);

            if (!conflict && numOfBookings <= minBookings) {
                minBookings = numOfBookings;
                selectedDJ = x;
            }

        }
        System.out.println("\nSelected DJ: " + selectedDJ);
        if (!selectedDJ.isEmpty()) {
            Schedule result = new Schedule(customer, selectedDJ, date, timeslot);
            writeSchedule(result, "Schedule.txt");
        } else {
            writeWaitList(customer, date, timeslot);
        }
    }

    public static void schedule_dropout(String customer, LocalDateTime date, String timeslot) {
        List<String> djList = readDJName("");
        boolean available = true;
        Schedule result = new Schedule(customer, "", date, timeslot);
        for (String x : djList) {
            DJ currentDJ = new DJ(x);
            result.setDJName(x);
            List<Schedule> currDJBookings = currentDJ.djStatus();
            if (currDJBookings.isEmpty()) {
                available = true;
                break;
            } else {
                for (Schedule y : currDJBookings) {
                    if (date.equals(y.getDate()) && timeslot.equals(y.getTimeslot())) {
                        available = false;
                        break;
                    } else {
                        result = new Schedule(customer, x, date, timeslot);
                    }
                }
            }
        }
        if (!available) {
            WaitList w = new WaitList(customer, date, timeslot);
            addFront(w);
        } else {
            writeSchedule(result, "Schedule.txt");
        }
    }

    public static void addFront(List<WaitList> waitLists) {
        try {
            List<WaitList> currentWaitingList = readWaitinglist("");
            File file = new File("WaitList.txt");
            file.delete();
            file.createNewFile();

            FileWriter fileWriter = new FileWriter("WaitList.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Format - Date (MM/DD/YY) , Timeslot (Afternoon, Early Evening, or Late Night)");

            for (WaitList x : waitLists) {
                printWriter.println(x.toFileString());
            }

            for (WaitList y : currentWaitingList) {
                printWriter.println(y.toFileString());
            }

            printWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error Occurred: " + e.getMessage());
            System.exit(0);
        }
    }

    public static void addFront(WaitList waitList) {
        try {
            List<WaitList> currentWaitingList = readWaitinglist("");
            File file = new File("WaitList.txt");
            file.delete();
            file.createNewFile();

            FileWriter fileWriter = new FileWriter("WaitList.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Format - Date (MM/DD/YY) , Timeslot (Afternoon, Early Evening, or Late Night)");


            printWriter.println(waitList.toFileString());


            for (WaitList y : currentWaitingList) {
                printWriter.println(y.toFileString());
            }

            printWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("Error Occurred: " + e.getMessage());
            System.exit(0);
        }
    }


}
