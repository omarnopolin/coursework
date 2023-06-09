package coursework.cashflow.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonAuthenticationFilter   extends UsernamePasswordAuthenticationFilter {
    protected AuthenticationManager authenticationManager;
    @SneakyThrows
    JsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        Authentication authentication = null;
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        else if (request.getHeader("Content-Type").equals(MediaType.APPLICATION_JSON.toString())) {
        UsernamePasswordAuthenticationToken authRequest = getUsernamePasswordToken(request);
            System.out.println(authRequest.getAuthorities());
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        this.setAuthenticationManager(authenticationManager);
            authentication = this.getAuthenticationManager().authenticate(authRequest);
            response.setStatus(200);
        } else {
            authentication = super.attemptAuthentication(request, response);
        }
        return authentication;
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordToken(
            HttpServletRequest request) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj = mapper.readTree(request.getInputStream());
        String username = jsonObj.get("login").asText();
        String password = jsonObj.get("password").asText();
        return new UsernamePasswordAuthenticationToken(
                username, password);
    }

    // other methods
}

