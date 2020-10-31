package src.main.java;

public class AllocateLargeCPU implements AllocatorChain {

	private AllocatorChain allocatorChain;

	@Override
	public void setNextChain(AllocatorChain nextChain) {
		this.allocatorChain = nextChain;

	}

	@Override
	public void allocate(CPUParam parameters) {
		if (parameters.getRequiredCount() > parameters.getTotalAllocatedCount() /* makeit as constasnt */) {
			int num = parameters.getRequiredCount() - parameters.getTotalAllocatedCount();
			int remaining = 0; // make it as const
			System.out.println("Allocating " + num + " CPU(s)");

			parameters.setCpuLargeCount(num);

			parameters.setTotalAllocatedCount(parameters.getRequiredCount());
			parameters.setTotalCost(parameters.getTotalCost() + (num * parameters.getCpuLargeCost()));

			/*
			 * to write --> 1.delegating the request to other allocators 2. checking total
			 * cost
			 */

			if (remaining != 0) {
				this.allocatorChain.allocate(parameters);
			}

		}

	}

}
