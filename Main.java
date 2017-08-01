
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		double startTime = System.currentTimeMillis();
		double time, savTime ;
		
		File fout = new File("C:/Users/.hp/Desktop/GAOUT.txt");
		PrintWriter p = new PrintWriter(fout);		
		
		File fin = new File("C:/Users/.hp/Desktop/GAtesting.txt");
		
		Scanner in = new Scanner(new FileReader(fin));
		
		int n = in.nextInt(), m = in.nextInt();
		
		List<edge> list[] = new ArrayList[n+1];
		
		for(int i =1 ; i<=n; i++){
			list[i] = new ArrayList<edge>(30);
		}
		
		while(m-->0){
			int u = in.nextInt(), v= in.nextInt(), w = in.nextInt();
			list[u].add(new edge(v,w));
			list[v].add(new edge(u,w));		
			
		}
			savTime = System.currentTimeMillis();
			System.err.println("Data read at Time : " + (savTime- startTime)/1000 );
			
			GA ga = new GA(list, n, p);
			ga.solveTask();
			
			time = System.currentTimeMillis();
			System.err.println("Exiting program at Time : " + (time - startTime)/1000 );
			System.err.println("Converged in time : " + (time - savTime )/1000 );
			
			p.close();
	}
	
	
	
}

class edge{
	int end, weight;
	public edge(int x, int y){
		end = x;
		weight = y;
	}
		
}