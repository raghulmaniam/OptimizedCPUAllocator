package src.main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

		if ( input.getCpu10xLargeCost() > 0 )
			costPerUnitMap.put( new Allocate10xLargeCPU(), input.getCpu10xLargeCost() / 32 );

		if ( input.getCpu8xLargeCost() > 0 )
			costPerUnitMap.put( new Allocate8xLargeCPU(), input.getCpu8xLargeCost() / 16 );

		if ( input.getCpu4xLargeCost() > 0 )
			costPerUnitMap.put( new Allocate4xLargeCPU(), input.getCpu4xLargeCost() / 8 );

		if ( input.getCpu2xLargeCost() > 0 )
			costPerUnitMap.put( new Allocate2xLargeCPU(), input.getCpu2xLargeCost() / 4 );

		if ( input.getCpuxLargeCost() > 0 )
			costPerUnitMap.put( new AllocatexLargeCPU(), input.getCpuxLargeCost() / 2 );

		if ( input.getCpuLargeCost() > 0 )
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

	public static void main( String[] args ) throws IOException
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
		int cpus = 110;
		int price = 14;

		DecimalFormat df = new DecimalFormat( "0.00" );

		List<AllocatedParam> regionWiseAllocatedResult = get_costs( hours, cpus, price );

		for ( AllocatedParam region : regionWiseAllocatedResult )
		{

			displayRegionResult( region, df );

			//to sort it 

		}

	}

	private static void displayRegionResult( AllocatedParam region, DecimalFormat df )
	{
		System.out.println( "{" );
		System.out.println( " \"region\":" + region.getRegion() );
		System.out.println( " \"total_cost\":\"$" + df.format( region.getTotalAllocatedPrice() ) + "\"" );
		System.out.println( " \"servers\" :[ " );
		if ( region.getCpuLargeCount() > 0 )
			System.out.println( "(\"large\"," + region.getCpuLargeCount() + ")" );
		if ( region.getCpuxLargeCount() > 0 )
			System.out.println( "(\"xlarge\"," + region.getCpuxLargeCount() + ")" );
		if ( region.getCpu2xLargeCount() > 0 )
			System.out.println( "(\"2xlarge\"," + region.getCpu2xLargeCount() + ")" );
		if ( region.getCpu4xLargeCount() > 0 )
			System.out.println( "(\"4xlarge\"," + region.getCpu4xLargeCount() + ")" );
		if ( region.getCpu8xLargeCount() > 0 )
			System.out.println( "(\"8xlarge\"," + region.getCpu8xLargeCount() + ")" );
		if ( region.getCpu10xLargeCount() > 0 )
			System.out.println( "(\"10xlarge\"," + region.getCpu10xLargeCount() + ")" );
		System.out.println( "] " );
		System.out.println( "}" );
	}

	public static Properties readPropertiesFile( String fileName ) throws IOException
	{
		FileInputStream fis = null;
		Properties prop = null;
		try
		{
			fis = new FileInputStream( fileName );
			prop = new Properties();
			prop.load( fis );
		}
		catch ( FileNotFoundException fnfe )
		{
			fnfe.printStackTrace();
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace();
		}
		finally
		{
			if ( fis != null )
				fis.close();
		}
		return prop;
	}

	private static List<AllocatedParam> get_costs( int hours, int cpus, int price )
	{

		List<AllocatedParam> allocatedList = new ArrayList<>();

		String propLocation = "src" + Constants.SEPERATOR + "main" + Constants.SEPERATOR + "resource" + Constants.SEPERATOR;
		Properties prop = null;

		File folder = new File( propLocation );

		File[] listOfFiles = folder.listFiles();

		for ( int i = 0; i < listOfFiles.length; i++ )
		{
			if ( listOfFiles[i].isFile() )
			{
				try
				{
					prop = readPropertiesFile( propLocation + listOfFiles[i].getName() );

					InputParam input = new InputParam();
					AllocatedParam allocated = new AllocatedParam();

					//check proce per hour
					loadPropertyData( allocated, input, prop );

					double pricePerHour = price / hours;

					input.setRequiredPrice( pricePerHour );
					input.setRequiredCount( cpus );

					CPUAllocatorMain cpuAllocatorMain = new CPUAllocatorMain( input );
					cpuAllocatorMain.first.allocate( input, allocated );

					allocatedList.add( allocated );

				}
				catch ( IOException e )
				{
					System.out.println( listOfFiles[i].getName() + " could not be loaded, skipping the region" );
					e.printStackTrace();
				}

			}
			else if ( listOfFiles[i].isDirectory() )
			{
				//do nothing
			}
		}

		return allocatedList;

	}

	private static void loadPropertyData( AllocatedParam allocated, InputParam input, Properties prop )
	{
		allocated.setRegion( prop.getProperty( "region" ) );

		if ( prop.get( "large" ) != null )
			input.setCpuLargeCost( Double.parseDouble( ( String ) prop.get( "large" ) ) );
		if ( prop.get( "xlarge" ) != null )
			input.setCpuxLargeCost( Double.parseDouble( ( String ) prop.get( "xlarge" ) ) );
		if ( prop.get( "2xlarge" ) != null )
			input.setCpu2xLargeCost( Double.parseDouble( ( String ) prop.get( "2xlarge" ) ) );
		if ( prop.get( "4xlarge" ) != null )
			input.setCpu4xLargeCost( Double.parseDouble( ( String ) prop.get( "4xlarge" ) ) );
		if ( prop.get( "8xlarge" ) != null )
			input.setCpu8xLargeCost( Double.parseDouble( ( String ) prop.get( "8xlarge" ) ) );
		if ( prop.get( "10xlarge" ) != null )
			input.setCpu10xLargeCost( Double.parseDouble( ( String ) prop.get( "10xlarge" ) ) );

	}

}
