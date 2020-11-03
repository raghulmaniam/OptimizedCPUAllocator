package src.main.java;

public class Allocate2xLargeCPU implements AllocatorChain
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
			if ( remaining > Constants.TOTAL_CPU_2XLARGE )
				allocation = remaining / Constants.TOTAL_CPU_2XLARGE;

		}
		else if ( input.getRequiredPrice() > 0 )
		{
			double remaining = input.getRequiredPrice() - allocated.getTotalAllocatedPrice();
			if ( remaining > input.getCpu2xLargeCost() )
				allocation = ( int ) ( remaining / input.getCpu2xLargeCost() );
		}

		System.out.println( "Allocating " + allocation + " 2x Large CPU(s)" );

		allocated.setCpu2xLargeCount( allocation );

		allocated.addAllocatedCount( allocation * Constants.TOTAL_CPU_2XLARGE );
		allocated.addAllocatedPrice( allocation * input.getCpu2xLargeCost() );

		if ( ( input.getRequiredCount() > 0 && allocated.getTotalAllocatedCount() < input.getRequiredCount() ) || ( input.getRequiredPrice() > 0 && allocated.getTotalAllocatedPrice() < input.getRequiredPrice() ) )
		{
			this.allocatorChain.allocate( input, allocated );
		}

	}

}
