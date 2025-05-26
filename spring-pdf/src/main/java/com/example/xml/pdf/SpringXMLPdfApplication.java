package com.example.xml.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 *  
 */

@SpringBootApplication
public class SpringXMLPdfApplication {

    @PostConstruct
    public void init() throws Exception {
	System.out.println("Start");
	File xsltFile = new File(
		"E:\\Program Files\\EclipseWorkspace\\spring-pdf\\src\\main\\resources\\xmlexample\\example.xsl");
	File xmlFile = new File(
		"E:\\Program Files\\EclipseWorkspace\\spring-pdf\\src\\main\\resources\\xmlexample\\example.xml");
	File pdfFile = new File("F:\\tmp\\output.pdf");

	FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
	FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
	OutputStream out = new FileOutputStream(pdfFile);

	try {
	    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

	    javax.xml.transform.Source source = new StreamSource(xmlFile);
	    SAXResult res = new SAXResult(fop.getDefaultHandler());
	    transformer.transform(source, res);

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    out.close();
	}
	System.out.println("Done");
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringXMLPdfApplication.class, args);
    }

}
