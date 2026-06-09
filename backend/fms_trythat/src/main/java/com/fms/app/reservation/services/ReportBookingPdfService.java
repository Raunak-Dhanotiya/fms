package com.fms.app.reservation.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.fms.app.notification.controllers.NotifyRequestor;
import com.fms.app.reservation.models.dto.ReportBookingDto;
import com.fms.app.utils.CommonConstant;

@Service
public class ReportBookingPdfService {

	private static final Logger logger = LogManager.getLogger(NotifyRequestor.class);

	final float leftMarginLogo = PageSize.A4.getWidth() - 90f;
	final float bottomMarginLogo = PageSize.A4.getHeight() - 65f;

	final float leftMarginDate = PageSize.A4.getWidth() - 85f;
	final float bottomMarginDate = PageSize.A4.getHeight() - 65f;

	public byte[] generatePdf(ReportBookingDto record) {

		final String FILE_NAME = CommonConstant.FILE_NAME1;
		String rootDir = System.getProperty("user.dir");
		String path = new java.io.File(rootDir + File.separator + FILE_NAME) + ".pdf";
		File file = new File(path);

		try {
			Document document = new Document(PageSize.A4, 12, 12, 70, 50);
			PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			String filename = rootDir + File.separator + CommonConstant.IMAGE_PATH;
			if(record.getHeading()!=null) {
				document = setTitleAndHeaderToPdf(record.getTitle(), document, filename, record.getHeading());
			}else {
				document = setTitleToPdf(record.getTitle(), document, filename);	
			}
			document = createContentWithImage(document, record);
			document.close();
			setPageNumberAndLogoForPdf(filename, file);
			byte[] pdf = loadFile(file);
			file.delete();
			return pdf;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	private Document setTitleToPdf(String title, Document document, String filename) {

		try {
			Font titleStyle = FontFactory.getFont(FontFactory.TIMES, 20, BaseColor.BLACK);
			Paragraph reportTitle = new Paragraph(title, titleStyle);
			Paragraph lineSpace = new Paragraph(" ");
			reportTitle.setAlignment(Element.ALIGN_CENTER);
			document.add(reportTitle);
			document.add(lineSpace);
			document.add(lineSpace);
			return document;

		} catch (DocumentException e) {

			logger.error(e.getMessage());
		}

		return document;
	}

	private static Document createContentWithImage(Document document, ReportBookingDto record) {
        List<String> encodedImagesList = record.getChartImg();
        for (String encodedImage : encodedImagesList) {
            byte[] decodedBytes = Base64.decode(encodedImage.split(",")[1]);
            PdfPTable contentImgTable = new PdfPTable(1);
            contentImgTable.setWidthPercentage(100f);
            
            try {
                final Image spareImage = Image.getInstance(decodedBytes);
                float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
                float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
                spareImage.scaleToFit(documentWidth, documentHeight);
                PdfPCell imge = new PdfPCell(spareImage);
                imge.setBorder(0);
                imge.setFixedHeight(245f);
                imge.setPadding(0);
                
                contentImgTable.addCell(imge);

                try {
                    Paragraph lineSpace = new Paragraph(" ");
                    document.add(contentImgTable);
                    document.add(lineSpace);
                    document.add(lineSpace);
                } catch (DocumentException e) {
                    logger.error(e.getMessage());
                }

            } catch (BadElementException | IOException e1) {
                logger.error(e1.getMessage());
            }
        }

        return document;

    }

	private byte[] setPageNumberAndLogoForPdf(String filename, File file) {
		try {
			byte[] pdf = loadFile(file);

			PdfReader reader = new PdfReader(pdf);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
			int pageCount = reader.getNumberOfPages();
			int i = 0;
			PdfContentByte over;
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
			String strDate = sdf.format(date);
			while (i < pageCount) {
				i++;
				over = stamper.getOverContent(i);

				over.beginText();
				over.setFontAndSize(bf, 8);
				over.setTextMatrix(520, 15);
				over.showText("Page " + i + " of " + pageCount);
				over.endText();

				if (i > 0) {

					Image imageTitle = Image.getInstance(filename);
					imageTitle.scaleToFit(50, 70);
					imageTitle.setAbsolutePosition(leftMarginLogo, bottomMarginLogo);
					over.addImage(imageTitle);

					over.setFontAndSize(bf, 8);
					over.setTextMatrix(leftMarginDate, bottomMarginDate);
					over.showText(strDate);

				}
			}
			stamper.close();
			reader.close();
			return pdf;
		} catch (Exception de) {
			de.printStackTrace();
		}
		return null;
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {

		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		is.close();
		return bytes;
	}
	
	private Document setTitleAndHeaderToPdf(String title, Document document, String filename, String header) {

		try {
//			String[] headerList = header.split(",");
			Font titleStyle = FontFactory.getFont(FontFactory.TIMES, 20, BaseColor.BLACK);
			Font headingStyle = FontFactory.getFont(FontFactory.TIMES, 10, BaseColor.BLACK);
			Paragraph reportTitle = new Paragraph(title, titleStyle);
			Paragraph lineSpace = new Paragraph(" ");
			reportTitle.setAlignment(Element.ALIGN_CENTER);
			document.add(reportTitle);
			document.add(lineSpace);
			document.add(lineSpace);
			Paragraph heading = new Paragraph(header, headingStyle);
			document.add(heading);
//			Paragraph blheading1 = new Paragraph("Building:", headingStyle);
//			Paragraph blheading2 = new Paragraph(headerList[0], headingStyle);
//			Paragraph flheading1 = new Paragraph("Floor:", headingStyle);
//			Paragraph flheading2 = new Paragraph(headerList[1], headingStyle);
//			PdfPTable hdTable = new PdfPTable(2);
//			float[] columnWidths = {8f, 80f};
//			hdTable.setWidths(columnWidths);
//			hdTable.setWidthPercentage(100f);
//			PdfPCell cell1 = getCellForHeadingTable(blheading1, true);
//			hdTable.addCell(cell1);
//			PdfPCell cell2 = getCellForHeadingTable(blheading2, false);
//			hdTable.addCell(cell2);
//			PdfPCell cell3 = getCellForHeadingTable(flheading1, true);
//			hdTable.addCell(cell3);
//			PdfPCell cell4 = getCellForHeadingTable(flheading2, false);
//			hdTable.addCell(cell4);
//			hdTable.setHorizontalAlignment(Element.ALIGN_LEFT);
//			document.add(hdTable);
			document.add(lineSpace);
			return document;
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
		return document;
	}
	
	private PdfPCell  getCellForHeadingTable(Paragraph heading, Boolean isrightaligned) {
		PdfPCell cell = new PdfPCell(heading);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setBorderColor(BaseColor.WHITE);
		if(isrightaligned) {
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		}
		return cell;
	}

}
