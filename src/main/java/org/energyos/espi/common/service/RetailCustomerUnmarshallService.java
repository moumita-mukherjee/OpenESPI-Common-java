package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;

import org.energyos.espi.common.domain.IdentifiedObject;

public interface RetailCustomerUnmarshallService {

	List<IdentifiedObject> unmarshall(InputStream is);
}
