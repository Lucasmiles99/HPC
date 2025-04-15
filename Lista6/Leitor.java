package Lista6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Leitor extends Thread {

    private int contador = 0;

    public Leitor() {
    }

    @Override
    public void run() {
        while (contador < 10) {
            try {
                String caminhoArquivo = "C:\\Users\\lucas\\eclipse-HPC\\HPC\\dna-" + contador + ".txt";
                String caminhoDestino = "C:\\Users\\lucas\\eclipse-HPC\\HPC\\complemento-" + contador + ".txt";
                File arqDes = new File(caminhoDestino);
                FileReader arq = new FileReader(caminhoArquivo);
                BufferedReader lerArq = new BufferedReader(arq);
                String linha = lerArq.readLine();
                int nr_linha = 0;
                int fitasValidas = 0;
                int fitasInvalidas = 0;
                ArrayList<Integer> linhasInvalidas = new ArrayList<Integer>();

                File diretorio = arqDes.getParentFile();
                if (!diretorio.exists()) {
                    diretorio.mkdirs();
                }

                FileWriter print = new FileWriter(arqDes);

                String nomeArquivo = new File(caminhoArquivo).getName();

                while (linha != null) {
                    nr_linha += 1;
                    boolean isValid = true;
                    char[] caracteres = linha.toCharArray();      
                    for (int i = 0; i < caracteres.length; i++) {
                        if (caracteres[i] != 'A' && caracteres[i] != 'T' && 
                            caracteres[i] != 'G' && caracteres[i] != 'C') {
                            isValid = false;
                            break;
                        }
                    }
         
                    if (isValid) {
                        char[] complementar = new char[caracteres.length];
                        for (int i = 0; i < caracteres.length; i++) {
                            switch(caracteres[i]) {
                                case 'A':
                                    complementar[i] = 'T';
                                    break;
                                case 'T':
                                    complementar[i] = 'A';
                                    break;
                                case 'G':
                                    complementar[i] = 'C';
                                    break;
                                case 'C':
                                    complementar[i] = 'G';
                                    break;
                            }
                        }
                        String linhaTraduzida = new String(complementar);
                        print.write(linhaTraduzida + "\n");
                        fitasValidas++;
                    } else {
                        fitasInvalidas++;
                        linhasInvalidas.add(nr_linha);
                    }
                    
                    linha = lerArq.readLine();
                }

                System.out.println("Total de fitas do arquivo " + nomeArquivo + ": " + (fitasValidas + fitasInvalidas));
                System.out.println("Número de fitas válidas: " + fitasValidas);
                System.out.println("Número de fitas inválidas: " + fitasInvalidas);
                System.out.println("Listar as Fitas inválidas:");
                for (Integer linhaInvalida : linhasInvalidas) {
                    System.out.println("Fita " + linhaInvalida);
                }
                
                contador += 1;
                lerArq.close();
                print.close();
                arq.close();
            } catch (IOException e) {
                e.printStackTrace();
                contador += 1;
            }
        }

        System.out.println("-- Transcrição finalizada --");
    }
}