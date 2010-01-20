package com.wissen.itext.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("itext")
public interface ReportService extends RemoteService {
    String generateReport();
}
