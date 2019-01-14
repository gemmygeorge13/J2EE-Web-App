<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html" 
	xmlns:t="http://myfaces.apache.org.tomahawk" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:f="http://java.sun.com/jsf/core"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:t="http://myfaces.apache.org/tomahawk">
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Regression</title>
    </head>
    <body>
        <f:view>
			<div class = "report">
            <div align="center">
			<h2>IDS517 f18:EAD Project</h2>
			<hr />
		</div>
              <h:form>
              <div align="center">
					<h:commandButton value = "Back to Menu"
						action = "mainMenu" styleClass = "button"/> &#160;&#160;
                    <h:commandButton value = "Logout"
                    	action = "#{mainMenu.processLogout}"
                    	styleClass = "button"/> 
              &#160;&#160;
                	<h:outputText value = "Database Schema: "/>
					<h:selectOneMenu id = "host" value = "#{mainMenu.schema}"
							onchange = "submit()"
							valueChangeListener = "#{mainMenu.schemaChangeEvent}"
							rendered = "#{mainMenu.renderSchema}" >
							<f:selectItems value = "#{mainMenu.schemaList}"/>
                    </h:selectOneMenu> <br /><br />
              </div>      <div align="center">
                    <h:panelGrid columns="6">
                        <h:commandButton value = "TABLES" 
                            action = "#{statisticsBean.getTables}" styleClass = "button"/>
                        <h:commandButton value = "COLUMNS"
                            action = "#{statisticsBean.getColumnNames}" 
                            styleClass = "button" disabled="#{statisticsBean.renderColumnListbutton}"/>
						<h:commandButton value = "SUMMARY STATISTICS"
                            action = "#{statisticsBean.generateReport}"
                            styleClass = "button" disabled="#{statisticsBean.renderReport}"/>
						<h:commandButton value = "PREDICTOR AND TARGET VARIABLES FOR REGRESSION" 
                            action = "#{statisticsBean.displayColumnsforRegression}" 
                            styleClass = "button"/>
						<h:commandButton value = "REGRESSION REPORT" 
                            action = "#{statisticsBean.generateRegressionReport}" 
                            styleClass = "button" 
                            disabled="#{statisticsBean.renderRegressionButton}"/>
					<h:commandButton value = "RESET" action = "#{statisticsBean.resetButton}" styleClass = "button"/>
				
					</h:panelGrid>
					
                    <pre>
						<h:outputText value = "#{statisticsBean.message}"
							rendered="#{statisticsBean.renderMessage}"
							style="color:blue" />
					</pre>
                    <h:panelGrid columns="4">
                        <h:selectOneListbox id = "selectOneCb" style="width:150px; height:100px"
                            value = "#{statisticsBean.tableSelected}"
                            rendered = "#{statisticsBean.renderTablename}" size = "5">
                            <f:selectItems value = "#{statisticsBean.tableList}" />
                        </h:selectOneListbox>
                        <h:selectManyListbox id="selectcolumns" style="width:150px; height:100px"
                            value="#{statisticsBean.columnSelected}"
                            rendered="#{statisticsBean.columnRender}" size="5">
                            <f:selectItems value="#{statisticsBean.columnsList}" />
                        </h:selectManyListbox>
						<h:selectOneListbox id = "predictor" value = "#{statisticsBean.predictorValue}"
							rendered = "#{statisticsBean.renderRegressionColumn}" size="5">
							<f:selectItem itemValue="0" itemLabel="Select Predictor Value" />
							<f:selectItems value = "#{statisticsBean.numericData}" />
						</h:selectOneListbox> 
						<h:selectOneListbox id = "response" value = "#{statisticsBean.responseValue}"
                      		rendered = "#{statisticsBean.renderRegressionColumn}" size="5">
                      		<f:selectItem itemValue="0" itemLabel="Select Response Value" />
                        	<f:selectItems value = "#{statisticsBean.numericData}"/>
                    	</h:selectOneListbox> 
					
					</h:panelGrid> 
					
					<div style="background-attachment: scroll; overflow:auto;
                    		background-repeat: repeat" align="center">
                        <t:dataTable value="#{statisticsBean.statisticList}" var="rowNumber"
							rendered="#{statisticsBean.renderTabledata}"
							border="1" cellspacing="0" cellpadding="1"
							headerClass = "headerWidth">
							<h:column><f:facet name="header"><h:outputText value ="Column Selected"/></f:facet>
								<h:outputText value="#{rowNumber.columnSelected}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Minimum Value"/></f:facet>
								<h:outputText value="#{rowNumber.minValue}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Maximum Value"/></f:facet>
								<h:outputText value="#{rowNumber.maxValue}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Mean"/></f:facet>
								<h:outputText value="#{rowNumber.mean}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Variance"/></f:facet>
								<h:outputText value="#{rowNumber.variance}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Standard Deviation"/></f:facet>
								<h:outputText value="#{rowNumber.std}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Q1"/></f:facet>
								<h:outputText value="#{rowNumber.q1}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Q3"/></f:facet>
								<h:outputText value="#{rowNumber.q3}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="Range"/></f:facet>
								<h:outputText value="#{rowNumber.range}"/></h:column>
							<h:column><f:facet name="header"><h:outputText value ="IQR"/></f:facet>
								<h:outputText value="#{rowNumber.iqr}"/></h:column>
						</t:dataTable>
				    </div><br />
				    	<h:outputText value="Final Regression Equation: "
				    		rendered="#{statisticsBean.renderRegressionResult}">
						</h:outputText> &#160;
						<h:outputText value="#{regressionAnalysisManagedBean.regressionEquation}"
							rendered="#{statisticsBean.renderRegressionResult}">
						</h:outputText> <br /><br />
						<h:outputText value="Regression Model" rendered="#{statisticsBean.renderRegressionResult}"></h:outputText>
						<h:panelGrid columns="5" rendered="#{statisticsBean.renderRegressionResult}" border="1" >
							<h:outputText value="Predictor"/>
							<h:outputText value="Co-efficient"/>
							<h:outputText value="Standard Error Co-efficient"/>
							<h:outputText value="T-Statistic"/>
							<h:outputText value="P-Value"/>
							<h:outputText value="Constant"/>
							<h:outputText value="#{regressionAnalysisManagedBean.intercept}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.interceptStandardError}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.tStatistic }"/>
							<h:outputText value="#{regressionAnalysisManagedBean.interceptPValue }"/>
							<h:outputText value="#{statisticsBean.predictorValue}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.slope}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.slopeStandardError}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.tStatisticPredictor }"/>
							<h:outputText value="#{regressionAnalysisManagedBean.pValuePredictor }"/>
						</h:panelGrid> <br/> <br/>
						<h:panelGrid columns="2" rendered="#{statisticsBean.renderRegressionResult}" border="1">
							<h:outputText value="Model Standard Error:"/>
							<h:outputText value="#{regressionAnalysisManagedBean.standardErrorModel}"/>
							<h:outputText value="R Square(Co-efficient of Determination)"/>
							<h:outputText value="#{regressionAnalysisManagedBean.rSquare}"/>
							<h:outputText value="Adjusted R Square(Co-efficient of Determination)"/>
							<h:outputText value="#{regressionAnalysisManagedBean.rSquareAdjusted}"/>
						</h:panelGrid> <br/> <br/>
						<h:outputText value="Analysis of Variance" rendered="#{statisticsBean.renderRegressionResult}"/> <br/>
						<h:panelGrid columns="6" rendered="#{statisticsBean.renderRegressionResult}" border="1" >
							<h:outputText value="Source"/>
							<h:outputText value="Degrees of Freedom(DF)"/>
							<h:outputText value="Sum of Squares"/>
							<h:outputText value="Mean of Squares"/>
							<h:outputText value="F-Statistic"/>
							<h:outputText value="P-Value"/>
							<h:outputText value="Regression"/>
							<h:outputText value="#{regressionAnalysisManagedBean.predictorDF}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.regressionSumSquares}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.meanSquare }"/>
							<h:outputText value="#{regressionAnalysisManagedBean.fValue }"/>
							<h:outputText value="#{regressionAnalysisManagedBean.pValue}"/>
							<h:outputText value="Residual Error"/>
							<h:outputText value="#{regressionAnalysisManagedBean.residualErrorDF}"/>
							<h:outputText value="#{regressionAnalysisManagedBean.sumSquaredErrors }"/>
							<h:outputText value="#{regressionAnalysisManagedBean.meanSquareError }"/>
							<h:outputText value=""/>
							<h:outputText value=""/>
							<h:outputText value="Total"/>
							<h:outputText value="#{regressionAnalysisManagedBean.totalDF}"/>
						</h:panelGrid>
					</div>
				</h:form>
           </div>
        </f:view>
    </body>
    </html>
</jsp:root>