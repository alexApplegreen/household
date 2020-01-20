package de.applegreen.household.web.controller;

import de.applegreen.household.model.Bill;
import de.applegreen.household.model.GroceryList;
import de.applegreen.household.persistence.BillRepository;
import de.applegreen.household.persistence.ClosingRepository;
import de.applegreen.household.persistence.GroceryListRepository;
import de.applegreen.household.web.util.HasLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/list")
public class ListController implements HasLogger {

    private GroceryListRepository groceryListRepository;


    private BillRepository billRepository;

    private ClosingRepository closingRepository;

    private GroceryList currentList;

    private void init() {
        try {
            this.currentList = this.groceryListRepository.findUndoneAndNewest().get(0);
        }
        catch (IndexOutOfBoundsException e) {
            this.logger().error("Keine aktuelle Einkaufsliste gefunden.");
            GroceryList newCurrentList = new GroceryList();
            newCurrentList.setCurrent(true);
            this.groceryListRepository.save(newCurrentList);
            this.currentList = newCurrentList;
        }
    }

    @Autowired
    public ListController(GroceryListRepository groceryListRepository,
                          BillRepository billRepository,
                          ClosingRepository closingRepository) {
        this.groceryListRepository = groceryListRepository;
        this.billRepository = billRepository;
        this.closingRepository = closingRepository;
    }

    @GetMapping
    public String showCurrent(Model model) {
        this.init();
        model.addAttribute("list", this.currentList.getProducts());
        return "List";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("newItem") String newItem) {
        logger().info("adding new Item: " + newItem);

        List<String> products = this.currentList.getProducts();
        products.add(newItem);
        this.currentList.setProducts(products);
        this.groceryListRepository.save(currentList);

        return "redirect:/list";
    }

    @PostMapping("/delete/{item}")
    public String deletitem(@PathVariable("item") String item) {
        logger().info("Deleting Item: " + item);

        List<String> products = this.currentList.getProducts();
        if (!products.contains(item)) {
            this.logger().error("Zu löschendes Item existiert nicht!");
            return "redirect:/list";
        }

        products.remove(item);
        this.currentList.setProducts(products);
        this.groceryListRepository.save(this.currentList);
        return "redirect:/list";
    }

    @PostMapping("/commit")
    public String commitList(@ModelAttribute("bill") Bill bill) {

        if (bill.getUser().toLowerCase().equals("alex")) {
            bill.setPayedByAlex(true);
            bill.setPayedbySophie(false);
            this.currentList.setCurrent(false);
            this.currentList.setDone(true);
        }
        else if (bill.getUser().toLowerCase().equals("sophie")) {
            bill.setPayedbySophie(true);
            bill.setPayedByAlex(false);
            this.currentList.setCurrent(false);
            this.currentList.setDone(true);
        }
        else {
            this.logger().error("Nutzer mit dem Namen: " + bill.getUser() + " nicht gefunden");
            return "redirect:/list";
        }
        this.logger().info("Bill was payed by: " + bill.getUser() + " about " + bill.getPrice());
        this.billRepository.save(bill);

        try {
            GroceryList nextList = this.groceryListRepository.findNext().get(0);
            nextList.setCurrent(true);
            this.groceryListRepository.saveAndFlush(nextList);
        }
        catch (IndexOutOfBoundsException e) {
            GroceryList nextList = new GroceryList();
            nextList.setCurrent(true);
            this.groceryListRepository.saveAndFlush(nextList);
        }
        return "redirect:/list";
    }

    @PostMapping("/save/{itemLater}")
    public String saveForLater(@PathVariable("itemLater") String itemLater) {
        this.deletitem(itemLater);
        try {
            GroceryList futureList = this.groceryListRepository.findNext().get(0);
            if (futureList.equals(this.currentList)) {
                GroceryList futurelist = new GroceryList();
                ArrayList<String> products = new ArrayList<>();
                products.add(itemLater);
                futurelist.setProducts(products);
                this.groceryListRepository.save(futurelist);
            }
            else {
                List<String> products = futureList.getProducts();
                products.add(itemLater);
                futureList.setProducts(products);
                this.groceryListRepository.save(futureList);
            }
        }
        catch (IndexOutOfBoundsException e) {
            GroceryList futureList = new GroceryList();
            ArrayList<String> products = new ArrayList<>();
            products.add(itemLater);
            futureList.setProducts(products);
            this.groceryListRepository.save(futureList);
        }
        finally {
            return "redirect:/list";
        }
    }
}
