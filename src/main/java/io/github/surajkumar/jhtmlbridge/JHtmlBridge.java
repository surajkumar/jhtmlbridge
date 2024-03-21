package io.github.surajkumar.jhtmlbridge;

import io.vertx.core.Vertx;

public class JHtmlBridge {
    private final JHtmlBridgeHelper jHtmlBridgeHelper;
    private final JHtmlFrame jHtmlFrame;
    private final Vertx vertx;
    private final String webRoot;
    private final String windowTitle;
    private final int height;
    private final int width;

    public JHtmlBridge(String webRoot, String windowTitle, int height, int width) {
        this.jHtmlBridgeHelper = new JHtmlBridgeHelper();
        this.jHtmlFrame = new JHtmlFrame(jHtmlBridgeHelper);
        this.vertx = Vertx.vertx();
        this.webRoot = webRoot;
        this.windowTitle = windowTitle;
        this.height = height;
        this.width = width;
    }

    public void launch() {
        vertx.deployVerticle(new WebServer(webRoot));
        vertx.eventBus()
                .consumer(
                        "http.port",
                        message -> {
                            int bindPort = (int) message.body();
                            jHtmlFrame.createFrame(windowTitle, height, width, bindPort);
                        });
    }

    public JHtmlBridgeHelper getHelper() {
        return jHtmlBridgeHelper;
    }

    public void bind(String alias, Object handler) {
        jHtmlFrame.bind(alias, handler);
    }
}
