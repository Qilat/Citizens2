package fr.poudlardrp.citizens.api.ai;


import fr.poudlardrp.citizens.api.ai.tree.Behavior;

/**
 * Represents a collection of goals that are prioritised and executed, allowing behaviour trees via a
 * {@link GoalSelector}.
 * <p>
 * The highest priority {@link Goal} that returns true in {@link Goal#shouldExecute(GoalSelector)} is executed. Any
 * existing goals with a lower priority are replaced via {@link Goal#reset()}.
 */
public interface GoalController extends Runnable, Iterable<GoalController.GoalEntry> {
    /**
     * Registers a {@link Behavior} with a given priority.
     *
     * @param behavior The behavior
     * @param priority The priority
     * @see #addGoal(Goal, int)
     */
    void addBehavior(Behavior behavior, int priority);

    /**
     * Registers a {@link Goal} with a given priority. Priority must be greater than 0.
     *
     * @param priority The goal priority
     * @param goal     The goal
     */
    void addGoal(Goal goal, int priority);

    /**
     * Registers a goal which can reprioritise itself dynamically every tick. Implementation note: this may slow down
     * individual goal controller ticks as the list must be sorted every tick.
     *
     * @param goal
     */
    void addPrioritisableGoal(PrioritisableGoal goal);

    /**
     * Cancels and resets the currently executing goal.
     */
    void cancelCurrentExecution();

    /**
     * Clears the goal controller of all {@link Goal}s. Will stop the execution of any current goal.
     */
    void clear();

    /**
     * @return Whether a goal is currently being executed
     */
    boolean isExecutingGoal();

    /**
     * @return Whether the controller is currently paused
     * @see #setPaused(boolean)
     */
    boolean isPaused();

    /**
     * Sets whether the controller is paused. While paused, no new {@link Goal}s will be selected and any executing
     * goals will be suspended.
     *
     * @param paused Whether to pause execution
     */
    void setPaused(boolean paused);

    /**
     * Removes the given {@link Behavior} from rotation.
     *
     * @param behavior The behavior to remove
     */
    void removeBehavior(Behavior behavior);

    /**
     * Removes a {@link Goal} from rotation.
     *
     * @param goal The goal to remove
     */
    void removeGoal(Goal goal);

    public static interface GoalEntry extends Comparable<GoalEntry> {
        /**
         * @return The {@link Behavior} held by this entry if it holds one, otherwise null
         */
        Behavior getBehavior();

        /**
         * @return The {@link Goal} held by this entry
         */
        Goal getGoal();

        /**
         * @return The goal's priority
         */
        int getPriority();
    }
}
