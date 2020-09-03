package com.f2m.aquarius.utils;

import java.io.File;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.model.fields.FieldUpdater;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Br;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;

import com.sun.media.jfxmedia.logging.Logger;

public class OfficeUtils {
	
	private HashMap<String, WordprocessingMLPackage> templates = new HashMap<String, WordprocessingMLPackage>();
	private Mapper fontMapper = new IdentityPlusMapper();
	
	public OfficeUtils() {
		try {
			System.out.println("Subiendo las fuentes");
			PhysicalFonts.addPhysicalFont(new URL("file:/opt/ecm/fonts/verdana.ttf"));
			PhysicalFonts.addPhysicalFont(new URL("file:/opt/ecm/fonts/verdanab.ttf"));
			PhysicalFonts.addPhysicalFont(new URL("file:/opt/ecm/fonts/lhandw.ttf"));
			
			String regex = null;
			regex=".*(lucida handwriting italic|calibri|camb|cour|arial|times|comic|georgia|impact|LSANS|pala|tahoma|trebuc|verdana|symbol|webdings|wingding).*";
			PhysicalFonts.setRegex(regex);
			PhysicalFont verdana = PhysicalFonts.get("Verdana");
			if (verdana != null) {
				fontMapper.put("Verdana", verdana);
			}
			PhysicalFont verdanab = PhysicalFonts.get("Verdana Bold");
			if (verdanab != null) {
				fontMapper.put("Verdana Bold", verdanab);
			}
			PhysicalFont LucidaHandwriting = PhysicalFonts.get("Lucida Handwriting Italic");
			if (LucidaHandwriting != null) {
				fontMapper.put("Lucida Handwriting", LucidaHandwriting);
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private WordprocessingMLPackage readWord(String idPlantilla, String inputFile) throws Exception {
		try {
			System.out.println("Leyendo la plantilla: " + inputFile);
			return WordprocessingMLPackage.load(new File(inputFile));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private WordprocessingMLPackage replaceParamsOffice(HashMap<String, String> mappings,
			WordprocessingMLPackage wordMLPackage) throws Exception {
		try {
			if (mappings != null) {
				org.docx4j.model.datastorage.migration.VariablePrepare.prepare(wordMLPackage);
				MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
				documentPart.variableReplace(mappings);
			}
			return wordMLPackage;
		} catch (Exception e) {
			System.out.println(wordMLPackage.getTitle());
			e.printStackTrace();
			return null;
		}
	}

	public boolean generatePDF(WordprocessingMLPackage wordMLPackage, String outputFile) throws Exception {
		try {
			System.out.println("Comenzando a generar el PDF: " + outputFile);
			System.out.println("Home: " + System.getProperty("user.home"));
			FieldUpdater updater = new FieldUpdater(wordMLPackage);
			updater.update(true);
			OutputStream os = new java.io.FileOutputStream(outputFile);
			wordMLPackage.setFontMapper(fontMapper);
			FOSettings foSettings = Docx4J.createFOSettings();
			foSettings.setWmlPackage(wordMLPackage);
			Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_NONXSL);
			os.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error al generar el archivo: " + outputFile);
			e.printStackTrace();
			return false;
		}
	}
	
	public WordprocessingMLPackage generateDocument(HashMap<String, String> mappings, String idPlantilla, String input) throws Exception {
		WordprocessingMLPackage word = null; 
		System.out.println("Generando el documento de la plantilla: " + idPlantilla);
		if (!templates.containsKey(idPlantilla)) {
			word = readWord(idPlantilla, input);
			templates.put(idPlantilla, word);
		} else {
			word = templates.get(idPlantilla);
		}
		
		WordprocessingMLPackage word1 = (WordprocessingMLPackage) word.clone();
		word1 = replaceParamsOffice(mappings, word1);
		return word1;
	}
	
	public WordprocessingMLPackage addImages(WordprocessingMLPackage word1, HashMap<String, String> mappings) {
		String filenameHint = null;
        int id1 = 0;
        int id2 = 1;
        
		for (String texto:mappings.keySet()) {
			String data = mappings.get(texto);
			try {
				P newImage = newImageParagraph(word1, data, filenameHint, texto, id1, id2, 2000);
				word1.getMainDocumentPart().addObject(newImage);
			} catch (Exception e) {
				System.out.println("Error al agregar la imagen");
				e.printStackTrace();
			}
		}
		
		
		return word1;
	}
	
	public P newImageParagraph(WordprocessingMLPackage wordMLPackage,
			String imageBase64,
			String filenameHint, String altText, 
			int id1, int id2, long cx) throws Exception {
		
		String base64Image = imageBase64.split(",")[1];
		byte[] bytes = DatatypeConverter.parseBase64Binary(base64Image);
		
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
		
        Inline inline = imagePart.createImageInline( filenameHint, altText, 
    			id1, id2, cx, false);
        
        // Now add the inline in w:p/w:r/w:drawing
		ObjectFactory factory = Context.getWmlObjectFactory();
		P  p = factory.createP();
		R  run = factory.createR();		
		p.getContent().add(run);        
		Drawing drawing = factory.createDrawing();		
		run.getContent().add(drawing);
		drawing.getAnchorOrInline().add(inline);
		if (altText != null && altText.length() > 0) {
			Br br = factory.createBr();
			run.getContent().add(br);
			org.docx4j.wml.Text text = new org.docx4j.wml.Text();
			text.setValue(altText);
			run.getContent().add(text);
		}
		return p;
	}
	
}
