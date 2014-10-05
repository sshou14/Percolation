/** 
 * problem description
 * this is an example of Dynamic Connectivity Problem that commonly appear in computer networks, social networks,circuit systems, etc
 * in lieu of this example, we solve a Percolation problem using the union find algorithm
 * In an N by N grid, with each cell open w/ probability p
 * we say that system percolates if top and bottom of the system are connected
 * we want to study how p affects the likelyhood of percolation
 */

public class Percolation {
	protected final int N;
	private int open = 1;
	private int blocked = 0;
	private int[][] grid;
	QuickUnion qu;
	private int entry;
	private int right, left, up, down;

	/** 
	 * @param N: number of cells per row/column
	 * @post initialize the system with array, with all site BLOCKED except for top and bottom
	 * @post connect the top cell with all cells in the first row and the bottom cell with all cells in the last
	 */
	public Percolation(int N){
		this.N=N;
		qu = new QuickUnion(N*N+2);
		grid = new int[N][N];
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				grid[i][j] = blocked;
			}
		}
		for (int i=1;i<=N;i++)           {qu.union(0,i);}
		for (int i=N*(N-1)+1;i<=N*N;i++) {qu.union(N*N+1,i);}
	}

	/** 
	 * @param i, j: row, column number
	 * @throw if row or column of given cell is out of range
	 * @post open site (row i, column j) if it is not already
	 * @post connect site (row i, column j) with all its neighbor cells that are open
	 */
	public void open(int i, int j){
		if (i>N||j>N||i<1||j<1) throw new IndexOutOfBoundsException();
		if (!isOpen(i, j)){
			grid[i-1][j-1] = open;
			entry = (i-1)*N +j;
			right = (i-1)*N +j+1;
			left  = (i-1)*N +j-1;
			up    = (i-2)*N +j;
			down  = (i)*N +j;
			// connect the neighbors: constant number of calls to connect() function
			if (j<N && isOpen(i,j+1)) qu.union(entry, right);
			if (j>1 && isOpen(i,j-1)) qu.union(entry, left);
			if (i<N && isOpen(i+1,j)) qu.union(entry, down);
			if (i>1 && isOpen(i-1,j)) qu.union(entry, up);		
		}
	}

	/** 
	 * @param i, j: row, column number
	 * @throw if row or column of given cell is out of range
	 * @return true if site located at (row i, column j) is open
	 */
	public boolean isOpen(int i, int j){
		if (i>N||j>N||i<1||j<1) throw new IndexOutOfBoundsException();
		return (grid[i-1][j-1] == open);
	}

	/** 
	 * @param i, j: row, column number
	 * @throw if row or column of given cell is out of range
	 * @return true if site at row i column j is connected with any site in the top row through a chain of open grids
	 * equivalently, we can check if a site is full by checking if it's connected to the top cell
	 */
	public boolean isFull(int i, int j){
		if (i>N||j>N||i<1||j<1) throw new IndexOutOfBoundsException();
		entry = (i-1)*N +j;
		return qu.connected(0,entry);
	}
	
	/**
	 * @return true if the system percolates and false otherwise
	 */
	public boolean percolates(){
		return qu.connected(0,N*N+1); //returns whether there's a connection between top and bottom
	}

	/**
	 * test client; tests whether the system percolates when we open certain cells
	 * the test should print out in order: false, false, true, true, false, true
	 */ 
	public static void main(String[] args){
		Percolation newgrid = new Percolation(20);
		System.out.println(newgrid.isOpen(2,2));
		System.out.println(newgrid.isFull(2,3));
		newgrid.open(2,3);
		newgrid.open(1,3);
		newgrid.open(2,2);
		System.out.println(newgrid.isFull(2,3)); // this should return true	
		System.out.println(newgrid.isOpen(2,2));
		System.out.println(newgrid.percolates()); // this should return false

		Percolation newgrid2 = new Percolation(3);
		newgrid2.open(1,2);
		newgrid2.open(2,2);
		newgrid2.open(3,2);
		System.out.println(newgrid2.percolates()); // this should return true
	}
}

