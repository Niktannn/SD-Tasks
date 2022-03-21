package netty;

import controller.CatalogController;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

public class NettyHttpServer {
    private static final CatalogController catalogController = new CatalogController();

    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String action = req.getDecodedPath().substring(1);

                    Observable<String> responseContent;
                    try {
                        responseContent = catalogController.getResponse(action, req.getQueryParameters());
                    } catch (RuntimeException e) {
                        responseContent = Observable.just("Error occurred:" + e.getMessage());
                    }

                    return resp.writeString(responseContent);
                })
                .awaitShutdown();
    }
}