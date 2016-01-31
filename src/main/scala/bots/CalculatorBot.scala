package bots

import io.scalac.slack.MessageEventBus
import io.scalac.slack.bots.AbstractBot
import io.scalac.slack.common.{Command, OutboundMessage}

import scala.util.Try

/**
  * Created by alvo on 1/30/16.
  * Simple closure - style calculator.
  */
class CalculatorBot(override val bus: MessageEventBus) extends AbstractBot {
  override def help(channel: String): OutboundMessage = {
    OutboundMessage(channel, s"*$name* will help you to solve difficult math problems \\n" +
      "Usage: $calc {operation} {arguments separated by space}")
  }

  /**
    * Map of all possible arithmetic operations.
    */
  private val operations = Map(
    "+" -> ((x: Double, y: Double) => x + y),
    "-" -> ((x: Double, y: Double) => x - y),
    "*" -> ((x: Double, y: Double) => x * y),
    "/" -> ((x: Double, y: Double) => x / y)
  )

  override def act: Receive = {
    case Command("calc", operation :: args, message) if args.nonEmpty =>
      val operationFunction = operations.get(operation)

      val response = operationFunction.map { function =>
        // actual calculation:
        val result = Try(args.map(_.toDouble).reduceLeft(function)).toOption.getOrElse {
         s"Sorry, but I can't parse arguments"
        }

        OutboundMessage(message.channel, s"Result is: _${result}_")
      }.getOrElse(OutboundMessage(message.channel, s"No available operation for $operation"))

      publish(response)
    case Command("calc", _, message) =>
      publish(OutboundMessage(message.channel, "No arguments specified"))
  }
}
