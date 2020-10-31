package src.main.java;

public class CPUAllocatorMain {

	private AllocatorChain c1;

	public CPUAllocatorMain() {
		this.c1 = new Allocate16xLargeCPU();

		AllocatorChain c2 = new Allocate8xLargeCPU();
		AllocatorChain c3 = new Allocate4xLargeCPU();
		AllocatorChain c4 = new Allocate2xLargeCPU();
		AllocatorChain c5 = new AllocatexLargeCPU();
		AllocatorChain c6 = new AllocateLargeCPU();

		c1.setNextChain(c2);
		c2.setNextChain(c3);
		c3.setNextChain(c4);
		c4.setNextChain(c5);
		c5.setNextChain(c6);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * to write 1. ordering the cpu's based on cost per cpu 2. reading the input
		 * property file 3. setting the chain 4. pritning the values 5. comparator class
		 * to sort the region param, write a method get_cost
		 */

		/* 1. to give handle to add locations */

		int hours = 10;
		int cpus = 50;
		int price = 50;

		get_costs(hours, cpus, price);

	}

	private static void get_costs(int hours, int cpus, int price) {

		CPUAllocatorMain cpuAllocatorMain = new CPUAllocatorMain();
		InputParam inputParam = new InputParam();

		int pricePerHour = price / hours;

		inputParam.setRequiredCost(pricePerHour);
		inputParam.setRequiredCount(cpus);

	}

}
