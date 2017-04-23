package net.frangarcia.thechallenge;

import io.vertx.core.AbstractVerticle

class MainVerticle extends AbstractVerticle {

  @Override
  void start() {
    println "Starting..."

    vertx.deployVerticle("groovy:net.frangarcia.thechallenge.service.web.WebVerticle")
    vertx.deployVerticle("groovy:net.frangarcia.thechallenge.service.voting.VotingVerticle")
  }

}
