package pl.financemanager.gui;

public class Utils {

	public static String monthList[] = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	public static Integer yearList[] = { 2017, 2018, 2019 };

	public static String getMonth(int month) {
		String monthAsString = "January";
		switch (month) {
		case 0:
			monthAsString = "January";
			break;
		case 1:
			monthAsString = "February";
			break;
		case 2:
			monthAsString = "March";
			break;
		case 3:
			monthAsString = "April";
			break;
		case 4:
			monthAsString = "May";
			break;
		case 5:
			monthAsString = "June";
			break;
		case 6:
			monthAsString = "July";
			break;
		case 7:
			monthAsString = "August";
			break;
		case 8:
			monthAsString = "September";
			break;
		case 9:
			monthAsString = "October";
			break;
		case 10:
			monthAsString = "November";
			break;
		case 11:
			monthAsString = "December";
			break;
		default:
			break;
		}
		return monthAsString;

	}
}
