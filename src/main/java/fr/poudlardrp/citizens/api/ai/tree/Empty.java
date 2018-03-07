package fr.poudlardrp.citizens.api.ai.tree;

public class Empty extends BehaviorGoalAdapter {
    public static Empty INSTANCE = new Empty();

    private Empty() {
    }

    @Override
    public void reset() {
    }

    @Override
    public BehaviorStatus run() {
        return null;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}