package xyz.fokion.ivy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.fokion.ivy.models.tests.IResult;
import xyz.fokion.ivy.models.tests.executors.Script;
import xyz.fokion.ivy.services.StepService;

import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class IvyProjectApplicationTests {

    @Autowired
    private StepService stepService;

    @Test
    void contextLoads() {
        List<IResult> results = stepService.run(List.of(new Script(Map.of(Script.SCRIPT_KEY, "echo", Script.ARGS_KEY, "hello world"))));
        log.info("Results: {}", results);
    }

}
