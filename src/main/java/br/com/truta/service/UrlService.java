package br.com.truta.service;

import java.util.List;

import br.com.truta.models.URLPayload;
import br.com.truta.persistance.UrlEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UrlService {

    private final String PREFIX = "http://localhost:8080/url/get/";

    @Transactional
    public String persistir(String urlOriginal) {
        UrlEntity url_nova = new UrlEntity();
        url_nova.url_longa = urlOriginal;
        String url_curta = String.valueOf(UrlEntity.count());
        url_nova.url_curta = url_curta;
        url_nova.persist();
        return PREFIX + url_curta;
    }

    public String buscarUrlOriginal(String url_curta) {
        UrlEntity url = UrlEntity.find("url_curta", url_curta).firstResult();
        return url.url_longa;
    }

    public List<String> listar(){
        List<UrlEntity> listAll = UrlEntity.listAll();
        List<String> listString = listAll.stream()
                                    .map(url -> url.toString())
                                    .toList();
        return listString;
    }

    public String validarPayload(URLPayload payload) {
        if (payload.url_longa() != null) {
            String url = payload.url_longa();
            if (!(url.contains("https://") || url.contains("http://"))) {
                throw new RuntimeException("url invalida");
            }
            return url;
        }
        throw new RuntimeException("Url invalida");
    }


}
