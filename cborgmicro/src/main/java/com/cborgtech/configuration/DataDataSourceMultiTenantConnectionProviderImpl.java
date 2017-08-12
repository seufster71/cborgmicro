package com.cborgtech.configuration;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.toasthub.core.system.model.AppCacheClientDomains;


public class DataDataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dataDataSourcesApplication")
	private Map<String, DataSource> dataDataSourcesApplication;
	
	@Autowired
	AppCacheClientDomains appCacheClientDomains;
	
	@Override
	protected DataSource selectAnyDataSource() {
		logger.info("get any data datasource " + dataDataSourcesApplication.size());
		return this.dataDataSourcesApplication.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		logger.info("data tenant datasource Identifier "+ tenantIdentifier);
		return this.dataDataSourcesApplication.get(tenantIdentifier);
	}

}
