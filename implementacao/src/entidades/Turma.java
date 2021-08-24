package entidades;

import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private int ano; // Recomendado o uso de:
                     // ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).getYear();
    private TurmaStatus status;

    private List<Aluno> alunos;
    private Disciplina disciplina;
    private Professor professor;

    static {
        idCounter = 100_000L; // ID's das disciplinas começam com 100.000;

        NUMERO_MAX_OFERTAS = 60; // Máximo de 60 alunos por disciplina
        NUMERO_MIN_OFERTAS = 3; // Mínimo de 3 alunos para ter a disciplina
    }

    private static synchronized long criarID() {
        return idCounter++;
    }

    private void init(Semestre semestre, int ano, Disciplina disciplina, Professor professor) {
        this.id = criarID();

        this.semestre = semestre;
        if (ano <= 0) {
            this.ano = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).getYear();
            System.err.println("Ano inválido. Turma criada com ano: " + this.ano);
        } else
            this.ano = ano;

        this.status = TurmaStatus.MATRICULA_ABERTA; // Sempre que uma Turma é criada quer dizer que as matrículas estão
                                                    // abertas

        this.alunos = new ArrayList<>(NUMERO_MAX_OFERTAS);
        this.disciplina = disciplina;
        this.professor = professor;
    }

    public Turma(Semestre semestre, int ano, Disciplina disciplina, Professor professor) {
        init(semestre, ano, disciplina, professor);
    }

    public List<Aluno> buscarAlunos() {
        return getAlunos();
    }

    public boolean verificarAlunoMatriculado(Aluno aluno) {
        boolean resultado = false;
        if (aluno != null) {
            resultado = this.alunos.stream().anyMatch(alunoAtual -> alunoAtual.equals(aluno));
        }
        return resultado;
    }

    public void matricular(Aluno aluno) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (this.alunos.size() >= NUMERO_MAX_OFERTAS)
            throw new IndexOutOfBoundsException("Turma cheia!");
        if (verificarAlunoMatriculado(aluno))
            throw new IllegalArgumentException("Este aluno já está nesta turma!");

        this.alunos.add(aluno);
    }

    public void cancelarMatricula(Aluno aluno) {
        if (verificarAlunoMatriculado(aluno))
            throw new IllegalArgumentException("Este aluno não está nesta turma!");

        this.alunos.remove(aluno);
    }

    public long getId() {
        return this.id;
    }

    public Semestre getSemestre() {
        return this.semestre;
    }

    public int getAno() {
        return this.ano;
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

    public Professor getProfessor() {
        return this.professor;
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
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
        sb.append("Nome: " + this.disciplina.getNome());
        sb.append("Ano/Semestre: " + this.semestre.getNumerico() + "/" + this.ano);
        sb.append("Professor: " + this.professor.getNome());
        if (!this.status.equals(TurmaStatus.INATIVA))
            sb.append("Quantidade de alunos: " + this.alunos.size());
        return sb.toString();
    }

}
