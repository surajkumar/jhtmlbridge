package io.github.surajkumar.jhtmlbridge;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

class WebServer extends AbstractVerticle {
    private final String webRoot;

    public WebServer(String webRoot) {
        this.webRoot = webRoot;
    }

    @Override
    public void start(Promise promise) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(StaticHandler.create(webRoot));
        server.requestHandler(router);
        server.listen(
                0,
                res -> {
                    if (res.succeeded()) {
                        vertx.eventBus().publish("http.port", res.result().actualPort());
                        promise.complete();
                    } else {
                        promise.fail(res.cause());
                    }
                });
    }
}
