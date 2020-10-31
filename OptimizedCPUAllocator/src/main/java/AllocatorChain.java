package src.main.java;

public interface AllocatorChain {

	
	void setNextChain (AllocatorChain nextChain);
	void allocate(InputParam input, AllocatedParam allocated);
	
}
