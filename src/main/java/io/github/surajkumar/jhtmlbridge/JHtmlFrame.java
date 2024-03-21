package io.github.surajkumar.jhtmlbridge;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import netscape.javascript.JSObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

class JHtmlFrame {
    private static final String HOST = "http://localhost";
    private final Map<String, Object> bridges;
    private final JHtmlBridgeHelper jHtmlBridgeHelper;

    public JHtmlFrame(JHtmlBridgeHelper jHtmlBridgeHelper) {
        this.bridges = new HashMap<>();
        this.jHtmlBridgeHelper = jHtmlBridgeHelper;
    }

    public void createFrame(String windowTitle, int height, int width, int port) {
        JFXPanel jfxPanel = new JFXPanel();
        JFrame frame = new JFrame(windowTitle);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        loadPage(jfxPanel, "%s:%d".formatted(HOST, port));
        frame.getContentPane().add(jfxPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void bind(String alias, Object bridge) {
        bridges.put(alias, bridge);
    }

    private void loadPage(JFXPanel jfxPanel, String index) {
        Platform.runLater(
                () -> {
                    WebView webView = new WebView();
                    jHtmlBridgeHelper.setWebView(webView);

                    injectJavaBinding(webView);
                    injectJavaScriptHelper(webView);

                    webView.getEngine().load(index);
                    jfxPanel.setScene(new Scene(webView));
                });
    }

    private void injectJavaBinding(WebView webView) {
        webView.getEngine()
                .getLoadWorker()
                .stateProperty()
                .addListener(
                        (obs, oldState, newState) -> {
                            if (newState == Worker.State.SUCCEEDED) {
                                JSObject jsObject =
                                        (JSObject) webView.getEngine().executeScript("window");
                                bridges.forEach(jsObject::setMember);
                            }
                        });
    }

    private void injectJavaScriptHelper(WebView webView) {
        webView.getEngine()
                .getLoadWorker()
                .stateProperty()
                .addListener(
                        (obs, oldState, newState) -> {
                            if (newState == Worker.State.SUCCEEDED) {
                                webView.getEngine()
                                        .executeScript(
                                                """
                        function getTextContentById(elementId) {
                            var element = document.getElementById(elementId);
                            if (element) {
                                if (element.tagName.toLowerCase() === 'input') {
                                    return element.value;
                                } else {
                                    return element.textContent;
                                }
                            } else {
                                return "";
                            }
                        }
                        """);
                            }
                        });
    }
}
