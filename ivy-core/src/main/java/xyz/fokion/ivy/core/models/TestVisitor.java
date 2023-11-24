package xyz.fokion.ivy.core.models;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public sealed interface TestVisitor permits Context {
    Result visitTestSuite(TestSuite testSuite);

    Result getInitialResult();

    ExecutorService getExecutor();
}
