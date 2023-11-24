package xyz.fokion.ivy.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class TestCaseImpl implements TestCase, NamedEntity, StepExecutor {
    private String name;
    private final List<Step> steps = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Step> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public void setSteps(List<Step> steps) {
        this.steps.clear();
        this.steps.addAll(steps);
    }
}
