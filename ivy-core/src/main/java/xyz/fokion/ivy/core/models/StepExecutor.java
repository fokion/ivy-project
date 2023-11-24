package xyz.fokion.ivy.core.models;

import java.util.List;

public non-sealed interface StepExecutor extends ExecutableElement, StepsContainer {
    @Override
    default Result run(TestVisitor executor) {
        return getSteps()
                .stream()
                .map(Step -> Step.run(executor))
                .reduce(executor.getInitialResult(), Result::complete);
    }


    List<Step> getSteps();
}
