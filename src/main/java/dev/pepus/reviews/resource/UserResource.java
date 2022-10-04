package dev.pepus.reviews.resource;

import com.nimbusds.jose.shaded.json.JSONObject;
import dev.pepus.reviews.config.JwtTokenProvider;
import dev.pepus.reviews.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/authenticate")
public class UserResource {

    private static Logger log = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> authenticate(@RequestBody Map<String, String> json) {
        log.info("UserResource : authenticate");
        String username = json.get("username");
        String password = json.get("password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",
                    tokenProvider.createToken(username, userRepository.findUserByUsername(username).get().getRole()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
    }
}
