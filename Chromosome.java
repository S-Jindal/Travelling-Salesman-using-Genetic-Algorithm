import java.util.Arrays;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome>{
	int length;
	int path[];			// phenotype and genotype both
	double fitness;
	int pathLen;
	public Chromosome(int a[], int mat[][]){
		path = Arrays.copyOf(a, a.length);
		length = a.length;
		computeFitness(mat);
	}
	
	 Chromosome crossover(Random r, Chromosome x, Chromosome y, int mat[][], int counter){
		 
		 if (counter > 5) return null;
		int p1 = r.nextInt(length);
		int p2 = r.nextInt(length);
		if(p1 == p2) return crossover(r, x, y, mat, counter+1);
	
		if(p1 > p2) {
			int tmp= p1;
			p1 = p2;
			p2 = tmp;
		}
		
		int[] newPath = new int[length];
		boolean isUsed[] = new boolean[length+1];
		for(int i = p1; i <= p2; i++){
			newPath[i] = x.path[i];
			isUsed[x.path[i]] = true;
		}
		
		int j = p2;
	//	System.err.println(p1 + "  " + p2);
		for(int i = (p2+1)%length; i != p1; i = (i+1)%length ){
			
			for( j = (j+1)%length;  ; j = (j+1)%length){
				if(!isUsed[y.path[j]]){
					newPath[i] = y.path[j];
					break;
				}
			}
		}
		
		
		boolean correct = isValid(newPath, mat);
		if(correct)
			return new Chromosome(newPath, mat);
		else{
			//System.err.println("NOT VALID");
			return crossover(r,x,y,mat,counter+1);
		}
	}	
	 
	boolean isValid(int arg[], int mat[][]){
		 
			for(int j = 0; j < length - 1; j++){
			//  	System.err.println(mat[arg[j]][arg[j+1]]);
				if(mat[arg[j]][arg[j+1]] == 0)
					return false;
			}
			
			if(mat[arg[length-1]][arg[0]] == 0) return false;
			return true;
	}

	@Override
	public int compareTo(Chromosome c) {		// sort in descending
		if(c.fitness > this. fitness)			
			return 1;
		
		if(c.fitness < this.fitness)
			return -1;		
		
		return 0;
	}
	
	void computeFitness(int mat[][]){
		int p = 0;
		for(int j = 0; j < length - 1; j++)
		{	
			p += mat[path[j]][path[j+1]];
		}
		p += mat[path[length - 1]][0];
		this.pathLen = p;
		this.fitness = 100/(double)p;
	}
	
	void mutate(Random r, int mat[][]){
		int p1 = r.nextInt(length), p2 = r.nextInt(length);
		if(p1 == p2){
			this.mutate(r, mat);
			return ;
		}
		
		if(p1 > p2){
			int tmp = p1;
			p1 = p2; 
			p2 = tmp;
		}
		
		int tmp = this.path[p1];
		this.path[p1] = this.path[p2];
		this.path[p2] = tmp;
		if(isValid(this.path, mat)){
			return ;
		}
		else{
			tmp = this.path[p1];
			this.path[p1] = this.path[p2];
			this.path[p2] = tmp;
			this.mutate(r, mat);
		}
		/*
		
		int k = 1+r.nextInt(length-1);
		int [] temp = new int [length];
		for ( int i = 0; i<length; i++){
			temp[(i+k)%length] = this.path[i];
		}
		this.path = Arrays.copyOf(temp, length);
	*/
	}
	
	
}
