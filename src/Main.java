import java.util.*;

class Main {
    public static void main(String[] args) {
        int matriz[][] = new int[4][4];
        No no;
        int i,j;

        Scanner scan = new Scanner(System.in);
        //leitura do estado inicial
        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                matriz[i][j] = scan.nextInt();
            }
        }
        no = new No(matriz);

        //procura da solucao
        findSolution(no);
    }

    public static void findSolution(No start){
        //criacao da lista
        /*Set<No> listaAberta = new HashSet<No>();
        Set<No> listaFechada = new HashSet<No>();*/

        ArrayList<No> listaAberta = new ArrayList<No>();
        ArrayList<No> listaFechada = new ArrayList<No>();

        Euristica3 euristica = new Euristica3();
        No comeca = new No(copyMatriz(start.getState()));

        //set das informações do primeiro elemento
        start.setFather(null);
        start.setG(0);
        start.setH(euristica.ficarEmOrdem(start.getState()));
        start.setF(start.getG() + start.getH());

        //primeiro elemento a ser processado
        comeca.setFather(null);
        comeca.setG(start.getG());
        comeca.setH(start.getH());
        comeca.setF(comeca.getG() + comeca.getH());

        listaAberta.add(comeca);


        while(listaAberta.size() != 0){
            int ij[], i = 0;

            i = menorF(listaAberta); //no em execucao retirado da lista aberta
            No emProcesso = listaAberta.get(i);
            listaAberta.remove(emProcesso);
            System.out.println(emProcesso.getH());
            No noAux;

            if( emProcesso == null){
                System.out.println("Error");
                break;
            }

            //verificacao se o no em processo é a configuracao em final
            if(euristica.ficarEmOrdem(emProcesso.getState()) == 0){
                System.out.println(emProcesso.getF());
                break;
            }

            //criacao das 4 possibilidades
            if(!listaFechada.contains(emProcesso)){

                ij = find0(emProcesso.getState());
                if(ij[0] != 0){
                    noAux = new No (changeUp(emProcesso,ij[0],ij[1]));
                    if(!listaFechada.contains(noAux)){
                        euristica = new Euristica3();
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        listaAberta.add(noAux);
                    }
                }
                if(ij[0]!=3){

                    noAux = new No(changeDown(emProcesso,ij[0],ij[1]));
                    if(!listaFechada.contains(noAux)){
                        euristica = new Euristica3();
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        listaAberta.add(noAux);
                    }
                }
                if(ij[1]!=3){

                    noAux = new No(changeRight(emProcesso,ij[0],ij[1]));
                    if(!listaFechada.contains(noAux)){
                        euristica = new Euristica3();
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        listaAberta.add(noAux);
                    }
                }
                if(ij[1]!=0){

                    noAux = new No(changeLeft(emProcesso,ij[0],ij[1]));
                    if(!listaFechada.contains(noAux)){
                        euristica = new Euristica3();
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        listaAberta.add(noAux);
                    }

                }
                listaFechada.add(emProcesso);
            }



        }

    }

    public static int menorF(ArrayList<No> listaAberta){
        Scanner scan = new Scanner(System.in);
        int i = 0,teste = 0;
        No emProcesso = new No(null);
        emProcesso.setF(Integer.MAX_VALUE);
            for(No nozinho: listaAberta){
                if(nozinho.getF() < emProcesso.getF()){
                    emProcesso = nozinho;
                    i = teste;
                }
            teste++;
            }

        return i;

    }

    public static int[] find0(int[][] matriz){
        int i,j;
        int ij[] = new int[2];
        for(i=0; i<4; i++){
            for(j=0; j<4; j++){
                if(matriz[i][j] == 0){
                    ij[0] = i;
                    ij[1] = j;
                    return ij;
                }
            }
        }
        return ij;
    }

    public static int[][] copyMatriz(int [][] original){
        int i,j;
        int [][]copia = new int[4][4];
        for( i=0; i<4 ; i++){
            for(j=0; j<4; j++){
                copia[i][j] = original[i][j];
            }
        }
        return copia;
    }

    public static No copiaNo(No original){
        No copia = new No(copyMatriz(original.getState()));
        copia.setFather(original.getFather());
        copia.setF(original.getF());
        copia.setG(original.getG());
        copia.setH(original.getH());

        return copia;
    }

    public static void printMatriz(int matriz[][]){
        int i,j;
        int [][]copia = new int[4][4];
        for( i=0; i<4 ; i++){
            for(j=0; j<4; j++){
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }

    }

    public static int[][] changeUp(No no, int i, int j){
        int aux;
        int matriz[][] = copyMatriz(no.getState());
        aux = matriz[i][j];
        matriz[i][j] = matriz[i-1][j];
        matriz[i-1][j] = aux;

        return matriz;
    }
    public static int[][] changeDown(No no, int i, int j){
        int aux;
        int matriz[][] = (copyMatriz(no.getState()));
        aux = matriz[i][j];
        matriz[i][j] = matriz[i+1][j];
        matriz[i+1][j] = aux;

        return matriz;
    }
    public static int[][] changeRight(No no, int i, int j){
        int aux;
        int matriz[][] = copyMatriz(no.getState());
        aux = matriz[i][j];
        matriz[i][j] = matriz[i][j+1];
        matriz[i][j+1] = aux;

        return matriz;
    }
    public static int[][] changeLeft(No no, int i, int j) {
        int aux;
        int matriz[][] = copyMatriz(no.getState());
        aux = matriz[i][j];
        matriz[i][j] = matriz[i][j-1];
        matriz[i][j-1] = aux;

        return matriz;
    }

}

class No{
    int state[][] = new int [4][4];
    int g,h,f;
    No father;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        No no = (No) o;

        return Arrays.deepEquals(state, no.state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    public No(int[][] state) {
        this.state = state;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public No getFather() {
        return father;
    }

    public void setFather(No father) {
        this.father = father;
    }

}

class Euristica1{
    public int numerosFora(int matriz[][]){
        int quant = 0,i,j;
        int teste [][] = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};

        for(i=0; i<4; i++){
            for(j=0; j<4; j++){
                if(matriz[i][j] == teste[i][j] || matriz[i][j] == 0){
                }else{
                    quant++;
                }
            }
        }
        return quant;
    }
}
class Euristica2{
    int i,quant = 0, inicio;
    int matriz[] = new int[16];
    public void convert (int matriz[][]){
        this.matriz[0] = matriz[0][0];
        this.matriz[1] = matriz[0][1];
        this.matriz[2] = matriz[0][2];
        this.matriz[3] = matriz[0][3];

        this.matriz[4] = matriz[1][3];
        this.matriz[5] = matriz[2][3];
        this.matriz[6] = matriz[3][3];

        this.matriz[7] = matriz[3][2];
        this.matriz[8] = matriz[3][1];
        this.matriz[9] = matriz[3][0];

        this.matriz[10] = matriz[2][0];
        this.matriz[11] = matriz[1][0];

        this.matriz[12] = matriz[1][1];
        this.matriz[13] = matriz[1][2];

        this.matriz[14] = matriz[2][2];
        this.matriz[15] = matriz[2][1];
    }


    public int foraDeOrdem(){

        for(i=0;i<15;i++) {
            if((matriz[i] == (matriz[i+1]-1)) || matriz[i] == 0){

            }else{
                quant++;
            }
        }
        return quant;
    }
}
class Euristica3{

    int i1,j1,aux;
    int resp = 0;

    public int ficarEmOrdem(int matriz[][]){
        for(i1 = 0; i1 < 4; i1++){
            for(j1 = 0; j1 < 4; j1++){
                aux = matriz[i1][j1];
                resp = resp + ijCerto(i1,j1,aux);
            }
        }
        return resp;


    }

    public int ijCerto(int i1, int j1, int num) {
        int i2, j2;
        int teste[][] = {{1, 2, 3, 4}, {12, 13, 14, 5}, {11, 0, 15, 6}, {10, 9, 8, 7}};

        for (i2 = 0; i2 < 4; i2++) {
            for (j2 = 0; j2 < 4; j2++) {
                if (teste[i2][j2] == num && num != 0) {
                    return (Math.abs(i2 - i1)) + (Math.abs(j2 - j1));
                }
            }
        }
        return 0;
        }
}