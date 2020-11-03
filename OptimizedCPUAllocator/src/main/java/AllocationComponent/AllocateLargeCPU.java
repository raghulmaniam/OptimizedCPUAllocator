package src.main.java.AllocationComponent;

import src.main.java.AllocatedParam;
import src.main.java.AllocatorChain;
import src.main.java.Constants;
import src.main.java.InputParam;

public class AllocateLargeCPU implements AllocatorChain
{

	private AllocatorChain allocatorChain;

	@Override
	public void setNextChain( AllocatorChain nextChain )
	{
		this.allocatorChain = nextChain;
	}

	@Override
	public void allocate( InputParam input, AllocatedParam allocated )
	{

		int allocation = 0;

		if ( input.getRequiredCount() > 0 )
		{
			int remaining = input.getRequiredCount() - allocated.getTotalAllocatedCount();
			if ( remaining > Constants.TOTAL_CPU_LARGE )
				allocation = remaining / Constants.TOTAL_CPU_LARGE;

		}
		else if ( input.getRequiredPrice() > 0 )
		{
			double remaining = input.getRequiredPrice() - allocated.getTotalAllocatedPrice();
			if ( remaining > input.getCpuLargeCost() )
				allocation = ( int ) ( remaining / input.getCpuLargeCost() );
		}

		//System.out.println( "Allocating " + allocation + "  Large CPU(s)" );

		allocated.setCpuLargeCount( allocation );

		allocated.addAllocatedCount( allocation * Constants.TOTAL_CPU_10XLARGE );
		allocated.addAllocatedPrice( allocation * input.getCpuLargeCost() );

	}

}
