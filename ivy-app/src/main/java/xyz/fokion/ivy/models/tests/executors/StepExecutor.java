package xyz.fokion.ivy.models.tests.executors;

import xyz.fokion.ivy.models.tests.Step;

import java.util.List;

public interface StepExecutor {
    boolean isParallel();

    List<Step> getSteps();

}
