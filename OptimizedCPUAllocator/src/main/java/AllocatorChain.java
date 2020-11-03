package src.main.java;


/*
 * Author: Raghul S
 * Date: 03 Nov 2020
 */
public interface AllocatorChain {

	
	void setNextChain (AllocatorChain nextChain);
	void allocate(InputParam input, AllocatedParam allocated);
	
}
