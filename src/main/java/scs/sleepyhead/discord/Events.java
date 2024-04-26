
package scs.sleepyhead.discord;

import discord4j.core.object.entity.User;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.channel.TypingStartEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;

public class Events {

    public static void onReadyEvent (ReadyEvent event) {

        var self = event.getSelf();

        System.out.println("Session: " + event.getSessionId());
        System.out.println("Logged in as '" + self.getUsername() + "'");
    }

    public static void onMessageCreateEvent (MessageCreateEvent event) {

        Message message = event.getMessage();
        MessageChannel channel = message.getChannel().block();
        if (message.getAuthor().isEmpty()) return;
        if (message.getContent().isEmpty()) return;

        if (message.getContent().equals(";ping"))
            channel.createMessage("pong!").block();
    }

    public static void onGuildCreateEvent (GuildCreateEvent event) {
    }

    public static void onTypingStartEvent (TypingStartEvent event) {
    }
}
