package src.main.java.AllocationComponent;

import src.main.java.AllocatedParam;
import src.main.java.AllocatorChain;
import src.main.java.Constants;
import src.main.java.InputParam;

public class Allocate10xLargeCPU implements AllocatorChain
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
			if ( remaining > Constants.TOTAL_CPU_10XLARGE )
				allocation = remaining / Constants.TOTAL_CPU_10XLARGE;

		}
		else if ( input.getRequiredPrice() > 0 )
		{
			double remaining = input.getRequiredPrice() - allocated.getTotalAllocatedPrice();
			if ( remaining > input.getCpu10xLargeCost() )
				allocation = ( int ) ( remaining / input.getCpu10xLargeCost() );
		}

		System.out.println( "Allocating " + allocation + " 10x Large CPU(s)" );

		allocated.setCpu10xLargeCount( allocation );

		allocated.addAllocatedCount( allocation * Constants.TOTAL_CPU_10XLARGE );
		allocated.addAllocatedPrice( allocation * input.getCpu10xLargeCost() );

		if ( ( input.getRequiredCount() > 0 && allocated.getTotalAllocatedCount() < input.getRequiredCount() ) || ( input.getRequiredPrice() > 0 && allocated.getTotalAllocatedPrice() < input.getRequiredPrice() ) )
		{
			this.allocatorChain.allocate( input, allocated );
		}

	}

}
