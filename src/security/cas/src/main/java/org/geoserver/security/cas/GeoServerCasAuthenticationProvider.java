/* Copyright (c) 2001 - 2011 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.security.cas;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.geoserver.config.util.XStreamPersister;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.security.GeoServerSecurityFilterChain;
import org.geoserver.security.GeoServerSecurityManager;
import org.geoserver.security.RequestFilterChain;
import org.geoserver.security.config.SecurityNamedServiceConfig;
import org.geoserver.security.filter.AbstractFilterProvider;
import org.geoserver.security.filter.GeoServerSecurityFilter;
import org.geoserver.security.validation.SecurityConfigValidator;
import org.geotools.util.logging.Logging;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.springframework.security.web.util.AntPathRequestMatcher;

/**
 * Security provider for CAS
 * 
 * @author mcr
 */
public class GeoServerCasAuthenticationProvider extends AbstractFilterProvider {

    static Logger LOGGER = Logging.getLogger("org.geoserver.security.cas");

    protected ProxyGrantingTicketCallbackFilter pgtCallback;
    protected ProxyGrantingTicketStorage pgtStorage;

    public GeoServerCasAuthenticationProvider(ProxyGrantingTicketCallbackFilter pgtCallback, 
        ProxyGrantingTicketStorage pgtStorage) {
        this.pgtCallback = pgtCallback;
        this.pgtStorage = pgtStorage;
    }

    @Override
    public void configure(XStreamPersister xp) {
        super.configure(xp);
        xp.getXStream().alias("casAuthentication", GeoServerCasAuthenticationFilter.class);
    }

    @Override
    public Class<? extends GeoServerSecurityFilter> getFilterClass() {
        return GeoServerCasAuthenticationFilter.class;
    }

    @Override
    public GeoServerSecurityFilter createFilter(SecurityNamedServiceConfig config) {
        return new GeoServerCasAuthenticationFilter(pgtStorage);
    }

    @Override
    public SecurityConfigValidator createConfigurationValidator(
            GeoServerSecurityManager securityManager) {
        return new CasFilterConfigValidator(securityManager);
    }

    @Override
    public void configureFilterChain(GeoServerSecurityFilterChain filterChain) {
        RequestFilterChain casChain = 
            new RequestFilterChain(GeoServerCasConstants.CAS_PROXY_RECEPTOR_PATTERN);
        casChain.setFilterNames(pgtCallback.getName());
        filterChain.getRequestChains().add(0,casChain);
    }
}
