package xyz.fokion.ivy.core.models;

import java.util.List;

public non-sealed interface TestSuite extends ExecutableElement, NamedEntity {
    @Override
    default Result run(TestVisitor executor) {
        return getTestCases()
                .stream()
                .map(TestCase -> TestCase.run(executor))
                .reduce(executor.getInitialResult(), Result::complete);
    }

    List<TestCase> getTestCases();
}
