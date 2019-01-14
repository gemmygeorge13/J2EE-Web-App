package edu.uic.ids517.model;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.sql.Result;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@ManagedBean(name = "actionBeanGraph", eager = true)
@SessionScoped
public class ActionBeanGraph {
	
	@ManagedProperty("#{actionBean}")
	private ActionBean actionBean;
	private DbAccessBean dbAccessBean;
	private boolean chartDualColumnsNeeded;
	private int chartType = 0;
	private boolean chartSingleColumnNeeded;
	private DefaultPieDataset pieModel;
	private DefaultCategoryDataset barModel;
	private String predictor;
	private String response;
	
	
	
	public DefaultCategoryDataset getBarModel() {
		return barModel;
	}

	public void setBarModel(DefaultCategoryDataset barModel) {
		this.barModel = barModel;
	}

	public DbAccessBean getDbAccessBean() {
		return dbAccessBean;
	}

	public void setDbAccessBean(DbAccessBean dbAccessBean) {
		this.dbAccessBean = dbAccessBean;
	}

	public DefaultPieDataset getPieModel() {
		return pieModel;
	}

	public void setPieModel(DefaultPieDataset pieModel) {
		this.pieModel = pieModel;
	}

	public boolean isChartSingleColumnNeeded() {
		return chartSingleColumnNeeded;
	}

	public void setChartSingleColumnNeeded(boolean chartSingleColumnNeeded) {
		this.chartSingleColumnNeeded = chartSingleColumnNeeded;
	}

	public int getChartType() {
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public boolean isChartDualColumnsNeeded() {
		return chartDualColumnsNeeded;
	}

	public void setChartDualColumnsNeeded(boolean chartDualColumnsNeeded) {
		this.chartDualColumnsNeeded = chartDualColumnsNeeded;
	}

	public ActionBeanGraph() {

	}

	public ActionBean getActionBean() {
		return actionBean;
	}

	public void setActionBean(ActionBean actionBean) {
		this.actionBean = actionBean;
	}
	 private boolean regRendered=false;
	 public void setRegRendered(boolean regRendered) {
			this.regRendered = regRendered;
		}

		public boolean isRegRendered() {
		return regRendered;
		}
		public boolean regRendered() {
			return regRendered;
			}
	public String showScatterplotChart() {
		setRegRendered(true);

		return "Success";
	}
	
	public void getColumns(ValueChangeEvent e) {
		
		chartType = Integer.parseInt(e.getNewValue().toString());
		
		if(chartType == 1){
			System.out.println("Chart Type:"+chartType);

			scatterPlotSelected = true;
			histogramSelected=false;
		}
		else if(chartType == 2) {
			System.out.println("Chart Type:"+chartType);

			scatterPlotSelected = false;
			histogramSelected=true;
		}
		else {
			System.out.println("Chart Type:"+chartType);

			scatterPlotSelected = false;
			histogramSelected=false;
		}
		
	}
	
	public boolean goBack() throws IOException {
		this.renderFalseDuplicate();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/faces/execute.jsp");
		return true;
	}
	
	
	public JFreeChart createplotChart() {
		
		JFreeChart chart = null;
		try {
			System.out.println("Inside actionBeanGraph function");
			Double x, y;
			
			actionBean.getTableDataForScatterPlot();
			ArrayList<ArrayList<String>> list = actionBean.getTableData();
			System.out.println("table data in graph: "+ list); 
			XYSeries series = new XYSeries("First");
			
			System.out.println("predictor in graph bean "+actionBean.getPredictor());
			System.out.println(actionBean.getColumnNames().get(0));
			
			System.out.println(actionBean.getColumnNames().get(0).equals(actionBean.getPredictor()));

			for (int row = 0; row < list.size(); row++) {
				if (list.get(row).get(0) != null && !"".equals(list.get(row).get(0)) && list.get(row).get(1) != null
						&& !"".equals(list.get(row).get(1))) {
					
					if(actionBean.getSelectedColumnNames().get(0).equals(actionBean.getPredictor())) {
					y = Double.parseDouble(list.get(row).get(0));
					x = Double.parseDouble(list.get(row).get(1));

					}
					else {
						y = Double.parseDouble(list.get(row).get(0));
						x = Double.parseDouble(list.get(row).get(1));

					}
					series.add(x, y);
				}
			}
			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			chart = ChartFactory.createScatterPlot("Scatter Plot", // chart title
					actionBean.getResponse(), // x axis label
					actionBean.getPredictor(), // y axis label
					dataset, // data
					PlotOrientation.VERTICAL, true, // include legend
					true, // tooltips
					false // urls
			);
			System.out.println("Created the chart");
			return chart;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Exception occured while generating scatterplot");
			return chart;
		}

	}
	
	

	JFreeChart xyChart;
	JFreeChart pieChart;
	JFreeChart barPlot;
	JFreeChart histogramChart;
	private boolean renderChart = false;

	private boolean renderScatterPlot = false;
	private boolean renderHistogramPlot=false;
	

	private String xyPath;
	private String ipAddress;

	private boolean scatterPlotSelected;
	private boolean histogramSelected;
	
	
	public boolean isRenderHistogramPlot() {
		return renderHistogramPlot;
	}

	public void setRenderHistogramPlot(boolean renderHistogramPlot) {
		this.renderHistogramPlot = renderHistogramPlot;
	}	


	public boolean isHistogramSelected() {
		return histogramSelected;
	}

	public void setHistogramSelected(boolean histogramSelected) {
		this.histogramSelected = histogramSelected;
	}


	public boolean isRenderScatterPlot() {
		return renderScatterPlot;
	}

	public void setRenderScatterPlot(boolean renderScatterPlot) {
		this.renderScatterPlot = renderScatterPlot;
	}


	public boolean isScatterPlotSelected() {
		return scatterPlotSelected;
	}

	public void setScatterPlotSelected(boolean scatterPlotSelected) {
		this.scatterPlotSelected = scatterPlotSelected;
	}

	public String getXyPath() {
		return xyPath;
	}

	public void setXyPath(String xyPath) {
		this.xyPath = xyPath;
	}
	
	@PostConstruct
	public void init(){
		FacesContext context =FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

	}
	
	public void renderFalseDuplicate() {
		renderScatterPlot = false;
		renderHistogramPlot = false;
		//renderPieChart = false;
		//renderBarPlot = false;
	}
	
	public boolean generateChart() {
		
	    if(scatterPlotSelected == true && histogramSelected==false){
			boolean created = createScatterplotChart();
			 if(created) {
				 return true;
			 }
			 else {
				 System.out.println("Error in scatter plot");
				 return false;
			 }
		}
		else if(scatterPlotSelected == false && histogramSelected==true){
			boolean created = createHistogramDiagram();
			 if(created) {
				 return true;
			 }
			 else {
				 System.out.println("Error in histogram plot");
				 return false;
			 }
		}
		else {
			System.out.println("Error in chart logic");
			return false;
		}
		
	}

	public boolean createScatterplotChart(){

		System.out.println("Inside function graph bean scatterplot");
		xyChart = createplotChart();
		System.out.println("Got the chart");
		FacesContext context = FacesContext.getCurrentInstance();
		String path = context.getExternalContext().getRealPath("/temp"+actionBean.getSession());
		System.out.println("Path:" + path);
		 File dir = new File(path);
		  if(!dir.exists())
		  {
			  new File(path).mkdirs();
		  }
		if (xyChart!=null){
			File file1 = new File(path+"/ScatterPlot.png");
			xyPath = "/temp"+actionBean.getSession()+"/ScatterPlot.png";
			System.out.println("xyPath:" + xyPath);
			try {
				ChartUtilities.saveChartAsPNG(file1, xyChart, 600, 450);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: "+ e.getMessage());
				e.printStackTrace();
			}
			renderScatterPlot = true;
			renderHistogramPlot=false;
			return true;
		}
		else {
			System.out.println("Null chart");
		}
		
		return false;
	}


public JFreeChart createHistogramChart() {
	
	JFreeChart chart = null;
	try {
		System.out.print(actionBean.getHist());
		System.out.println("Inside actionBeanGraph function");
		
		actionBean.getTableDataForHistogram();
		ArrayList<ArrayList<String>> list = actionBean.getTableData();
		System.out.println("table data in graph1: "+ list);
		for(int i=0; i<list.size(); i++)
		{
			ArrayList<String> tempArrayList = new ArrayList();
			tempArrayList = list.get(i);
			if(tempArrayList.size()==0)
				list.remove(i);	
			if((tempArrayList.get(0)).isEmpty())
				list.remove(i);	
		}
		
		System.out.println("table data in graph2: "+ list);
		HistogramDataset dataset=new HistogramDataset();
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		System.out.println("List 1: "+list.get(0).get(0));
		System.out.println("List size"+list.size());
		double[] h = new double[list.size()];
		for(int i = 0; i < list.size(); i++) {
			try {
				h[i]= Double.parseDouble(list.get(i).get(0));
				System.out.println(h[i]);
			}
			catch(Exception e)
			{
				h[i]=0.0;
			}
		}
		System.out.println(h);
		dataset.addSeries("H1", h, 40);
		System.out.println("After setvalue");
		chart = ChartFactory.createHistogram("Histogram",actionBean.getHist(), "frequency", dataset,PlotOrientation.VERTICAL, false, false, false);
		System.out.println("Created the chart");
		
		return chart;
	} catch (Exception e) {
		// TODO: handle exception
		//System.out.print(e.printStackTrace());
		e.printStackTrace();
		System.out.println("Exception occured while generating histogram chart");
		return chart;
	}

}

public boolean createHistogramDiagram(){
	System.out.print(actionBean.getHist());
	
	System.out.println("Inside function graph bean Histogram");
	histogramChart = createHistogramChart();
	System.out.println("Got the chart");
	FacesContext context = FacesContext.getCurrentInstance();
	String path = context.getExternalContext().getRealPath("/temp"+actionBean.getSession());
	System.out.println("Path:" + path);
	 File dir = new File(path);
	  if(!dir.exists())
	  {
		  new File(path).mkdirs();
	  }
	if (histogramChart!=null){
		File file1 = new File(path+"/histogram.png");
		xyPath = "/temp"+actionBean.getSession()+"/histogram.png";
		System.out.println("xyPath:" + xyPath);
		try {
			ChartUtilities.saveChartAsPNG(file1, histogramChart, 600, 450);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+ e.getMessage());
			e.printStackTrace();
		}
		renderScatterPlot = false;
		renderHistogramPlot=true;
		return true;
	}
	else {
		System.out.println("Null chart");
	}
	
	return false;
}


	
	
	public JFreeChart getHistogramChart() {
	return histogramChart;
}

public void setHistogramChart(JFreeChart histogramChart) {
	this.histogramChart = histogramChart;
}

	public JFreeChart getXyChart() {
		return xyChart;
	}

	public void setXyChart(JFreeChart xyChart) {
		this.xyChart = xyChart;
	}

	public JFreeChart getPieChart() {
		return pieChart;
	}

	public void setPieChart(JFreeChart pieChart) {
		this.pieChart = pieChart;
	}

	public JFreeChart getBarPlot() {
		return barPlot;
	}

	public void setBarPlot(JFreeChart barPlot) {
		this.barPlot = barPlot;
	}

	public boolean isRenderChart() {
		return renderChart;
	}

	public void setRenderChart(boolean renderChart) {
		this.renderChart = renderChart;
	}

	public String getPredictor() {
		return predictor;
	}

	public void setPredictor(String predictor) {
		this.predictor = predictor;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}


}

