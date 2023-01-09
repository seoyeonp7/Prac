package kr.or.ddit.servlet08;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.*; // static import->이거 쓰면 Calendar.DAY_OF_MONTH 이렇게 길게 안써도 됨

import java.text.DateFormatSymbols;

public class CalendarWrapper {
	private Calendar adaptee;
	private Locale locale;
	
	private int offset;
	private int dayOfWeekFirst;
	private int lastDate;
	private int beforeYear;
	private int beforeMonth;
	private int currentYear;
	private int currentMonth;
	private int nextYear;
	private int nextMonth;
	
	private String[] weekDays;
	private String[] months;

	public CalendarWrapper(Calendar adaptee, Locale locale) { // 만들어지는 순간 기본생성자가 없어짐~! 왜?
		super();
		this.adaptee = adaptee;
		this.locale = locale;

		DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
		weekDays = dfs.getWeekdays(); // 일주일이 7일이라는 데이터가 들어있음~
		months = dfs.getMonths();

		adaptee.set(DAY_OF_MONTH, 1);
		dayOfWeekFirst = adaptee.get(DAY_OF_WEEK); // 오늘 요일의 숫자를 가져옴
		offset = dayOfWeekFirst - SUNDAY;
		lastDate = adaptee.getActualMaximum(DAY_OF_MONTH);

		currentYear = adaptee.get(YEAR); // 현재 년도
		currentMonth = adaptee.get(MONTH);
		
		adaptee.add(MONTH, -1); // 현재 달의 전 달
		beforeYear = adaptee.get(YEAR);
		beforeMonth = adaptee.get(MONTH);

		adaptee.add(MONTH, 2); // 달력이 전 달로 가있으니까 +2를 하고 현재달의 다음달로 넘어감
		nextYear = adaptee.get(YEAR);
		nextMonth = adaptee.get(MONTH);

		adaptee.add(MONTH, -1);
	}

	public Calendar getAdaptee() {
		return adaptee;
	}

	public void setAdaptee(Calendar adaptee) {
		this.adaptee = adaptee;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getDayOfWeekFirst() {
		return dayOfWeekFirst;
	}

	public void setDayOfWeekFirst(int dayOfWeekFirst) {
		this.dayOfWeekFirst = dayOfWeekFirst;
	}

	public int getLastDate() {
		return lastDate;
	}

	public void setLastDate(int lastDate) {
		this.lastDate = lastDate;
	}

	public String[] getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String[] weekDays) {
		this.weekDays = weekDays;
	}

	public int getBeforeYear() {
		return beforeYear;
	}

	public void setBeforeYear(int beforeYear) {
		this.beforeYear = beforeYear;
	}

	public int getBeforeMonth() {
		return beforeMonth;
	}

	public void setBeforeMonth(int beforeMonth) {
		this.beforeMonth = beforeMonth;
	}

	public int getNextYear() {
		return nextYear;
	}

	public void setNextYear(int nextYear) {
		this.nextYear = nextYear;
	}

	public int getNextMonth() {
		return nextMonth;
	}

	public void setNextMonth(int nextMonth) {
		this.nextMonth = nextMonth;
	}
	
	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return String.format(locale, "%1$tY, %1$tB", adaptee);
	}
}