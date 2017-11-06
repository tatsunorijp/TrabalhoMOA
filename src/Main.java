import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        final int n =4,m =4;
        int matriz[][] = new int[n][m];
        int i,j;
        Euristica1 euristica1 = new Euristica1();
        Euristica2 euristica2 = new Euristica2();
        Scanner scan = new Scanner(System.in);

        for(i=0;i<n;i++){
            for(j=0;j<m;j++){
                matriz[i][j] = scan.nextInt();
            }
        }

        //System.out.println(euristica1.numerosFora(matriz));
        euristica2.convert(matriz);
        System.out.println(euristica2.foraDeOrdem());
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
    int teste [][] = {{1,2,3,4},{12,13,14,5},{11,0,15,6},{10,9,8,7}};

    public void ficarEmOrdem(int matriz[][]){
        
    }
}