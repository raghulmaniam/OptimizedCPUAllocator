package src.main.java;

public interface AllocatorChain {

	
	void setNextChain (AllocatorChain nextChain);
	void allocate(CPUParam parameters);
	
}
