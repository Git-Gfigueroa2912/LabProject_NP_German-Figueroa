/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package labproject1_germanfigueroa;

import java.util.Scanner;

public class LabProjectP1_GermanFigueroa {
    public static Scanner leer = new Scanner(System.in);
    public static String obstaculo = "";
    public static boolean caja=false;
    public static int steeb_i = 3, steeb_j =13; 
    public static boolean gameOver = false;
    public static int dulces = 0;
    public static String [][] mapa;
    
    
    public static void main(String[] args) {
       mapa = generarMatriz(mapa);
       contarDulces();
       menu();  
    }
    public static void menu(){
        printMatriz(mapa);
        
        System.out.println("Estado de caja: " + (caja ? "Cargando caja" : "Sin caja"));
        System.out.println("Dulces restantes: " + dulces);
        
        if(dulces == 0){
            System.out.println("FELICIDADES, HA ENTREGADO TODOS LOS DULCES !!");
            return;
        }
        if(gameOver){
            System.out.println("GAME OVER");
            return;
        }
        System.out.println("Comandos: (U,D,L,R,F): ");
        String[] comandos = leer.nextLine().toUpperCase().split(",");
        
        for(int i = 0; i < comandos.length; i++){
            if(gameOver) break;
            ejecutar(comandos[i].trim());
        }
        menu();
    }
    public static void ejecutar(String c){
        int nueva_x = steeb_i;
        int nueva_y = steeb_j;
        
        if(c.equals("U"))nueva_x--;
        else if(c.equals("D"))nueva_x++;
        else if(c.equals("L"))nueva_y--;
        else if(c.equals("R"))nueva_y++;
        else if(c.equals("F")){
            usarCaja();
            return;
        }else{
            System.out.println("El comando es invalido");
            return;
        }
        mover(nueva_x, nueva_y);
    }
    public static void mover(int nueva_x, int nueva_y){
        if(nueva_x < 0 || nueva_y < 0 || nueva_x >= 24 || nueva_y >= 24){
            System.out.println("No se puede salir del tablero");
            return;
                    
        }
        
        String destino =  mapa[nueva_x][nueva_y];
        
        if(Obstaculo(destino)){
            gameOver = true;
            return;
        }
        mapa[steeb_i][steeb_j] = " ";
        steeb_i = nueva_x;
        steeb_j = nueva_y;
        mapa[steeb_i][steeb_j] = "S";
    }
    public static void usarCaja(){
        if(!caja){
            if(mapa[steeb_i][steeb_j].equals("j")){
                caja = true;
                mapa[steeb_i][steeb_j] = "S";
                System.out.println("Cargando caja ");
            }else{
                System.out.println("No se esta cargando ninguna caja");
            }
        }else{
           if(mapa[steeb_i][steeb_j].equals("D")||
              mapa[steeb_i][steeb_j].equals("H")||  
              mapa[steeb_i][steeb_j].equals("L")){
              
               caja = false;
               dulces--;
               mapa[steeb_i][steeb_j] = "j";
               
               System.out.println("La caja ha sido entregada");
               System.out.println("Dulces restantes: " + dulces);
           }else{
               System.out.println("La caja se dejo en el suelo temporalmente");
           }     
        }
    }
    public static void contarDulces(){
        for(int i = 0; i < 24; i++){
            for(int j = 0; j < 24; j++){
                if(mapa[i][j].equals("j")){
                    dulces++;
                }
            }
        }
    }
    public static boolean Obstaculo(String s){
        return s.equals("/") || s.equals("-") || s.equals("|") ||
               s.equals("\\") || s.equals("0") || s.equals("o")||
               s.equals("0") || s.equals("^") || s.equals("X");
    }
    
    public static void printMatriz(String[][]matriz){
        System.out.println("");
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                String s = matriz[i][j];
                if (s.equals("/") || s.equals("-") || s.equals("|") || s.equals("\\")) {
                    System.out.print("[" + "\u001B[32m" + s + "\u001B[0m" + "]");//verde
                } else if (s.equals("O") || s.equals("o") || s.equals("0") || s.equals("^")||s.equals("X")) {
                    System.out.print("[" + "\u001B[31m" + s + "\u001B[0m" + "]");//rojo
                } else if (s.equals("D")||s.equals("H")||s.equals("L")||s.equals("S")||s.equals("!S")
                        ||s.equals("DS")||s.equals("HS")||s.equals("LS")) {
                    System.out.print("[" + "\u001B[33m" + s + "\u001B[0m" + "]");//amarillo
                } else if (s.equals("S")||s.equals("!S")) {
                    System.out.print("[" + "\u001B[36m" + s + "\u001B[0m" + "]");//turquesa
                } else if (!s.equals(" ")) {
                    System.out.print("[" + "\u001B[34m" + s + "\u001B[0m" + "]");//azul
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println("");
        }
    }
    
    public static String[][] generarMatriz(String[][] mat) {
        mat = new String[24][24];
        
        int c = 2;
        
        // Inicializar matriz
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                mat[i][j] = " ";
            }
        }
        
        int centro = 12;
        // Tronco
        mat[18-c][centro] = "|";
        mat[19-c][centro] = "|";
        mat[17-c][centro-1] = "|";
        mat[18-c][centro-1] = "|";
        mat[19-c][centro-1] = "|";

        for (int j = 4; j < 20; j++) {
            mat[16-c][j] = "-";
            if ((j)==8||(j)==12) {
                mat[16-c][j] = " ";
            }
        }
        
        int con=1;
        for (int i = 8; i < 16; i++) {
            
            mat[i-c][centro - con] = "/";
            mat[i-c][centro-1+con] = "\\";
            if ((i-c)==7) {
                mat[i-c][centro - con] = " ";
            }
            con++;
        }
        for (int i = 20; i < 24; i++) {
            mat[1][i]="X";
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (!((i==1||i==2)&&j==2)) {
                    mat[i][j]="X";
                }
            }
        }
        for (int i = 8; i < 11; i++) {
            for (int j = 22; j < 24; j++) {
                if (!((j==21||j==22)&&i==9)) {
                    mat[i][j]="X";
                }
            }
        }
        for (int i = 11; i < 13; i++) {
            for (int j = 1; j < 4; j++) {
                if (!((j==21||j==22)&&i==9)) {
                    mat[i][j]="X";
                }
            }
        }
        mat[21][3]="X";mat[20][3]="X";mat[21][4]="X";
        mat[19][20]="X";mat[19][21]="X";mat[18][21]="X";


        mat[9-c][centro] = "O";
        mat[10-c][centro - 1] = "j";
        mat[10-c][centro + 1] = " ";
        mat[11-c][centro - 2] = "o";
        mat[11-c][centro] = " ";
        mat[11-c][centro + 2] = "j";
        mat[12-c][centro - 3] = "0";
        mat[12-c][centro - 1] = "j";
        mat[12-c][centro + 1] = " ";
        mat[12-c][centro + 3] = "j";
        mat[13-c][centro - 4] = "^";
        mat[13-c][centro - 2] = "^";
        mat[13-c][centro] = "o";
        mat[13-c][centro + 2] = "o";
        mat[14-c][centro - 5] = "O";
        mat[14-c][centro - 3] = "j";
        mat[14-c][centro - 1] = "O";
        mat[14-c][centro + 1] = "^";
        mat[15-c][centro - 6] = "0";
        mat[15-c][centro - 2] = "o";
        mat[15-c][centro] = "j";
        mat[15-c][centro + 2] = "O";
        mat[15-c][centro + 4] = "0";
        
        mat[2][2] = "D";mat[0][23]="H";mat[12][2]="L";
        mat[9][22]= "D";mat[20][4]= "H";mat[18][20]="L";
        mat[3][13]="S";
        
        
        return mat;
    }

}
