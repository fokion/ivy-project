package xyz.fokion.ivy.core.models;

public sealed interface TestCase extends ExecutableElement, StepExecutor permits TestCaseImpl {

}
