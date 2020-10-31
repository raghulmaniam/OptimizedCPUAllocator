package src.main.java;

public class AllocateLargeCPU implements AllocatorChain {

	private AllocatorChain allocatorChain;

	@Override
	public void setNextChain(AllocatorChain nextChain) {
		this.allocatorChain = nextChain;

	}

	@Override
	public void allocate(InputParam input, AllocatedParam allocated) {
		if (allocated.getTotalAllocatedCount() < input.getRequiredCount()) {
			int allocation = input.getRequiredCount() - allocated.getTotalAllocatedCount();
			int remaining = 0; // make it as const
			System.out.println("Allocating " + allocation + " CPU(s)");

			allocated.setCpuLargeCount(allocation);
			// parameters.setCpuLargeCost(num * parameters.getCpuLargeCost());

			allocated.setTotalAllocatedCount(input.getRequiredCount());
			allocated.setTotalCost(allocated.getTotalCost() + (allocation * input.getCpuLargeCost()));

			/*
			 * to write --> 1.delegating the request to other allocators 2. checking total
			 * cost
			 */

			if (remaining > 0) {
				this.allocatorChain.allocate(input, allocated);
			}

		}

	}

}
