/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrii
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        ATM atm = new ATM(1000);
        double expResult = 1000;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.001);
    }
    
    @Test(expected=NullPointerException.class)
    public void testIfTryToValidateNullCard()
    {
        ATM atm = new ATM();
        Card card = null;
        assertFalse(atm.validateCard(card, 1234));
        fail("test not valid");
    }
    
    @Test
    public void testValidateIfCardNotBlockedAndWrongPin() {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(Boolean.FALSE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.FALSE);
        boolean result = atm.validateCard(card, 1234);
        assertFalse(result);
    }
    
    @Test
    public void testValidateIfCardBlockedAndRightPin() {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(Boolean.TRUE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        boolean result = atm.validateCard(card, 1234);
        assertFalse(result);
    }
    
    @Test
    public void testValidateIfCardNotBlockedAndRightPin() {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(Boolean.FALSE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        boolean result = atm.validateCard(card, 1234);
        assertTrue(result);
    }
    
    @Test
    public void testValidateIfCardBlockedAndWrongPin() {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(Boolean.TRUE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.FALSE);
        boolean result = atm.validateCard(card, 1234);
        assertFalse(result);
    }
    
    @Test
    public void testCheckBalanceIfCardValid() throws NoCardInserted {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(acc.getBalance()).thenReturn(1000.0);
        when(card.isBlocked()).thenReturn(Boolean.FALSE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        when(card.getAccount()).thenReturn(acc);
        atm.validateCard(card, 1434);
        double expResult = 1000;
        double result = atm.checkBalance();
        assertEquals(expResult, result, 0.001);
    }
    
    @Test(expected=NoCardInserted.class)
    public void testCheckBalanceIfCardNotValid() throws NoCardInserted {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(acc.getBalance()).thenReturn(1000.0);
        when(card.isBlocked()).thenReturn(Boolean.TRUE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        when(card.getAccount()).thenReturn(acc);
        atm.validateCard(card, 1434);
        atm.checkBalance();
        fail("test not valid");
    }

    @Test
    public void testGetCashWhenEnoughMoneyInAccountAndATM() throws NotEnoughMoneyInATM, NotEnoughMoneyInAccount, NoCardInserted {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(Boolean.FALSE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        atm.validateCard(card, 1434);
        
        when(card.getAccount()).thenReturn(acc);
        when(acc.getBalance()).thenReturn(2000.0, 1500.0);
        when(acc.withdrow(500)).thenReturn(500.0);
        double result = atm.getCash(500);
        double expResult = 1500;
        assertEquals(expResult, result, 0.001);
    }
    
    @Test(expected=NoCardInserted.class)
    public void testGetCashWhenCardNotValid() throws NotEnoughMoneyInAccount,NotEnoughMoneyInATM,NoCardInserted {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(Boolean.TRUE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        atm.validateCard(card, 1434);
        atm.getCash(500);
        fail("test not valid");
    }
    
    @Test(expected=NotEnoughMoneyInATM.class)
    public void testGetCashWhenNotEnoughMoneyInATM() throws NotEnoughMoneyInAccount,NotEnoughMoneyInATM,NoCardInserted {
        ATM atm = new ATM(100);
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(Boolean.FALSE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        atm.validateCard(card, 1434);
        atm.getCash(500);
        fail("test not valid");
    }
    
    @Test(expected=NotEnoughMoneyInAccount.class)
    public void testGetCashWhenNotEnoughMoneyInAccount() throws NotEnoughMoneyInAccount,NotEnoughMoneyInATM,NoCardInserted {
        ATM atm = new ATM();
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(Boolean.FALSE);
        when(card.checkPin(anyInt())).thenReturn(Boolean.TRUE);
        atm.validateCard(card, 1434);
        
        when(card.getAccount()).thenReturn(acc);
        when(acc.getBalance()).thenReturn(100.0);
        when(acc.withdrow(500)).thenReturn(500.0);
        atm.getCash(500);
        fail("test not valid");
    }    
    

}
