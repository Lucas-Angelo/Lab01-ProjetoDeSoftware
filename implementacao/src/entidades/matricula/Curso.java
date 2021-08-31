package entidades.matricula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCounter;

    private int id;
    private String nome;
    private int creditoNecessarios;

    private List<Disciplina> disciplinas;

    static {
        idCounter = 0;
    }

    private void init(String nome, int creditoNecessarios) {
        this.id = criarID();
        this.setNome(nome);
        this.setCreditoNecessarios(creditoNecessarios);
        this.disciplinas = new ArrayList<>();
    }

    public Curso(String nome, int creditoNecessarios) {
        init(nome, creditoNecessarios);
    }

    public void gerarCurriculoSemestral(List<Disciplina> disciplinas) {
        if (disciplinas.size() > 0)
            this.disciplinas.addAll(disciplinas);
    }

    public void addDisciplina(Disciplina disciplina) {
        if (disciplina != null)
            this.disciplinas.add(disciplina);
    }

    private static synchronized int criarID() {
        return idCounter++;
    }

    public int getId() {
        return id;
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
        if (creditoNecessarios > 0)
            this.creditoNecessarios = creditoNecessarios;
        else
            System.err.println("Numero de creditos invalido invalido");
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
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
        sb.append("Creditos Necessarios: " + this.creditoNecessarios + "\n");
        sb.append("Qtde Disciplinas: " + this.disciplinas.size());
        return sb.toString();
    }
}
