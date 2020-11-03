package src.main.java;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import src.main.java.AllocationComponent.*;

public class CPUAllocatorMain
{

	private AllocatorChain first;

	public CPUAllocatorMain( InputParam input )
	{

		/*Double costPerUnit10x = input.getCpu10xLargeCost() / 32;
		Double costPerUnit8x = input.getCpu8xLargeCost() / 16;
		Double costPerUnit4x = input.getCpu4xLargeCost() / 8;
		Double costPerUnit2x = input.getCpu2xLargeCost() / 4;
		Double costPerUnitx = input.getCpuxLargeCost() / 2;
		Double costPerUnit = input.getCpuLargeCost();*/

		Map<AllocatorChain, Double> costPerUnitMap = new HashMap<AllocatorChain, Double>();
		costPerUnitMap.put( new Allocate10xLargeCPU(), input.getCpu10xLargeCost() / 32 );
		costPerUnitMap.put( new Allocate8xLargeCPU(), input.getCpu8xLargeCost() / 16 );
		costPerUnitMap.put( new Allocate4xLargeCPU(), input.getCpu4xLargeCost() / 8 );
		costPerUnitMap.put( new Allocate2xLargeCPU(), input.getCpu2xLargeCost() / 4 );
		costPerUnitMap.put( new AllocatexLargeCPU(), input.getCpuxLargeCost() / 2 );
		costPerUnitMap.put( new AllocateLargeCPU(), input.getCpuLargeCost() );

		//check how it is getting sorted -- to be made desc
		Map<AllocatorChain, Double> sortedMap = sortByValue( costPerUnitMap );

		AllocatorChain prev = null;

		for ( Map.Entry<AllocatorChain, Double> entry : sortedMap.entrySet() )
		{
			if ( prev == null )
			{
				//first entry
				this.first = entry.getKey();
			}
			else
				prev.setNextChain( entry.getKey() );

			prev = entry.getKey();

		}

	}

	private Map<AllocatorChain, Double> sortByValue( Map<AllocatorChain, Double> hm )
	{
		List<Map.Entry<AllocatorChain, Double>> list = new LinkedList<Map.Entry<AllocatorChain, Double>>( hm.entrySet() );

		// Sort the list 
		Collections.sort( list, new Comparator<Map.Entry<AllocatorChain, Double>>()
		{
			public int compare( Map.Entry<AllocatorChain, Double> o1, Map.Entry<AllocatorChain, Double> o2 )
			{
				return ( o1.getValue() ).compareTo( o2.getValue() );
			}
		} );

		// put data from sorted list to hashmap  
		HashMap<AllocatorChain, Double> temp = new LinkedHashMap<AllocatorChain, Double>();
		for ( Map.Entry<AllocatorChain, Double> aa : list )
		{
			temp.put( aa.getKey(), aa.getValue() );
		}
		return temp;
	}

	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

		/*
		 * to write 1. ordering the cpu's based on cost per cpu 2. reading the input
		 * property file 3. setting the chain 4. pritning the values 5. comparator class
		 * to sort the region param, write a method get_cost
		 */
		/* 1. to give handle to add locations 
		 * 2. to multiple hours back*/

		int hours = 10;
		int cpus = 0;
		int price = 50;

		DecimalFormat df = new DecimalFormat( "0.00" );

		AllocatedParam allocated = get_costs( hours, cpus, price );

		System.out.println( " \"total_cost\":\"$" + df.format( allocated.getTotalAllocatedPrice() ) + "\"" );
		System.out.println( " \"servers\" :[ " );
		System.out.println( "(\"large\"," + allocated.getCpuLargeCount() + ")" );
		System.out.println( "(\"xlarge\"," + allocated.getCpuxLargeCount() + ")" );
		System.out.println( "(\"2xlarge\"," + allocated.getCpu2xLargeCount() + ")" );
		System.out.println( "(\"4xlarge\"," + allocated.getCpu4xLargeCount() + ")" );
		System.out.println( "(\"8xlarge\"," + allocated.getCpu8xLargeCount() + ")" );
		System.out.println( "(\"10xlarge\"," + allocated.getCpu10xLargeCount() + ")" );
		System.out.println( "] " );

	}

	private static AllocatedParam get_costs( int hours, int cpus, int price )
	{

		AllocatedParam allocated = new AllocatedParam();
		InputParam input = new InputParam();

		double pricePerHour = price / hours;

		input.setRequiredPrice( pricePerHour );
		input.setRequiredCount( cpus );

		input.setCpuLargeCost( 0.12 );
		input.setCpuxLargeCost( 0.23 );
		input.setCpu2xLargeCost( 0.45 );
		input.setCpu4xLargeCost( 0.774 );
		input.setCpu8xLargeCost( 1.4 );
		input.setCpu10xLargeCost( 2.82 );

		CPUAllocatorMain cpuAllocatorMain = new CPUAllocatorMain( input );
		cpuAllocatorMain.first.allocate( input, allocated );

		return allocated;

	}

}
