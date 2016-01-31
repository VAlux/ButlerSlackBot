package bots

import com.decodified.scalassh.{SSH, SshClient}
import io.scalac.slack.MessageEventBus
import io.scalac.slack.bots.AbstractBot
import io.scalac.slack.common.{Command, OutboundMessage}

/**
  * Created by alvo on 1/31/16.
  *
  */
class SshBot(override val bus: MessageEventBus) extends AbstractBot {

  private val trigger = "UATexec"

  override def help(channel: String): OutboundMessage = {
    OutboundMessage(channel, s"*$name* Will help you with quick performing some useful operation on UAT server \\n" +
    s"Usage: $trigger {operation} {arguments}")
  }

  override def act: Receive = {
    case Command(`trigger`, operation :: args, message) =>
      SSH("localhost") { client =>
        client.exec("ls -l").right.map { result =>
          publish(OutboundMessage(message.channel, "Result:\n" + result.stdOutAsString()))
        }
      }
    case Command(`trigger`, _, message) =>
      publish(OutboundMessage(message.channel, "No arguments specified"))
  }
}
