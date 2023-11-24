package xyz.fokion.ivy.models.tests;

import java.util.Map;
import java.util.Optional;



public record Result(int exitCode , String output, String errorLog , Map<String,Object> input) implements IResult {
    @Override
    public boolean isSuccessful() {
        return exitCode == 0;
    }

    @Override
    public Optional<String> getOutput() {
        return Optional.of(output);
    }

    @Override
    public Optional<String> getError() {
        return Optional.of(errorLog);
    }

    @Override
    public Map<String, Object> getInput() {
        return input;
    }
}
