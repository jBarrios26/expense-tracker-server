package com.jorgebarrios.expensetracker.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/dashboard")
@RequiredArgsConstructor
@RestController
public class DashboardController {

    @GetMapping("/last-transactions/{userId}")
    public ResponseEntity<String> getLastTransactions(
            @PathVariable(required = true) String userId
                                                     ) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/total-spent/{userId}")
    public ResponseEntity<String> getTotalSpent(
            @PathVariable String userId,
            @RequestParam(
                    name = "year",
                    value = "2022"
            ) int year
                                               ) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/total-spent-by-month/{userId}")
    public ResponseEntity<String> getTotalSpentByMonth(
            @PathVariable String userId,
            @RequestParam(
                    name = "month",
                    value = "1"
            ) int month,
            @RequestParam(
                    name = "year",
                    value = "2023"
            ) int years
                                                      ) {
        return ResponseEntity.ok("");
    }


    @GetMapping("/categories/{userId}")
    public ResponseEntity<String> getTopCategories(
            @PathVariable String userId
                                                  ) {
        return ResponseEntity.ok("");
    }

}
