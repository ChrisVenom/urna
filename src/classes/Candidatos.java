package classes;

/**
 *
 * @author CHRISTIAN
 */
public class Candidatos {
    
    protected String nome;
    protected String cargo;
    protected int numero;
    protected String partido;
    protected String coligacao;
    protected int votos;
    protected String imagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getColigacao() {
        return coligacao;
    }

    public void setColigacao(String coligacao) {
        this.coligacao = coligacao;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
    
    public String getImagem() {
        return imagem;
    }
    
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}