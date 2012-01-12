package cc.spray
package examples.spraycan

import akka.config.Supervision._
import akka.actor.{Supervisor, Actor}
import Actor._
import can.{HttpServer, ServerConfig}
import org.slf4j.LoggerFactory

object Boot extends App {

  LoggerFactory.getLogger(getClass) // initialize SLF4J early

  val mainModule = new HelloService {
    // bake your module cake here
  }

  val host = "0.0.0.0"
  val port = Option(System.getenv("PORT")).getOrElse("8080").toInt

  val httpService    = actorOf(new HttpService(mainModule.helloService))
  val rootService    = actorOf(new SprayCanRootService(httpService))
  val sprayCanServer = actorOf(new HttpServer(new ServerConfig(host = host, port = port)))

  Supervisor(
    SupervisorConfig(
      OneForOneStrategy(List(classOf[Exception]), 3, 100),
      List(
        Supervise(httpService, Permanent),
        Supervise(rootService, Permanent),
        Supervise(sprayCanServer, Permanent)
      )
    )
  )
}
