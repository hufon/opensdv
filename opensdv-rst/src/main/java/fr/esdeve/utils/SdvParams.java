package fr.esdeve.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: hufon
 * Date: 30/04/14
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */

@Component
public class SdvParams {

    @Value("${default.vente.location}")
    private String defaultVenteLocation;

    public String getDefaultVenteLocation() {
        return defaultVenteLocation;
    }

    public void setDefaultVenteLocation(String defaultVenteLocation) {
        this.defaultVenteLocation = defaultVenteLocation;
    }
}
