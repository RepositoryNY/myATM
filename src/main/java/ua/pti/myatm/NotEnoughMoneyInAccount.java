/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

/**
 *
 * @author Lenivez
 */
public class NotEnoughMoneyInAccount extends Exception {
    String errmsg;
    NotEnoughMoneyInAccount(String msg) {
        errmsg = msg;
    }
    void printErrMsg()
    {
        System.out.println(errmsg);
    }
}
