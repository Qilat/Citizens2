package fr.poudlardrp.citizens.api.ai.goals;

import fr.poudlardrp.citizens.api.ai.event.CancelReason;
import fr.poudlardrp.citizens.api.ai.event.NavigatorCallback;
import fr.poudlardrp.citizens.api.ai.tree.BehaviorGoalAdapter;
import fr.poudlardrp.citizens.api.ai.tree.BehaviorStatus;
import fr.poudlardrp.citizens.api.npc.NPC;
import org.bukkit.Location;

public class MoveToGoal extends BehaviorGoalAdapter {
    private final NPC npc;
    private final Location target;
    private boolean finished;
    private CancelReason reason;

    public MoveToGoal(NPC npc, Location target) {
        this.npc = npc;
        this.target = target;
    }

    @Override
    public void reset() {
        npc.getNavigator().cancelNavigation();
        reason = null;
        finished = false;
    }

    @Override
    public BehaviorStatus run() {
        if (finished) {
            return reason == null ? BehaviorStatus.SUCCESS : BehaviorStatus.FAILURE;
        }
        return BehaviorStatus.RUNNING;
    }

    @Override
    public boolean shouldExecute() {
        boolean executing = !npc.getNavigator().isNavigating() && target != null;
        if (executing) {
            npc.getNavigator().setTarget(target);
            npc.getNavigator().getLocalParameters().addSingleUseCallback(new NavigatorCallback() {
                @Override
                public void onCompletion(CancelReason cancelReason) {
                    finished = true;
                    reason = cancelReason;
                }
            });
        }
        return executing;
    }
}
