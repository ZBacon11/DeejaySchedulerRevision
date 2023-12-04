# Deejay-Scheduler
The everything is ran through Main.java

There are 3 objects we created: Schedule, DJ, and WaitList

We used a switch case for a menu which executes each function based on user input. 

Most of our functions are in Main unless they particulary apply to one of our objects.

Schedule process used schedule function - schedule(String customer, String date, String timeslot) Ask Tristian

Cancel process uses cancel function - cancel(String customer, String date, String timeslot) Ask Tristian

Signup process gets all DJs from file and puts it into a list, then uses that list to update the DeejayName.txt file. Then we used a waitlist function to check and register the new DJ to any new fittign appoinments

Dropout Logic handled in switch case and not a functions. Ask Langston

DJ Status uses the bookings instance variable which gets all of the bookings of the DJ with that DJ name, assumes all DJs are unique

Langston & Tristian
