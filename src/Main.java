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
		PriorityQueue<No> listaAberta = new PriorityQueue<No>();
		HashSet<No> listaFechada = new HashSet<No>();

		No comeca = new No(start,0, findZero(start)[0], findZero(start)[1]);

		//primeiro elemento a ser processado
		listaAberta.add(comeca);

		while(!listaAberta.isEmpty()){
			int ij[];
			No emProcesso = listaAberta.poll(); //no em execucao retirado da lista aberta
            //System.out.println(emProcesso.h);

//			if(listaFechada.contains(emProcesso)){
//				continue;
//			}

            listaFechada.add(emProcesso);
			No noAux;
			//verificacao se o no em processo é a configuracao em final
			if(emProcesso.h == 0){
				System.out.println(emProcesso.f);
				break;
			}
			//criacao das 4 possibilidades
			if(emProcesso.i != 0){
				noAux = new No(changeUp(emProcesso, emProcesso.i, emProcesso.j), emProcesso.g+1, emProcesso.i -1, emProcesso.j);
				if(!listaFechada.contains(noAux)){
					listaAberta.add(noAux);
				}
			}
			if(emProcesso.i !=3){
				noAux = new No(changeDown(emProcesso, emProcesso.i, emProcesso.j), emProcesso.g+1, emProcesso.i+1, emProcesso.j);
				if(!listaFechada.contains(noAux)){
					listaAberta.add(noAux);
				}
			}
			if(emProcesso.j !=3){
				noAux = new No(changeRight(emProcesso, emProcesso.i, emProcesso.j), emProcesso.g+1, emProcesso.i,emProcesso.j+1);
				if(!listaFechada.contains(noAux)){
					listaAberta.add(noAux);
				}
			}
			if(emProcesso.j !=0){
				noAux = new No(changeLeft(emProcesso, emProcesso.i, emProcesso.j), emProcesso.g+1, emProcesso.i, emProcesso.j-1);
				if(!listaFechada.contains(noAux)){
					listaAberta.add(noAux);
				}
			}
		}
	}

	public static int[] findZero(int[][] matriz){
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

	public static int[][] changeUp(No no, int i, int j){
		int matriz[][] = copyMatriz(no.state);
		matriz[i][j] = matriz[i-1][j];
		matriz[i-1][j] = 0;
		return matriz;
	}
	public static int[][] changeDown(No no, int i, int j){
		int matriz[][] = copyMatriz(no.state);
		matriz[i][j] = matriz[i+1][j];
		matriz[i+1][j] = 0;
		return matriz;
	}
	public static int[][] changeRight(No no, int i, int j){
		int matriz[][] = copyMatriz(no.state);
		matriz[i][j] = matriz[i][j+1];
		matriz[i][j+1] = 0;
		return matriz;
	}
	public static int[][] changeLeft(No no, int i, int j) {
		int matriz[][] = copyMatriz(no.state);
		matriz[i][j] = matriz[i][j-1];
		matriz[i][j-1] = 0;
		return matriz;
	}
}

class No implements Comparable<No>{
	int state[][] = new int [4][4];
	int g,h,f,i,j;

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(state);
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        No no = (No) o;

        return Arrays.deepEquals(state, no.state);
    }

    public No(int h[][], int g, int i, int j){
		this.i = i;
		this.j = j;
        this.state = h;
		this.g = g;
		this.h = euristica2(h);
		this.f = this.g + this.h;
	}


	public int euristica1(int matriz[][]){
    	int resp = 0;
		int i,j;
		int teste[][] = {{1, 2, 3, 4}, {12, 13, 14, 5}, {11, 0, 15, 6}, {10, 9, 8, 7}};


		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				if(matriz[i][j] == teste[i][j] || matriz[i][j] == 0){

				}else{
					resp++;
				}
			}
		}

    	return resp;
	}

	public int euristica2(int matriz[][]){
		int resp;
		int vetor[] = new int[16];
		vetor[0] = matriz[0][0];
		vetor[1] = matriz[0][1];
		vetor[2] = matriz[0][2];
		vetor[3] = matriz[0][3];

		vetor[4] = matriz[1][3];
		vetor[5] = matriz[2][3];
		vetor[6] = matriz[3][3];

		vetor[7] = matriz[3][2];
		vetor[8] = matriz[3][1];
		vetor[9] = matriz[3][0];

		vetor[10] = matriz[2][0];
		vetor[11] = matriz[1][0];

		vetor[12] = matriz[1][1];
		vetor[13] = matriz[1][2];

		vetor[14] = matriz[2][2];
		vetor[15] = matriz[2][1];

		return resp = (euristica2part2(vetor));

	}

	public int euristica2part2(int vetor[]){
		int resp = 0;
		for(i=0;i<15;i++) {
			if((vetor[i] == (vetor[i+1]-1)) || vetor[i] == 0){

			}else{
				resp++;
			}
		}
		return resp;
	}

	public int euristica3(int matriz[][]){
		int i1,j1,aux;
		int resp = 0;

		for(i1 = 0; i1 < 4; i1++){
			for(j1 = 0; j1 < 4; j1++){

				aux = matriz[i1][j1];
				resp = resp + eurista3part2(i1, j1, aux);
			}
		}
		return resp;


	}

	public int eurista3part2(int i1, int j1, int num) {
		int i2, j2;
		int teste[] = {0, 0, 1, 2, 3, 7, 11, 15, 14, 13, 12, 8, 4, 5, 6, 10};

		if(num == 0){
			return 0;
		}

		i2 = teste[num] / 4;
		j2 = teste[num] % 4;

		return (Math.abs(i1 - i2) + Math.abs(j1 - j2));

	}

	public int euristica5(int matriz[][]){
		int aux = Math.max(euristica1(matriz), euristica2(matriz));
		return Math.max(aux, euristica3(matriz));
	}

    @Override
    public int compareTo(No o) {
        return (int) f - o.f;
    }
}
