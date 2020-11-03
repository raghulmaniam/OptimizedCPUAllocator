package src.main.java;

public class Allocate8xLargeCPU implements AllocatorChain
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
			if ( remaining > Constants.TOTAL_CPU_8XLARGE )
				allocation = remaining / Constants.TOTAL_CPU_8XLARGE;

		}
		else if ( input.getRequiredPrice() > 0 )
		{
			double remaining = input.getRequiredPrice() - allocated.getTotalAllocatedPrice();
			if ( remaining > input.getCpu8xLargeCost() )
				allocation = ( int ) ( remaining / input.getCpu8xLargeCost() );
		}

		System.out.println( "Allocating " + allocation + " 8x Large CPU(s)" );

		allocated.setCpu8xLargeCount( allocation );

		allocated.addAllocatedCount( allocation * Constants.TOTAL_CPU_8XLARGE );
		allocated.addAllocatedPrice( allocation * input.getCpu8xLargeCost() );

		if ( ( input.getRequiredCount() > 0 && allocated.getTotalAllocatedCount() < input.getRequiredCount() ) || ( input.getRequiredPrice() > 0 && allocated.getTotalAllocatedPrice() < input.getRequiredPrice() ) )
		{
			this.allocatorChain.allocate( input, allocated );
		}

	}

}
