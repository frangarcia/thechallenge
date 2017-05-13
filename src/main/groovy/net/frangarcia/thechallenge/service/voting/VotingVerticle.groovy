package net.frangarcia.thechallenge.service.voting;

import io.vertx.core.AbstractVerticle
import io.vertx.core.eventbus.Message
import io.vertx.core.http.HttpServer
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.sockjs.SockJSHandler

class VotingVerticle extends AbstractVerticle {

  private Map<String, Long> totals = [:]

  private Handler handlerVoting = { Message message ->
    println "Handler voting ${message.body()}"
    JsonObject jsonObject = new JsonObject(message.body())
    if (totals.containsKey(jsonObject.getString("option"))) {
      totals.put(jsonObject.getString("option"), ++totals.get(jsonObject.getString("option")))
    } else {
      totals.put(jsonObject.getString("option"), 1)
    }
    vertx.eventBus().publish("net.frangarcia.thechallenge.updatevote", new JsonObject("{\"option\":\"${jsonObject.getString("option")}\",\"total\":${totals.get(jsonObject.getString("option"))}}"))
  }

  @Override
  void start() {
    println "Deploying voting verticle"
    vertx.eventBus().consumer("net.frangarcia.thechallenge.vote", handlerVoting)
  }

}
