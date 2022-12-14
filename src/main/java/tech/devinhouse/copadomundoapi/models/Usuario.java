package tech.devinhouse.copadomundoapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "USUARIOSCOPA")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer id;

    private String email;

    private String password;

    private LocalDate dataNascimento;

    @ElementCollection(targetClass = Papel.class)
    @JoinTable(name = "USUARIOSCOPA_ROLES", joinColumns = @JoinColumn(name = "ID_USUARIO"))
    @Column(name = "ROLE",nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Papel> papeis;

    public Usuario(Object o, String s, String abcd, LocalDate minusYears, List<Papel> asList) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return papeis.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
