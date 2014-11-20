package org.energyos.espi.common.utils;

import java.io.Serializable;
import java.util.Properties;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.UsagePoint;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IncrementGenerator;
import org.hibernate.type.Type;

public class IdGenerator extends IncrementGenerator {
		
	@Override
	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		super.configure(type, params, dialect);
		
	}

	@Override
	public Serializable generate(SessionImplementor session, Object obj) {
		System.err.println("obj " + obj);
		if (obj instanceof RetailCustomer || obj instanceof UsagePoint || obj instanceof MeterReading) {
			IdentifiedObject rc = (IdentifiedObject) obj;
			String link = rc.getSelfLink().getHref();
			System.err.println("link " + link);
			return Long.parseLong(link.substring(link.lastIndexOf("/") + 1));
		}
		return super.generate(session, obj);
	}
}
