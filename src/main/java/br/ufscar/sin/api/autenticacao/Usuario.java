package br.ufscar.sin.api.autenticacao;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "usuario", schema = "autenticacao")
@Data
public class Usuario {

    Usuario() {

    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.version = 0l;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false)
    private Long version;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() { return id; }

    public void setUsername(String username) {
        this.username = username;
    }
}
