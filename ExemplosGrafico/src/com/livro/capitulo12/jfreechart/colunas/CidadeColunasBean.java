package com.livro.capitulo12.jfreechart.colunas;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "cidadeColunasBean")
@RequestScoped
public class CidadeColunasBean {
	
	private StreamedContent grafico;
	private static final Logger log = Logger.getLogger(CidadeColunasBean.class.getName());
	
	public CidadeColunasBean() {
		try {
			JFreeChart graficoColunas = ChartFactory.createBarChart("5 cidades mais populosas de SC", "Cidades", "Popula��o", geraDados(), PlotOrientation.VERTICAL, false, true, false);
			File arquivoGrafico = new File("colunas.png");
			ChartUtilities.saveChartAsJPEG(arquivoGrafico, graficoColunas, 500, 325);
			grafico = new DefaultStreamedContent(new FileInputStream(arquivoGrafico), "image/png");
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
	}
	
	private DefaultCategoryDataset geraDados() {
		DefaultCategoryDataset dts = new DefaultCategoryDataset();
		dts.setValue(299416.0, "Popula��o", "Blumwenau");
		dts.setValue(174187.0, "Popula��o", "Chapec�");
		dts.setValue(188557.0, "Popula��o", "Crici�ma");
		dts.setValue(408161.0, "Popula��o", "Florian�polis");
		dts.setValue(497331.0, "Popula��o", "Joinville");
		return dts;
	}

	public StreamedContent getGrafico() {
		return grafico;
	}

	public void setGrafico(StreamedContent grafico) {
		this.grafico = grafico;
	}
}
