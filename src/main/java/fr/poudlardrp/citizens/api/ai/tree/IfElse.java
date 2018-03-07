package fr.poudlardrp.citizens.api.ai.tree;

public class IfElse extends BehaviorGoalAdapter {
    private final Condition condition;
    private final Behavior ifBehavior, elseBehavior;
    private Behavior executing;

    public IfElse(Condition condition, Behavior ifBehavior, Behavior elseBehavior) {
        this.condition = condition;
        this.ifBehavior = ifBehavior;
        this.elseBehavior = elseBehavior;
    }

    public static IfElse create(Condition condition, Behavior ifBehavior, Behavior elseBehavior) {
        return new IfElse(condition, ifBehavior, elseBehavior);
    }

    @Override
    public void reset() {
        if (executing != null) {
            executing.reset();
            executing = null;
        }
    }

    @Override
    public BehaviorStatus run() {
        return executing.run();
    }

    @Override
    public boolean shouldExecute() {
        boolean cond = condition.get();
        if (cond) {
            executing = ifBehavior;
        } else {
            executing = elseBehavior;
        }
        if (executing == null || !executing.shouldExecute()) {
            executing = null;
            return false;
        }
        return true;
    }
}
