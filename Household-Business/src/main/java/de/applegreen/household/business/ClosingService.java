package de.applegreen.household.business;

import de.applegreen.household.model.Bill;
import de.applegreen.household.model.Closing;
import de.applegreen.household.model.util.ProbationDAO;
import de.applegreen.household.persistence.BillRepository;
import de.applegreen.household.persistence.ClosingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClosingService implements HasLogger {

    private BillRepository billRepository;

    private ClosingRepository closingRepository;

    @Autowired
    public ClosingService(BillRepository billRepository, ClosingRepository closingRepository) {
        this.billRepository = billRepository;
        this.closingRepository = closingRepository;
    }

    /**
     * Generates a database entry for a closing of the current Month
     */
    public void generateProbation() {
        try {
            this.billRepository.findAll().get(0);

            Closing closing = new Closing();

            int month = LocalDateTime.now().getMonth().getValue();

            List<Bill> bills_Alex = this.billRepository.findAllByUser("Alex", month);
            List<Bill> bills_Sophie = this.billRepository.findAllByUser("Sophie", month);
            List<Bill> all = new ArrayList<>();
            all.addAll(bills_Alex);
            all.addAll(bills_Sophie);

            Double sum_alex = 0.0;
            for (Bill bill : bills_Alex) {
                sum_alex += bill.getPrice();
            }

            Double sum_sophie = 0.0;
            for (Bill bill : bills_Sophie) {
                sum_sophie += bill.getPrice();
            }

            if (sum_alex > sum_sophie) {
                closing.setProbationAlex(sum_alex - sum_sophie);
                closing.setProbationSophie(0.00);
            }
            else {
                closing.setProbationSophie(sum_sophie - sum_alex);
                closing.setProbationAlex(0.00);
            }

            closing.setBills(all);
            closing.setProbationCombined(Math.max(closing.getProbationAlex(), closing.getProbationSophie()) / 2);
            this.closingRepository.saveAndFlush(closing);
        }
        catch (IndexOutOfBoundsException e) {
            this.logger().info("No Bill entrys yet. Go shopping!");
        } catch (ParseException e) {
            this.logger().error(e.getMessage());
        }

    }
}
