package gupta.app.assignmentmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class Task {
	
	private int id;
	private String subject;
	private String title;
	private Long date;
	
	public Task(int id, String subject, String title, Long date) {
		this.subject = subject;
		this.title = title;
		this.date = date;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getDate() {
		return date;
	}
	
	public void setDate(Long date) {
		this.date = date;
	}
	
	
	/**
	 * 
	 * @param date  consumes a calendar object
	 * @return      a long number to store in the db
	 */
	public long getIntegerAsDate(Calendar date) {
		return date.getTimeInMillis();
	}
	


}
