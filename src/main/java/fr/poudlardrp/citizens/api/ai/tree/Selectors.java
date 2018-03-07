package fr.poudlardrp.citizens.api.ai.tree;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import java.util.*;

public class Selectors {
    private static final Comparator<Behavior> BEHAVIOR_COMPARATOR = new Comparator<Behavior>() {
        @Override
        @SuppressWarnings("unchecked")
        public int compare(Behavior o1, Behavior o2) {
            return ((Comparable<Behavior>) o1).compareTo(o2);
        }
    };

    private Selectors() {
    }

    public static Function<List<Behavior>, Behavior> prioritySelectionFunction() {
        return prioritySelectionFunction0(BEHAVIOR_COMPARATOR);
    }

    private static Function<List<Behavior>, Behavior> prioritySelectionFunction0(
            final Comparator<Behavior> comparator) {
        return new PrioritySelection(comparator);
    }

    public static Selector.Builder prioritySelector(Comparator<Behavior> comparator, Behavior... behaviors) {
        return prioritySelector(comparator, Arrays.asList(behaviors));
    }

    public static Selector.Builder prioritySelector(final Comparator<Behavior> comparator,
                                                    Collection<Behavior> behaviors) {
        Preconditions.checkArgument(behaviors.size() > 0, "must have at least one behavior for comparison");
        return Selector.selecting(behaviors).selectionFunction(prioritySelectionFunction0(comparator));
    }

    public static final class PrioritySelection implements Function<List<Behavior>, Behavior> {
        private final Comparator<Behavior> comparator;

        private PrioritySelection(Comparator<Behavior> comparator) {
            this.comparator = comparator;
        }

        @Override
        public Behavior apply(List<Behavior> input) {
            Collections.sort(input, comparator);
            return input.get(input.size() - 1);
        }
    }
}
