package commppetterm.gui.page;

import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;

public class Controller {
    	
	/**
	 * <h1>Datenfelder der Klasse Connector</h1>
	 * Eine Klasse welche Daten eines Termins in Objekte umwandelt. Dieses Objekt kann anschleißend weiterverwendet werden durch implementierte Getter- und Settermethoden.
	 * 
	 * @param appointmentID 	Eindeutige ID des Termins
	 * @param title 			Überschrift des Termins
	 * @param repetition  		
	 */

	Integer appointmentID;
	String title;
	Integer repetition;
	Integer rgb;
	LocalDate dateStart;
	LocalDate dateEnd;
	String note;
	String location;
	Integer userID;

	public Connector(Integer id, String title, Integer repetition, int color, CharSequence dateStart,
			CharSequence dateEnd, String note, String location, Integer user) {
		super();
		this.appointmentID = id;
		this.title = title;
		this.repetition = repetition;
		if (color < 0) {
			color = color * -1;
		}
		if (color > 255) {
			color = color % 255;
		}
		this.rgb = color;
		this.dateStart = LocalDate.parse(dateStart);
		this.dateEnd = LocalDate.parse(dateEnd);
		this.note = note;
		this.location = location;
		this.userID = user;
	}

	public Integer getNumber() {
		return appointmentID;
	}

	public void setNumber(Integer id) {
		this.appointmentID = id;
	}

	public String getName() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public Integer getRepetition() {
		return repetition;
	}

	public void setRepetition(Integer repetition) {
		this.repetition = repetition;
	}

	public int getColor() {
		return rgb;
	}

	public void setColor(int color) {
		this.rgb = color;
	}

	public LocalDate getDateStart() {
		return dateStart;
	}

	public void setDateStart(CharSequence dateStart) {
		this.dateStart = LocalDate.parse(dateStart);
	}

	public LocalDate getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(CharSequence dateEnd) {
		this.dateEnd = LocalDate.parse(dateEnd);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public void deleteAppointment() {
		appointmentID = null;
		title = null;
		repetition = null;
		rgb = null;
		dateStart = null;
		dateEnd = null;
		note = null;
		location = null;
		userID = null;
	}
}
