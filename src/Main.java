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

        //procura da solucao
        findSolution(matriz);
    }

    public static void findSolution(int[][] start){

        ArrayList<No> listaAberta = new ArrayList<No>();
        ArrayList<No> listaFechada = new ArrayList<No>();

        Euristica3 euristica = new Euristica3();
        No comeca = new No(start,0,null);

        //primeiro elemento a ser processado
        listaAberta.add(comeca);


        while(listaAberta.size() != 0){
            int ij[], i = 0;

            No emProcesso = menorF(listaAberta); //no em execucao retirado da lista aberta
            System.out.println(emProcesso.h);
            listaFechada.add(emProcesso);
            No noAux;

            //verificacao se o no em processo é a configuracao em final
            if(euristica.ficarEmOrdem(emProcesso.state) == 0){
                System.out.println(emProcesso.f);
                break;
            }


            //criacao das 4 possibilidades
            ij = find0(emProcesso.state);
            if(ij[0] != 0){
                noAux = new No(changeUp(emProcesso,ij[0],ij[1]), emProcesso.g+1,emProcesso);
                if(!listaFechada.contains(noAux)){
                    if(!listaAberta.contains(noAux)){
                        listaAberta.add(noAux);
                    }
                }
            }
            if(ij[0]!=3){
                noAux = new No(changeDown(emProcesso,ij[0],ij[1]), emProcesso.g+1,emProcesso);
                if(!listaFechada.contains(noAux)){
                    if(!listaAberta.contains(noAux)){
                        listaAberta.add(noAux);
                    }
                }
            }
            if(ij[1]!=3){
                noAux = new No(changeRight(emProcesso,ij[0],ij[1]), emProcesso.g+1,emProcesso);
                if(!listaFechada.contains(noAux)){
                    if(!listaAberta.contains(noAux)){
                        listaAberta.add(noAux);
                    }
                }
            }
            if(ij[1]!=0){
                noAux = new No(changeLeft(emProcesso,ij[0],ij[1]), emProcesso.g+1,emProcesso);
                if(!listaFechada.contains(noAux)){
                    if(!listaAberta.contains(noAux)){
                        listaAberta.add(noAux);
                    }
                }

            }


        }
    }

    public static No menorF(ArrayList<No> listaAberta){

        No resultado = null;
        int menorF = Integer.MAX_VALUE;
        for(No nozinho: listaAberta){
            if(nozinho.f < menorF) {
                menorF = nozinho.f;
                resultado = nozinho;
            }
        }
        listaAberta.remove(resultado);
        return resultado;

    }

    public static boolean trocaNos(No noAux, ArrayList<No> listaAberta){
        for(No no: listaAberta){
            if(no.equals(noAux)){
                if(no.g < noAux.g){
                    noAux.g = no.g;
                    noAux.f = no.f;
                    noAux.father = no.father;
                }
                return true;
            }
        }
        return false;
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
        No copia = new No(copyMatriz(original.state),original.g,original.father);
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
        int matriz[][] = copyMatriz(no.state);
        aux = matriz[i][j];
        matriz[i][j] = matriz[i-1][j];
        matriz[i-1][j] = aux;

        return matriz;
    }
    public static int[][] changeDown(No no, int i, int j){
        int aux;
        int matriz[][] = (copyMatriz(no.state));
        aux = matriz[i][j];
        matriz[i][j] = matriz[i+1][j];
        matriz[i+1][j] = aux;

        return matriz;
    }
    public static int[][] changeRight(No no, int i, int j){
        int aux;
        int matriz[][] = copyMatriz(no.state);
        aux = matriz[i][j];
        matriz[i][j] = matriz[i][j+1];
        matriz[i][j+1] = aux;

        return matriz;
    }
    public static int[][] changeLeft(No no, int i, int j) {
        int aux;
        int matriz[][] = copyMatriz(no.state);
        aux = matriz[i][j];
        matriz[i][j] = matriz[i][j-1];
        matriz[i][j-1] = aux;

        return matriz;
    }

}

class No{
    Euristica3 euristica = new Euristica3();
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

    public No(int h[][],int g, No father){
        int i,j;
        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                this.state[i][j] = h[i][j];
            }
        }

        this.g = g;
        this.h = euristica.ficarEmOrdem(h);
        this.f = this.g + this.h;
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