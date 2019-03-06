package com.kyoshisakuda.budget.account.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static com.kyoshisakuda.budget.account.transaction.TransactionTestSample.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    private static final String URI_TRANSACTIONS = "/accounts/1/transactions";
    private static final String URI_TRANSACTION = "/accounts/1/transactions/2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Test
    public void getTransactions_returnListOfTransactions() throws Exception {
        when(service.getTransactions(anyInt())).thenReturn(getListOfSampleTransactions());
        mockMvc.perform(get(URI_TRANSACTIONS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].description", is("Almuerzo")));
    }

    @Test
    public void getTransactions_whenEmpty_returnEmptyList() throws Exception {
        when(service.getTransactions(anyInt())).thenReturn(getEmptyListOfTransactions());
        mockMvc.perform(get(URI_TRANSACTIONS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getTransaction_returnTransaction() throws Exception {
        when(service.getTransaction(anyLong())).thenReturn(getSampleTransaction());
        mockMvc.perform(get(URI_TRANSACTION))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(getSampleTransaction().getDescription())))
                .andExpect(jsonPath("$.currency", is(getSampleTransaction().getCurrency().toString())));
    }

    @Test
    public void getTransaction_whenNotExist_return404() throws Exception {
        when(service.getTransaction(anyLong())).thenReturn(null);
        mockMvc.perform(get(URI_TRANSACTION))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTransaction_whenAccountNotExist_return404() throws Exception {
        when(service.getTransaction(anyLong())).thenReturn(null);
        mockMvc.perform(get(URI_TRANSACTION))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addTransaction_return201() throws Exception {
        when(service.addTransaction(Mockito.any(Transaction.class))).thenReturn(true);
        mockMvc.perform(post(URI_TRANSACTIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSampleTransactionAsJSONString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void addTransaction_whenErrorSaving_return500() throws Exception {
        when(service.addTransaction(Mockito.any(Transaction.class))).thenReturn(false);
        mockMvc.perform(post(URI_TRANSACTIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSampleTransactionAsJSONString()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateTransaction_return204() throws Exception {
        when(service.updateTransaction(anyLong(), Mockito.any(Transaction.class))).thenReturn(true);
        mockMvc.perform(put(URI_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSampleTransactionAsJSONString()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateTransaction_whenErrorSaving_return500() throws Exception {
        when(service.updateTransaction(anyLong(), Mockito.any(Transaction.class))).thenReturn(false);
        mockMvc.perform(put(URI_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSampleTransactionAsJSONString()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateTransaction_whenNotExist_return404() throws Exception {
        when(service.updateTransaction(anyLong(), Mockito.any(Transaction.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mockMvc.perform(put(URI_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSampleTransactionAsJSONString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTransaction_return204() throws Exception {
        when(service.deleteTransaction(anyLong())).thenReturn(true);
        mockMvc.perform(delete(URI_TRANSACTION))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTransaction_whenErrorDeleting_return500() throws Exception {
        when(service.deleteTransaction(anyLong())).thenReturn(false);
        mockMvc.perform(delete(URI_TRANSACTION))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteTransaction_whenNotExist_return204() throws Exception {
        when(service.deleteTransaction(anyLong())).thenReturn(true);
        mockMvc.perform(delete(URI_TRANSACTION))
                .andExpect(status().isNoContent());
    }
}