package com.jeanroldao.horariosval;

import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class HorariosVal {

	public static void main(String[] args) throws IOException {
		String horarios_pdf = "http://www.viacaoalvorada.com.br/html/modules/uploader/index.php?action=downloadfile&filename=horariosv.pdf";
		
		PDDocument doc = PDDocument.load(new URL(horarios_pdf));
		
		PDFTextStripper pdf = new PDFTextStripper();
		String data = pdf.getText(doc);
		
		System.out.println(data);
	}

}
