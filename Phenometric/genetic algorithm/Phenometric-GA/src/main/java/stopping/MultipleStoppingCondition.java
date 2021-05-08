package stopping;

import metaheuristic.GenerationalGA;

import java.util.ArrayList;
import java.util.List;

public class MultipleStoppingCondition extends StoppingCondition {
    private final List<StoppingCondition> stoppingConditions;

    public MultipleStoppingCondition() {
        this.stoppingConditions = new ArrayList<>();
    }

    public void add(StoppingCondition stoppingCondition) {
        this.stoppingConditions.add(stoppingCondition);
    }

    @Override
    public boolean checkStop(GenerationalGA<?> ga) {
        for(StoppingCondition stoppingCondition: stoppingConditions) {
            if(stoppingCondition.checkStop(ga)) {
                return true;
            }
        }
        return false;
    }
}
