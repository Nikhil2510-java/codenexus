package com.bhasaka.codenexus.core.services;

import java.util.Collections;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        service = ResolverUtilService.class,
        property = {
                "sling.serviceusermapping.serviceuser.mapping=" + ResolverUtilService.SUB_SERVICE
        })
public class ResolverUtilService {

    /** Subservice name; must match {@code ServiceUserMapperImpl} mapping for this bundle. */
    public static final String SUB_SERVICE = "codenexus-service";

    @Reference
    private ResourceResolverFactory resolverFactory;

    public ResourceResolver getServiceResolver() throws LoginException {

        Map<String, Object> param = Collections.singletonMap(
                ResourceResolverFactory.SUBSERVICE,
                SUB_SERVICE);

        return resolverFactory.getServiceResourceResolver(param);
    }
}