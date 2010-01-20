/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Dipti Bhave on 4:27:22 PM
 */
package com.wissen.itext.server;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Dipti Bhave
 * GeneratePdf class generates a pdf file
 * Create Date : 15-Jan-2010
 */
public class GeneratePdf {

    Document doc=null;
    String      username = "root";

    String      password = "wissen";

    Connection  conInvoice      = null;

    ResultSet   rsInvoice       = null;

    Statement   stmtInvoice     = null;

    String      url      = "jdbc:mysql://localhost:3306/pdf_db";

    PrintWriter out      = null;
    
    
    public GeneratePdf(){
        try {

            Class.forName("com.mysql.jdbc.Driver");
            conInvoice = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            System.out.println("Err : " + e);
        }
    }
    
    
    /**
     * getData method retrives information from database 
     * @return array of string containing resultset data
     */
    protected String[] getData(){
        String data[]=new String[10];
        try{
            stmtInvoice=conInvoice.createStatement();
            int invoice_id=65;
            rsInvoice=stmtInvoice.executeQuery("select * from Invoice where invoice_id='"+invoice_id+"'");
            while(rsInvoice.next()){
                for(int i=1;i<=9;i++){
                    data[i]=rsInvoice.getObject(i).toString();
                }//for
            }//while
            
        }
        catch(Exception e){
            System.out.println("Exception : "+e);
        }
        return data;
    }

    /**
     * createPdf creates a pdf report file
     * @return fileurl : url for the pdf file
     */
    protected String createPdf(){
        
        String fileurl="report/try.pdf";
        doc=new Document();

        try{

        PdfWriter writer=PdfWriter.getInstance(doc,new FileOutputStream(fileurl));
        doc.open();
        String data[]=getData();

        Font font = new Font(Font.STRIKETHRU, 18, Font.BOLD);

        
        
        /*----------------------Set tilte-----------------------------------*/

        Paragraph title=new Paragraph("Sheet1\n");
        title.setAlignment(1);
        doc.add(title);
        
        
        /*---------------set address table and invoice table------------------*/
       
        PdfPTable invoiceSubTable=new PdfPTable(2);
        
        font=new Font(Font.TIMES_ROMAN,12,Font.BOLD);
        Phrase date=new Phrase("Date",font); 
        PdfPCell invoiceCell1=new PdfPCell(date);
        invoiceCell1.setBorder(0);
        
        PdfPCell invoiceCell2=new PdfPCell(new Phrase("June 17,2009"));
        invoiceCell2.setBorder(0);
        invoiceCell2.setBackgroundColor(new BaseColor(0xE4,0xE8,0xF3));
        
        Phrase inv=new Phrase("Invoice#",font);
        PdfPCell invoiceCell3=new PdfPCell(inv);
        invoiceCell3.setBorder(0);
        
        PdfPCell invoiceCell4=new PdfPCell(new Phrase(data[1]));
        
        Phrase cust=new Phrase("Customer ID",font);
        PdfPCell invoiceCell5=new PdfPCell(cust);
        invoiceCell5.setBorder(0);
        PdfPCell invoiceCell6=new PdfPCell(new Phrase(data[9]));
        
     
        invoiceSubTable.addCell(invoiceCell1);
        invoiceSubTable.addCell(invoiceCell2);
        invoiceSubTable.addCell(invoiceCell3);
        invoiceSubTable.addCell(invoiceCell4);
        invoiceSubTable.addCell(invoiceCell5);
        invoiceSubTable.addCell(invoiceCell6);
        
        
        PdfPTable invoiceTable=new PdfPTable(1);
       
        Font font1=new Font(Font.COURIER, 24, Font.BOLD);
        font1.setColor(new BaseColor(0x83,0x94,0xC9));
        
        Phrase invoice=new Phrase("INVOICE\n",font1);
        
        
        PdfPCell invoiceCell=new PdfPCell(invoice);
        invoiceCell.setHorizontalAlignment(2);
        invoiceCell.setBorder(0);
        
        PdfPCell invoiceSubTableCell=new PdfPCell(invoiceSubTable);
        invoiceSubTableCell.setHorizontalAlignment(1);
        invoiceSubTableCell.setBorder(0);
        
        invoiceTable.addCell(invoiceCell);
        invoiceTable.addCell(invoiceSubTableCell);
        
        
        
        float[] colsWidth = {2f,1f};
        PdfPTable mainTable=new PdfPTable(colsWidth);
        mainTable.setWidthPercentage(100);
        
        font=new Font(Font.TIMES_ROMAN,14,Font.BOLD);
        font.setColor(BaseColor.BLACK);
        
        Paragraph senderNameParagraph=new Paragraph("\nWissen Labs \n\n",font);
        
     
        Paragraph senderAddrParagraph=new Paragraph("4th Floor, Rajiv Enclave\nNew Pandit Colony, Nashik, Maharashtra, India\nPhone: 91 253 301 2029/91 253 301 2038");
        
        PdfPCell senderAddrTableCell1=new PdfPCell(new PdfPCell(senderAddrParagraph));
        senderAddrTableCell1.setBorder(0);
        senderAddrTableCell1.setHorizontalAlignment(0);
        
        PdfPCell senderAddrTableCell2=new PdfPCell(new PdfPCell(senderNameParagraph));
        senderAddrTableCell2.setBorder(0);
        senderAddrTableCell2.setHorizontalAlignment(0);
                
        mainTable.addCell(senderAddrTableCell2);
        
        
        PdfPCell invoiceTableCell=new PdfPCell(invoiceTable);
        invoiceTableCell.setBorder(0);
        invoiceTableCell.setHorizontalAlignment(2);
       
        PdfPCell blank=new PdfPCell();
        blank.setBorder(0);
       
        
        mainTable.addCell(invoiceTableCell);
        mainTable.addCell(senderAddrTableCell1);
        mainTable.addCell(blank);
        mainTable.setSpacingAfter(40f);
        
        doc.add(mainTable);

      
        
        
        /*----------------------------Set Receiver-Address table--------------------------*/
        
        PdfPTable receiverAddrTable=new PdfPTable(1);
        receiverAddrTable.setWidthPercentage(25);
        receiverAddrTable.setHorizontalAlignment(0);
        font = new Font(Font.STRIKETHRU,12,Font.BOLD);
        font.setColor(BaseColor.WHITE);
        
        Phrase receiverPhrase=new Phrase("Bill To : ",font);
        PdfPCell receiverHeader=new PdfPCell(receiverPhrase);
        receiverHeader.setBorderColorBottom(BaseColor.BLACK);
       
        receiverHeader.setBackgroundColor(new BaseColor(0x3B,0x4E,0x87));
        
        
        PdfPCell customerNameCell=new PdfPCell(new Paragraph(data[7]));
        customerNameCell.setBorder(0);
    
        PdfPCell customerAddrCell=new PdfPCell(new Paragraph(data[8]));
        customerAddrCell.setBorder(0);
   
        PdfPCell customerCityCell=new PdfPCell(new Paragraph("HongKong"));
        customerCityCell.setBorder(0);
   
        
        receiverAddrTable.addCell(receiverHeader);
        receiverAddrTable.addCell(customerNameCell);
        receiverAddrTable.addCell(customerAddrCell);
        receiverAddrTable.addCell(customerCityCell);
        receiverAddrTable.setSpacingAfter(30f);
        doc.add(receiverAddrTable);


     
        
        /*----------------------------Set Bill Details--------------------------*/
        float[] billTableColsWidth = {3f,1f};
        PdfPTable billTable=new PdfPTable(billTableColsWidth);
        billTable.setWidthPercentage(100);
        
        font=new Font(Font.TIMES_ROMAN,14,Font.NORMAL);
        font.setColor(BaseColor.WHITE);
        
        Paragraph descriptionHeader=new Paragraph(" Description ",font);
        PdfPCell descriptionHeaderCell=new PdfPCell(descriptionHeader);
        descriptionHeaderCell.setBorder(0);
        descriptionHeaderCell.setHorizontalAlignment(1);
        descriptionHeaderCell.setBackgroundColor(new BaseColor(0x3B,0x4E,0x87));
        
        Paragraph amtHeader=new Paragraph(" Amount ",font);
        PdfPCell amtHeaderCell=new PdfPCell(amtHeader);
        amtHeaderCell.setBorder(0);
        amtHeaderCell.setHorizontalAlignment(1);
        amtHeaderCell.setBackgroundColor(new BaseColor(0x3B,0x4E,0x87));
        
        PdfPCell descriptionCell=new PdfPCell(new Paragraph(data[2]));
        descriptionCell.setExtraParagraphSpace(170f);
        
        PdfPCell amtCell=new PdfPCell(new Paragraph(data[3]));
        amtCell.setHorizontalAlignment(2);
        amtCell.setExtraParagraphSpace(170f);
       
        
        billTable.addCell(descriptionHeaderCell);
        billTable.addCell(amtHeaderCell);
        billTable.addCell(descriptionCell);
        billTable.addCell(amtCell);
       
       
        doc.add(billTable);

        
      
        
        /*----------------------------Set Comment Table----------------------------*/
        
        PdfPTable commentTable=new PdfPTable(1);
        commentTable.setHorizontalAlignment(0);
        PdfPCell commentHeaderCell=new PdfPCell(new Phrase("Other Comments"));
        commentHeaderCell.setBackgroundColor(new BaseColor(0xC0,0xC0,0xC0));
        PdfPCell commentsCell=new PdfPCell(new Phrase("1.Total Payment Due in 30 days\n2.Please Include the invoice number on your check\n\n"));
        commentTable.addCell(commentHeaderCell);
        commentTable.addCell(commentsCell);
        commentTable.setWidthPercentage(60);
        commentTable.setTotalWidth(250f);
        commentTable.writeSelectedRows(0,-1,70,310,writer.getDirectContent());
        
        /*----------------------------Set AmtTotal Table----------------------------*/
        
        PdfPTable amtTable=new PdfPTable(2);
        amtTable.setHorizontalAlignment(2);
        
        PdfPCell amtTableCell1=new PdfPCell(new Phrase("SUB TOTAL"));
        amtTableCell1.setBorder(0);
        
        PdfPCell amtTableCell2=new PdfPCell(new Phrase("$"+data[3]));
        amtTableCell2.setBorder(0);
        amtTableCell2.setHorizontalAlignment(2);
        amtTableCell2.setBackgroundColor(new BaseColor(0xE4,0xE8,0xF3));
        
        
        PdfPCell amtTableCell3=new PdfPCell(new Phrase("TAX RATE"));
        amtTableCell3.setBorder(0);
        
        
        PdfPCell amtTableCell4=new PdfPCell(new Phrase(data[4]+"%"));
        amtTableCell4.setHorizontalAlignment(2);
        amtTableCell4.setBorder(0);
        
        
        PdfPCell amtTableCell5=new PdfPCell(new Phrase("TAX"));
        amtTableCell5.setBorder(0);
        
        float tax=(Float.parseFloat(data[4])/100)*(Float.parseFloat(data[3]));
        PdfPCell amtTableCell6=new PdfPCell(new Phrase("$"+tax));
        amtTableCell6.setBorder(0);
        amtTableCell6.setHorizontalAlignment(2);
        amtTableCell6.setBackgroundColor(new BaseColor(0xE4,0xE8,0xF3));
        
        PdfPCell amtTableCell7=new PdfPCell(new Phrase("OTHER"));
        amtTableCell7.setBorder(0);
      
        
        PdfPCell amtTableCell8=new PdfPCell(new Phrase("$0.00"));
        amtTableCell8.setHorizontalAlignment(2);
        amtTableCell8.setBorder(0);
      
      
        
        
        PdfPCell amtTableCell9=new PdfPCell(new Phrase("TOTAL"));
    
        
        amtTableCell9.setBorder(0);
        amtTableCell9.setBorderColorTop(BaseColor.BLACK);
        
        
        float total=Float.parseFloat(data[3])-tax;
        PdfPCell amtTableCell10=new PdfPCell(new Phrase(""+total));
        amtTableCell10.setHorizontalAlignment(2);
        amtTableCell10.setBorderColorTop(BaseColor.BLACK);
        amtTableCell10.setBorder(0);
        amtTableCell10.setBackgroundColor(new BaseColor(0xE4,0xE8,0xF3));
       
        
                       
        
        amtTable.addCell(amtTableCell1);
        amtTable.addCell(amtTableCell2);
        amtTable.addCell(amtTableCell3);
        amtTable.addCell(amtTableCell4);
        amtTable.addCell(amtTableCell5);
        amtTable.addCell(amtTableCell6);
        amtTable.addCell(amtTableCell7);
        amtTable.addCell(amtTableCell8);
        amtTable.addCell(amtTableCell9);
        amtTable.addCell(amtTableCell10);
        
     //   doc.add(amtTable);
        
        
        
        /*----------Set Comment Table and Total amount info table in one table---------------*/
        float[] width = {3f,1.5f};
        PdfPTable table=new PdfPTable(width);
        table.setWidthPercentage(100);
                
        PdfPCell amtTableCell=new PdfPCell(amtTable);
        amtTableCell.setBorder(0);
        amtTableCell.setHorizontalAlignment(2);
        
        PdfPTable blankTable=new PdfPTable(1);
        PdfPCell blankTableCell=new PdfPCell(new Paragraph("\n\n\n\n"));
        blankTableCell.setBorder(0);
        blankTable.addCell(blankTableCell);
               

        table.addCell(blankTableCell);
        table.addCell(amtTableCell);
        table.setSpacingAfter(30f);
        doc.add(table);

        
        /*----------------------------Set Extra Information----------------------------*/
       
              
        Paragraph extraInfo=new Paragraph("If you have any questions about this invoice, please contact.\n Sushrut Bidwai, ");
        
        font = new Font(Font.BOLD,12);
        font.setColor(BaseColor.BLUE);
        
        com.itextpdf.text.Anchor anchor = new com.itextpdf.text.Anchor("sushrut@wissen.co.in",font);
        anchor.setReference("sushrut@wissen.co.in");
        extraInfo.add(anchor);
        extraInfo.add(",+91 986 023 8124\n\n");
        extraInfo.setAlignment(1);
        doc.add(extraInfo);
        
        font = new Font(Font.TIMES_ROMAN,16,Font.BOLDITALIC);
        Paragraph lastLine=new Paragraph("Thank You For Your Business!\n\n",font);
        lastLine.setAlignment(1);
        doc.add(lastLine);
       
        Paragraph pagenumber=new Paragraph("Page1");
        pagenumber.setAlignment(1);
        doc.add(pagenumber);
        
        /*----------------------------Close Document----------------------------*/
        doc.close();
        }
        catch(Exception e)
        {
            System.out.println("Err : "+e);;
        }
        return "http://localhost:8090/itext/report/try.pdf";
    }



   

}
