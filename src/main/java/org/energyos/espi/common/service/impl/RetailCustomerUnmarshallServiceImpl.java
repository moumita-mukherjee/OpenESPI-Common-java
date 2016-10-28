package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.SAXParserFactory;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.service.RetailCustomerUnmarshallService;
import org.energyos.espi.common.utils.RetailCustomerATOMContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class RetailCustomerUnmarshallServiceImpl implements
		RetailCustomerUnmarshallService {

	private Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);
    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;
    
    
    // this is a list of the UsagePointIds referenced during
    // this import
    private List<EntryType> entries;
    
    // Min Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar minUpdated = null;
    
    // Max Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar maxUpdated = null;
    
    
    
	@Override
	public List<IdentifiedObject> unmarshall(InputStream is) {


		// setup the parser\
		List<IdentifiedObject> identifiedObjectList =new ArrayList<IdentifiedObject>();
		try {

			JAXBContext context = marshaller.getJaxbContext();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XMLReader reader = factory.newSAXParser().getXMLReader();
			RetailCustomerATOMContentHandler atomContentHandler = new RetailCustomerATOMContentHandler(
					context);
			reader.setContentHandler(atomContentHandler);

			try {
				reader.parse(new InputSource(is));
			} catch (Exception e1) {
				log.error("Parse error", e1);
			}

			entries = atomContentHandler.getEntries();

			minUpdated = atomContentHandler.getMinUpdated();
			maxUpdated = atomContentHandler.getMaxUpdated();
			LinkType upLink = null;
			LinkType selfLink = null;
			Iterator<EntryType> its = entries.iterator();
			while (its.hasNext()) {
				EntryType entry = its.next();
				if (entry.getContent().getResource() != null) {
					for (LinkType link : entry.getLinks()) {
						if (LinkType.SELF.equalsIgnoreCase(link.getRel())) {
							selfLink = link;
						}
						if (LinkType.UP.equalsIgnoreCase(link.getRel())) {
							upLink = link;
						}

					}
					IdentifiedObject obj = entry.getContent().getResource();
					obj.setUpLink(upLink);
					obj.setSelfLink(selfLink);
					identifiedObjectList.add(obj);
				}
			}
		} catch (Exception ex) {
			log.error("Exception in import data :",ex);
		}
		return identifiedObjectList;
	}
}
