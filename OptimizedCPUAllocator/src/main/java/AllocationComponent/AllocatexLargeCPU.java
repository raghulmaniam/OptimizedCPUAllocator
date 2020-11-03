package src.main.java.AllocationComponent;

import src.main.java.AllocatedParam;
import src.main.java.AllocatorChain;
import src.main.java.Constants;
import src.main.java.InputParam;
/*
 * Author: Raghul S
 * Date: 03 Nov 2020
 */

public class AllocatexLargeCPU implements AllocatorChain
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
			//since this is the least unit, allocating all the remaining CPU(s)
			int remaining = input.getRequiredCount() - allocated.getTotalAllocatedCount();
			if ( remaining > Constants.TOTAL_CPU_XLARGE )
				allocation = remaining / Constants.TOTAL_CPU_XLARGE;

		}
		else if ( input.getRequiredPrice() > 0 )
		{
			double remaining = input.getRequiredPrice() - allocated.getTotalAllocatedPrice();
			if ( remaining > input.getCpuxLargeCost() )
				allocation = ( int ) ( remaining / input.getCpuxLargeCost() );
		}

		//System.out.println( "Allocating " + allocation + " x Large CPU(s)" );

		allocated.setCpuxLargeCount( allocation );

		allocated.addAllocatedCount( allocation * Constants.TOTAL_CPU_XLARGE );
		allocated.addAllocatedPrice( allocation * input.getCpuxLargeCost() );

		if ( ( input.getRequiredCount() > 0 && allocated.getTotalAllocatedCount() < input.getRequiredCount() ) || ( input.getRequiredPrice() > 0 && allocated.getTotalAllocatedPrice() < input.getRequiredPrice() ) )
		{
			// this will not be called - no element after this chain
			this.allocatorChain.allocate( input, allocated );
		}

	}

}
