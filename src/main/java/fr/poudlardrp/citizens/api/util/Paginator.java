package fr.poudlardrp.citizens.api.util;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Paginator {
    private static final int LINES_PER_PAGE = 9;
    private final List<String> lines = new ArrayList<String>();
    private String header;

    public static String wrapHeader(Object string) {
        String highlight = "<e>";
        return highlight + "=====[ " + string.toString() + highlight + " ]=====";
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public Paginator header(String header) {
        this.header = header;
        return this;
    }

    public boolean sendPage(CommandSender sender, int page) {
        int pages = (int) (Math.ceil((double) lines.size() / LINES_PER_PAGE) == 0 ? 1
                : Math.ceil((double) lines.size() / LINES_PER_PAGE));
        if (page <= 0 || page > pages)
            return false;

        int startIndex = LINES_PER_PAGE * page - LINES_PER_PAGE;
        int endIndex = page * LINES_PER_PAGE;

        Messaging.send(sender, wrapHeader("<e>" + header + " <f>" + page + "/" + pages));

        if (lines.size() < endIndex)
            endIndex = lines.size();
        for (String line : lines.subList(startIndex, endIndex))
            Messaging.send(sender, line);
        return true;
    }
}