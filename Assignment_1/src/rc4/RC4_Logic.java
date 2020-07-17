package rc4;

public class RC4_Logic {
	
	//private variables
	private final int[] S = new int[256];
    private final int[] T = new int[256];
    private final int keyLength;
    private int l = 0, m = 0;

    //constructor (KSA) for the class object
    public RC4_Logic(final byte[] key) {
    	
    	//check if the key matches the requirements
        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException(
                    "key must be between 1 and 256 bytes");
        } else {
        	
        	//start initialization of S array
            keyLength = key.length;
            for (int i = 0; i < 256; i++) {
                S[i] = i;
                T[i] = key[i % keyLength];
            }
            int j = 0;
            int tmp;
            
            //Generate loop for S array
            for (int i = 0; i < 256; i++) {
            	//obtain value of j 
                j = (j + S[i] + T[i]) & 0xFF;		//performing Bitwise AND to calculate (modulo 256)
                //swapping S[i] and S[j]
                tmp = S[j];
                S[j] = S[i];
                S[i] = tmp;
            }
            
            System.out.print("\n");
            System.out.println("Initial S state obtained from the KSA:");
            
            //printing the S array obtained after KSA
        	for(int pos = 0; pos< 255; pos++) {
        		System.out.print(S[pos] + " ");
        	}
        }
    }
 
    //PRGA logic for specific number of rounds (n)
    public void prga(int n) {
    	
    	//local variable
    	int i = 0, j = 0;
    	int tmp;
    	
    	//the PRGA algorithm
    	for(int counter = 0; counter < n; counter++) {
    		i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
    	}
    	
    	//printing the S array obtained after PRGA
    	for(int pos = 0; pos< 255; pos++) {
    		System.out.print(S[pos] + " ");
    	}
    	
    	//variables to pass value of last (S,i,j) state to IPRGA
    	l = i;
    	m = j;
    }
    
    //IPRGA logic for specific number of rounds (n)
    public void iprga(int n) {
    	
    	//local variables
    	int tmp;
    	//take value of last (S,i,j) state
    	int i = l;
    	int j = m;
    	
    	//IPRGA algorithm
    	for(int counter = 0; counter < n; counter++) {
    		tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
            j = (j - S[i]) & 0xFF;
            i = (i - 1) & 0xFF;
        }
    	
    	//printing the S array obtained after IPRGA
    	for(int pos = 0; pos< 255; pos++) {
    		System.out.print(S[pos] + " ");
    	}
    }   
}