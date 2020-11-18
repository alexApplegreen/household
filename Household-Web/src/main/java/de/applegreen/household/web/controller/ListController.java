package de.applegreen.household.web.controller;

import de.applegreen.household.business.AdviceAnnotations;
import de.applegreen.household.model.Bill;
import de.applegreen.household.model.Closing;
import de.applegreen.household.model.GroceryList;
import de.applegreen.household.model.ListElement;
import de.applegreen.household.persistence.BillRepository;
import de.applegreen.household.persistence.ClosingRepository;
import de.applegreen.household.persistence.GroceryListRepository;
import de.applegreen.household.persistence.ListElementRepository;
import de.applegreen.household.web.util.HasLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;


@Controller
@RequestMapping("/list")
// FIXME deleting the last item will result in not loading the correct subsequent shopping list
public class ListController implements HasLogger {

    private GroceryListRepository groceryListRepository;

    private BillRepository billRepository;

    private ClosingRepository closingRepository;

    private GroceryList currentList;

    private ListElementRepository listElementRepository;

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

    private String checkEmptyAndReload() {
        if (this.currentList.getProducts().size() == 0) {
            logger().info("List is Empty, fetching new list...");
            this.currentList.setDone(true);
            this.currentList.setCurrent(false);
            this.init();
        }
        return "redirect:/list";
    }

    @Autowired
    public ListController(GroceryListRepository groceryListRepository,
                          BillRepository billRepository,
                          ClosingRepository closingRepository,
                          ListElementRepository listElementRepository) {
        this.groceryListRepository = groceryListRepository;
        this.billRepository = billRepository;
        this.closingRepository = closingRepository;
        this.listElementRepository = listElementRepository;
    }

    /**
     * Find current chopping list and display
     * @param model
     * @return
     */
    @GetMapping
    public String showCurrent(Model model) {
        this.init();
        model.addAttribute("list", this.currentList.getProducts());
        return "List";
    }

    /**
     * Add item to shopping list
     * @param newItem
     * @return
     */
    @PostMapping("/add")
    public String addItem(@ModelAttribute("newItem") String newItem) {
        if (StringUtils.isEmpty(newItem)) {
            this.logger().error("Item Name darf nicht leer sein");
            return "redirect:/list";
        }
        logger().info("adding new Item: " + newItem);

        ListElement elem = new ListElement();
        elem.setElement(newItem);

        List<ListElement> products = this.currentList.getProducts();

        products.add(elem);
        this.currentList.setProducts(products);
        this.groceryListRepository.save(this.currentList);

        return "redirect:/list";
    }

    /**
     * Delete specified item by item ID
     * @param item
     * @return
     */
    // TODO URL characters are not escaped
    @PostMapping("/delete/{item}")
    public String deletitem(@PathVariable("item") long item) {
        logger().info("Deleting Item: " + item);

        ArrayList<ListElement> elements = new ArrayList<>();
        elements.addAll(this.currentList.getProducts());

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getId() == item) {
                elements.remove(i);
                this.currentList.setProducts(elements);

                this.listElementRepository.deleteById(item);

                // Check if there are items remaining and put new List in focus if necessary
                return this.checkEmptyAndReload();
            }
        }
        this.logger().error("Item " + item + " not found");
        return "redirect:/list";
    }

    /**
     * Save current list as new bill
     * @param bill
     * @return
     */
    @PostMapping("/commit")
    @AdviceAnnotations.BillCommit
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

        String priceString = bill.getPriceString().replaceAll(",", ".");
        try {
            bill.setPrice(Double.parseDouble(priceString));
        }
        catch (NumberFormatException e) {
            this.logger().error(e.getMessage());
            return "redirect:/list";
        }
        this.logger().info("Bill was payed by: " + bill.getUser() + " about " + bill.getPrice().toString());

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

    /**
     * Shift item onto next shopping List
     * @param itemLater
     * @return
     */
    @PostMapping("/save/{itemLater}")
    public String saveForLater(@PathVariable("itemLater") long itemLater) {
        this.logger().info("Saving Item " + itemLater);
        try {
            GroceryList futureList = this.groceryListRepository.findNext().get(0);
            // neue Liste existiert noch nicht
            if (futureList.equals(this.currentList)) {
                GroceryList futurelist = new GroceryList();
                ArrayList<ListElement> products = new ArrayList<>();

                ListElement newItem = new ListElement();
                try {
                    newItem.setElement(this.listElementRepository.findById(itemLater).get().getElement());
                    products.add(newItem);
                    futureList.setProducts(products);
                    this.groceryListRepository.save(futureList);
                }
                catch (NoSuchElementException e) {
                    this.logger().error("Element cannot be saved to next list");
                }
            }
            // Neue Liste existiert bereits
            else {
                List<ListElement> products = futureList.getProducts();

                ListElement newItem = new ListElement();
                try {
                    newItem.setElement(this.listElementRepository.findById(itemLater).get().getElement());
                    products.add(newItem);
                    futureList.setProducts(products);
                    this.groceryListRepository.save(futureList);
                }
                catch (NoSuchElementException e) {
                    this.logger().error("Element cannot be saved to next list");
                }
            }
        }
        // Es existiert noch garkeine Einkaufsliste
        catch (IndexOutOfBoundsException e) {
            GroceryList futureList = new GroceryList();
            ArrayList<ListElement> products = new ArrayList<>();
            ListElement newItem = new ListElement();
            try {
                newItem.setElement(this.listElementRepository.findById(itemLater).get().getElement());
                products.add(newItem);
                futureList.setProducts(products);
                this.groceryListRepository.save(futureList);
            }
            catch (NoSuchElementException ex) {
                this.logger().error("Element cannot be saved to next List");
            }
        }
        finally {
            this.deletitem(itemLater);
            return this.checkEmptyAndReload();
        }
    }
}
