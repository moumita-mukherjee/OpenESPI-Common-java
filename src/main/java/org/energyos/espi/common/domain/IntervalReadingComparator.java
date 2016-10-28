package org.energyos.espi.common.domain;

import java.util.Comparator;

public class IntervalReadingComparator implements Comparator<IntervalReading> {
	@Override
	public int compare(IntervalReading o1, IntervalReading o2) {
		return o1.getTimePeriod().getStart().compareTo(o2.getTimePeriod().getStart());
	}
}
