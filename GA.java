
import java.io.PrintWriter;
import java.util.*;

public class GA {
	
	final int GENERATIONS = 300;
	int POPULATION_SIZE = 200;
	final int MAX_ALLOWED_REPRODUCTIONS = (2 * POPULATION_SIZE) / 3;
	final int pm = 10; 		// probability of mutation
	
	List<edge> adj[];	// graph		
	int N;				// #nodes
	int edgeLen[][];
	PrintWriter pw ;
	
	public GA(List<edge> adj[], int n, PrintWriter arg){
				N = n;
				this.adj = adj;
				this.pw = arg;
				
				
	}
	
	Random r = new Random();
	
	void solveTask(){
	
		edgeLen = new int[N+1][N+1];
		for(int i= 1; i<=N; i++){
			for(edge x : adj[i])
				edgeLen[i][x.end] = x.weight;
		}
		
		int i, j, k;		
		// initialse population
		Chromosome initial[] = new Chromosome[POPULATION_SIZE];
		Chromosome currGen[] = new Chromosome[POPULATION_SIZE];
		
		for(int it = 0; it < POPULATION_SIZE; it ++ )
		{
			cnt = 0;
			int path [] = generateRandomPath();			
			Chromosome c = new Chromosome(path, edgeLen);			
			initial[it] = c;
		}
		
		Arrays.sort(initial);		
		pw.println("Initial population has path length : " + initial[0].pathLen+"\n");
				
		for(i = 0; i < POPULATION_SIZE; i ++)
			currGen[i] = initial[i];
		
		int prev = initial[0].pathLen;
		
		for(int genIt = 1; genIt <= GENERATIONS; genIt ++ ){
			
			// parent selection for crossover 
			// number of offsprings generated will be randomly chosen and will be less than MAX_ALLOWED_REPRODUCTION 
			
			int num_offs = r.nextInt(MAX_ALLOWED_REPRODUCTIONS) + 1; 
			Chromosome children [] = new Chromosome[num_offs];
			double sumFit = 0;
			
			for(Chromosome it : currGen)			
				sumFit += it.fitness;			
		
			// reproduction stage
			
			for(j = 0; j < num_offs; j ++){
			
				double u = r.nextDouble() * sumFit;
				double v = r.nextDouble() * (sumFit);
			
				double p = 0;
				int x = -1, y = -1;
				for(i = 0; i < POPULATION_SIZE; i ++){
					p += currGen[i].fitness;
					if(p >= u){
						x = i;
						break;
					}
				}
				p = 0;
				for(i = 0; i < POPULATION_SIZE; i ++){
					p += currGen[i].fitness;
					if(p >= v){
						y = i;
						break;
					}
				}
			
				if(y == x)
				{
					j -- ;
					continue;
				}
				
				Chromosome cx = currGen[x], cy = currGen[y];
				Chromosome c1 = cx.crossover(r, cx, cy, edgeLen, 0), c2 = cx.crossover(r, cy, cx, edgeLen, 0), fc=null;
				if(c1 == null && c2 == null)
					j -- ;
				else{
					if(c1 == null ) fc = c2;
					else if(c2 == null) fc = c1;
					else if(c1.fitness > c2.fitness) fc = c1;
					else fc = c2;
				}
				
				if(fc != null){
					
					children[j] = fc;
				}
			}
			
			// replacement of less fit individuals
			
			for(i = 0; i < num_offs ; i++){
				currGen[POPULATION_SIZE - i - 1] = children[i];
			}
			
			Arrays.sort(currGen);
		
			// MUTATION STAGE, avoid mutating the elite member
			for(i = 1; i < POPULATION_SIZE ; i++){
				k = r.nextInt(101);
				if( k <= pm){
					currGen[i].mutate(r, edgeLen);
					currGen[i].computeFitness(edgeLen);
				}
			}
			
			Arrays.sort(currGen);
			
		}		
			
		pw.println("The path length produced is : "+ currGen[0].pathLen );
		for(int cities : currGen[0].path)
			pw.print(cities + "  ");
		
	}
	
	int cnt = 0;
	int[] generateRandomPath(){
	
		int ret[] = new int [N];
		int s = r.nextInt(N)+1, max = N*(N-1)/2;
		ret[0] = s;
		boolean is[] = new boolean[N+1];
		is[s] = true;
		for(int i=1, j = 0; i<N && j < max; i++, j++){
			int size = adj[ret[i-1]].size();
			s = r.nextInt(size);
			ret[i] = adj[ret[i-1]].get(s).end;
			if(is[ret[i]])
				i--;
			else
				is[ret[i]] = true;
		}
		
		if(edgeLen[ret[N-1]][ret[0]] > 0)
			return ret;
		else
			return generateRandomPath();
	}

}
