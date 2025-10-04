package br.com.truta.resource;

import java.net.URI;
import java.util.List;
import org.jboss.logging.Logger;

import br.com.truta.models.URLPayload;
import br.com.truta.service.UrlService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/url")
public class UrlResource {

    @Inject
    UrlService urlService;

    private static final Logger logger = Logger.getLogger(UrlResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/encurtar")
    public String encurtar(URLPayload payload) {
        String url_tratada = urlService.validarPayload(payload);
        return urlService.persistir(url_tratada);
    }

    @GET
    @Path("/get/{url_curta}")
    public Response get(@PathParam("url_curta") String url_curta) {
        return Response.temporaryRedirect(URI.create(urlService.buscarUrlOriginal(url_curta))).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/listar")
    public List<String> listar(){
        return urlService.listar();
    }
}
