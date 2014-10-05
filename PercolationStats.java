/**
 * We will run a Monte Carlo Simulation to study how the probability p with which each cell is open affects the likelyhood of percolation
 * To do so, we will create a Percolation object, and repeat the following until the system percolates:
 *     Choose a site (row i, col j) uniformly at random among all blocked cells
 *     open the site (row i, col j)
 * the fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold
 */

import java.util.Random;
import java.lang.Math;
/**
 * @param 
 * ms: Percolation object
 * N: size of the grid
 * T: number of Monte Carlo runs
 * cell: an integer that keeps track of the number of cells that're open
 * result: an array of doubles that keeps track of the fraction of sites open when the system percolates in run i of the simulation
 */
public class PercolationStats{
	private Percolation ms;
	private int N; 
	private int T; 
	private double[] result;
	private int cell;
	private Random generator;
	private int i, j;

	/** 
	 * constructor takes in user input N and T and performs T independent computational experiments on an N-by-N grid
	 * @post compute the fraction of cells open when the system percolates and store the fraction in the result array 
	 */
	public PercolationStats(int N, int T){
		this.N=N;
		this.T=T;
		if (N<=0 || T<=0) throw new java.lang.IllegalArgumentException();
		ms = new Percolation(N);
		generator = new Random();
		result = new double[T]; // initialize result array; result[i] gives fraction of open cells need to percolate system in the i-th run
		int trial = 0;

		while (trial<T){
			ms = new Percolation(N);
			cell = 0; // initialize open cells count to 0
			while (ms.percolates()==false){
				while (true){
					int gen = generator.nextInt(N*N); // generate a random number between 0 and N^2-1
					i = gen/N+1;
					j = gen%N+1;
					if (ms.isOpen(i,j)==false) break;
				}
				ms.open(i,j);
				cell++; // increment the number of cells open
			}
			result[trial] = (double)cell/(double)(N*N);
			trial++;
		}
	}    
	
	/**
	 * @return sample mean of percolation threshold
	 */
	public double mean(){
		double sum = 0.0;
		for (double x : result)
			sum += x;
		return sum/T;
	}                     
	
	/**
	 * @return sample standard deviation of percolation threshold
	 */
	public double stddev(){
		double mean = mean();
		double temp = 0;
		for (double x : result)
			temp += (mean-x)*(mean-x);
		double var = temp/T;
		return Math.sqrt(var);
	}                   
	
	/**
	 * @return lower bound of the 95% confidence interval
	 * recall the confidence interval is calculated as: sample mean +/- 1.96*[sample std/sqrt(#samples)]
	 */
	public double confidenceLo(){
		return mean()-1.96*stddev()/Math.sqrt(T);
	}

	/**
	 * @return lower bound of the 95% confidence interval
	 */
	public double confidenceHi(){// returns upper bound of the 95% confidence interval
		return mean()+1.96*stddev()/Math.sqrt(T);
	}             
	
	/**
	 * test client, output threshold statistics
	 */
	public static void main(String[] args){
		PercolationStats new_ms = new PercolationStats(200,100);
		System.out.println("mean = "    + new_ms.mean());
		System.out.println("std = "     + new_ms.stddev());
		String stdstat = "95% confidence interval = ";
		stdstat += new_ms.confidenceLo() + ", " + new_ms.confidenceHi();
		System.out.println(stdstat);
	}

}