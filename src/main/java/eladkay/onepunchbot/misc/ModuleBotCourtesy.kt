package eladkay.onepunchbot.misc

import de.btobastian.javacord.DiscordAPI
import de.btobastian.javacord.entities.message.Message
import eladkay.onepunchbot.IModule
import java.util.*

/**
 * Created by Elad on 2/3/2017.
 */
object ModuleBotCourtesy : IModule {
    override fun onMessage(api: DiscordAPI, message: Message): Boolean {
        if (message.content.startsWith("Hey, thanks, bot.", true)) {
            message.reply("You're welcome ;)")
        } else if (message.content.contains("<:autorip:277120850975653900>", true) && message.channelReceiver.name != "modlog") {
            Thread {
                Thread.sleep(5000 * "<:autorip:277120850975653900>".toRegex().findAll(message.content).toList().size.toLong())
                message.delete()
            }.start()
        } else if (message.content.startsWith("I wonder how many members OPM has.", true)) {
            message.reply(message.channelReceiver.server.memberCount.toString())
        } else if (message.content.startsWith("bot?", true)) {
            message.reply("I'm here!")
        }

        if (message.content.toLowerCase().containsAtLeastTwo("hey bot", "what's", "your", "opinion", "about", "think", "on")) {
            if(message.mentions.size == 1)
                if(message.mentions[0].name.toLowerCase().contains("kitten"))
                    message.reply(selectMeme(message.content.toLowerCase(), "She", "She's"))
                else message.reply(selectMeme(message.content.toLowerCase(), "He", "He's"))
            else if(message.mentions.size > 1 || message.content.replace("?", "").endsWith("s"))
                message.reply(selectMeme(message.content.toLowerCase(), "They", "They're", false))
            else message.reply(selectMeme(message.content.toLowerCase()))
        }

        return super.onMessage(api, message)
    }
    fun selectMeme(string: String, pronoun: String = "It", pronounBe: String = "It's", presSim: Boolean = true): String {

        val seed = string.intern().hashCode().toLong();
        val negativeMemes = listOf<String>("octuple", "enderium", "trump", "nazi", "java", "hitler", "occ")
        val elucent = listOf("elucent", "roots", "elu", "embers", "goetia")
        if (elucent.any { it in string }) {
            val quotes = listOf("wtf is that?", "dunno what it is but sounds shitty", "sounds broken, explain")
            val quote = quotes[Random(seed).nextInt(quotes.size)]
            return quote
        } else if (negativeMemes.any { it in string }) {
            val quotes = listOf("not rly dank", "not cool, bro", "nah, not great", "wtf is that idea even", "WHY")
            val quote = quotes[Random(seed).nextInt(quotes.size)]
            return quote
        } else {
            val quotes = listOf<String>("donaldtrumpmemes", "$pronounBe more unbalanced than DE", "$pronounBe a literal dumpster fire", "$pronoun run${if(presSim) "s" else ""} better than you do", "$pronounBe gonna be yuge!", "$pronounBe only good if ${pronounBe.toLowerCase()} written in Kotlin", "Turn those lights off!"/*, "/giphy $string"*/)
            val quote = quotes[Random(seed).nextInt(quotes.size)]
            val trumpMemes = listOf<String>("Like Donald Trump always said, 'If you need Viagra you're probably with the wrong girl'", "Like Donald Trump always said, 'I don't like losers'")
            return if (quote == "donaldtrumpmemes") trumpMemes[Random(seed).nextInt(trumpMemes.size)]
            else quote

        }

    }

    fun String.containsAtLeastTwo(vararg string: String) = string.count { it in this } > 2
}