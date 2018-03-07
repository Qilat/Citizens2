package fr.poudlardrp.citizens.api.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.*;
import java.util.regex.Pattern;

public class Messaging {
    private static final Pattern CHAT_NEWLINE = Pattern.compile("<br>|<n>|\\n", Pattern.MULTILINE);
    private static final Splitter CHAT_NEWLINE_SPLITTER = Splitter.on(CHAT_NEWLINE);
    private static final Joiner SPACE = Joiner.on(" ").useForNull("null");
    private static boolean DEBUG = false;
    private static Logger DEBUG_LOGGER;
    private static String HIGHLIGHT_COLOUR = ChatColor.YELLOW.toString();
    private static Logger LOGGER = Logger.getLogger("Citizens");
    private static String MESSAGE_COLOUR = ChatColor.GREEN.toString();

    public static void configure(File debugFile, boolean debug, String messageColour, String highlightColour) {
        DEBUG = debug;
        MESSAGE_COLOUR = messageColour;
        HIGHLIGHT_COLOUR = highlightColour;

        if (Bukkit.getLogger() != null) {
            LOGGER = Bukkit.getLogger();
            DEBUG_LOGGER = LOGGER;
        }
        if (debugFile != null) {
            DEBUG_LOGGER = Logger.getLogger("CitizensDebug");
            try {
                FileHandler fh = new FileHandler(debugFile.getAbsolutePath(), true);
                fh.setFormatter(new DebugFormatter());
                DEBUG_LOGGER.setUseParentHandlers(false);
                DEBUG_LOGGER.addHandler(fh);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void debug(Object... msg) {
        if (isDebugging()) {
            DEBUG_LOGGER.log(Level.INFO, SPACE.join(msg));
        }
    }

    public static boolean isDebugging() {
        return DEBUG;
    }

    private static void log(Level level, Object... msg) {
        LOGGER.log(level, "[Citizens] " + SPACE.join(msg));
    }

    public static void log(Object... msg) {
        log(Level.INFO, msg);
    }

    public static void logTr(String key, Object... msg) {
        log(Level.INFO, Translator.translate(key, msg));
    }

    private static String prettify(String message) {
        String trimmed = message.trim();
        String messageColour = Colorizer.parseColors(MESSAGE_COLOUR);
        if (!trimmed.isEmpty()) {
            if (trimmed.charAt(0) == ChatColor.COLOR_CHAR) {
                ChatColor test = ChatColor.getByChar(trimmed.substring(1, 2));
                if (test == null) {
                    message = messageColour + message;
                } else
                    messageColour = test.toString();
            } else {
                message = messageColour + message;
            }
        }
        message = message.replace("[[", Colorizer.parseColors(HIGHLIGHT_COLOUR));
        return CHAT_NEWLINE.matcher(message).replaceAll("<n>]]").replace("]]", messageColour);
    }

    public static void send(CommandSender sender, Object... msg) {
        sendMessageTo(sender, SPACE.join(msg));
    }

    public static void sendError(CommandSender sender, Object... msg) {
        send(sender, ChatColor.RED.toString() + SPACE.join(msg));
    }

    public static void sendErrorTr(CommandSender sender, String key, Object... msg) {
        sendMessageTo(sender, ChatColor.RED + Translator.translate(key, msg));
    }

    private static void sendMessageTo(CommandSender sender, String rawMessage) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            rawMessage = rawMessage.replace("<player>", player.getName());
            rawMessage = rawMessage.replace("<world>", player.getWorld().getName());
        }
        rawMessage = Colorizer.parseColors(rawMessage);
        for (String message : CHAT_NEWLINE_SPLITTER.split(rawMessage)) {
            sender.sendMessage(prettify(message));
        }
    }

    public static void sendTr(CommandSender sender, String key, Object... msg) {
        sendMessageTo(sender, Translator.translate(key, msg));
    }

    public static void sendWithNPC(CommandSender sender, Object msg, NPC npc) {
        String send = msg.toString();
        send = send.replace("<owner>", npc.getTrait(Owner.class).getOwner());
        send = send.replace("<npc>", npc.getName());
        send = send.replace("<id>", Integer.toString(npc.getId()));
        send(sender, send);
    }

    public static void severe(Object... messages) {
        log(Level.SEVERE, messages);
    }

    public static void severeTr(String key, Object... messages) {
        log(Level.SEVERE, Translator.translate(key, messages));
    }

    public static String tr(String key, Object... messages) {
        return prettify(Translator.translate(key, messages));
    }

    public static String tryTranslate(Object possible) {
        if (possible == null)
            return "";
        String message = possible.toString();
        int count = 0;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '.')
                count++;
        }
        return count >= 2 ? tr(message) : message;
    }

    private static class DebugFormatter extends Formatter {
        private final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

        @Override
        public String format(LogRecord rec) {
            Throwable exception = rec.getThrown();

            String out = this.date.format(rec.getMillis());

            out += "[" + rec.getLevel().getName().toUpperCase() + "] ";
            out += rec.getMessage() + '\n';

            if (exception != null) {
                StringWriter writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));

                return out + writer;
            }

            return out;
        }
    }
}
