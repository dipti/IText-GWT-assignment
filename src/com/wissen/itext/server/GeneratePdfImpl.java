/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Dipti Bhave on 2:22:00 PM
 */
package com.wissen.itext.server;

import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wissen.itext.client.services.ReportService;


/**
 * @author Dipti Bhave
 * Server side class implementing ReportService
 * Create Date : 16-Jan-2010
 */
@SuppressWarnings("serial")
public class GeneratePdfImpl extends RemoteServiceServlet implements ReportService {

    @Override
    public String generateReport() {
        String url="";
        try{
            GeneratePdf generate=new GeneratePdf();
            url=generate.createPdf();
        
        }
        catch(Exception e){
            System.out.println("ERROR : "+e);
        }
        return url;
    }
    

}
