/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Dipti Bhave on 3:36:12 PM
 */
package com.wissen.itext.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wissen.itext.client.services.ReportService;
import com.wissen.itext.client.services.ReportServiceAsync;




/**
 * @author Dipti Bhave
 * A widget for pdf generation
 * Create Date : 16-Jan-2010
 */
public class GeneratePdfWidget extends Composite{
    
        public GeneratePdfWidget(){
            HorizontalPanel generatePdfPanel=new HorizontalPanel();
            Button generatePdfButton=new Button("Generate Pdf");
            generatePdfPanel.add(generatePdfButton);
            generatePdfButton.addClickHandler(new ClickHandler(){

              
                public void onClick(ClickEvent event) {
              
                        generateReport();
                }
                
            });
            
            
            initWidget(generatePdfPanel);
            
        }
        
      
        /**
         * Create a remote service proxy to talk to the server-side Report service.
         */
        
        private final ReportServiceAsync reportService = GWT.create(ReportService.class);

        
        public ReportServiceAsync getReportService() {
            return reportService;
        }
        
        // ---------------------- Controller's Methods ---------------------
        public void generateReport() {
            reportService.generateReport(generateReportCallback);

        }

        // -------------------- Call Back classes ---------------------------
        AsyncCallback<String> generateReportCallback = new AsyncCallback<String>() {

                                        
                                            @Override
                                            public void onSuccess(String url) {
                                               
                                                Window.alert(url);
                                                Frame frame=new Frame(url);
                                                frame.setWidth("100%");
                                                frame.setHeight("450px");
                                                RootPanel.get().add(frame);

                                            }

                                            @Override
                                            public void onFailure(Throwable caught) {
                                                Window.alert("Err : "+caught);
                                             
                                            }
                                           };
}
