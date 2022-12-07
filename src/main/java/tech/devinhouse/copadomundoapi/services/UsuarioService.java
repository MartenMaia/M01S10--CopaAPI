package tech.devinhouse.copadomundoapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.devinhouse.copadomundoapi.exception.AlreadyRegisteredException;
import tech.devinhouse.copadomundoapi.models.Usuario;
import tech.devinhouse.copadomundoapi.repositories.UsuariosRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuariosRepository repo;
    private final PasswordEncoder encoder;

    private String segredo ="LKSDHFLKADHFA894375864T8427KDSJHFDLKJGJA";

    public Usuario criar(Usuario usuario){
        boolean existe = repo.existsUsuarioByEmail(usuario.getEmail());
        if (existe)
            throw new AlreadyRegisteredException("Usuario", usuario.getEmail());
        String senhaCodificada = encoder.encode(usuario.getPassword());
        usuario = repo.save(usuario);
        return usuario;
    }

    public List<Usuario> consultar(){
        return repo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = repo.findByEmail(username);
        if(usuarioOpt.isEmpty())
            throw new UsernameNotFoundException("Usuario não encontrada!!");
        Usuario usuario = usuarioOpt.get();
        return usuario;
    }

    /**
     * Cria um Token JWT para a class Usuario
     * @param usuario
     * @return Token JWT - String
     */
    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(segredo.getBytes());
        String accessToken = JWT.create()
                .withSubject(usuario.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 6000))  // expires in 10 min
                .withIssuer("Copa Do Mundo-API")
                .withClaim("roles", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return accessToken;
    }

    /**
     * Extrai o token do header Authorization
     * @param authorizationHeader
     * @return Token JWT - String
     */
    public String getTokenFrom(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new IllegalArgumentException("Invalid Headers");
        String token = authorizationHeader.substring("Bearer ".length());
//        String token = authorizationHeader.split(" ")[1];
        return token;
    }

    /**
     * Decodifica o token e retorna um objetivo representando o sdados constantes no token
     * @param token
     * @return Token decodificado
     */
    public DecodedJWT getDecodedTokenFrom(String token) {
        Algorithm algorithm = Algorithm.HMAC256(segredo.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }


}
