package bots

import io.scalac.slack.MessageEventBus
import io.scalac.slack.bots.AbstractBot
import io.scalac.slack.common.{Command, OutboundMessage}

/**
  * Created by alvo on 1/31/16.
  * Bot for executing commands via ssh tunnel.
  */
class SshBot(override val bus: MessageEventBus) extends AbstractBot {

  private val trigger = "SSH"

  override def help(channel: String): OutboundMessage = {
    OutboundMessage(channel, s"*$name* Will help you with quick performing some useful operation on UAT server \\n" +
    s"Usage: $trigger {operation} {arguments}")
  }

  private def formatResponse(response: String): String = {
    if(response.lines.nonEmpty) response.lines.mkString("\\n")
    else response
  }

  override def act: Receive = {
    case Command(`trigger`, operation :: args, message) =>
      val response = jassh.SSH.once("localhost", "alvo", "****", "****") { ssh =>
        ssh.execute(s"""$operation ${args.mkString(" ")}""")
      }
      publish(OutboundMessage(message.channel, s"```${formatResponse(response)}```"))
    case Command(`trigger`, _, message) =>
      publish(OutboundMessage(message.channel, "No arguments specified"))
  }
}
