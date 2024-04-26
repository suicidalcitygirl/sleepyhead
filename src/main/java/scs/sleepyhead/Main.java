
package scs.sleepyhead;

import java.lang.Thread;

import scs.sleepyhead.discord.Discord;

public class Main {

    public static void main (String[] args) {

        Discord.start();

        try { System.in.read(); } catch (Exception e) {}

        Discord.waitForStop();
    }
}
