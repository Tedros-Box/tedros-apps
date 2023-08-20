/**
 * 
 */
package org.tedros.samples.module.order.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.chart.TAreaChart;
import org.tedros.fx.annotation.chart.TAxis;
import org.tedros.fx.annotation.chart.TBarChart;
import org.tedros.fx.annotation.chart.TBubbleChart;
import org.tedros.fx.annotation.chart.TLineChart;
import org.tedros.fx.annotation.chart.TPieChart;
import org.tedros.fx.annotation.chart.TStackedAreaChart;
import org.tedros.fx.annotation.chart.TStackedBarChart;
import org.tedros.fx.annotation.chart.TXYChart;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TAxisType;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.sample.ejb.controller.IOrderController;
import org.tedros.sample.entity.Order;
import org.tedros.samples.module.order.builder.OrderParamBuilder;

/**
 * @author Davis Gordon
 *
 */

@TPresenter(model=ChartModel.class,
		decorator = @TDecorator(type=TViewDecorator.class),
		behavior=@TBehavior(type=TViewBehavior.class))
public class ChartMV extends TModelView<ChartModel> {

	@TAreaChart(xyChart = @TXYChart(service=IOrderController.JNDI_NAME,
			paramsBuilder=OrderParamBuilder.class,
			xAxis = @TAxis(axisType = TAxisType.CATEGORY, label = TUsualKey.DATE), 
			yAxis = @TAxis(axisType = TAxisType.NUMBER, label = "Total")))
	@TGenericType(model=Order.class)
	@TFlowPane(hgap=20, vgap=20,
	pane=@TPane(children={"items","items1", "items2", 
			"items3", "items4", "items5"}))
	private ITObservableList<Order> items;
	

	@TBarChart(xyChart = @TXYChart(service=IOrderController.JNDI_NAME,
			paramsBuilder=OrderParamBuilder.class,
			xAxis = @TAxis(axisType = TAxisType.CATEGORY, label = TUsualKey.DATE), 
			yAxis = @TAxis(axisType = TAxisType.NUMBER, label = "Total")))
	@TGenericType(model=Order.class)
	private ITObservableList<Order> items1;
	

	@TPieChart(service=IOrderController.JNDI_NAME,
		paramsBuilder=OrderParamBuilder.class)
	@TGenericType(model=Order.class)
	private ITObservableList<Order> items2;
	
	@TStackedAreaChart(xyChart = @TXYChart(service=IOrderController.JNDI_NAME,
			paramsBuilder=OrderParamBuilder.class,
			xAxis = @TAxis(axisType = TAxisType.CATEGORY, label = TUsualKey.DATE), 
			yAxis = @TAxis(axisType = TAxisType.NUMBER, label = "Total")))
	@TGenericType(model=Order.class)
	private ITObservableList<Order> items3;

	@TStackedBarChart(xyChart = @TXYChart(service=IOrderController.JNDI_NAME,
			paramsBuilder=OrderParamBuilder.class,
			xAxis = @TAxis(axisType = TAxisType.CATEGORY, label = TUsualKey.DATE), 
			yAxis = @TAxis(axisType = TAxisType.NUMBER, label = "Total")))
	@TGenericType(model=Order.class)
	private ITObservableList<Order> items4;
	
	@TLineChart(xyChart = @TXYChart(service=IOrderController.JNDI_NAME,
			paramsBuilder=OrderParamBuilder.class,
			xAxis = @TAxis(axisType = TAxisType.CATEGORY, label = TUsualKey.DATE), 
			yAxis = @TAxis(axisType = TAxisType.NUMBER, label = "Total")))
	@TGenericType(model=Order.class)
	private ITObservableList<Order> items5;
	
	public ChartMV(ChartModel model) {
		super(model);
	}

}
