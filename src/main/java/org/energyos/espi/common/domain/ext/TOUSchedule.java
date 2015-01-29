package org.energyos.espi.common.domain.ext;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name = "tou_schedules")
@NamedQueries(value = {
		@NamedQuery(name = TOUSchedule.QUERY_FIND_ALL, query = "SELECT tou FROM TOUSchedule tou")		
})
public class TOUSchedule {
	private static final long serialVersionUID = 2039274056287015564L;

	public static final String QUERY_FIND_ALL = "TOUSchedule.findAll";
	
	@Id
	@Column(name = "tou_schedules_id", insertable = false, updatable = false)
	private Long id;
	@Column(name = "peak", insertable = false, updatable = false)
	private String peak;
	@Column(name = "season", insertable = false, updatable = false)
	private String season;
	@Column(name = "eff_date", insertable = false, updatable = false)
	private Date effDate;
	@Column(name = "end_date", insertable = false, updatable = false)
	private Date endDate;
	@Column(name = "eff_hour", insertable = false, updatable = false)
	private Time effHour;
	@Column(name = "end_hour", insertable = false, updatable = false)
	private Time endHour;
	@Column(name = "price", insertable = false, updatable = false)
	private Double price;

	public String getPeak() {
		return peak;
	}

	public void setPeak(String peak) {
		this.peak = peak;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Time getEffHour() {
		return effHour;
	}

	public void setEffHour(Time effHour) {
		this.effHour = effHour;
	}

	public Time getEndHour() {
		return endHour;
	}

	public void setEndHour(Time endHour) {
		this.endHour = endHour;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean equals(TOUSchedule obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TOUSchedule other = (TOUSchedule) obj;

		return com.google.common.base.Objects.equal(this.peak, other.peak)
				&& com.google.common.base.Objects.equal(this.season, other.season)
				&& com.google.common.base.Objects.equal(this.effDate, other.effDate)
				&& com.google.common.base.Objects.equal(this.endDate, other.endDate)
				&& com.google.common.base.Objects.equal(this.effHour, other.effHour)
				&& com.google.common.base.Objects.equal(this.endHour, other.endHour)
				&& com.google.common.base.Objects.equal(this.price, other.price);

	}

	public int hashCode() {
		return com.google.common.base.Objects.hashCode(this.peak, this.season, this.effDate, this.endDate,
				this.effHour, this.endHour, this.price);
	}

	public String toString() {
		return com.google.common.base.Objects.toStringHelper(this).addValue(this.peak).addValue(this.season)
				.addValue(this.effDate).addValue(this.endDate).addValue(this.effHour).addValue(this.endHour)
				.addValue(this.price).toString();
	}

	public boolean matches(Date date) {
		Calendar cal = Calendar.getInstance();
		// First match dates
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		if (cal.getTime().after(endDate) || cal.getTime().before(effDate)) return false;

		// Let's treat weekend and holiday as one case
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return "weekend".equalsIgnoreCase(season) || "holiday".equalsIgnoreCase(season);
		} else if ("weekend".equalsIgnoreCase(season) || "holiday".equalsIgnoreCase(season)) return false;

		cal.clear();
		cal.setTime(date);

		int h = cal.get(Calendar.HOUR_OF_DAY);
		// Hours are relative to the epoch
		cal.clear();
		cal.setTime(effHour);
		int effh = cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(endHour);
		cal.add(Calendar.MINUTE, 1); // A workaround for up-to-a-minute schedule
		int endh = cal.get(Calendar.HOUR_OF_DAY);

		boolean match;
		// If this is the night schedule, time must be shifted 12 hours
		if (effh > endh) {
			match = (h >= effh && h <= 23) || (h >= 0 && h < endh); // Looking
																	// backwards
		} else {
			match = h >= effh && h < endh;
		}
		return match;
	}

}