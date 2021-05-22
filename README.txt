Application Title: Scheduling Application

Purpose:    	This Application allows for the user to add/modify appointments
	        and customers in a pre-existing database. The user can view scheduled
	        appointments in either a graphical calendar or in a table. The 			user can also view reports generated on metrics of appointment 			activity.

Version: 1.00
Date: 05/20/2021

Author Information: Caleb Reigada, creigad@wgu.edu/reigadacaleb@gmail.com

IDE: IntelliJ IDEA 2021.1
JDK: Java SE 11.0.9
JavaFX SDK: JavaFX-SDK-11.0.2
MySQL Connector Driver: mysql-connector-java-8.0.24

Directions:     The app must be connected to a mysql database that is setup with
                the proper schema. To do this, recreate the schema in the 
		'dbSchema' folder. The url, user, and password for the database
		must be input as variables in the src/main/db/DBConnection.java 		file.

Login	        Enter credentials (username/password) that match a record in the
		application database to proceed.
		The Login page will display everything (including error messages)
		in French if French is the system language.

Home            Navigate through the graphical calendar by pressing the buttons on
	    	the bottom of the page. This will shift the calendar by one month
		in the respective direction. Scheduled appointments will be
		displayed in the calendar showing the start/end time and the
		title.

Appointments	Appointments will be displayed in the table and filtered according
		to the radio button selected. By default all appointments will be
		displayed. When filtering by week/month, use the buttons on the
		top of the page to move 1 week/month respectively in the direction
		corresponding to the button. In order to delete or update an
		existing appointment, an appointment in the table needs to bes 
		selected.

Add/Update	When adding or updating an appointment all fields need to be
Appointments	filled out. A start and end time cannot be chose until a date for
		the appointment is selected. Once a start or end time is initially
		selected the options for the alternative time is reduced to a
		maximum of 4 (This company has a maximum appointment length of 2
		hours and each appointment length is in blocks of 30 minutes).

Customers	Customers will all be displayed in the table. In order to
		delete or update an existing customer, a customer in the table
		needs to be selected.

Add/Update	When adding or updating a customer all fields need to be 
Customers	filled in. The first level division cannot be selected until a
		country is selected.

Reports		One of three reports is displayed in the table. Use the drop
		down at the top of the page to change which report is displayed.
