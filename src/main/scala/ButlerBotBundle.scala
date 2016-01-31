import akka.actor.{ActorContext, ActorRef, Props}
import akka.event.EventBus
import bots.{SshBot, CalculatorBot}
import io.scalac.slack.BotModules
import io.scalac.slack.bots.system.{CommandsRecognizerBot, HelpBot}

/**
  * Created by alvo on 1/30/16.
  */
class ButlerBotBundle(val eventBus: EventBus) extends BotModules {
  override def registerModules(context: ActorContext, websocketClient: ActorRef): Unit = {
    context.actorOf(Props(classOf[CommandsRecognizerBot], eventBus), "CommandProcessor")
    context.actorOf(Props(classOf[HelpBot], eventBus), "HelpBot")
    context.actorOf(Props(classOf[CalculatorBot], eventBus), "calculatorBot")
    context.actorOf(Props(classOf[SshBot], eventBus), "uatBot")
  }
}
