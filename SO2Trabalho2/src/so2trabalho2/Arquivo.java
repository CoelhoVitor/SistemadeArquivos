package so2trabalho2;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Arquivo {
    
    private String nome;
    private int id;
    private String data;
    private String tamanho;
    private String tamanhoReal;
    private String origem;
            
    public Arquivo(String nome, int id, String origem, String data) {
        this.nome = nome;
        this.id = id;
        this.origem = origem;
        this.data = data;
    }
        
    //Inserir, em forma binária, um arquivo no Sistema de Arquivos    
    //O arquivo deve ser escrito no final do Sistema
    public void inserirArquivo(String diretorio, byte[] conteudoBinario) {
        try {
            FileOutputStream fos = new FileOutputStream(diretorio, true);
            fos.write(conteudoBinario);
            fos.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível inserir o Arquivo no Sistema de Arquivos");
            return;
        }        
    }
    
    //Transformar conteúdo para binário de um arquivo
    public String lerArquivo(String conteudo){
        String conteudoLido = ""; 
        StringBuilder strBuilder = new StringBuilder(conteudoLido);
        
        try {
            FileReader arq = new FileReader(conteudo);
            BufferedReader lerArq = new BufferedReader(arq);
            
            conteudoLido = lerArq.readLine();
            
            while(conteudoLido != null){ 
                strBuilder.append(conteudoLido);
                conteudoLido = lerArq.readLine();                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Arquivo não encontrado");
            return "-1";
        }
        
        return strBuilder.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getTamanhoReal() {
        return tamanhoReal;
    }

    public void setTamanhoReal(String tamanhoReal) {
        this.tamanhoReal = tamanhoReal;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
    
    
}
