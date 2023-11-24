package xyz.fokion.ivy.core.models;

import lombok.Getter;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TestSuiteResult extends AbstractResultHandler implements Result {
    private final OffsetDateTime startTime;
    private final List<String> testCases = new ArrayList<>();

    public TestSuiteResult(String name) {
        super(name);
        startTime = OffsetDateTime.now(Clock.systemUTC());
    }

    @Override
    public boolean isSuccessful() {
        return state;
    }

    @Override
    public Optional<String> getOutput() {
        return Optional.ofNullable(output);
    }

    @Override
    public Optional<String> getError() {
        return Optional.ofNullable(errorLog);
    }

    @Override
    public OffsetDateTime getStartTime() {
        return startTime;
    }

    @Override
    public OffsetDateTime getEndTime() {
        return endTime;
    }

    @Override
    public Result complete(Result task) {
        if (task instanceof TestCaseResult) {
           handleResult(task);
           testCases.add(task.getName());
        }
        return this;
    }

}
