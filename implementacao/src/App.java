import java.io.*;
import java.lang.StackWalker.Option;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import entidades.*;
import entidades.enums.*;
import serializacao.*;

public class App {

    public static final String arquivoUsuarios = "usuarios.bin";

    private static List<Usuario> listaUsuarios = new LinkedList<>();
    private static List<Turma> listaTurmas = new LinkedList<>();
    private static Usuario logado;

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
                    "Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Usuario, não foi possível carregar os usuários."
                );
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
                    "Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Usuario, não foi possível salvar os usuários.");
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
        System.out.println("0 - Salvar e sair");
        System.out.print("Digite sua opção: ");

        int opcao = teclado.nextInt();
        teclado.nextLine();
        return opcao;
    }

    private static void gerarCurriculoSemestral(Scanner teclado) {
        limparTela();
        
        Curso curso = criarCurso(teclado);
        Disciplina disciplina = criarDisciplina(teclado);
        
    }

    private static Curso criarCurso(Scanner teclado) {
        return null; // Fazer aqui o cadastro de um Curso... perguntando os dados etc...
    }

    private static Disciplina criarDisciplina(Scanner teclado) {
        return null; // Fazer aqui o cadastro de uma Disciplina... perguntando os dados etc...
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
    private static int seletorDeTipoDeUsuario(Scanner teclado){
        limparTela();

        System.out.println("Qual o tipo do usuário?");
        System.out.println("1 - Aluno");
        System.out.println("2 - Professor");
        System.out.println("3 - Secretaria");
        System.out.println("0 - Cancelar");

        int tipo = teclado.nextInt();
        teclado.nextLine();
        return tipo;
    }
    private static void criarUsuario(Scanner teclado) {

        Usuario novo = null;

        int tipo = seletorDeTipoDeUsuario(teclado);

        if ( tipo>=1 && tipo<=3 ){
            System.out.print("Insira o e-mail: ");
            String email = teclado.nextLine();
            System.out.print("Insira a senha: ");
            String senha = teclado.nextLine();

            switch (tipo) { 

                case 1:
                case 2:
                    System.out.print("Insira o nome: ");
                    String nome = teclado.nextLine();
                    if (tipo==1){
                        System.out.print("Insira a CPF: ");
                        String cpf = teclado.nextLine();
                        novo = new Aluno( email, senha, nome, cpf );
                    }
                    else{
                        novo = new Professor( email, senha, nome );
                    }
                    
                    break;
                case 3:
                    System.out.print("Insira o telefone: ");
                    long telefone = teclado.nextLong();
                    teclado.nextLine();
                    novo = new Secretaria(email, senha, telefone);
                    break;
            
                default:
                    break;
            }

            if (novo!=null){
                listaUsuarios.add(novo);
                salvarUsuariosNoArquivo(arquivoUsuarios);
                System.out.println("Usuário criado: ");
                System.out.println(novo);
            }

            pausa(teclado);
        }

    }
    // ve usuários sem filtro (todos usuários)
    private static void verUsuarios(){
        verUsuarios( null );
    }
    private static void verUsuarios( Predicate<Usuario> filtro ) {
        limparTela();
        Stream<Usuario> usuariosStream =  listaUsuarios.stream() ;
        // se existir filtro stream é filtrada de acordo
        if ( filtro != null )
            usuariosStream = usuariosStream.filter(filtro);
        
        // printa todos usuários da stream
        usuariosStream.forEach(System.out::println);
    }
    private static void verTurmas(){
        verTurmas( null );
    }
    private static void verTurmas( Predicate<Turma> filtro ) {
        limparTela();
        Stream<Turma> turmasStream =  listaTurmas.stream() ;
        // se existir filtro, stream é filtrada de acordo
        if ( filtro != null )
            turmasStream = turmasStream.filter(filtro);
        
        // printa todas turmas da stream
        turmasStream.forEach(System.out::println );
    }
    private static void atualizarUsuario(Scanner teclado) {
        verUsuarios( u -> !u.getClass().equals(Secretaria.class) );
        System.out.println("Digite o ID do aluno que deseja atualizar os dados");
        int usuarioId = teclado.nextInt();
        teclado.nextLine();

        //encontra turma com id digitado
        Optional<Usuario> usuarioEscolhido = Optional.empty();
        for ( Usuario u: listaUsuarios ){
            if (u.getId()==usuarioId && !u.getClass().equals(Secretaria.class)){
                usuarioEscolhido = Optional.of(u);
                break;
            }
        }

        usuarioEscolhido.ifPresentOrElse( u -> {
            System.out.println("(Para todos os campos, digitar até 3 caracteres ignora a edição)");
            System.out.println("Email atual: " + u.getEmail());
            System.out.print("Novo email: ");
            String email = teclado.nextLine();
            System.out.print("Nova senha: ");
            String senha = teclado.nextLine();

            if (u.getClass().equals(Aluno.class)){
                Aluno a = (Aluno) u;
                System.out.println("Nome atual: " + a.getNome());
                System.out.print("Novo nome: ");
                String nome = teclado.nextLine();
                System.out.println("CPF atual: " + a.getcpf());
                System.out.print("Novo CPF: ");
                String cpf = teclado.nextLine();
                a.atualizar(
                    email.length() > 3 ? Optional.of(email) : Optional.empty(), 
                    senha.length() > 3 ? Optional.of(senha) : Optional.empty(), 
                    nome.length() > 3 ? Optional.of(nome) : Optional.empty(), 
                    cpf.length() > 3 ? Optional.of(cpf) : Optional.empty()
                );
            }
            else if (u.getClass().equals(Professor.class)){
                Professor p = (Professor) u;
                System.out.println("Nome atual: " + p.getNome());
                System.out.print("Novo nome: ");
                String nome = teclado.nextLine();
                p.atualizar(
                    !email.equals("0") ? Optional.of(email) : Optional.empty(), 
                    !senha.equals("0") ? Optional.of(senha) : Optional.empty(), 
                    !nome.equals("0") ? Optional.of(nome) : Optional.empty()
                );
            }

            System.out.println(u);
            salvarUsuariosNoArquivo(arquivoUsuarios);

        }, () -> System.out.println("Usuário não encontrado"));

        pausa(teclado);

        // Fazer função para atualizar dado do usuário

    }

    private static int menuProfessor(Scanner teclado){
        limparTela();
        Professor professorLogado = (Professor) logado ;
        System.out.println("Olá, professor " + professorLogado.getNome());
        System.out.println("O que deseja fazer?");
        System.out.println("==========================");
        System.out.println("1 - Ver alunos por disciplina lecionada");
        System.out.println("0 - Sair");

        int opcao = teclado.nextInt();
        teclado.nextLine();
        return opcao;

    }
    private static void verAlunosPorDisciplina(Scanner teclado){
        limparTela();

        verTurmas( t -> t.getProfessor().equals(logado) );

        System.out.println("Digite o ID da turma que deseja consultar os alunos matriculados nela");
        int turmaId = teclado.nextInt();
        teclado.nextLine();

        //encontra turma com id digitado
        Optional<Turma> turmaEscolhida = Optional.empty();
        for ( Turma t: listaTurmas ){
            if (t.getId()==turmaId && t.getProfessor().equals(logado)){
                turmaEscolhida = Optional.of(t);
                break;
            }
        }

        turmaEscolhida.ifPresent( t -> t.buscarAlunos().forEach( System.out::println ) );

        pausa(teclado);
    }

    // #endregion

    public static void main(String[] args) throws Exception {
        limparTela();
        Scanner teclado = new Scanner(System.in);

        /* Caso queria apagar o arquivo e adic */
        listaUsuarios = carregarUsuariosDoArquivo(arquivoUsuarios);
        if (listaUsuarios.isEmpty()) {
            listaUsuarios.add(new Secretaria("sec@email.com", "supersenha", 99999999));
            listaUsuarios.add(new Professor("prof@email.com", "supersenha", "Zé"));
            listaUsuarios.add(new Aluno("alu@email.com", "supersenha", "Tom", "111.111.111-11"));
        }
        salvarUsuariosNoArquivo(arquivoUsuarios);
        listaUsuarios = carregarUsuariosDoArquivo(arquivoUsuarios);

        logado = login(teclado, listaUsuarios);
        if (logado == null)
            System.err.println("Email ou senha incorretos!");
        else{
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
                    }
    
                } while (opcao!=0);
                
            } else if (logado.getClass().equals(Professor.class)) {
                int opcao = 0;
                do {
                    opcao = menuProfessor(teclado);
                    if (opcao==1)
                        verAlunosPorDisciplina(teclado);
    
                } while (opcao!=0);
            } else if (logado.getClass().equals(Aluno.class)) {
                // Matricular em uma disciplina (turma) validando matéria obrigatória e optativa
            }
        }
        
    }
}
