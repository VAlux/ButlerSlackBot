import akka.actor.{ActorSystem, Props}
import io.scalac.slack.MessageEventBus
import io.scalac.slack.api.Start
import io.scalac.slack.common.Shutdownable
import io.scalac.slack.common.actors.SlackBotActor
import io.scalac.slack.websockets.WebSocket

/**
  * Created by alvo on 1/30/16.
  */
object BotRunner extends Shutdownable {
  val system = ActorSystem("SlackBotSystem")
  val eventBus = new MessageEventBus
  val bot = {
    system.actorOf(Props(classOf[SlackBotActor], new ButlerBotBundle(eventBus), eventBus, this, None), "butler")
  }

  def main(args: Array[String]) = {
    try {
      bot ! Start
      system.awaitTermination()
      println("Shutdown successful")
    } catch {
      case e: Exception =>
        println(s"Exception occurred: $e")
        system.shutdown()
        system.awaitTermination()
    }
  }

  sys.addShutdownHook(shutdown())

  override def shutdown(): Unit = {
    bot ! WebSocket.Release
    system.shutdown()
    system.awaitTermination()
  }
}
