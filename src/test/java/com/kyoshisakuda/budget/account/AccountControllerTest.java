package com.kyoshisakuda.budget.account;

import com.kyoshisakuda.budget.Currency;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import static com.kyoshisakuda.budget.account.AccountTestSample.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    private static final String URI_BASE = "/accounts";
    private static final String URI_PARAM_1 = "/accounts/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    @Test
    public void getAllAccounts_returnListOfAccounts() throws Exception {
        Mockito.when(service.getAccounts()).thenReturn(getListOfSampleAccounts());

        mockMvc.perform(MockMvcRequestBuilders.get(URI_BASE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name", is("AFP")));
    }

    @Test
    public void getAllAccounts_whenEmpty_returnEmptyList() throws Exception{
        Mockito.when(service.getAccounts()).thenReturn(getListOfSampleAccounts(true));

        mockMvc.perform(MockMvcRequestBuilders.get(URI_BASE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAccount_returnSpecificAccount() throws Exception {
        Mockito.when(service.getAccount(1)).thenReturn(getSampleAccount());

        mockMvc.perform(MockMvcRequestBuilders.get(URI_PARAM_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("AFP")))
                .andExpect(jsonPath("$.currency", is(Currency.PEN.toString())));
    }

    @Test
    public void getAccount_whenNotExist_return404() throws Exception {
        Mockito.when(service.getAccount(1)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(URI_PARAM_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addAccount_return201() throws Exception {
        Mockito.when(service.addAccount(Mockito.any(Account.class))).thenReturn(true);
        
        mockMvc.perform(MockMvcRequestBuilders.post(URI_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getAccountAsJSONString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void addAccount_whenErrorSaving_return500() throws Exception {
        Mockito.when(service.addAccount(getSampleAccount())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post(URI_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getAccountAsJSONString()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateAccount_return204() throws Exception {
        Mockito.when(service.updateAccount(Mockito.anyInt(), Mockito.any(Account.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(URI_PARAM_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getAccountAsJSONString()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateAccount_whenErrorSaving_return500() throws Exception {
        Mockito.when(service.updateAccount(getSampleAccount().getId(), getSampleAccount())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put(URI_PARAM_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getAccountAsJSONString()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateAccount_whenIdNotExist_return400() throws Exception {
        Mockito.when(service.updateAccount(Mockito.anyInt(), Mockito.any(Account.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.put(URI_PARAM_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getAccountAsJSONString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAccount_return404() throws Exception {
        Mockito.when(service.deleteAccount(getSampleAccount().getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete(URI_PARAM_1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAccount_whenErrorDeleting_return500() throws Exception {
        Mockito.when(service.deleteAccount(getSampleAccount().getId())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete(URI_PARAM_1))
                .andExpect(status().isInternalServerError());
    }
}