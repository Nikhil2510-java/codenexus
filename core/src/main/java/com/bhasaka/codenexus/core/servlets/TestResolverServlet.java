package com.bhasaka.codenexus.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.bhasaka.codenexus.core.services.ResolverUtilService;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

@Component(
        service = Servlet.class,
        property = {
                SLING_SERVLET_PATHS + "=/bin/testresolver",
                SLING_SERVLET_METHODS + "=GET"
        }
)
public class TestResolverServlet extends SlingSafeMethodsServlet {

    @Reference
    private ResolverUtilService resolverUtilService;

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws IOException {

        try (ResourceResolver resolver =
                     resolverUtilService.getServiceResolver()) {

            Resource resource =
                    resolver.getResource("/content/codenexus");

            if (resource != null) {

                response.getWriter().write(
                        "SUCCESS : Service User Working");

            } else {

                response.getWriter().write(
                        "FAILED : Resource Not Found");
            }

        } catch (Exception e) {

            response.getWriter().write(
                    "ERROR : " + e.getMessage());

            e.printStackTrace();
        }
    }
}