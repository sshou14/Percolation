/** 
 * a quick union algorithm with weighting and path compression
 * the id array at i equates the parent of object i
 * the sz array is to maintain count number of objects in the tree rooted at i
 * for example, id[]=0, 1, 9, 4, 9, 6, 6, 7, 8, 9 represents the following relationship
 *	         0       1        9         6       7         8
 *	                        /   \       |
 *	                       2     4      5
 *	                             |
 *	                             3
 * in this example, 9 is called 3's root, hence sz[3] = 4
 */

public class QuickUnion{
	private int[] id;
	private int[] sz; 

	/** 
	 * constructor initializes id array to 0,1,...,N-1 and sz array all to 1's
	 */
	public QuickUnion(int N){
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i<N; i++){
			id[i] = i; 
			sz[i] = 1; 
		}
	}

	/** 
	 * a helper function that finds the root of i
	 * @param i: a number from 0 to N-1
	 * @return the root of number i as an interger 
	 */
	private int root(int i){
		while (i!=id[i]){
			id[i]=id[id[i]]; // reduce path length by having each node pointing to its grandparent
			i=id[i];
		}
		return i;
	}

	/** 
	 * @param p, q: integer values between 0 and N-1
	 * @return true if there exists a connection between p and q
	 */
	public boolean connected(int p, int q){
		return root(p) == root(q);
	}

	/** 
	 * @param p, q: integer values between 0 and N-1
	 * @post connect p and q by pointing the root of the smaller one to the root of the larger
	 */
	public void union(int p, int q){ 
		int i = root(p);
		int j = root(q);
		if (i==j) return;
		if (sz[i]<sz[j]) {id[i]=j; sz[j]+=sz[i]; }
		else             {id[j]=i; sz[i]+=sz[j]; }
	}

	/** 
	 * test client for QuickUnion
	 * client should take in two integers p, q, connect them, and print out all integers between 0 and N that's connected to p
	 */
	public static void main(String args[]) {
		int N = StdIn.readInt();
		QuickUnion un = new QuickUnion(N);
		while (!StdIn.isEmpty()){
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!un.connected(p,q)){
				un.union(p,q);
			}
			for (int i=0;i<N;i++){
				if (un.connected(p,i)) System.out.println(i);
			}
		}
	}
	
}

