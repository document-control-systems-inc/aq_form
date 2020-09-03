package aquarius;

import java.io.File;

import javax.xml.bind.DatatypeConverter;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.Br;

public class ImageTest {

	public org.docx4j.wml.P newImageParagraph(WordprocessingMLPackage wordMLPackage,
			String imageBase64,
			String filenameHint, String altText, 
			int id1, int id2, long cx) throws Exception {
		
		String base64Image = imageBase64.split(",")[1];
		byte[] bytes = DatatypeConverter.parseBase64Binary(base64Image);
		
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
		
        Inline inline = imagePart.createImageInline( filenameHint, altText, 
    			id1, id2, cx, false);
        
        // Now add the inline in w:p/w:r/w:drawing
		org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
		org.docx4j.wml.P  p = factory.createP();
		org.docx4j.wml.R  run = factory.createR();		
		p.getContent().add(run);        
		org.docx4j.wml.Drawing drawing = factory.createDrawing();		
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
	
	public void addImage(String data, String wordPath, String addText) {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
			
	        String filenameHint = null;
	        int id1 = 0;
	        int id2 = 1;
	        
	        org.docx4j.wml.P p2 = newImageParagraph( wordMLPackage, data, 
	        		filenameHint, addText, 
	    			id1, id2, 2000 );
			wordMLPackage.getMainDocumentPart().addObject(p2);
			
			wordMLPackage.save(new java.io.File(wordPath));
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ImageTest image = new ImageTest();
		String imagen = "data:image/png;base64,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";
		
		image.addImage(imagen, "/opt/sampleImage.docx", "");
		
		
        
	}
	
}
