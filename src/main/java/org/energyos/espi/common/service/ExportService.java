package org.energyos.espi.common.service;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.energyos.espi.common.utils.ExportFilter;

public interface ExportService {
    // ApplicationInformation

    //  - ROOT form
    public void exportApplicationInformation(Long applicationInformationId, OutputStream stream, ExportFilter exportFilter) throws Exception;

    public void exportApplicationInformations(OutputStream stream, ExportFilter exportFilter) throws Exception;

    // Authorization
    //  - ROOT form
	public void exportAuthorization(Long authorizationId, OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportAuthorizations(OutputStream stream, ExportFilter exportFilter) throws Exception;

    //  - XPath form
	public void exportAuthorization(Long retailCustomerId, Long authorizationId, OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportAuthorizations(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws Exception;


    // ElectricPowerQualitySummary
    //  - ROOT form

	void exportElectricPowerQualitySummary_Root(Long subscriptionId, Long electricPowerQualitySummaryId, OutputStream outputStream, ExportFilter exportFilter) throws Exception;

	void exportElectricPowerQualitySummarys_Root(Long subscriptionId, OutputStream outputStream, ExportFilter exportFilter) throws Exception;

    //  - XPath form
	public void exportElectricPowerQualitySummary(Long subscriptionId, Long retailCustomerId, Long usagePointId, Long electricPowerQualitySummaryId,
						      OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportElectricPowerQualitySummarys(Long subscriptionId, Long retailCustomerId, Long usagePointId,
						       OutputStream stream, ExportFilter exportFilter) throws Exception;


    // ElectricPowerUsageSummary
    //  - ROOT form

	void exportElectricPowerUsageSummarys_Root(Long subscriptionId, ServletOutputStream outputStream,
			ExportFilter exportFilter) throws Exception;

	void exportElectricPowerUsageSummary_Root(Long subscriptionId, long electricPowerUsageSummaryId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws Exception;

    //  - XPath form

	public void exportElectricPowerUsageSummary(Long subscriptionId, Long retailCustomerId, Long usagePointId, Long electricPowerUsageSummaryId,
						    OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportElectricPowerUsageSummarys(Long subscriptionId, Long retailCustomerId, Long usagePointId,
						     OutputStream stream, ExportFilter exportFilter) throws Exception;


    // IntervalBlock
    //  - ROOT form
	void exportIntervalBlocks_Root(Long subscriptionId, OutputStream outputStream,
			ExportFilter exportFilter) throws Exception;

	void exportIntervalBlock_Root(Long subscriptionId, Long intervalBlockId,
			OutputStream outputStream, ExportFilter exportFilter) throws Exception;

    //  - XPath form

	public void exportIntervalBlock(Long subscriptionId, Long retailCustomerId, Long usagePointId, Long meterReadingId, Long intervalBlockId,
					OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportIntervalBlocks(Long subscriptionId, Long retailCustomerId, Long usagePointId, Long meterReadingId,
					 OutputStream stream, ExportFilter exportFilter) throws Exception;

	// MeterReading
    //  - ROOT form
	void exportMeterReadings_Root(Long subscriptionId, ServletOutputStream outputStream, ExportFilter exportFilter) throws Exception ;

	void exportMeterReading_Root(Long subscriptionId, long meterReadingId, ServletOutputStream outputStream, ExportFilter exportFilter) throws Exception ;

    //  - XPath form
	public void exportMeterReading(Long subscriptionId, Long retailCustomerId, Long usagePointId, Long meterReadingId,
				       OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportMeterReadings(Long subscriptionId, Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws Exception;

	// ReadingType
	// - ROOT form
	void exportReadingTypes(OutputStream outputStream, ExportFilter exportFilter) throws Exception;

	void exportReadingType(Long readingTypeId, OutputStream outputStream, ExportFilter exportFilter) throws Exception;

    // RetailCustomer
    //  - ROOT form

	public void exportRetailCustomer(Long subscriptionId, Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportRetailCustomers(Long subscriptionId, OutputStream stream, ExportFilter exportFilter) throws Exception;


	// Subscriptions
	//
    // ROOT forms
    // -- TODO: original Pivotal version - used for pub/sub activity b/c of the String/Hashed implication 
    public void exportSubscription(String subscriptionHashedId, OutputStream stream, ExportFilter exportResourceFilter) throws Exception;

    public void exportSubscription(Long subscriptionId, OutputStream stream, ExportFilter exportResourceFilter) throws Exception;

    public void exportSubscriptions(OutputStream stream, ExportFilter exportResourceFilter) throws Exception;

    // XPath forms

	public void exportSubscriptions(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportSubscription(Long retailCustomerId, Long subscriptionId, OutputStream stream, ExportFilter exportFilter) throws Exception;

    // TimeConfiguration
    //  - ROOT form

	public void exportTimeConfiguration(Long timeConfigurationId, OutputStream stream, ExportFilter exportFilter) throws Exception;

	public void exportTimeConfigurations(OutputStream stream, ExportFilter exportFilter) throws Exception;

	// UsagePoints
	// - ROOT form
	void exportUsagePoint_Root(Long subscriptionId, Long usagePointId, OutputStream stream, ExportFilter exportResourceFilter) throws Exception;

	void exportUsagePoints_Root(Long subscriptionId, OutputStream stream, ExportFilter exportResourceFilter) throws Exception;
        
	// - XPath form
	void exportUsagePoint(Long subscriptionId, Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportResourceFilter) throws Exception;

	void exportUsagePoints(Long subscriptionId, Long retailCustomerId, OutputStream stream,
			ExportFilter exportResourceFilter) throws Exception;

	// - Full Export form
	void exportUsagePointsFull(Long subscriptionId, Long retailCustomerId, ServletOutputStream outputStream,
			ExportFilter exportFilter) throws Exception;

	void exportUsagePointFull(Long subscriptionId, Long usagePointId, Long RetailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter)  throws Exception;

	void exportMeterReadingFull(Long subscriptionId,Long meterReadingId, Long usagePointId, Long RetailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter)  throws Exception;
	public void exportBatchBulk(long bulkId, OutputStream outputStream, ExportFilter exportFilter) throws Exception;

	public void exportBatchSubscription(long subscriptionId, OutputStream outputStream, ExportFilter exportFilter) throws Exception;


}