package entidades;

import java.util.Optional;

public class Professor extends Usuario {

    private String nome;

    private void init(String nome) {
        this.nome = nome;
    }

    public Professor(String email, String senha, String nome) {
        super(email, senha);
        init(nome);
    }

    public void atualizar(Optional<String> email, Optional<String> senha, Optional<String> nome) {
        email.ifPresent(this::setEmail);
        senha.ifPresent(this::setSenha);
        nome.ifPresent(this::setNome);
    }

    public void consultar() {
        System.out.println(this);
    }

    public String getNome() {
        return nome;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Informações Professor:");
        sb.append("\nNome: " + nome);
        sb.append(super.toString());
        return sb.toString();
    }
}
