import entidades.*;
import entidades.enums.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        Turma turma = new Turma(Semestre.PRIMEIRO, 2021, new Disciplina(), Professor.Cadastrar("email@example.pucminas", "senha123", "TodoYear"));

        turma.matricular(Aluno.Cadastrar("email@example.com", "123senha", "FullAno", "00000000000"));

        System.out.println(turma.buscarAlunos().size());
    }
}
