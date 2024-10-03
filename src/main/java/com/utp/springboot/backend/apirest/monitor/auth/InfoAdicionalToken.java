package com.utp.springboot.backend.apirest.monitor.auth;

import com.utp.springboot.backend.apirest.monitor.models.entity.Usuario;
import com.utp.springboot.backend.apirest.monitor.models.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

    /** Variables **/
    @Autowired
    @Qualifier("usuarioServiceJPA")
    private IUsuarioService usuarioService;

    /** Metodos **/
    //Metodo para agregar mas informacion al TOKEN
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Usuario usuario = usuarioService.findByUsername(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("info_adicional", "Hola que tal!: ".concat(authentication.getName()));
        info.put("nombre_empleado", usuario.getCodigoEmpleado().getCodigoPersona().getNombres());
        info.put("apellido_empleado", usuario.getCodigoEmpleado().getCodigoPersona().getApellidos());
        info.put("email_empleado", usuario.getCodigoEmpleado().getCodigoPersona().getEmail());
        info.put("id_empleado", usuario.getCodigoEmpleado().getId());


        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }
}
