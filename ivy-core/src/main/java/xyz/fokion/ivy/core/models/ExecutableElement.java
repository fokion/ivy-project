package xyz.fokion.ivy.core.models;

public sealed interface ExecutableElement permits TestSuite, TestCase, Step, StepExecutor {
    Result run(TestVisitor executor);
}
