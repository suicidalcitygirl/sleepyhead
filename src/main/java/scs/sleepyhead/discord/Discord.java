
package scs.sleepyhead.discord;

import java.lang.Thread;
import java.io.File;
import java.nio.file.Files;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.channel.TypingStartEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.gateway.intent.IntentSet;

public class Discord {

    public static void start () {

        shouldStop = false;
        running = true;

        new Thread () {
            public void run () { loop(); }
        }.start();
    }
    public static void stop () {

        shouldStop = true;
    }
    public static void waitForStop () {

        stop();
        try { while (running) Thread.sleep(10); }
        catch (Exception e) {}
    }

    private static boolean running, shouldStop;
    public static boolean isRunning () { return running; }

    private static void loop () {

        System.out.println("connecting to discord...");

        try {

            String token = loadToken();
            GatewayDiscordClient gateway = DiscordClient.create(token)
                .gateway().setEnabledIntents(IntentSet.all()).login().block();

            gateway.on(ReadyEvent.class).subscribe(event -> {
                Events.onReadyEvent(event);
            });
            gateway.on(MessageCreateEvent.class).subscribe(event -> {
                Events.onMessageCreateEvent(event);
            });
            gateway.on(GuildCreateEvent.class).subscribe(event -> {
                Events.onGuildCreateEvent(event);
            });
            gateway.on(TypingStartEvent.class).subscribe(event -> {
                Events.onTypingStartEvent(event);
            });

            while (!shouldStop) { try {

                Thread.sleep(100);

            } catch (Exception e) {

                e.printStackTrace();
            } }

            gateway.logout().block();
            running = false;

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static String loadToken () {

        try {

            File tokenFile = new File("token.dct");
            if (!tokenFile.exists() || tokenFile.isDirectory()) {

                System.out.println("token file was not found!!");
                System.exit(-1);
            }

            String tokenRaw = Files.readString(tokenFile.toPath());

            if (tokenRaw.isEmpty()) {

                System.out.println("token file was empty!!");
                System.exit(-1);
            }
            if (tokenRaw.contains("\n"))
                tokenRaw = tokenRaw.split("\n")[0];

            return tokenRaw;

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(-1);
        }

        return "";
    }
}
