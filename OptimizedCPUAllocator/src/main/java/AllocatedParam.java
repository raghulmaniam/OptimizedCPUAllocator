package src.main.java;

/*
 * Author: Raghul S
 * Date: 03 Nov 2020
 */

public class AllocatedParam
{

	private String region;
	private String currency;

	private int totalAllocatedCount;
	private double totalAllocatedPrice;

	private int cpuLargeCount;
	private int cpuxLargeCount;
	private int cpu2xLargeCount;
	private int cpu4xLargeCount;
	private int cpu8xLargeCount;
	private int cpu10xLargeCount;

	public int getTotalAllocatedCount()
	{
		return totalAllocatedCount;
	}

	public void addAllocatedCount( int totalAllocatedCount )
	{
		this.totalAllocatedCount = this.totalAllocatedCount + totalAllocatedCount;
	}

	public double getTotalAllocatedPrice()
	{
		return totalAllocatedPrice;
	}

	public void addAllocatedPrice( double totalCost )
	{
		this.totalAllocatedPrice = this.totalAllocatedPrice + totalCost;
	}

	public int getCpuLargeCount()
	{
		return cpuLargeCount;
	}

	public void setCpuLargeCount( int cpuLargeCount )
	{
		this.cpuLargeCount = cpuLargeCount;
	}

	public int getCpuxLargeCount()
	{
		return cpuxLargeCount;
	}

	public void setCpuxLargeCount( int cpuxLargeCount )
	{
		this.cpuxLargeCount = cpuxLargeCount;
	}

	public int getCpu2xLargeCount()
	{
		return cpu2xLargeCount;
	}

	public void setCpu2xLargeCount( int cpu2xLargeCount )
	{
		this.cpu2xLargeCount = cpu2xLargeCount;
	}

	public int getCpu4xLargeCount()
	{
		return cpu4xLargeCount;
	}

	public void setCpu4xLargeCount( int cpu4xLargeCount )
	{
		this.cpu4xLargeCount = cpu4xLargeCount;
	}

	public int getCpu8xLargeCount()
	{
		return cpu8xLargeCount;
	}

	public void setCpu8xLargeCount( int cpu8xLargeCount )
	{
		this.cpu8xLargeCount = cpu8xLargeCount;
	}

	public int getCpu10xLargeCount()
	{
		return cpu10xLargeCount;
	}

	public void setCpu10xLargeCount( int cpu10xLargeCount )
	{
		this.cpu10xLargeCount = cpu10xLargeCount;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion( String region )
	{
		this.region = region;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency( String currency )
	{
		this.currency = currency;
	}

}