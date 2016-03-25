package com.ff.admin.dashboard.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * 
 * @author prmeher
 */
public class SampleChart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SampleChart() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// This will create the dataset
		PieDataset dataset = createDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, "Revenue Details");

		if (chart != null) {
			int width = 900;
			int height = 450;
			final ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			response.setContentType("image/png");
			OutputStream out = response.getOutputStream();
			ChartUtilities.writeChartAsPNG(out, chart, width, height, info);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	/**
	 * @return
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Credit Booking", 30);
		result.setValue("BA Booking", 20);
		result.setValue("ACC Booking", 10);
		result.setValue("Cash Booking", 22);
		result.setValue("FOC Booking", 13);
		result.setValue("EB Booking", 5);
		return result;

	}

	/**
	 * @param dataset
	 * @param title
	 * @return
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
                "{0} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
        plot.setLabelGenerator(generator);
		return chart;

	}

}
