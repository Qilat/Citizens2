package fr.poudlardrp.citizens.api.ai.tree;

public class ForwardingBehaviorGoalAdapter extends BehaviorGoalAdapter {
    private final Behavior behavior;

    public ForwardingBehaviorGoalAdapter(Behavior behavior) {
        this.behavior = behavior;
    }

    public Behavior getWrapped() {
        return behavior;
    }

    @Override
    public void reset() {
        behavior.reset();
    }

    @Override
    public BehaviorStatus run() {
        return behavior.run();
    }

    @Override
    public boolean shouldExecute() {
        return behavior.shouldExecute();
    }

    @Override
    public String toString() {
        return "ForwardingBehaviorGoalAdapter [behavior=" + behavior + "]";
    }
}