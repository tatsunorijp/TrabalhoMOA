import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        final int n =4,m =4;
        int matriz[][] = new int[n][m];
        int i,j;
        Euristicas euristicas = new Euristicas();
        Scanner scan = new Scanner(System.in);

        for(i=0;i<n;i++){
            for(j=0;j<m;j++){
                matriz[i][j] = scan.nextInt();
            }
        }
        System.out.println(euristicas.numerosFora(matriz));
    }


}

class Euristicas{
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
