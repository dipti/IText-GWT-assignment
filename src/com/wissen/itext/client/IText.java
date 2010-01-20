package com.wissen.itext.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.wissen.itext.client.widget.GeneratePdfWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IText extends Composite implements EntryPoint  {

   
    HorizontalPanel containerPanel;
    public void onModuleLoad() {
        containerPanel=new HorizontalPanel();
       
        containerPanel.add(new GeneratePdfWidget());
        
        RootPanel.get().add(containerPanel);
    }

   
    
}
