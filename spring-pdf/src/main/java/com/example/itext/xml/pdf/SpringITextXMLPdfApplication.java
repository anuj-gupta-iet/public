package com.example.itext.xml.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

/*
 *  
 */

@SpringBootApplication
public class SpringITextXMLPdfApplication {

    @PostConstruct
    public void init() throws Exception {
	System.out.println("Start");
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(
		"E:\\Program Files\\EclipseWorkspace\\spring-pdf\\src\\main\\resources\\xmlexample\\itext\\itext_xsl.xsl")));
	transformer.transform(new StreamSource(new File(
		"E:\\Program Files\\EclipseWorkspace\\spring-pdf\\src\\main\\resources\\xmlexample\\itext\\itext_xml.xml")),
		new StreamResult(new FileOutputStream("E:\\tmp\\itext_output_html.html")));
	System.out.println("Html done");
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	PdfDocument pdfDocument = new PdfDocument(new PdfWriter(byteArrayOutputStream));
	pdfDocument.setDefaultPageSize(PageSize.A4);
	ConverterProperties props = new ConverterProperties();
	props.setCharset(StandardCharsets.UTF_8.name());
	HtmlConverter.convertToPdf(new File("E:\\tmp\\itext_output_html.html"),
		new File("E:\\tmp\\itext_output_pdf.pdf"));
	pdfDocument.close();
	System.out.println("Done");
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringITextXMLPdfApplication.class, args);
    }

}
