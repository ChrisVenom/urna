package classes;

/**
 *
 * @author CHRISTIAN
 */
public class Partidos {
    
    
    // Atributos da classe partidos
    protected String nome;
    protected String sigla;
    protected int numero;
    protected String imagem;

    // Construtor privado que será utilizado pela classe builder
    private Partidos(String nome, String sigla, int numero, String imagem) {
        this.nome = nome;
        this.sigla = sigla;
        this.numero = numero;
        this.imagem = imagem;
    }
    
    // Classe builder
    public static class PartidosBuilder{
        protected String nome;
        protected String sigla;
        protected int numero;
        protected String imagem;
        
        // Construtor do builder
        public PartidosBuilder(int numero) {
            this.numero = numero; // isso deixa o número obrigatório, já que é uma chave primária
        }
        
        // Concatenação de atributos
        public PartidosBuilder nome(String nome){
            this.nome = nome;
            return this;
        }
        public PartidosBuilder sigla(String sigla){
            this.sigla = sigla;
            return this;
        }
        public PartidosBuilder numero(int numero){
            this.numero = numero;
            return this;
        }
        public PartidosBuilder imagem(String imagem){
            this.imagem = imagem;
            return this;
        }
        
        // Método para criar partidos através da builder acessando o construtor partidos que está privado
        public Partidos criarPartidos(){
            return new Partidos(nome, sigla, numero, imagem);
        }
    }

    // Método getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    
    
    
}