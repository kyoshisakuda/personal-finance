package com.kyoshisakuda.budget.account.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kyoshisakuda.budget.Currency;
import com.kyoshisakuda.budget.account.AccountTestSample;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionTestSample {

    private static Transaction transaction = new Transaction((long) 1, "Almuerzo", 250.0d, Currency.PEN, AccountTestSample.getSampleAccount(), Category.GENERAL, LocalDate.now());
    private static List<Transaction> transactions = Stream.of(transaction,
                                                new Transaction("Cena", 45.7d, Currency.PEN, AccountTestSample.getSampleAccount().getId(), LocalDate.now()),
                                                new Transaction("Cafe", 3.2d, Currency.PEN, AccountTestSample.getSampleAccount().getId(), LocalDate.now()),
                                                new Transaction("taxi", 8.5d, Currency.PEN, AccountTestSample.getSampleAccount().getId(), LocalDate.now()),
                                                new Transaction("ropa", 70.0d, Currency.PEN, AccountTestSample.getSampleAccount().getId(), LocalDate.now()))
                                            .collect(Collectors.toList());

    public static Transaction getSampleTransaction() {
        return transaction;
    }

    public static List<Transaction> getListOfSampleTransactions() {
        return transactions;
    }

    public static List<Transaction> getEmptyListOfTransactions() {
        return new ArrayList<Transaction>(0);
    }

    public static String getSampleTransactionAsJSONString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        return transactionToJSON(transaction);
        //return writer.writeValueAsString(transaction);
    }

    public static String transactionToJSON(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
            .append("\"id\" : ").append(transaction.getId()).append(",")
            .append("\"description\" : ").append("\"").append(transaction.getDescription()).append("\",")
            .append("\"amount\" : ").append(transaction.getAmount()).append(",")
            .append("\"currency\" : ").append("\"").append(transaction.getCurrency().toString()).append("\",")
            .append("\"category\" : ").append("\"").append(transaction.getCategory().toString()).append("\",")
            .append("\"date\" : ").append("\"").append(transaction.getDate().toString()).append("\"")
        .append("}");
        return sb.toString();
    }

}
