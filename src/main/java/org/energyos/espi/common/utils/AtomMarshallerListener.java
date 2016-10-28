package org.energyos.espi.common.utils;

import java.util.List;

import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtomMarshallerListener extends Marshaller.Listener {
	
	private Logger log = LoggerFactory.getLogger(AtomMarshallerListener.class);

	private String hrefFragment;
	private String entryFragment;
	private List<String> relRefList;
	private Long subscriptionId;

	long depth;

	public AtomMarshallerListener(String fragment) {
		this.hrefFragment = fragment;
	}

	public void setRelList(List<String> relRefList) {
		this.relRefList = relRefList;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	@Override
	public void beforeMarshal(Object source) {
		depth++;
		if (source instanceof EntryType) {
			log.info("Inside 1st if of  beforeMarshal method>>");
			ContentType content = ((EntryType) source).getContent();
			this.entryFragment = content.buildSelfHref(subscriptionId, hrefFragment);
			List<String> relatedLinks = content.buildRelHref(subscriptionId, hrefFragment);
			EntryType et = (EntryType) source;
			if (!relatedLinks.isEmpty()) {
				for (LinkType lt : et.getLinks()) {
					if (lt.getRel().equals("related")) {
						if ((!relatedLinks.isEmpty()) && (relatedLinks.get(0) != null)) {
							lt.setHref(relatedLinks.remove(0));
						}
					}
				}
			}
			if (!relatedLinks.isEmpty()) {
				for (String link : relatedLinks) {
					et.addRelatedLink(link);
				}
			}
		}
		if ((source instanceof LinkType) && (((LinkType) source).getRel().equals("self"))) {
			log.info("Inside 2nd if of beforeMarshal method >>");
			((LinkType) source).setHref(mutateFragment((LinkType) source, this.entryFragment, 0));
		}
		if ((source instanceof LinkType) && (((LinkType) source).getRel().equals("up"))) {
			log.info("Inside 3rd if beforeMarshal method>>");
			((LinkType) source).setHref(mutateFragment((LinkType) source, this.entryFragment, 1));
		}
		if ((source instanceof DateTimeType)) {
			XMLGregorianCalendar xmlCal = ((DateTimeType) source).getValue();
			XMLGregorianCalendar xmlCal1 = xmlCal.normalize();
			((DateTimeType) source).setValue(xmlCal1);
		}
	}

	@Override
	public void afterMarshal(Object source) {
		depth--;
	}

	public String getHrefFragment() {
		return this.hrefFragment;
	}

	
	private String mutateFragment(Object source, String hrefFragment, Integer key) {
		String temp = hrefFragment;
		switch (key) {
		case 0: 
			break;
		case 1: 
			Integer i = temp.lastIndexOf("/");
			String t = temp.substring(i + 1);
			if (t.matches("-?\\d+(\\.\\d+)?")) {
				temp = temp.substring(0, i);
			}
			break;
		default:
			break;
		}
		return temp;

	}
}