package edu.uic.ids517.model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="statsBean", eager=true)
@SessionScoped
public class StatsBean {
	private int count;
	private double mean;
	private double median;
	private double variance;
	private double stdDev;
	private double q1;
	private double q3;
	private double iqr;
	private double min;
	private double max;
	private String colName;
	
	
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

	public double getStdDev() {
		return stdDev;
	}

	public void setStdDev(double stdDev) {
		this.stdDev = stdDev;
	}

	public double getQ1() {
		return q1;
	}

	public void setQ1(double q1) {
		this.q1 = q1;
	}

	public double getQ3() {
		return q3;
	}

	public void setQ3(double q3) {
		this.q3 = q3;
	}

	public double getIqr() {
		return iqr;
	}

	public void setIqr(double iqr) {
		this.iqr = iqr;
	}

	public double getMin() {
		return min;
	}


	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public StatsBean(){
		
	}
	
	
}
