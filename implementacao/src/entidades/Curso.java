package entidades;

import java.util.List;

public class Curso {
    private static final long serialVersionUID = 1L;
    private static int idCounter;

    private int id;
    private String nome;
    private int creditoNecessarios;

    private List<Aluno> disciplinas;

    static {
        idCounter = 0;
    }
    
    public void gerarCurriculoSemestral(List<Aluno> disciplinas){
        this.disciplinas = disciplinas;
    }

    public Curso(String nome, int creditoNecessarios) {
        this.id = criarID();
        this.nome = nome;
        this.creditoNecessarios = creditoNecessarios;
    }

    public static synchronized int criarID() {
        return idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditoNecessarios() {
        return creditoNecessarios;
    }

    public void setCreditoNecessarios(int creditoNecessarios) {
        this.creditoNecessarios = creditoNecessarios;
    }

    public List<Aluno> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Aluno> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        try {
            Curso objetoCurso = (Curso) obj;
            if (this.id == objetoCurso.getId())
                return true;
            else
                return false;
        } catch (ClassCastException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        Integer id = (int) this.id;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Informações do curso:");
        sb.append("ID: " + this.id + "\n");
        sb.append("Nome: " + this.nome + "\n");
        sb.append("Creditos Necessarios: " + this.creditoNecessarios);
        return sb.toString();
    }
}
