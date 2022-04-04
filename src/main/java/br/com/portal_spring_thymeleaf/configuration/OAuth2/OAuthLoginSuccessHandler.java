package br.com.portal_spring_thymeleaf.configuration.OAuth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import br.com.portal_spring_thymeleaf.services.UsuarioService;

@Service
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private UsuarioService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
		
		 userService.processOAuthPostLogin(oauth2User);
		 super.onAuthenticationSuccess(request, response, authentication);
	}

}
