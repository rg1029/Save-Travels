package com.example.savetravels.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.savetravels.models.Expense;
import com.example.savetravels.services.ExpenseService;

@Controller
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping("/")
    public String home() {
        return "redirect:/expenses";
    }

    @GetMapping("/expenses")
    public String index(@ModelAttribute("expense") Expense expense, Model model) {
        List<Expense> expenses = expenseService.allExpenses();
        model.addAttribute("expenses", expenses);
        return "index.jsp";
    }

    @PostMapping("/expenses")
    public String index(@Valid @ModelAttribute("expense") Expense expense, BindingResult result, Model model) {
        if(result.hasErrors()) {
            List<Expense> expenses = expenseService.allExpenses();
            model.addAttribute("expenses", expenses);
            return "index.jsp";
        }else {
            expenseService.createExpense(expense);
            return "redirect:/expenses";
        }
    }

    @GetMapping("/edit/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Expense expense = expenseService.findExpense(id);
        model.addAttribute("expense", expense);
        return "edit.jsp";
    }

    @PutMapping("/edit/{id}")
    public String update(
            @PathVariable("id") Long id,
            Model model,
            @Valid @ModelAttribute("expense") Expense expense,
            BindingResult result) {
        if(result.hasErrors()) {
            return "redirect:/edit/{id}";
        }else {
            expenseService.updateExpense(expense);
            return "redirect:/";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String destroy(@PathVariable("id")Long id){
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }


}