package stopping;

import metaheuristic.GenerationalGA;

public abstract class StoppingCondition {
    public abstract boolean checkStop(GenerationalGA<?> ga);
}
