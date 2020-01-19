package de.applegreen.household.web.util;

import de.applegreen.household.business.ClosingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Calendar;

@Component
public class DateDaemon implements HasLogger {

    private ClosingService closingService;

    @Autowired
    public DateDaemon(ClosingService closingService) {
        this.closingService = closingService;
    }

    @PostConstruct
    public void init() {
        try {
            this.listen();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    private void listen() throws InterruptedException {
        this.logger().info("Checking Date...");
        int lastDayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        LocalDateTime today = LocalDateTime.now();
        if (lastDayOfMonth == today.getDayOfMonth()) {
            this.logger().info("generating new Closing");
            this.closingService.generateProbation();
        }
        this.logger().info("Going to sleep");
    }
}
