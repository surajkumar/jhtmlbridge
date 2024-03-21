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

    // Set CSS properties for a specific element
    public void setCssProperties(String elementId, String cssProperties) {
        String script = "var element = document.getElementById('" + elementId + "');";
        script += "element.style.cssText += '" + cssProperties + "';";
        webView.getEngine().executeScript(script);
    }

    // Toggle CSS class for a specific element
    public void toggleCssClass(String elementId, String className) {
        String script = "var element = document.getElementById('" + elementId + "');";
        script += "if (element.classList.contains('" + className + "')) {";
        script += "    element.classList.remove('" + className + "');";
        script += "} else {";
        script += "    element.classList.add('" + className + "');";
        script += "}";
        webView.getEngine().executeScript(script);
    }

    // Retrieve CSS properties of a specific element
    public String getCssProperties(String elementId) {
        String script = "var element = document.getElementById('" + elementId + "');";
        script += "var style = window.getComputedStyle(element);";
        script += "JSON.stringify(style);";
        return (String) webView.getEngine().executeScript(script);
    }

    // Add a new HTML element to the DOM
    public void addElement(String parentId, String tagName, String attributes, String innerHTML) {
        String script = "var parent = document.getElementById('" + parentId + "');";
        script += "var element = document.createElement('" + tagName + "');";
        script += "element.innerHTML = '" + innerHTML + "';";
        script += "element.setAttribute('" + attributes + "');";
        script += "parent.appendChild(element);";
        webView.getEngine().executeScript(script);
    }

    // Remove an HTML element from the DOM
    public void removeElement(String elementId) {
        String script = "var element = document.getElementById('" + elementId + "');";
        script += "element.parentNode.removeChild(element);";
        webView.getEngine().executeScript(script);
    }

    // Modify attributes of an HTML element
    public void modifyAttribute(String elementId, String attributeName, String attributeValue) {
        String script = "var element = document.getElementById('" + elementId + "');";
        script += "element.setAttribute('" + attributeName + "', '" + attributeValue + "');";
        webView.getEngine().executeScript(script);
    }

    // Retrieve attribute value of an HTML element
    public String getAttribute(String elementId, String attributeName) {
        String script = "var element = document.getElementById('" + elementId + "');";
        script += "element.getAttribute('" + attributeName + "');";
        return (String) webView.getEngine().executeScript(script);
    }

    // Bind a Java method to an HTML event
    public void bindEvent(String elementId, String eventName, String methodName) {
        String script = "var element = document.getElementById('" + elementId + "');";
        script += "element.addEventListener('" + eventName + "', function() {";
        script += "    jhtmlbridge." + methodName + "();";
        script += "});";
        webView.getEngine().executeScript(script);
    }
}
