package com.techeule.cms.hugo.pages.control.matono;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/")
@Produces("application/json")
@Consumes("application/json")
@FunctionalInterface
public interface Actions {
    @GET
    Response getPageUrl(@BeanParam MatomoPageUrlRequest request);
}
