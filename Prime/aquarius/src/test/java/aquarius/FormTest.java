package aquarius;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.f2m.aquarius.service.PDFService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class FormTest {

	public static void createDocument() {
		try {
			String fileName = "test.pdf";
			Document document = null;
			//document = new Document(PageSize.A4.rotate());
			document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			document.addTitle("Aquarius PDF Forms");
			document.addSubject("");
			document.addKeywords("");
			document.addAuthor("Aquarius Magic Folder");
			document.addCreator("Aquarius Magic Folder");
			
			document.newPage();
			System.out.println("x:" + document.getPageSize().getWidth() + ",y: " + document.getPageSize().getHeight());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testHomeLand() {
		System.out.println("Iniciando proceso...");
		try {
			PDFService pdfService = new PDFService();
			ObjectMapper mapper = new ObjectMapper();
			String txtTemplate = "{\"form\": [{\"image\": \"/IBM/WebSphere/AppServer/ADN/files/formatoDPS.jpg\",\"params\": ["
					
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"numPeticion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 233,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"account\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 256,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"tipoOperacion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 279,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"subvencion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 301,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"numContrato\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 324,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"motivoPeticion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 346,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"concepto\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 368,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"itemSolicitado\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 390,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"justificacion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 411,\"fontSize\": 10},"

					
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"cuantiaSolicitada\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 256,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"cuantiaAprobada\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 279,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"cuantiaAjustada\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 301,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"peticionario\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 324,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"puesto\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 346,\"fontSize\": 10},"
					
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_solicitud_adelanto_fondos\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 495,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_solicitud_adelanto_fondos\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 495,\"fontSize\": 10},"
					+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_solicitud_adelanto_fondos\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 495,\"fontSize\": 10},"
					
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_informe_horas_extras\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 509,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_informe_horas_extras\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 509,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_informe_horas_extras\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 509,\"fontSize\": 10},"

+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_hojas_asistencia\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 523,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_hojas_asistencia\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 523,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_hojas_asistencia\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 523,\"fontSize\": 10},"

+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_cheque_cancelado\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 537,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_cheque_cancelado\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 537,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_cheque_cancelado\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 537,\"fontSize\": 10},"

+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_otros\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 551,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_otros\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 551,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_otros\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 551,\"fontSize\": 10},"

+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_gerentes_budget\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 565,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_gerentes_budget\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 565,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_gerentes_budget\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 565,\"fontSize\": 10},"

+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_finanzas_prifas\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 579,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_finanzas_prifas\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 579,\"fontSize\": 10},"
+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_finanzas_prifas\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 579,\"fontSize\": 10}"
					
					+ "]}],"
					+ "\"name\": \"HomeLand\",\"label\": \"HomeLand\"}";
			JsonNode template = mapper.readTree(txtTemplate);
			
			String docName = "homelandPdf";
			
			ObjectNode newData = mapper.createObjectNode();
			ArrayNode values = newData.putArray("values");		
			
			// peticion:
			ObjectNode peticion_respObject = mapper.createObjectNode();
			peticion_respObject.put("param", "peticion_resp");
			peticion_respObject.put("value", "1234567890");
			values.add(peticion_respObject);
			// account:
			ObjectNode account_respObject = mapper.createObjectNode();
			account_respObject.put("param", "account_resp");
			account_respObject.put("value", "Account:");
			values.add(account_respObject);
			// grant:
			ObjectNode grant_respObject = mapper.createObjectNode();
			grant_respObject.put("param", "grant_resp");
			grant_respObject.put("value", "Grant:");
			values.add(grant_respObject);
			// subvencion:
			ObjectNode subvencion_respObject = mapper.createObjectNode();
			subvencion_respObject.put("param", "subvencion_resp");
			subvencion_respObject.put("value", "Subvención:");
			values.add(subvencion_respObject);
			// contrato:
			ObjectNode contrato_respObject = mapper.createObjectNode();
			contrato_respObject.put("param", "contrato_resp");
			contrato_respObject.put("value", "Contrato:");
			values.add(contrato_respObject);
			// peticionPor:
			ObjectNode peticionPor_respObject = mapper.createObjectNode();
			peticionPor_respObject.put("param", "peticionPor_resp");
			peticionPor_respObject.put("value", "Petición por:");
			values.add(peticionPor_respObject);
			// concepto:
			ObjectNode concepto_respObject = mapper.createObjectNode();
			concepto_respObject.put("param", "concepto_resp");
			concepto_respObject.put("value", "Por concepto:");
			values.add(concepto_respObject);
			// item:
			ObjectNode item_respObject = mapper.createObjectNode();
			item_respObject.put("param", "item_resp");
			item_respObject.put("value", "Item Solicitado:");
			values.add(item_respObject);
			// justificacion:
			ObjectNode justificacion_respObject = mapper.createObjectNode();
			justificacion_respObject.put("param", "justificacion_resp");
			justificacion_respObject.put("value", "Si/No");
			values.add(justificacion_respObject);
			// cuantiaSolicitada:
			ObjectNode cuantiaSolicitada_respObject = mapper.createObjectNode();
			cuantiaSolicitada_respObject.put("param", "cuantiaSolicitada_resp");
			cuantiaSolicitada_respObject.put("value", "Cuantía Solicitada:");
			values.add(cuantiaSolicitada_respObject);
			// cuantiaAprobada:
			ObjectNode cuantiaAprobada_respObject = mapper.createObjectNode();
			cuantiaAprobada_respObject.put("param", "cuantiaAprobada_resp");
			cuantiaAprobada_respObject.put("value", "Cuantía Aprobada:");
			values.add(cuantiaAprobada_respObject);
			// cuantiaAjustada:
			ObjectNode cuantiaAjustada_respObject = mapper.createObjectNode();
			cuantiaAjustada_respObject.put("param", "cuantiaAjustada_resp");
			cuantiaAjustada_respObject.put("value", "Cuantía Ajustada:");
			values.add(cuantiaAjustada_respObject);
			// peticionario:
			ObjectNode peticionario_respObject = mapper.createObjectNode();
			peticionario_respObject.put("param", "peticionario_resp");
			peticionario_respObject.put("value", "Peticionario:");
			values.add(peticionario_respObject);
			// puesto:
			ObjectNode puesto_respObject = mapper.createObjectNode();
			puesto_respObject.put("param", "puesto_resp");
			puesto_respObject.put("value", "Puesto:");
			values.add(puesto_respObject);
			
			// Chk_1:
			ObjectNode chk1Object = mapper.createObjectNode();
			chk1Object.put("param", "chk1");
			chk1Object.put("value", "X");
			values.add(chk1Object);
			// Chk_2:
			ObjectNode chk2Object = mapper.createObjectNode();
			chk2Object.put("param", "chk2");
			chk2Object.put("value", "X");
			values.add(chk2Object);
			// Chk_3:
			ObjectNode chk3Object = mapper.createObjectNode();
			chk3Object.put("param", "chk3");
			chk3Object.put("value", "X");
			values.add(chk3Object);
			// Chk_4:
			ObjectNode chk4Object = mapper.createObjectNode();
			chk4Object.put("param", "chk4");
			chk4Object.put("value", "X");
			values.add(chk4Object);
			// Chk_5:
			ObjectNode chk5Object = mapper.createObjectNode();
			chk5Object.put("param", "chk5");
			chk5Object.put("value", "X");
			values.add(chk5Object);
			// Chk_6:
			ObjectNode chk6Object = mapper.createObjectNode();
			chk6Object.put("param", "chk6");
			chk6Object.put("value", "X");
			values.add(chk6Object);
			// Chk_7:
			ObjectNode chk7Object = mapper.createObjectNode();
			chk7Object.put("param", "chk7");
			chk7Object.put("value", "X");
			values.add(chk7Object);
			
			// adj_1:
			ObjectNode adj1Object = mapper.createObjectNode();
			adj1Object.put("param", "adj1");
			adj1Object.put("value", "X");
			values.add(adj1Object);
			// adj_2:
			ObjectNode adj2Object = mapper.createObjectNode();
			adj2Object.put("param", "adj2");
			adj2Object.put("value", "X");
			values.add(adj2Object);
			// adj_3:
			ObjectNode adj3Object = mapper.createObjectNode();
			adj3Object.put("param", "adj3");
			adj3Object.put("value", "X");
			values.add(adj3Object);
			// adj_4:
			ObjectNode adj4Object = mapper.createObjectNode();
			adj4Object.put("param", "adj4");
			adj4Object.put("value", "X");
			values.add(adj4Object);
			// adj_5:
			ObjectNode adj5Object = mapper.createObjectNode();
			adj5Object.put("param", "adj5");
			adj5Object.put("value", "X");
			values.add(adj5Object);
			// adj_6:
			ObjectNode adj6Object = mapper.createObjectNode();
			adj6Object.put("param", "adj6");
			adj6Object.put("value", "X");
			values.add(adj6Object);
			// adj_7:
			ObjectNode adj7Object = mapper.createObjectNode();
			adj7Object.put("param", "adj7");
			adj7Object.put("value", "X");
			values.add(adj7Object);
			
			// otros_1:
			ObjectNode otros1Object = mapper.createObjectNode();
			otros1Object.put("param", "otros1");
			otros1Object.put("value", "X");
			values.add(otros1Object);
			// otros_2:
			ObjectNode otros2Object = mapper.createObjectNode();
			otros2Object.put("param", "otros2");
			otros2Object.put("value", "X");
			values.add(otros2Object);
			// otros_3:
			ObjectNode otros3Object = mapper.createObjectNode();
			otros3Object.put("param", "otros3");
			otros3Object.put("value", "X");
			values.add(otros3Object);
			// otros_4:
			ObjectNode otros4Object = mapper.createObjectNode();
			otros4Object.put("param", "otros4");
			otros4Object.put("value", "X");
			values.add(otros4Object);
			// otros_5:
			ObjectNode otros5Object = mapper.createObjectNode();
			otros5Object.put("param", "otros5");
			otros5Object.put("value", "X");
			values.add(otros5Object);
			// otros_6:
			ObjectNode otros6Object = mapper.createObjectNode();
			otros6Object.put("param", "otros6");
			otros6Object.put("value", "X");
			values.add(otros6Object);
			// otros_7:
			ObjectNode otros7Object = mapper.createObjectNode();
			otros7Object.put("param", "otros7");
			otros7Object.put("value", "X");
			values.add(otros7Object);
			
			String file = pdfService.createPDFForm(template, newData, false, docName);
			System.out.println("File Creado: " + file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		testHomeLand();
	}
}
