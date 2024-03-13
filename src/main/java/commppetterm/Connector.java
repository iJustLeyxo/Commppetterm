package de.commppetterm.kalender;

import java.time.LocalDate;
import java.time.LocalTime;

public class Connector {

	/**
	 * <h1>Datenfelder der Klasse Connector</h1> Eine Klasse welche Daten eines
	 * Termins in Objekte umwandelt. Dieses Objekt kann anschleißend weiterverwendet
	 * werden durch implementierte Getter- und Settermethoden.
	 * 
	 * @param appointmentID    Eindeutige ID des Termins
	 * @param title            Überschrift des Termins
	 * @param repetition       Anzahl der Wiederholungen des Termins im Kalender
	 * @param rgb              Integer zwischen 0 und 255, bestimmt die Farbe des
	 *                         Termins im Kalender
	 * @param date
	 * @param appointmentDate
	 * @param appointmentStart
	 * @param appointmentEnd
	 * @param note
	 * @param location
	 * @param userID
	 */

	private Integer appointmentID;
	private String title;
	private Integer repetition;
	private Integer rgb;
	private LocalDate appointmentDate;
	private LocalTime appointmentStart;
	private LocalTime appointmentEnd;
	private String note;
	private String location;
	private Integer userID;

	public Connector(Integer id, String title, Integer repetition, int color, CharSequence appointmentDate,
			CharSequence appointmentStart, CharSequence appointmentEnd, String note, String location, Integer user) {
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
		this.appointmentDate = LocalDate.parse(appointmentDate);
		this.appointmentStart = LocalTime.parse(appointmentStart);
		this.appointmentEnd = LocalTime.parse(appointmentEnd);
		this.note = note;
		this.location = location;
		this.userID = user;
	}

	/**
	 * @return the appointmentID
	 */
	public Integer getAppointmentID() {
		return appointmentID;
	}

	/**
	 * @param appointmentID the appointmentID to set
	 */
	public void setAppointmentID(Integer appointmentID) {
		this.appointmentID = appointmentID;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the repetition
	 */
	public Integer getRepetition() {
		return repetition;
	}

	/**
	 * @param repetition the repetition to set
	 */
	public void setRepetition(Integer repetition) {
		this.repetition = repetition;
	}

	/**
	 * @return the rgb
	 */
	public Integer getRgb() {
		return rgb;
	}

	/**
	 * @param rgb the rgb to set
	 */
	public void setRgb(Integer rgb) {
		this.rgb = rgb;
	}

	/**
	 * @return the appointmentDate
	 */
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	/**
	 * @param appointmentDate the appointmentDate to set
	 */
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	/**
	 * @return the appointmentStart
	 */
	public LocalTime getAppointmentStart() {
		return appointmentStart;
	}

	/**
	 * @param appointmentStart the appointmentStart to set
	 */
	public void setAppointmentStart(LocalTime appointmentStart) {
		this.appointmentStart = appointmentStart;
	}

	/**
	 * @return the appointmentEnd
	 */
	public LocalTime getAppointmentEnd() {
		return appointmentEnd;
	}

	/**
	 * @param appointmentEnd the appointmentEnd to set
	 */
	public void setAppointmentEnd(LocalTime appointmentEnd) {
		this.appointmentEnd = appointmentEnd;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the userID
	 */
	public Integer getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public void deleteAppointment() {
		appointmentID = null;
		title = null;
		repetition = null;
		rgb = null;
		appointmentStart = null;
		appointmentEnd = null;
		note = null;
		location = null;
		userID = null;
	}

}
