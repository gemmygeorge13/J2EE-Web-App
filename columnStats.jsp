<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Analysis</title>
<style> 
<%//body{
//background-color: lightblue;
//}%>
body{
background: #EFEFEF;
}

.disabled {
    opacity: 0.1;
    cursor: not-allowed;
}
</style>

</head>
<body>
<f:view>
<h:form>
<div align="center">
<a href="execute.jsp">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="execute.jsp">Back</a><br><br>

<h:commandButton type="submit" value="Logout"
				action="#{actionBean.closeConnection}" /><br><br>
</div>
				
	<h1 align = "center">Data for <h:outputText value="#{actionBean.tableName}"></h:outputText></h1>
	
	<div align = "center"
		style="background-attachment: scroll; overflow: auto; height: 400px; background-repeat: repeat">
			<t:dataTable value="#{actionBean.tableData}" var="row"
				rendered="#{actionBean.renderTableData}" border="2" cellspacing="2"
				cellpadding="1" columnClasses="columnClass1 border"
				headerClass="headerClass" footerClass="footerClass"
				rowClasses="rowClass2" styleClass="dataTableEx" width="900">
				<t:columns binding="#{col}" var="col" value="#{actionBean.selectedColumnNames}">
				<f:facet name="header">
				<t:outputText styleClass="outputHeader" value="#{col}" />
				</f:facet>
					<t:outputText styleClass="outputText" value="#{row[actionBean.columnNumbers[col]]}" />
				</t:columns>
				</t:dataTable>
	</div>
	<div align="center"></br> </br>
	<h:commandButton type="submit" value="Export Column Data" rendered="#{actionBean.renderTableData}"
				action="#{actionBean.exportToExcel}" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<h:commandButton type="submit" value="Generate Stats" rendered="#{actionBean.renderTableData}"
				action="#{actionBean.generateStats}" styleClass="disabled" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<h:commandButton type="submit" value="Show  Columns for Scatterplot" 
	action="#{actionBeanGraph.showScatterplotChart}" styleClass="disabled"  /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<h:outputLabel value="X-Axis:" rendered="#{actionBeanGraph.regRendered}"/>
				<h:selectOneListbox style="vertical-align:top" size="8"
					value="#{actionBeanGraph.predictor}"
					rendered="#{actionBeanGraph.regRendered}">
					<f:selectItems value="#{actionBean.columnNames}" />
				</h:selectOneListbox>
				<h:outputLabel value="Y-Axis:" rendered="#{actionBean.regRendered}" />
				<h:selectOneListbox style="vertical-align:top" size="8"
					value="#{actionBeanGraph.response}"
					rendered="#{actionBeanGraph.regRendered}">
					<f:selectItems value="#{actionBean.columnNames}" />
				</h:selectOneListbox>
	
		<h:commandButton type="submit" value="Plot Scatterplot" 
	action="#{actionBeanGraph.createScatterplotChart}" rendered="#{actionBeanGraph.regRendered}" styleClass="disabled"  /> <br><br>
	
		<h:commandButton value="Show Regression Columns" 
	action="#{actionBean.showRegressionTables}"  styleClass="disabled"   />			<br><br>
				
	
	<h:outputLabel value="Predictor:" rendered="#{actionBean.regRendered}"/>
				<h:selectOneListbox style="vertical-align:top" size="8"
					value="#{actionBean.predictor}"
					rendered="#{actionBean.regRendered}">
					<f:selectItems value="#{actionBean.columnNames}" />
				</h:selectOneListbox>
				<h:outputLabel value="Response:" rendered="#{actionBean.regRendered}" />
				<h:selectOneListbox style="vertical-align:top" size="8"
					value="#{actionBean.response}"
					rendered="#{actionBean.regRendered}">
					<f:selectItems value="#{actionBean.columnNames}" />
				</h:selectOneListbox>
	
		<h:commandButton value="Generate Regression Report" 
	action="#{actionBean.generateRegressionReport}"  rendered="#{actionBean.regRendered}" styleClass="disabled"  /><br><br>	
				
	<h:commandButton type="submit" value="Export Stats"
				action="#{actionBean.exportStats}" styleClass = "disabled"/><br>
	</div>	
<h:dataTable
var="stats"
value="#{actionBean.statsList}"
rendered="#{actionBean.render}"
border="1" cellspacing="0" cellpadding="1"
columnClasses="columnClass1 border"
headerClass="headerClass"
footerClass="footerClass"
rowClasses="rowClass2"
styleClass="dataTableEx"
width="200">
<h:column>    				
      <f:facet name = "header">Column Name</f:facet>    				
      <h:outputText value="#{stats.colName}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Count</f:facet>    				
      <h:outputText value="#{stats.count}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Min</f:facet>    				
      <h:outputText value="#{stats.min}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Max</f:facet>    				
      <h:outputText value="#{stats.max}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Mean</f:facet>    				
      <h:outputText value="#{stats.mean}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Median</f:facet>    				
      <h:outputText value="#{stats.median}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Variance</f:facet>    				
      <h:outputText value="#{stats.variance}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Stddev</f:facet>    				
      <h:outputText value="#{stats.stdDev}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Q1</f:facet>    				
      <h:outputText value="#{stats.q1}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">Q3</f:facet>    				
      <h:outputText value="#{stats.q3}"/>
</h:column>
<h:column>    				
      <f:facet name = "header">IQR</f:facet>    				
      <h:outputText value="#{stats.iqr}"/>
</h:column>
</h:dataTable>
					<div class="bottom">
					<br>
					<br>
						<h:outputText value="Regression Statement: "
							rendered="#{actionBean.renderRegressionResult}">
						</h:outputText>
						&#160;
						<h:outputText
							value="#{actionBean.regEquation}"
							rendered="#{actionBean.renderRegressionResult}">
						</h:outputText>
						<br /> <br />
						<h:outputText value="Regression Model"
							rendered="#{actionBean.renderRegressionResult}"></h:outputText>
						<h:panelGrid columns="9"
							rendered="#{actionBean.renderRegressionResult}" border="1">
							<h:outputText value="Co-efficient" />
							<h:outputText value="Standard Error Co-efficient" />
							<h:outputText value="T-Statistic" />
							<h:outputText value="P-Value" />
							<h:outputText value="Slope" />
							<h:outputText value="Slope Standard Error" />
							<h:outputText value="T-Statistics Predicted Value" />
							<h:outputText value="Predicted P-Value" />
							<h:outputText value="VIF" />
							<h:outputText value="#{actionBean.intercept}" />
							<h:outputText
								value="#{actionBean.interceptStdErr}" />
							<h:outputText
								value="#{actionBean.tStatistic }" />
							<h:outputText
								value="#{actionBean.interceptPValue }" />
							<h:outputText value="#{actionBean.slope}" />
							<h:outputText
								value="#{actionBean.slopeStdErr}" />
							<h:outputText
								value="#{actionBean.tStatPred }" />
							<h:outputText
								value="#{actionBean.pValuePred }" />
								<h:outputText
								value="#{actionBean.vif }" />
						</h:panelGrid>
						
						<br /> <br />
						<h:outputText value="Analysis of Variance"
							rendered="#{actionBean.renderRegressionResult}" />
						<br />
						<h:panelGrid columns="6"
							rendered="#{actionBean.renderRegressionResult}" border="1">
							<h:outputText value="Source" />
							<h:outputText value="Degrees of Freedom" />
							<h:outputText value="Sum of Squares" />
							<h:outputText value="Mean of Squares" />
							<h:outputText value="F-Statistic" />
							<h:outputText value="P-Value" />
							<h:outputText value="Regression" />
							<h:outputText
								value="#{actionBean.predictorDegreesFreedom}" />
							<h:outputText
								value="#{actionBean.regSumSquares}" />
							<h:outputText
								value="#{actionBean.meanSquare }" />
							<h:outputText value="#{actionBean.fValue }" />
							<h:outputText value="#{actionBean.pValue}" />
							<h:outputText value="Residual Error" />
							<h:outputText
								value="#{actionBean.residualErrorDegreesFreedom}" />
							<h:outputText
								value="#{actionBean.sumSquaredErr }" />
							<h:outputText
								value="#{actionBean.meanSquareErr }" />
							<h:outputText value="" />
							<h:outputText value="" />
							<h:outputText value="Total" />
							<h:outputText value="#{actionBean.totalDegreesFreedom}" />
							<h:outputText value="#{actionBean.totalSumSquares}" />
						</h:panelGrid>
						<br /> <br />
						<h:outputText value="Model Summary"
							rendered="#{actionBean.renderRegressionResult}" />
						
						<h:panelGrid columns="2"
							rendered="#{actionBean.renderRegressionResult}" border="1">
							<h:outputText value="Standard Error of the model" />
							<h:outputText
								value="#{actionBean.stdErrorM}" />
							<h:outputText value="R Square" />
							<h:outputText value="#{actionBean.rSquare} %" /> 
							<h:outputText
								value="R Square Adjusted" />
							<h:outputText
								value="#{actionBean.rSquareAdj} %" />
						</h:panelGrid>
					</div>
					
		
			
			
				
</h:form>
</f:view>
</body>
</html>