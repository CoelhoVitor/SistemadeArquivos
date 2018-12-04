package so2trabalho2;


import java.util.List;
import javax.swing.JOptionPane;


public class Memoria {
    
    private Bloco[] blocos;
    private int tamanho;
    
    private List leds;

    public Memoria(int tamanhoMemoria, Bloco[] blocos, List leds) {
        this.tamanho = tamanhoMemoria;
        this.blocos = blocos;
        this.leds = leds;
    }
    
    //Inserir, em forma binária, um arquivo no Sistema de Arquivos
    //Esse arquivo deve caber na memória, logo é necessário o tamanho do arquivo
    //e da memória para saber se é possível tal inserção.
    //INSERIR UTILIZANDO ALOCAÇÃO ENCADEADA
    public boolean inserirNaMemoria(String diretorio, String input, Arquivo arquivo, int TAMANHO_BLOCOS, int QUANTIDADE_BLOCOS_MEMORIA){
        //salva conteudo do arquivo selecionado
        String conteudoString = arquivo.lerArquivo(input); 
        //se existir conteúdo, continua
        if(!conteudoString.equals("-1")){
            //converte conteudo para binário
            byte[] conteudoBinario = conteudoString.getBytes();
            int tamanhoConteudoBinario = conteudoBinario.length;
            
            
            if (tamanhoConteudoBinario < 1024){
                arquivo.setTamanhoReal(Integer.toString(tamanhoConteudoBinario) + " Byte(s)");
            } else if (tamanhoConteudoBinario < 1048576) {
                arquivo.setTamanhoReal(Integer.toString(tamanhoConteudoBinario/1024) + " KB");
            } else if (tamanhoConteudoBinario < 1073741824) {
                arquivo.setTamanhoReal(Integer.toString(tamanhoConteudoBinario/1048576) + " MB");
            } else 
                arquivo.setTamanhoReal(Integer.toString(tamanhoConteudoBinario/1073741824) + " GB");
            
            
            //Quantidade de Blocos a serem inseridos na Memória
            int quantidadeBlocos = (int) Math.ceil((double) tamanhoConteudoBinario/TAMANHO_BLOCOS);
            
            System.out.println("tamanho conteudo " + tamanhoConteudoBinario);
            System.out.println("quantidade blocos " + quantidadeBlocos);

            //se conteudo couber na memória disponível, a grava
            if(cabeNaMemoria(tamanhoConteudoBinario, TAMANHO_BLOCOS)){
                //se sim, remove quantidade de leds da lista e
                //associa o arquivo ao bloco, inutilizando-o

                removerLed(quantidadeBlocos);

                inserirNoBloco(arquivo, quantidadeBlocos, QUANTIDADE_BLOCOS_MEMORIA);

                arquivo.inserirArquivo(diretorio, conteudoBinario); 
                
                //System.out.println("tamanho leds " + leds.size());
                //System.out.println("quantidade blocos " + blocos.length);
                
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Arquivo não cabe na Memória");
                return false;
            }
            return true;
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Conteúdo inexistente");
            return false;
        }
    }
    
    //Remove Arquivo da Memória
    public void removerDaMemoria(String nomeArquivo, int QUANTIDADE_BLOCOS_MEMORIA){
        //retorna id do arquivo
        int idArquivo = arquivoExisteNaMemoria(nomeArquivo, QUANTIDADE_BLOCOS_MEMORIA);
        int proximoBloco, i = idArquivo;
        //se arquivo existe
        if(idArquivo != -1){
            do {
                leds.add(0);
                blocos[i].setIdArquivo(-1);
                blocos[i].setNomeArquivo(null);
                proximoBloco = blocos[i].getProximo();
                blocos[i].setProximo(-1);
                i = proximoBloco;                
            //se houver ponteiro para proximo bloco, continua
            } while (proximoBloco != -1);                
            JOptionPane.showMessageDialog(null, "Arquivo \"" + nomeArquivo + "\" removido da Memória");
        } else {
            JOptionPane.showMessageDialog(null, "Arquivo não existe na Memória!");
        }            
    }
    
    //-1 = arquivo não existe
    public int arquivoExisteNaMemoria(String nomeArquivo, int QUANTIDADE_BLOCOS_MEMORIA){
        for(int i = 0; i < QUANTIDADE_BLOCOS_MEMORIA; i++){
            if(blocos[i].getNomeArquivo() != null && blocos[i].getNomeArquivo().equals(nomeArquivo))
                return blocos[i].getIdBloco();
        }
        return -1;
    }
    
    public boolean cabeNaMemoria(int tamanhoArquivo, int TAMANHO_BLOCOS){
        return tamanhoArquivo <= leds.size() * TAMANHO_BLOCOS;
    }
    
    //Remover nós da lista Led
    public void removerLed(int quantidadeBlocos){
        int i = 0;
        while(quantidadeBlocos > 0){
            leds.remove(0);
            //System.out.println("removido led " + i++);
            quantidadeBlocos--;
        }
    }
    
    //Seta as informações dos blocos que serão ocupados    
    public void inserirNoBloco(Arquivo arquivo, int quantidadeBlocos, int quantidadeBlocosMemoria){
        //controla quantos arquivos faltam ser inseridos
        int i = 0;
        int k = 0;
        
        while(blocos[i].isOcupado()){ i++; }
        int blocoAnterior = i;
        
        //Já que o arquivo cabe na memória, não há necessidade de tal verificação
        for(i = blocoAnterior; i < quantidadeBlocosMemoria && k < quantidadeBlocos; i++){
            if(!blocos[i].isOcupado()){
                
                blocos[i].setNomeArquivo(arquivo.getNome());
                blocos[i].setIdArquivo(arquivo.getId());
                blocos[i].setOcupado(true);
                
                //se for o 1º bloco, não salva, pois salvaria o .proximo como ele mesmo
                if(blocoAnterior != blocos[i].getIdBloco())
                    blocos[blocoAnterior].setProximo(blocos[i].getIdBloco());
                
                blocoAnterior = blocos[i].getIdBloco();
                
                k++;
            }
        }
    }
    
    //Desfragmentar arquivo
    //Remover espaços na memória entre os arquivos,
    //reduzindo assim a distância do inicio ao final na memória
    public void desfragmentarMemoria(int QUANTIDADE_BLOCOS_MEMORIA){
        for(int i = 0; i < QUANTIDADE_BLOCOS_MEMORIA; i++){
            //se o bloco estiver ocupado e sem arquivo gravado, ele está em "branco"
            if(blocos[i].isOcupado() && blocos[i].getIdArquivo() == -1)
                blocos[i].setOcupado(false);                
        }        
    }
    
    //Atualiza a quantidade de espaço sendo ocupado na Memória
    public int atualizarTamanho(int QUANTIDADE_BLOCOS_MEMORIA, int TAMANHO_BLOCOS){
        int quantidadeBlocosEmUso = 0;
        int tamanhoArquivo;
        
        for(int i = 0; i < QUANTIDADE_BLOCOS_MEMORIA; i++){
            if(blocos[i].isOcupado())
                quantidadeBlocosEmUso++;
        }
        tamanhoArquivo = quantidadeBlocosEmUso * TAMANHO_BLOCOS;
        return tamanhoArquivo;        
    }
    
    //Atualiza a quantidade de espaço sendo ocupado na Memória por um Arquivo
    public String atualizarTamanho(String nomeArquivo, int QUANTIDADE_BLOCOS_MEMORIA, int TAMANHO_BLOCOS){
        int idBlocoDoArquivo = arquivoExisteNaMemoria(nomeArquivo, QUANTIDADE_BLOCOS_MEMORIA);
        
        if(idBlocoDoArquivo != -1){
            int quantidadeBlocosEmUso = 0;
            int tamanhoArquivo;
            //idBlocoDoArquivo é o mesmo valor de i
            int idArquivo = blocos[idBlocoDoArquivo].getIdArquivo();

            for(int i = idBlocoDoArquivo; i < QUANTIDADE_BLOCOS_MEMORIA; i++){
                if(blocos[i].isOcupado()){
                    if(blocos[i].getIdArquivo() == idArquivo)
                        quantidadeBlocosEmUso++;
                }
            }
            tamanhoArquivo = quantidadeBlocosEmUso * TAMANHO_BLOCOS;
            /*
             * Byte     = 1             Byte
             * KiloByte = 1024          Bytes
             * MegaByte = 1048576       Bytes
             * GigaByte = 1073741824    Bytes
            */

            if (tamanhoArquivo < 1024){
                return tamanhoArquivo + " Byte(s)";
            } else if (tamanhoArquivo < 1048576) {
                return tamanhoArquivo/1024 + " KB";
            } else if (tamanhoArquivo < 1073741824) {
                return tamanhoArquivo/1048576 + " MB";
            } else 
                return tamanhoArquivo/1073741824 + " GB";  
            
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Arquivo \""+ nomeArquivo +"\" não existe na Memória");
            return null;
        }
    }
    
    public Bloco[] getBlocos() {
        return blocos;
    }

    public void setBlocos(Bloco[] blocos) {
        this.blocos = blocos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public List getLeds() {
        return leds;
    }

    public void setLeds(List leds) {
        this.leds = leds;
    }
    
}
