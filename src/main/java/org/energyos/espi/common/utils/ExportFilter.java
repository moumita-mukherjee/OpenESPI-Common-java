package org.energyos.espi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.energyos.espi.common.domain.AtomPeriod;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;

@SuppressWarnings("restriction")
public class ExportFilter {
	private Map<String, String> params;
	private int matchedCounter = 0, emittedCounter = 0;
	private AtomPeriod filterPeriod = new AtomPeriod();
	private boolean subscriptionReq=false;

	public boolean isSubscriptionReq() {
		return subscriptionReq;
	}

	public void setSubscriptionReq(boolean subscriptionReq) {
		this.subscriptionReq = subscriptionReq;
	}

	public AtomPeriod getFilterPeriod() {
		return filterPeriod;
	}

	public void setFilterPeriod(AtomPeriod filterPeriod) {
		this.filterPeriod = filterPeriod;
	}

	public ExportFilter(Map<String, String> params) throws Exception{
		this.params = params;
		// update the filter period
		filterPeriod = new AtomPeriod();
		if (hasParam("published-min")) {
			filterPeriod.getPublishedMin().setTime(toTime("published-min"));
		}
		if (hasParam("published-max")) {
			filterPeriod.getPublishedMax().setTime(toTime("published-max"));
		}
		if (hasParam("updated-min")) {
			filterPeriod.getUpdatedMin().setTime(toTime("updated-min"));
		}
		if (hasParam("updated-max")) {
			filterPeriod.getUpdatedMax().setTime(toTime("updated-max"));
		}
		if (hasParam("usage-min")) {
			filterPeriod.setUsageMin(Long.parseLong(params.get("usage-min").trim()));
		}
		if (hasParam("usage-max")) {
			filterPeriod.setUsageMax(Long.parseLong(params.get("usage-max").trim()));
		}
		if (hasParam("subscription")) {
			setSubscriptionReq("true".equalsIgnoreCase(params.get("subscription").trim()));
		}
	}

	public boolean matches(EntryType entry) throws Exception {
		System.err.println(entry.isVisited() + " ---- "+entry + "  --- "+entry.getContent().getResource());
		if(entry.isVisited()) {
			return false;
		}
		entry.setVisited(true);
		
		if(subscriptionReq) {
			//for synchronous data transfer request send master relational data irrespective of any filter parameter
			//only interval block will be based on the time parameter
			if (entry.getContent().getResource() instanceof ReadingType) {
				return true;
			}
			if (entry.getContent().getResource() instanceof TimeConfiguration) {
				return true;
			}
			if (entry.getContent().getResource() instanceof MeterReading) {
				return true;
			}
			if (entry.getContent().getResource() instanceof UsagePoint) {
				return true;
			}
		}
		if (hasParam("max-results")) {
			if (!(params.get("max-results").equals("All"))) {
				if (emittedCounter >= Integer.valueOf(params.get("max-results").trim())) {
					return false;
				}
			}
		}
		if (hasParam("published-max")) {
			if (!(params.get("published-max").equals("All"))) {
				if (toTime("published-max") < toTime(entry.getPublished())) {
					return false;
				}
			}
		}
		if (hasParam("published-min")) {
			if (!(params.get("published-min").equals("All"))) {
				if (toTime("published-min") > toTime(entry.getPublished())) {
					return false;
				}
			}
		}

		if (hasParam("updated-max")) {
			if (!(params.get("updated-max").equals("All"))) {
				if (toTime("updated-max") < toTime(entry.getUpdated())) {
					return false;
				}
			}
		}

		if (hasParam("updated-min")) {
			if (!(params.get("updated-min").equals("All"))) {
				if (toTime("updated-min") > toTime(entry.getUpdated())) {
					return false;
				}
			}
		}

		if (hasParam("start-index")) {
			if (++matchedCounter < Integer.valueOf(params.get("start-index").trim())) {
				return false;

			}
		}

		if (hasParam("depth")) {
			if (!(params.get("depth").equals("All"))) {
				if (emittedCounter > Integer.valueOf(params.get("depth").trim())) {
					return false;
				}
			}
		}
		if (hasParam("reading-type") || hasParam("direction")) {


			List<IntervalBlock> blocks = entry.getContent().getIntervalBlocks();
			if (blocks != null && blocks.size() > 0) {
				IntervalBlock block = blocks.get(0);
				if (hasParam("reading-type")) {
					if (block.getMeterReading() == null
							|| !params.get("reading-type").trim().equals(block.getMeterReading().getReadingType().getUom())) {
						return false;
					}
				}
				if (hasParam("direction")) {
					if (block.getMeterReading() == null
							|| !params.get("direction").trim().equals(
									block.getMeterReading().getReadingType().getFlowDirection())) {
						return false;
					}
				}
			}

			if (entry.getContent().getResource() instanceof MeterReading) {
				MeterReading mr = (MeterReading) entry.getContent().getResource();
				if (hasParam("reading-type")) {
					if (!params.get("reading-type").trim().equals(mr.getReadingType().getUom())) {
						return false;
					}
				}
				if (hasParam("direction")) {
					if (!params.get("direction").trim().equals(mr.getReadingType().getFlowDirection())) {
						return false;
					}
				}
			}
			if (entry.getContent().getResource() instanceof ReadingType) {
				ReadingType rt = (ReadingType) entry.getContent().getResource();
				if (hasParam("reading-type")) {
					if (!params.get("reading-type").trim().equals(rt.getUom())) {
						return false;
					}
				}
				if (hasParam("direction")) {
					if (!params.get("direction").trim().equals(rt.getFlowDirection())) {
						return false;
					}
				}
			}
		}

		emittedCounter++;
		return true;
	}

	private boolean hasParam(String paramName) {
		return params.get(paramName) != null;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	private long toTime(String key) throws Exception {
		String param = params.get(key);
		// return XMLGregorianCalendarImpl.parse(param).toGregorianCalendar()
		// .getTimeInMillis();
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = null;
		
		date = sdf.parse(param.trim());
		
		return date.getTime();
	}

	private long toTime(DateTimeType published) {
		return published.getValue().toGregorianCalendar().getTimeInMillis();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExportFilter that = (ExportFilter) o;

		return params.equals(that.params);

	}

	@Override
	public int hashCode() {
		return params.hashCode();
	}
}
