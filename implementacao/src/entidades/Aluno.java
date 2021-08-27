package entidades;

import java.util.Optional;

public class Aluno extends Usuario {

    public static final int MAX_DISCIPLINAS;
    public static final int MAX_DISCIPLINAS_OPTATIVAS;

    private String nome;
    private String cpf;

    static {
        MAX_DISCIPLINAS = 4; // Máximo de 4 disciplinas obrigatórias matriculadas por semestre
        MAX_DISCIPLINAS_OPTATIVAS = 2; // Máximo de 2 disciplinas optativas matriculadas por semestre
    }

    private void init(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Aluno(String email, String senha, String nome, String cpf) {
        super(email, senha);
        init(nome, cpf);
    }

    public void atualizar(Optional<String> email, Optional<String> senha, Optional<String> nome, Optional<String> cpf) {
        email.ifPresent(this::setEmail);
        senha.ifPresent(this::setSenha);
        nome.ifPresent(this::setNome);
        cpf.ifPresent(this::setcpf);
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

    public String getcpf() {
        return cpf;
    }

    private void setcpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Informações Aluno:");
        sb.append("Nome: " + nome);
        sb.append("cpf: " + cpf);
        sb.append(super.toString());
        return sb.toString();
    }
}
