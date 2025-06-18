package Utils;

import java.util.ArrayList;
import java.util.List;

import com.store.cincomenos.domain.persona.login.User;
import com.store.cincomenos.domain.persona.login.role.Role;
import com.store.cincomenos.infra.security.TokenService;

public class TestUtils {

    private final TokenService tokenService;

    public TestUtils(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String generateTestJWT() {
        User user = getUser();
        return tokenService.generateToken(user);
    }

    public User getUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(Long.valueOf(1), "OWNER"));
        return new User(Long.valueOf(1), "teodoro@gmail.com", "teodoro-gonzales", "12345678", roles);
    }
}
