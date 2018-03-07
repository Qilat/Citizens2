package fr.poudlardrp.citizens.api.command.exception;

import fr.poudlardrp.citizens.api.util.Messaging;

public class CommandException extends Exception {
    private static final long serialVersionUID = 870638193072101739L;

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(Messaging.tryTranslate(message));
    }

    public CommandException(String key, Object... replacements) {
        super(Messaging.tr(key, replacements));
    }

    public CommandException(Throwable t) {
        super(t);
    }
}