import entidades.*;
import entidades.enums.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        Turma turma = new Turma(Semestre.PRIMEIRO, 2021, new Disciplina(), new Professor());

        turma.matricular(new Aluno());

        System.out.println(turma.buscarAlunos().size());
    }
}
