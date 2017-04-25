package net.frangarcia.thechallenge.service.web;

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.sockjs.SockJSHandler

class WebVerticle extends AbstractVerticle {

  @Override
  void start() {
    println "Deploying web verticle"
    Router router = Router.router(vertx)
    router.route("/web/*").handler(StaticHandler.create())

    def currentVotesHandler = { RoutingContext routingContext ->
      def response = routingContext.response()
      response.putHeader("content-type", "application/json")
      response.setChunked(true)
      response.write(new JsonObject("{\"options\":[{\"label\":\"RM\", \"total\":0}, {\"label\":\"FCB\", \"total\":0}]}").toString()).end()
    }

    def inboundPermitted = [
      address:"net.frangarcia.thechallenge.vote"
    ]

    def outboundPermitted = [
      address:"net.frangarcia.thechallenge.updatevote"
    ]

    def options = [
      inboundPermitteds: [inboundPermitted],
      outboundPermitteds: [outboundPermitted]
    ]

    def sockJSHandler = SockJSHandler.create(vertx)

    sockJSHandler.bridge(options)
    sockJSHandler.socketHandler({ sockJSSocket ->
      println "Something received ${sockJSSocket}"
      // Just echo the data back
      //sockJSSocket.handler(sockJSSocket.&write)
    })
    router.route("/eventbus/*").handler(sockJSHandler)
    router.route("/api/current").handler(currentVotesHandler)

    HttpServer server = vertx.createHttpServer()
    server.requestHandler(router.&accept)
    server.listen(8080)
  }

}
