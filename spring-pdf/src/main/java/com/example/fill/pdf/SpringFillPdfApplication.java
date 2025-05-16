package com.example.fill.pdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDComboBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

/*
 * We can take a Fillable PDF (template PDF) from BAs and fill those values programmatically 
 * instead of creating a new PDF every time from scratch
 * I have downloaded a sample fillable pdf in resources/pdfs folder and after running this program
 * the filled pdf is in filled-pdf.pdf 
 */

@SpringBootApplication
public class SpringFillPdfApplication {

    @PostConstruct
    public void init() throws Exception {
	System.out.println("Start");
	String filePath = "pdfs/Sample-Fillable-PDF.pdf";
	ClassPathResource resource = new ClassPathResource(filePath);
	try (InputStream inputStream = resource.getInputStream()) {
	    byte[] bytes = IOUtils.toByteArray(inputStream);
	    // String fileContents = IOUtils.toString(inputStream);
	    // String fileContents = FileCopyUtils.copyToString(reader);

	    try (PDDocument pdfDocument = PDDocument.load(bytes)) {
		PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
		System.out.println("Available Fillable Fields in PDF ============");
		List<PDCheckBox> pdCheckBoxs = new ArrayList<PDCheckBox>();
		acroForm.getFields().forEach(field -> {
		    if (field instanceof PDCheckBox) {
			PDCheckBox pdCheckBox = (PDCheckBox) field;
			pdCheckBoxs.add(pdCheckBox);
			System.out.println(pdCheckBox);
		    } else if (field instanceof PDTextField) {
			PDTextField pdTextField = (PDTextField) field;
			System.out.println(pdTextField);
		    } else if (field instanceof PDComboBox) {
			PDComboBox pdComboBox = (PDComboBox) field;
			System.out.println(pdComboBox);
		    } else {
			System.out.println(field);
		    }
		});
		System.out.println("==========================");

		setFieldValue(acroForm, "Name", "Anuj Gupta");
		setFieldValue(acroForm, "Name of Dependent", "Deepali Gupta");
		setFieldValue(acroForm, "Dropdown2", "Choice 2");
		setCheckBoxsOn(pdCheckBoxs, Arrays.asList("Option 1", "Option 2"));
		makeFieldsReadOnly(acroForm);

		pdfDocument.save(new File("D:\\itext_example\\filled-pdf.pdf"));
	    }
	}
	System.out.println("Done");
    }

    private void setCheckBoxsOn(List<PDCheckBox> pdCheckBoxs, List<String> values) {
	pdCheckBoxs.stream().filter(e -> values.contains(e.getPartialName())).forEach(e -> {
	    try {
		e.check();
	    } catch (IOException e1) {
		throw new RuntimeException(e1);
	    }
	});
    }

    private void makeFieldsReadOnly(PDAcroForm acroForm) {
	acroForm.getFields().forEach(field -> {
	    field.setReadOnly(true);
	});
    }

    private void setFieldValue(PDAcroForm acroForm, String fieldName, String fieldValue) throws IOException {
	PDField pdField = acroForm.getField(fieldName);
	if (pdField != null) {
	    pdField.setValue(fieldValue);
	}
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringFillPdfApplication.class, args);
    }

}
