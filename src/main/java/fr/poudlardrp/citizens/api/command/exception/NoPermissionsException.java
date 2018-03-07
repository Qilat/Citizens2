package fr.poudlardrp.citizens.api.command.exception;

import fr.poudlardrp.citizens.api.command.CommandMessages;

public class NoPermissionsException extends CommandException {
    private static final long serialVersionUID = -602374621030168291L;

    public NoPermissionsException() {
        super(CommandMessages.NO_PERMISSION);
    }
}