import java.io.*;
import java.util.*;

import entidades.*;
import entidades.enums.*;
import serializacao.*;

public class App {

    public static final String arquivoUsuarios = "usuarios.bin";

    private static List<Usuario> listaUsuarios = new LinkedList<>();
    private static List<Turma> listaTurmas = new LinkedList<>();

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
                        "Erro: Fallha ao fazer o casting dos objetos salvos no arquivo para Usuario, não foi possível carregar os usuários.");
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
                }

            } while (opcao!=0);
            
        } else if (logado.getClass().equals(Professor.class)) {
            // Pesquisar por diciplina para ver alunos
        } else if (logado.getClass().equals(Aluno.class)) {
            // Matricular em uma disciplina (turma) validando matéria obrigatória e optativa
        }
    }
}
