package so2trabalho2;


public class Bloco {
    private int tamanho;
    /**
     * -1 = não está em uso (como se o bloco fosse transparente)
     * +1 = bloco em uso, o idArquivo representa o arquivo que está nele 
    */
    private int idArquivo; 
    private String nomeArquivo;
    private int idBloco;
    private boolean ocupado;
    private int proximo;

    public Bloco(int idBloco, int tamanho) {
        this.idBloco = idBloco;
        this.tamanho = tamanho;
        this.idArquivo = -1;
        this.ocupado = false;
        this.proximo = -1;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }    
    
    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(int idArquivo) {
        this.idArquivo = idArquivo;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getProximo() {
        return proximo;
    }

    public void setProximo(int proximo) {
        this.proximo = proximo;
    }

    public int getIdBloco() {
        return idBloco;
    }

    public void setIdBloco(int idBloco) {
        this.idBloco = idBloco;
    }
    
    
    
}
