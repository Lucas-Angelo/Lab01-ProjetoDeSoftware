import entidades.*;
import entidades.enums.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        Turma turma = new Turma(Semestre.PRIMEIRO, 2021, new Disciplina("Projeto de Software", 80, 20, TipoDisciplina.OBRIGATORIA), new Professor("email@example.pucminas", "senha123", "TodoYear"));

        turma.matricular(new Aluno("email@example.com", "123senha", "FullAno", "00000000000"));

        System.out.println(turma.buscarAlunos().size());
    }
}
