package com.cborgtech.configuration;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.toasthub.core.general.utils.TenantContext;
import org.toasthub.core.system.model.AppCacheClientDomains;
import org.toasthub.core.system.model.ClientDomain;

public class DataTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static String DEFAULT_TENANT_ID = "badbox";
	
	@Autowired
	AppCacheClientDomains appCacheClientDomains;
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		String currentTenantId = DEFAULT_TENANT_ID;
		if (TenantContext.getURLDomain() == null){
			currentTenantId = TenantContext.getTenantId(); 
			logger.info("data resolver using tenant id "+ currentTenantId);
		} else {
			ClientDomain clientDomain = appCacheClientDomains.getClientDomain(TenantContext.getURLDomain());
			if (clientDomain != null) {
				currentTenantId = clientDomain.getAPPDomain();
			}
			logger.info("data resolver "+ currentTenantId);
		}
		return currentTenantId;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		// TODO Auto-generated method stub
		return false;
	}

}
