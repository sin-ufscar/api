package br.ufscar.sin.api.autenticacao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "usuario_permissao", schema = "autenticacao",  uniqueConstraints=
@UniqueConstraint(columnNames={"usuario_id", "permissao_id"}))
@Data
public class UsuarioPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @Column(nullable = false, name = "usuario_id")
    @OneToOne()
    Usuario usuario;

//    @Column(nullable = false, name = "permissao_id")
    @OneToOne()
    Permissao permissao;

    @Column(nullable = false)
    private Long version;

    public UsuarioPermissao(){}
    public UsuarioPermissao(Usuario usuario, Permissao permissao) {
        this.usuario = usuario;
        this.permissao = permissao;
        this.version = 0l;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


}
