package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] possibildiades; //= {"rock","fire","scissors","sponge","paper","air","water"};
        String entrada, saida;
        System.out.println("Enter your name: ");
        String jogador = scanner.next();
        System.out.printf("Hello, %s \n",jogador);

        int pontuacao=readToFile("rating.txt",jogador);
        //System.out.println(pontuacao);
        int ganhador=0;
        possibildiades=possiveis();
        System.out.println("Okay, let's start");
        while (true){
            entrada = menu(possibildiades);
            if (entrada.equals("!exit")){
                System.out.println("Bye!");
                System.exit(0);
            }else if (entrada.equals("!rating")){
                System.out.printf("Your rating: %s \n",pontuacao);
            }else {
                saida = computador(possibildiades);
                ganhador=teste(entrada, saida,possibildiades);
                if (ganhador==0){
                    pontuacao+=100;
                }else if (ganhador==2){
                    pontuacao+=50;
                }
            }

        }

    }
    public static String[] possiveis(){
        String[]  possibildiades;
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine();
        if (entrada.isBlank() || entrada.isEmpty()){
            possibildiades= new String[]{"rock","paper","scissors"};
        }else{
            possibildiades=entrada.split(",");
        }
        return possibildiades;
    }
    public static String menu(String[] possibilidades) {
        Scanner scanner = new Scanner(System.in);
        String entrada = scanner.nextLine();
        String saida = "";
        while (true) {
            if (Arrays.asList(possibilidades).contains(entrada) || entrada.equals("!exit") || entrada.equals("!rating")  ) {
                break;
            } else {
                System.out.println("Invalid input");
                entrada = scanner.nextLine();
            }
        }
        return entrada;
    }

    public static String computador(String [] possibildiades) {
        Random r = new Random();
        int saida= r.nextInt(possibildiades.length);
        return possibildiades[saida];
    }


    public static int teste(String entrada, String saida,String[] possibilidades) {
        int ganhador = 1; // ganhador =0 -> usuario , ganhador 1-> computador ganhador 2->empate
        int indexEntrada=Arrays.asList(possibilidades).indexOf(entrada);
        int indexSaida=Arrays.asList(possibilidades).indexOf(saida);
        int resposta=(indexSaida-indexEntrada+ possibilidades.length)% possibilidades.length;
        //System.out.println(resposta);
        if (resposta==0){ganhador=2;}
        else if (resposta>(possibilidades.length/2)){ganhador=0;}
        else {ganhador=1;}

        switch (ganhador) {
            case 0:
                System.out.printf("Well done. The computer chose %s and failed \n", saida);
                break;
            case 1:
                System.out.printf("Sorry, but the computer chose %s \n", saida);
                break;
            case 2:
                System.out.printf("There is a draw (%s) \n", saida);
                break;
        }
        return ganhador;
    }
    public static void printToFile(String file, boolean append, String jogador, int pontuacao) {
        try (FileWriter writer = new FileWriter(file, append)) {
                writer.write(jogador + " "+ String.valueOf(pontuacao)+"\n");

        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }
    public static int readToFile (String file, String jogador){
         //   String curDir = System.getProperty("user.dir")+"/"+ file;
        String curDir = file;
            //System.out.println(curDir);
            File arq=new File(curDir);
            //System.out.println(arq.isFile());
            int pontuacao=0;
            String[] leitura;
            try (Scanner scanner = new Scanner(arq)){
                while (scanner.hasNext()) {
                    leitura = scanner.nextLine().split(" ");
                    if(leitura[0].equals(jogador) ){
                        //System.out.println(leitura[1]);
                        pontuacao=Integer.parseInt(leitura[1]);
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("No file found: " + curDir);
            }
            return pontuacao;
        }
    }
