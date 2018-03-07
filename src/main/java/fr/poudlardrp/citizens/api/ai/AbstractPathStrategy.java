package fr.poudlardrp.citizens.api.ai;

import fr.poudlardrp.citizens.api.ai.event.CancelReason;

public abstract class AbstractPathStrategy implements PathStrategy {
    private final TargetType type;
    private CancelReason cancelReason;

    protected AbstractPathStrategy(TargetType type) {
        this.type = type;
    }

    @Override
    public void clearCancelReason() {
        cancelReason = null;
    }

    @Override
    public CancelReason getCancelReason() {
        return cancelReason;
    }

    protected void setCancelReason(CancelReason reason) {
        cancelReason = reason;
    }

    @Override
    public TargetType getTargetType() {
        return type;
    }
}