package xyz.fokion.ivy.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.fokion.ivy.models.tests.IResult;
import xyz.fokion.ivy.models.tests.Step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class StepService {
    @Autowired
    private ExecutorService executor;


    public List<IResult> run(List<Step> steps) {
        CompletableFuture<IResult> chain = null;

        ConcurrentLinkedQueue<IResult> results = new ConcurrentLinkedQueue<>();
        for (Step step : steps) {
            if (chain == null) {
                chain = step.run(executor);
                continue;
            }
            chain.thenApplyAsync((result) -> {
                        log.info("Result: {}", result.getOutput());
                        results.add(result);
                        if (result.isSuccessful()) {
                            return step.run(result.getOutput(), executor);
                        }
                        return CompletableFuture.failedFuture(new RuntimeException("Previous step failed"));
                    }, executor)
                    .exceptionallyAsync((e) -> {
                        log.warn("Could not run step", e);
                        return null;
                    }, executor);
        }

        if (chain == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Collections.singleton(chain.join()));
    }

}
