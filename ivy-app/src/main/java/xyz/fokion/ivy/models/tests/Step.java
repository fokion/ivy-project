package xyz.fokion.ivy.models.tests;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public sealed interface Step permits AbstractExecutor, AbstractStep {
    String TIMEOUT_KEY = "timeout";
    String NAME_KEY = "name";
    CompletableFuture<IResult> run(Optional<String> previousOutput, ExecutorService executorService);
    CompletableFuture<IResult> run(ExecutorService executorService);
    String getName();
}
