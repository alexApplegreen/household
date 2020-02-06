package de.applegreen.household.business;

import de.applegreen.household.model.Bill;
import de.applegreen.household.model.Closing;
import de.applegreen.household.persistence.BillRepository;
import de.applegreen.household.persistence.ClosingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClosingService implements HasLogger {

    private ClosingRepository closingRepository;

    @Autowired
    public ClosingService(ClosingRepository closingRepository) {
        this.closingRepository = closingRepository;
    }

    /**
     * Method to update Closing every time a list is commited
     */
    public void updateProbation(Bill bill) {
        // find current closing if exists
        List<Closing> closings = this.closingRepository.findRecent(LocalDate.now().getMonthValue());

        // if none exists for current month
        if (closings.size() == 0) {

            Closing newClosing = new Closing();
            Collection<Bill> bills = newClosing.getBills();
            bills.add(bill);
            newClosing.setBills(new ArrayList<>(bills));

            // set Closings bills and Probations
            try {
                if (bill.isPayedByAlex()) {
                    newClosing.setProbationAlex(bill.getPrice());
                    newClosing.setProbationCombined(bill.getPrice() / 2);
                } else if (bill.isPayedbySophie()) {
                    newClosing.setProbationSophie(bill.getPrice());
                    newClosing.setProbationCombined(bill.getPrice() / 2);
                }
                else {
                    throw new IllegalArgumentException("User " + bill.getUser() + " does not exist");
                }
            }
            catch (ParseException e) {
                this.logger().error(e.getMessage());
            }

            this.closingRepository.save(newClosing);
        }
        // Closing was already generated for current month
        else {
            // get current
            Closing closing = closings.get(0);

            // update with new Data
            Collection<Bill> bills = closing.getBills();
            bills.add(bill);
            closing.setBills(new ArrayList<>(bills));

            try {
                if (bill.isPayedByAlex()) {
                    closing.setProbationAlex(closing.getProbationAlex() + bill.getPrice());
                }
                else if (bill.isPayedbySophie()) {
                    closing.setProbationSophie(closing.getProbationSophie() + bill.getPrice());
                }
                else {
                    throw new IllegalArgumentException("User " + bill.getUser() + " does not exist");
                }
                Double newProbation = Math.abs((closing.getProbationAlex() - closing.getProbationSophie()) / 2);
                closing.setProbationCombined(newProbation);
            } catch (Exception e) {
                this.logger().error(e.getMessage());
            }

            this.closingRepository.save(closing);
        }
    }
}
