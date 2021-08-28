import java.io.*;
import java.util.*;

import entidades.*;
import entidades.enums.*;
import serializacao.*;

public class App {

    public static final String arquivoUsuarios = "usuarios.bin";
    public static final String arquivoCursos = "cursos.bin";
    public static final String arquivoDisciplinas = "disciplinas.bin";
    public static final String arquivoTurma = "turma.bin";

    private static List<Usuario> listaUsuarios = new LinkedList<>();
    private static List<Turma> listaTurmas = new LinkedList<>();
    private static List<Curso> listaCursos = new LinkedList<>();
    private static List<Disciplina> listaDisciplinas = new LinkedList<>();

    // #region Utilidades
    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     * 
     * @throws IOException Se ocorrer algum erro ao tentar emtrar ou sair com o
     *                     comando no cmd.
     */
    private static void limparTela() {
        final String os = System.getProperty("os.name");
        try {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (InterruptedException exception) {
            System.out.println("Erro de interrupção ao tentar limpar terminal " + exception);
        } catch (IOException exception) {
            System.out.println("Erro de entrada/saída ao tentar limpar terminal " + exception);
        }
    }

    public static List<Usuario> carregarUsuariosDoArquivo(String arquivo) {
        File f = new File(arquivo);
        if (f.exists() && !f.isDirectory()) {
            LeituraSerializada leitura = new LeituraSerializada();
            leitura.abrirArquivo(arquivo);
            try {
                for (Object objeto : leitura.lerArquivo()) {
                    listaUsuarios.add((Usuario) objeto);
                }
            } catch (ClassCastException e) {
                System.err.println(
                        "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Usuario, não foi possível carregar os usuários.");
            }
            leitura.fecharArquivo();
        }

        return listaUsuarios;
    }
    public static void salvarUsuariosNoArquivo(String arquivo) {
        EscritaSerializada<Usuario> escrita = new EscritaSerializada<Usuario>();
        escrita.abrirArquivo(arquivo);
        try {
            escrita.escrever(listaUsuarios);
        } catch (ClassCastException e) {
            System.err.println(
                    "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Usuario, não foi possível salvar os usuários.");
        }
        escrita.fecharArquivo();
    }

    public static List<Curso> carregarCursosDoArquivo(String arquivo) {
        File f = new File(arquivo);
        if (f.exists() && !f.isDirectory()) {
            LeituraSerializada leitura = new LeituraSerializada();
            leitura.abrirArquivo(arquivo);
            try {
                for (Object objeto : leitura.lerArquivo()) {
                    listaCursos.add((Curso) objeto);
                }
            } catch (ClassCastException e) {
                System.err.println(
                    "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Curso, não foi possível carregar os cursos.");
            }
            leitura.fecharArquivo();
        }

        return listaCursos;
    }
    public static void salvarCursosNoArquivo(String arquivo) {
        EscritaSerializada<Curso> escrita = new EscritaSerializada<Curso>();
        escrita.abrirArquivo(arquivo);
        try {
            escrita.escrever(listaCursos);
        } catch (ClassCastException e) {
            System.err.println(
                "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Curso, não foi possível salvar os cursos.");
        }
        escrita.fecharArquivo();
    }

    public static List<Disciplina> carregarDisciplinasDoArquivo(String arquivo) {
        File f = new File(arquivo);
        if (f.exists() && !f.isDirectory()) {
            LeituraSerializada leitura = new LeituraSerializada();
            leitura.abrirArquivo(arquivo);
            try {
                for (Object objeto : leitura.lerArquivo()) {
                    listaDisciplinas.add((Disciplina) objeto);
                }
            } catch (ClassCastException e) {
                System.err.println(
                    "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Disciplina, não foi possível salvar as disciplinas.");
            }
            leitura.fecharArquivo();
        }

        return listaDisciplinas;
    }
    public static void salvarDisciplinasNoArquivo(String arquivo) {
        EscritaSerializada<Disciplina> escrita = new EscritaSerializada<Disciplina>();
        escrita.abrirArquivo(arquivo);
        try {
            escrita.escrever(listaDisciplinas);
        } catch (ClassCastException e) {
            System.err.println(
                "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Disciplina, não foi possível salvar as disciplinas.");
        }
        escrita.fecharArquivo();
    }

    public static List<Turma> carregarTurmaDoArquivo(String arquivo) {
        File f = new File(arquivo);
        if (f.exists() && !f.isDirectory()) {
            LeituraSerializada leitura = new LeituraSerializada();
            leitura.abrirArquivo(arquivo);
            try {
                for (Object objeto : leitura.lerArquivo()) {
                    listaTurmas.add((Turma) objeto);
                }
            } catch (ClassCastException e) {
                System.err.println(
                    "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Turma, não foi possível carregar as turmas.");
            }
            leitura.fecharArquivo();
        }

        return listaTurmas;
    }
    public static void salvarTurmaNoArquivo(String arquivo) {
        EscritaSerializada<Turma> escrita = new EscritaSerializada<Turma>();
        escrita.abrirArquivo(arquivo);
        try {
            escrita.escrever(listaTurmas);
        } catch (ClassCastException e) {
            System.err.println(
                "Erro: Falha ao fazer o casting dos objetos salvos no arquivo para Turma, não foi possível salvar as turmas.");
        }
        escrita.fecharArquivo();
    }

    public static Usuario login(Scanner teclado, List<Usuario> usuarios) {
        System.out.print("Insira seu email: ");
        String email = teclado.nextLine();
        System.out.print("Insira sua senha: ");
        String senha = teclado.nextLine();

        for (Usuario usuarioUnico : usuarios) {
            if (usuarioUnico.logar(email, senha))
                return usuarioUnico;
        }

        return null;
    }

    static void pausa(Scanner teclado) {
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }
    // #endregion

    // #region Secretaria
    private static int menuSecretaria(Scanner teclado) {
        limparTela();

        System.out.println("XULAMBS Secretaria");
        System.out.println("==========================");
        System.out.println("1 - Gerar currículo semestral para curso");
        System.out.println("2 - CRUD usuário");
        System.out.println("3 - Criar Turma");
        System.out.println("0 - Salvar e sair");
        System.out.print("Digite sua opção: ");

        int opcao = teclado.nextInt();
        teclado.nextLine();
        return opcao;
    }

    private static void gerarCurriculoSemestral(Scanner teclado) {
        limparTela();

        System.out.print("Deseja adicionar uma disciplina: (S/N): ");
        if(teclado.nextLine().equals("S")) {
            String sair;
            do {
                Disciplina disciplina = criarDisciplina(teclado);
                listaDisciplinas.add(disciplina);
                System.out.println("Digite 1 para cadastrar outra disciplina ou digite 0 para finalizar o cadastro de disciplinas");
                sair = teclado.nextLine();
            }while (sair.equals("1"));
        }
        salvarDisciplinasNoArquivo(arquivoDisciplinas);
        
        Curso curso = criarCurso(teclado);
        listaCursos.add(curso);
        salvarCursosNoArquivo(arquivoCursos);
        
    }

    private static Curso criarCurso(Scanner teclado) {
        Curso curso;

        System.out.print("Insira o nome do curso: ");
        String nome = teclado.nextLine();

        System.out.print("Insira o numero de creditos necessarios: ");
        int numeroCreditos = teclado.nextInt();
        
        curso = new Curso(nome, numeroCreditos);

        List<Disciplina> ld = new LinkedList<>();
        System.out.println("Inclusao de disciplinas, digite o numero da disciplina para incluir");

        String sair;
        do {
            for (int i = 0; i < listaDisciplinas.size(); i++)
                System.out.println((i + 1) + " " + listaDisciplinas.get(i).getNome());

            pausa(teclado);
            System.out.print("Numero da Disciplina: ");
            int num = Integer.parseInt(teclado.nextLine());
            ld.add(listaDisciplinas.get(num - 1));
            System.out.println("Deseja incluir outra disciplina ? (S/N): ");
            sair = teclado.nextLine();
        } while (sair.equals("S"));
        
        curso.gerarCurriculoSemestral(ld);
        System.out.println("Curso criado");
        pausa(teclado);
        
        return curso; // Fazer aqui o cadastro de um Curso... perguntando os dados etc...
    }

    private static Disciplina criarDisciplina(Scanner teclado) {
        Disciplina disciplina;
        
        System.out.println("Insira o nome da disciplina: ");
        String nome = teclado.nextLine();
        System.out.println("Insira o valor cargo-horario da disciplina: ");
        int horas = Integer.parseInt(teclado.nextLine());
        System.out.println("Insira o valor dos creditos da disciplina: ");
        int creditos = Integer.parseInt(teclado.nextLine());
        System.out.println("A disciplina é: \n1-Obrigatoria\n2-Optativa");
        String opcao = teclado.nextLine();
        TipoDisciplina td = null;
        if(opcao.equals("1"))
            td = TipoDisciplina.OBRIGATORIA;
        else if(opcao.equals("2"))
            td = TipoDisciplina.OPTATIVA;
        
        disciplina = new Disciplina(nome, horas, creditos, td);
        
        return disciplina;
    }

    private static void criarTurma(Scanner teclado){
        Turma t;
        System.out.println("Insira o ano de inicio da turma: ");
        int ano = Integer.parseInt(teclado.nextLine());
        System.out.println("Defina o periodo de inicio da turma: \n1 - 1ª Semestre\n2 - 2º Semestre\nOpcao: ");
        int semestre = Integer.parseInt(teclado.nextLine());
        Semestre s = null;
        if(semestre == 1)
            s = Semestre.PRIMEIRO;
        else if(semestre == 2)
            s = Semestre.SEGUNDO;

        System.out.println("Defina a disciplina da turma");
        for (int i = 0; i < listaDisciplinas.size(); i++)
            System.out.println((i + 1) + " " + listaDisciplinas.get(i).getNome());

        pausa(teclado);
        System.out.print("Numero da Disciplina: ");
        int num = Integer.parseInt(teclado.nextLine());

        // TODO: 8/27/2021  Substituir futuramente pelo professor mesmo
        Professor p = null;
        
        t = new Turma(s, ano, listaDisciplinas.get(num - 1) , p);
        listaTurmas.add(t);
        salvarTurmaNoArquivo(arquivoTurma);
        
    }
    
    private static int menuCRUDUsuario(Scanner teclado) {
        limparTela();
        
        System.out.println("XULAMBS Usuários");
        System.out.println("==========================");
        System.out.println("1 - Criar usuário");
        System.out.println("2 - Ver usuários");
        System.out.println("3 - Atualizar usuário");
        System.out.println("0 - Cancelar");
        System.out.print("Digite sua opção: ");

        int opcao = teclado.nextInt();
        teclado.nextLine();
        return opcao;
    }
    private static void criarUsuario(Scanner teclado) {
        limparTela();

        Usuario novo = null;
        System.out.println("Qual o tipo do usuário?");
        System.out.println("1 - Aluno");
        System.out.println("2 - Professor");
        System.out.println("2 - Secretaria");
        System.out.println("0 - Cancelar");

        int tipo = teclado.nextInt();

        switch (tipo) {
            case 1:
                /// Criar tipo aluno aqui.. pergunta nome etc..
                break;
        
            default:
                break;
        }

        listaUsuarios.add(novo);
        salvarUsuariosNoArquivo(arquivoUsuarios);
    }
    private static void verUsuarios() {
        limparTela();

        // Fazer função para ver todos os usuários

        // listaUsuarios.add(novo); Lembrar de adicionar na lista pra salvar no arquivo
        salvarUsuariosNoArquivo(arquivoUsuarios);
    }
    private static void atualizarUsuario(Scanner teclado) {
        limparTela();

        // Fazer função para atualizar dado do usuário

        // listaUsuarios.add(novo); Lembrar de adicionar na lista pra salvar no arquivo
        salvarUsuariosNoArquivo(arquivoUsuarios);
    }

    // #endregion
    
    private static void matricularAluno(Scanner teclado, Aluno a){
        int nMatriculaObrigatoria = 0; 
        int nMatriculaOptativa = 0;
        int maxObrigatoria = 4;
        int maxOptativa = 2;

        List<Turma> turmasEntrar = new LinkedList<>(); // Armazenar a lista das turmas que o aluno quer entrar
        
        String maisUm;
        do {
            for (int i = 0; i < listaTurmas.size(); i++) {
                if (!turmasEntrar.contains(listaTurmas.get(i))) { // Verificar se já entrou na turma, caso já nem aparece
                    Disciplina dTurma = listaTurmas.get(i).getDisciplina();
                    if (dTurma.getTipo() == TipoDisciplina.OBRIGATORIA && nMatriculaObrigatoria <= maxObrigatoria) // Imprimir caso n tenha chegado ao num max de materias obrigatorias
                        System.out.print((i + 1) + " - Turma " + (i + 1) + " " + dTurma.getNome() + " - Obrigatoria\n");
                    
                    if (dTurma.getTipo() == TipoDisciplina.OPTATIVA && nMatriculaOptativa <= maxOptativa) // Imprimir caso n tenha chegado ao num max de materias optativas
                        System.out.print((i + 1) + " - Turma " + (i + 1) + " " + dTurma.getNome() + " - Optativa\n");
                }
            }
            System.out.println("Digite a turma que deseja entrar: ");
            int opcao = Integer.parseInt(teclado.nextLine());
            turmasEntrar.add(listaTurmas.get(opcao - 1));
            if(listaTurmas.get(opcao - 1).getDisciplina().getTipo() == TipoDisciplina.OBRIGATORIA)
                nMatriculaObrigatoria++;
            else
                nMatriculaOptativa++;
            
            if(!(nMatriculaObrigatoria >= maxObrigatoria && nMatriculaOptativa >= maxOptativa)) {
                System.out.println("Matricular em mais uma turma? (S/N):  ");
                maisUm = teclado.nextLine();
            } else
                maisUm = "N";
        }while (maisUm.equals("S"));

        for (Turma t: turmasEntrar) { // Matricular o aluno nas turmas
            t.matricular(a);
        }

        System.out.println("Aluno matriculado!");
        
    }

    public static void main(String[] args) throws Exception {
        limparTela();
        Scanner teclado = new Scanner(System.in);

        /* Caso queria apagar o arquivo e adic */
        listaUsuarios = carregarUsuariosDoArquivo(arquivoUsuarios);
        listaCursos = carregarCursosDoArquivo(arquivoCursos);
        listaDisciplinas = carregarDisciplinasDoArquivo(arquivoDisciplinas);
        listaTurmas = carregarTurmaDoArquivo(arquivoTurma);
        if (listaUsuarios.isEmpty()) {
            listaUsuarios.add(new Secretaria("sec@email.com", "supersenha", 99999999));
            listaUsuarios.add(new Professor("prof@email.com", "supersenha", "Zé"));
            listaUsuarios.add(new Aluno("alu@email.com", "supersenha", "Tom", "111.111.111-11"));
        }
        salvarUsuariosNoArquivo(arquivoUsuarios);
        listaUsuarios = carregarUsuariosDoArquivo(arquivoUsuarios);

        Usuario logado = login(teclado, listaUsuarios);
        if (logado == null)
            System.err.println("Email ou senha incorretos!");
        else
            System.out.println("Login efetuado com sucesso!");
        pausa(teclado);

        if (logado.getClass().equals(Secretaria.class)) {
            int opcao = 0;
            do {
                opcao = menuSecretaria(teclado);
                if (opcao==1)
                    gerarCurriculoSemestral(teclado);
                else if (opcao==2) {
                    int acao = menuCRUDUsuario(teclado);
                    switch (acao) {
                        case 1:
                            criarUsuario(teclado);
                            break;
                        case 2:
                            verUsuarios();
                            break;
                        case 3:
                            atualizarUsuario(teclado);
                            break;
                        default:
                            break;
                    }
                } else if (opcao == 3)
                    criarTurma(teclado);

            } while (opcao!=0);
            
        } else if (logado.getClass().equals(Professor.class)) {
            // Pesquisar por diciplina para ver alunos
        } else if (logado.getClass().equals(Aluno.class)) {
            matricularAluno(teclado, (Aluno) logado);
        }
    }
}
