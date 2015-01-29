package org.energyos.espi.common.domain.ext;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "holidays")
@NamedQueries(value = {
		@NamedQuery(name = Holiday.QUERY_FIND_ALL, query = "SELECT hol FROM Holiday hol")		
})
public class Holiday {
	private static final long serialVersionUID = 8868869332997087911L;
	public static final String QUERY_FIND_ALL = "Holiday.findAll";
	

	@Id
	@Column(name = "holiday_id", insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "holiday_date", insertable = false, updatable = false)
	private Date holidayDate;
	@Column(name = "holiday_text", insertable = false, updatable = false)
	private String holidayText;

	public String getHolidayText() {
		return holidayText;
	}

	public void setHolidayText(String holidayText) {
		this.holidayText = holidayText;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public boolean equals(Holiday obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Holiday other = (Holiday) obj;

		return com.google.common.base.Objects.equal(this.holidayDate, other.holidayDate)
				&& com.google.common.base.Objects.equal(this.holidayText, other.holidayText);
	}

	/**
	 * Uses Google Guava to assist in providing hash code of this Hoep instance.
	 * 
	 * @return My hash code.
	 */
	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(this.holidayDate, this.holidayText);

	}

	/**
	 * Method using Google Guava to provide String representation of this
	 * IdentificationType instance.
	 * 
	 * @return My String representation.
	 */
	@Override
	public String toString() {
		return com.google.common.base.Objects.toStringHelper(this).addValue(this.holidayDate)
				.addValue(this.holidayText).toString();
	}
}