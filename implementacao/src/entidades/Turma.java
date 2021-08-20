package entidades;

import java.io.*;
import java.util.*;

import entidades.enums.Semestre;
import entidades.enums.TurmaStatus;

public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;

    private static long idCounter;

    public static final int NUMERO_MAX_OFERTAS;
    public static final int NUMERO_MIN_OFERTAS;

    private long id;
    private Semestre semestre;
    private int ano; // Recomendado o uso de: ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).getYear();
    private TurmaStatus status;

    private List<Aluno> alunos;
    private Disciplina disciplina;
    private Professor professor;
    
    static {
        idCounter = 100_000L; // ID's das disciplinas começam com 100.000;

        NUMERO_MAX_OFERTAS = 60; // Máximo de 60 alunos por disciplina
        NUMERO_MIN_OFERTAS = 3; // Mínimo de 3 alunos para ter a disciplina
    }

    public static synchronized long criarID() {
        return idCounter++;
    }

    public Turma(Semestre semestre, int ano, Disciplina disciplina, Professor professor) {
        this.id = criarID();

        this.semestre = semestre;
        this.ano = ano;
        this.status = TurmaStatus.MATRICULA_ABERTA; // Sempre que uma Turma é criada quer dizer que as matrículas estão abertas

        this.alunos = new ArrayList<>(60);
        this.disciplina = disciplina;
        this.professor = professor;
    }

    public List<Aluno> buscarAlunos() {
        return getAlunos();
    }

    public boolean verificarAlunoMatriculado(Aluno aluno) {
        boolean resultado = false;
        if(aluno != null) {
            resultado = this.alunos.stream().anyMatch(alunoAtual -> alunoAtual.equals(aluno));
        } 
        return resultado;
    }

    public void matricular(Aluno aluno) throws IndexOutOfBoundsException, IllegalArgumentException {
        if(this.alunos.size()>=60)
            throw new IndexOutOfBoundsException("Turma cheia!");
        if(verificarAlunoMatriculado(aluno))
            throw new IllegalArgumentException("Este aluno já está nesta turma!");

        this.alunos.add(aluno);
    }

    public void cancelarMatricula(Aluno aluno) {
        if(verificarAlunoMatriculado(aluno))
            throw new IllegalArgumentException("Este aluno não está nesta turma!");

        this.alunos.remove(aluno);
    }

    public long getId() {
        return this.id;
    }
    private void setId(long id) {
        this.id = id;
    }

    public Semestre getSemestre() {
        return this.semestre;
    }
    private void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public int getAno() {
        return this.ano;
    }
    private void setAno(int ano) {
        this.ano = ano;
    }

    public TurmaStatus getStatus() {
        return this.status;
    }
    public void setStatus(TurmaStatus status) {
        this.status = status;
    }

    public List<Aluno> getAlunos() {
        return this.alunos;
    }
    private void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Professor getProfessor() {
        return this.professor;
    }
    private void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
    }
    private void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        try {
            Turma objetoTurma = (Turma) obj;
            if (this.id == objetoTurma.getId())
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
        StringBuilder sb = new StringBuilder("Informações da turma:");
        sb.append("ID: " + this.id);
        sb.append("Status: " + this.status.getDescricao());
        sb.append("Nome: this.disciplina.getNome()"); // NECESSÁRIO FAZER O .getNome() da Disciplna
        sb.append("Ano/Semestre: " + this.semestre.getNumerico() + "/" + this.ano);
        sb.append("Professor: this.professor.getNome()");
        if(!this.status.equals(TurmaStatus.INATIVA))
            sb.append("Quantidade de alunos: " + this.alunos.size());
        return sb.toString();
    }

}
