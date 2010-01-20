/*
 Copyright 2010 Wissen System Pvt. Ltd. All rights reserved.
 Author: Dipti Bhave on 3:43:19 PM
 */
package com.wissen.itext.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Dipti Bhave
 *
 * Create Date : 16-Jan-2010
 */
public interface ReportServiceAsync {
    void generateReport(AsyncCallback<String> callback);
}
