package io.github.surajkumar.jhtmlbridge;

import javafx.scene.web.WebView;

public class JHtmlBridgeHelper {
    private WebView webView;

    void setWebView(WebView webView) {
        this.webView = webView;
    }

    public String getContentForElement(String element) {
        if (webView == null) {
            System.out.println("Web view is null");
            return "";
        }
        return (String) webView.getEngine().executeScript("getTextContentById('" + element + "')");
    }
}
