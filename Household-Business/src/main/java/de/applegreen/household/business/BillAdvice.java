package de.applegreen.household.business;

import de.applegreen.household.model.Bill;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author [ATE] Alexander Tepe | alexander.tepe@lmis.de
 **/
@Component
@Aspect
public class BillAdvice implements HasLogger {

    private ClosingService closingService;

    public BillAdvice(ClosingService closingService) {
        this.closingService = closingService;
    }

    @AfterReturning(value = "@annotation(de.applegreen.household.business.AdviceAnnotations.BillCommit)")
    public void updateClosing(JoinPoint jp) {
        this.logger().info("Advice triggering Closingservice");
        Arrays.stream(jp.getArgs())
                .filter((arg) -> arg instanceof Bill)
                .forEach((arg) -> this.closingService.updateProbation((Bill) arg));
    }
}
