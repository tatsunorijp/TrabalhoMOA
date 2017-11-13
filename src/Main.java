import java.util.*;

class Main {
    public static void main(String[] args) {
        int matriz[][] = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};
        No solution = new No(matriz);  //formato da solucao
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
        findSolution(no, solution);
    }

    public static No findSolution(No start, No solution){
        //criacao da tabela hash (hashmap)
        int initialSize = 16, i;
        double loadFactor = 0.75;
        double sizeToRehash = initialSize * loadFactor;
        Map<Integer,List<No>> hashAberta = new HashMap<Integer,List<No>>();
        List<No> listaAberta = new ArrayList<>();
        List<No> listaFechada = new ArrayList<>();


        Euristica3 euristica = new Euristica3();
        No comeca = new No(copyMatriz(start.getState()));

        start.setG(0);
        start.setH(euristica.ficarEmOrdem(start.getState()));

        comeca.setFather(null);
        comeca.setH(start.getH());
        comeca.setF(comeca.getH());

        listaAberta.add(comeca);
        hashAberta.put(comeca.getF(),listaAberta);//insercao do primeiro elemento

        while(!hashAberta.isEmpty()){
            int ij[] = new  int[2];
            No emProcesso = menorF(hashAberta);
            No noAux;

            if( emProcesso == null){
                System.out.println("Error");
                break;
            }

            if(euristica.ficarEmOrdem(emProcesso.getState()) == 0){
                return emProcesso; //solucao final
            }

            if(!listaFechada.contains(emProcesso)){
                ij = find0(emProcesso.getState());
                if(ij[0] != 0){
                    noAux = changeUp(emProcesso,ij[0],ij[1]);
                    if(!listaFechada.contains(noAux)){
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        addNo(hashAberta, noAux, noAux.getF());
                    }
                }
                if(ij[0]!=3){
                    noAux = changeDown(emProcesso,ij[0],ij[1]);
                    if(!listaFechada.contains(noAux)){
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        addNo(hashAberta, noAux, noAux.getF());
                    }
                }
                if(ij[1]!=3){
                    noAux = changeRight(emProcesso,ij[0],ij[1]);
                    if(!listaFechada.contains(noAux)){
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        addNo(hashAberta, noAux, noAux.getF());
                    }
                }
                if(ij[1]!=0){
                    noAux = changeLeft(emProcesso,ij[0],ij[1]);
                    if(!listaFechada.contains(noAux)){
                        noAux.setFather(emProcesso);
                        noAux.setH(euristica.ficarEmOrdem(noAux.getState()));
                        noAux.setG(emProcesso.getG() + 1);
                        noAux.setF(noAux.getH() + noAux.getG());
                        addNo(hashAberta, noAux, noAux.getF());
                    }

                }
            }

            listaFechada.add(emProcesso);


        }

        return null;
    }
    public static int addNo(Map<Integer, List<No>> hash, No novoNo, int i){
        No noAux;

        for(Map.Entry<Integer,List<No>> entry: hash.entrySet()){
            if(entry.getKey() == i){
                entry.getValue().add(novoNo);
                return 0;
            }
        }
        List<No> lista = new ArrayList<No>();
        lista.add(novoNo);
        hash.put(novoNo.getF(),lista);
        return -1;
    }

    public static No menorF(Map<Integer,List<No>> hash){
        int i = 99999;
        No noAux;
        for(Map.Entry<Integer,List<No>> entry: hash.entrySet()){
            if(entry.getKey() <= i){
                i = entry.getKey();
            }
        }

        for(Map.Entry<Integer,List<No>> entry: hash.entrySet()){
            if(entry.getKey() == i){
                for(No no: entry.getValue()){
                    noAux = no;
                    entry.getValue().remove(no);
                    return noAux;
                }
            }
        }
        return null;
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

    public static No changeUp(No no, int i, int j){
        int aux;
        No novoNo = new No(copyMatriz(no.getState()));
        aux = novoNo.state[i][j];
        novoNo.state[i][j] = novoNo.state[i-1][j];
        novoNo.state[i-1][j] = aux;

        return novoNo;
    }
    public static No changeDown(No no, int i, int j){
        int aux;
        No novoNo = new No(copyMatriz(no.getState()));
        aux = novoNo.state[i][j];
        novoNo.state[i][j] = novoNo.state[i+1][j];
        novoNo.state[i+1][j] = aux;

        return novoNo;
    }
    public static No changeRight(No no, int i, int j){
        int aux;
        No novoNo = new No(copyMatriz(no.getState()));
        aux = novoNo.state[i][j];
        novoNo.state[i][j] = novoNo.state[i][j+1];
        novoNo.state[i][j+1] = aux;

        return novoNo;
    }
    public static No changeLeft(No no, int i, int j){
        int aux;
        No novoNo = new No(copyMatriz(no.getState()));
        aux = novoNo.state[i][j];
        novoNo.state[i][j] = novoNo.state[i][j-1];
        novoNo.state[i][j-1] = aux;

        return novoNo;
    }

}

class No{
    int state[][] = new int [4][4];
    int g,h,f;
    No father;

    public No(int[][] state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        No no = (No) o;

        if (g != no.g) return false;
        if (h != no.h) return false;
        if (f != no.f) return false;
        return Arrays.deepEquals(state, no.state);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(state);
        result = 31 * result + g;
        result = 31 * result + h;
        result = 31 * result + f;
        return result;
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