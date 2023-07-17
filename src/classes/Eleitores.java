package classes;

/**
 *
 * @author CHRISTIAN
 */
public class Eleitores {
    
    // Atributos da classe eleitores
    protected int titulo;
    protected String nome;
    protected String senha;
    protected String situacao;
    protected String imagem;
    protected String perfil;

    // Construtor privado que será utilizado pela classe builder
    private Eleitores(int titulo, String nome, String senha, String situacao, String imagem, String perfil) {
        this.titulo = titulo;
        this.nome = nome;
        this.senha = senha;
        this.situacao = situacao;
        this.imagem = imagem;
        this.perfil = perfil;
    }
    
    // Classe builder
    public static class EleitoresBuilder{
        protected int titulo;
        protected String nome;
        protected String senha;
        protected String situacao;
        protected String imagem;
        protected String perfil;

        // Construtor do builder
        public EleitoresBuilder(int titulo) {
            this.titulo = titulo; // isso deixa o título obrigatório, já que é uma chave primária
        }
        
        // Concatenação de atributos
        public EleitoresBuilder titulo(int titulo){
            this.titulo = titulo;
            return this;
        }
        public EleitoresBuilder nome(String nome){
            this.nome = nome;
            return this;
        }
        public EleitoresBuilder senha(String senha){
            this.senha = senha;
            return this;
        }
        public EleitoresBuilder situacao(String situacao){
            this.situacao = situacao;
            return this;
        }
        public EleitoresBuilder imagem(String imagem){
            this.imagem = imagem;
            return this;
        }
        public EleitoresBuilder perfil(String perfil){
            this.perfil = perfil;
            return this;
        }
        
        // Método para criar eleitores através da builder acessando o construtor eleitores que está privado
        public Eleitores criarEleitores(){
            return new Eleitores(titulo, nome, senha, situacao, imagem, perfil);
        }
    }

    // Método getters e setters
    public int getTitulo() {
        return titulo;
    }

    public void setTitulo(int titulo) {
        this.titulo = titulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
}