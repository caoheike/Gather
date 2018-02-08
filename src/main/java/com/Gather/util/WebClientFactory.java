package com.Gather.util;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class WebClientFactory {
    private WebClient webClient = new WebClient();

    public WebClient getWebClient() {
    	webClient.getCookieManager().setCookiesEnabled(true);
    	webClient.getOptions().setCssEnabled(false);
    	webClient.getOptions().setTimeout(30000);
    	webClient.getOptions().setJavaScriptEnabled(true);
    	webClient.setJavaScriptTimeout(30000);
    	webClient.getOptions().setRedirectEnabled(true);
    	webClient.getOptions().setThrowExceptionOnScriptError(false);
    	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    	webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    	webClient.getOptions().setJavaScriptEnabled(false);  
        return webClient;
    }
}
