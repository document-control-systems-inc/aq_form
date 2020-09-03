package com.f2m.aquarius.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.f2m.aquarius.configuration.AppConf;
import com.f2m.aquarius.parameters.FormsParameters;
import com.f2m.aquarius.utils.AquariusException;
import com.f2m.aquarius.utils.OfficeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFService {
	
	private FormsParameters formsParameters;
	private OfficeUtils office = new OfficeUtils();
	
	public PDFService() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConf.class);
		ctx.refresh();
		formsParameters = ctx.getBean(FormsParameters.class);
		ctx.close();
	}

	public String createPDFForm(JsonNode template, JsonNode values, boolean landscape, String docName) throws AquariusException {
		String fileName = formsParameters.getFolderPath() + "/PDF_" + docName + ".pdf";
		try {
			System.out.println("Iniciando creación de forma");
			Document document = null;
			if (landscape) {
				document = new Document(PageSize.A4.rotate());
			} else {
				document = new Document();
			}
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			addMetaData(document, "Aquarius PDF Forms");
			for (JsonNode pageTemplate:template.findValue("form")) {
				addPage(writer, document, pageTemplate, values);
			}
			document.close();
			System.out.println("Finalizando creación de forma");
			return fileName;
		} catch (Exception e) {
			System.out.println("Error al generar el documento: " + e.getMessage());
			throw new AquariusException("Error al generar el archivo temporal PDF " + e.getMessage());
		}
	}
	
	public String createPDFFormFromWord(JsonNode template, JsonNode values, String docName) throws AquariusException {
		String fileName = formsParameters.getFolderPath() + "/PDF_" + docName + ".pdf";
		try {
			System.out.println("Iniciando creación de forma desde Word");
			String templateName = template.get("name").asText();
			String wordPath = template.get("wordTemplate").asText();
			
			HashMap<String, String> mappings = new HashMap<String, String> ();
			HashMap<String, String> mappingsImage = new HashMap<String, String> ();
			
			for (JsonNode pageTemplate:template.findValue("form")) {
				for(JsonNode params:pageTemplate.findValue("params")) {
					String paramName = params.findValue("name").asText();
					String value = findParam(values.findValue("values"), paramName);
					switch (params.findValue("type").asText()) {
						case "text":
							mappings.put(paramName, strValue(value));
							break;
						case "check":
							mappings.put(paramName, chkValue(value));
							break;
						case "image":
							String imageEncode = strValue(value);
							if (imageEncode.length() > 0) {
								String text = "Firma";
								if (paramName.toLowerCase().contains("cliente")) {
									text = "Firma Cliente";
								} else if (paramName.toLowerCase().contains("gerente")) {
									text = "Firma Prime";
								}
								mappingsImage.put(text, strValue(value));
							}
							break;
					}
				}
			}
			WordprocessingMLPackage doc = office.generateDocument(mappings, templateName, wordPath);
			doc = office.addImages(doc, mappingsImage);
			if (doc != null) {
				if (office.generatePDF(doc, fileName)) {
					return fileName;
				} else {
					throw new AquariusException("Error al generar el archivo PDF");
				}
			} else {
				throw new AquariusException("Error al generar el archivo Word");
			}
		} catch (Exception e) {
			System.out.println("Error al generar el documento: " + e.getMessage());
			throw new AquariusException("Error al generar el archivo temporal PDF " + e.getMessage());
		}
	}
	
	private String strValue(String value) {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}
	
	private String chkValue(String value) {
		if (value == null) {
			return " ";
		} else {
			return "X";
		}
	}
	
	private void addMetaData(Document document, String title) {
		document.addTitle(title);
		document.addSubject("");
		document.addKeywords("");
		document.addAuthor("Aquarius Magic Folder");
		document.addCreator("Aquarius Magic Folder");
	}
	
	private BaseFont getFont(String fontName) throws DocumentException, IOException {
		String font;
		switch (fontName) {
			case BaseFont.COURIER:
			case BaseFont.COURIER_BOLD:
			case BaseFont.HELVETICA:
			case BaseFont.HELVETICA_BOLD:
			case BaseFont.TIMES_BOLD:
			case BaseFont.TIMES_BOLDITALIC:
			case BaseFont.TIMES_ITALIC:
			case BaseFont.TIMES_ROMAN:
				font = fontName;
				break;
			default:
				font = BaseFont.TIMES_ROMAN;
		}
		return BaseFont.createFont(font, 
				BaseFont.CP1257, BaseFont.EMBEDDED);
		
	}
	
	private void addText(String value, JsonNode textParams, PdfWriter writer, Document document) throws DocumentException, IOException {
		PdfContentByte over = writer.getDirectContent();
		over.beginText();
		over.setFontAndSize(getFont(textParams.findValue("font").asText()), textParams.findValue("fontSize").asInt());
		over.setTextMatrix(textParams.findValue("xcoord").asInt(), document.getPageSize().getHeight() - textParams.findValue("ycoord").asInt());
		over.showText(value);
		over.endText();
	}
	
	private void addImage(String imagePath, JsonNode imageParams, Document document) throws DocumentException, IOException {
		if (imagePath == null || imagePath.length() == 0) {
			return;
		}
		Image img_sign = null;
		if (imagePath.startsWith("data:")) {
			String base64Image = imagePath.split(",")[1];
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
			img_sign = Image.getInstance(imageBytes);
		} else {
			img_sign = Image.getInstance(imagePath);
		}
		
		if (imageParams.findValue("scaleX").asInt() > 0) {
			img_sign.scaleToFit(imageParams.findValue("scaleX").asInt(), imageParams.findValue("scaleY").asInt());
		}
		img_sign.setAbsolutePosition(imageParams.findValue("xcoord").asInt(), document.getPageSize().getHeight() - imageParams.findValue("ycoord").asInt());
		document.add(img_sign);
	}
	
	private void addBackgroundImage(String imagePath, Document document) throws IOException, DocumentException {
		if (imagePath != null && imagePath.length() > 0) {
			System.out.println("x:" + document.getPageSize().getWidth() + ",y: " + document.getPageSize().getHeight());
			float documentWidth = document.getPageSize().getWidth();
			float documentHeight = document.getPageSize().getHeight();
			Image img_header = Image.getInstance(imagePath);
			img_header.scaleToFit(documentWidth, documentHeight);
			img_header.setAbsolutePosition(0, 0);
			document.add(img_header);
		}
	}
	
	private String findParam(JsonNode values, String paramName) {
		System.out.println("buscando parámetros: " + paramName);
		for (JsonNode value:values) {
			if (value.findValue("param").asText().equals(paramName)) {
				System.out.println("Se encontró el valor del parámetros: ");
				return value.findValue("value").asText();
			}
		}
		return null;
	}
	
	private void addFile(String source, PdfWriter writer, Document document) {
		try {
			PdfReader reader = new PdfReader(source);
			int n = reader.getNumberOfPages();
			PdfImportedPage page;
			for (int i = 1; i <= n; i++) {
                page = writer.getImportedPage(reader, i);
                float documentWidth = document.getPageSize().getWidth();
        		float documentHeight = document.getPageSize().getHeight();
                Image instance = Image.getInstance(page);
                instance.scaleToFit(documentWidth, documentHeight);
                instance.setAbsolutePosition(0, 0);
                document.add(instance);
                document.newPage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void addPage(PdfWriter writer, Document document, JsonNode pageTemplate, JsonNode values) throws DocumentException, IOException {
		document.newPage();
		addBackgroundImage(pageTemplate.findValue("image").asText(), document);
		for(JsonNode params:pageTemplate.findValue("params")) {
			String paramName = params.findValue("name").asText();
			String value = findParam(values.findValue("values"), paramName);
			if (value != null) {
				switch (params.findValue("type").asText()) {
					case "text":
						addText(value, params, writer, document);
						break;
					case "image":
						addImage(value, params, document);
						break;
					case "file":
						addFile(value, writer, document);
						break;
					case "check":
						addText("X", params, writer, document);
				}
			}
		}
	}
}
