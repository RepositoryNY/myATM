package ua.pti.myatm;

public class ATM {
    double moneyInATM;
    Card cardInside;
    int NumOfTries;
    Card cardBefore;
    
    ATM()
    {
        this(1000);
    }
    ATM(double moneyInATM){
        this.moneyInATM = moneyInATM;
        cardInside = null;
        cardBefore = null;
        NumOfTries = 0;
    }

    // Возвращает количество денег в банкомате
    public double getMoneyInATM() {
         return moneyInATM;
    }
    public Card getCardBefore()
    {
        return cardBefore;
    }
    
    public boolean validateCard(Card card, int pinCode) throws NullPointerException
    {
        try
        {
            if (card.isBlocked())
            {
                cardBefore = card;
                return false;     
            }
            if(card.checkPin(pinCode))
            {
                cardInside = card;
                cardBefore = card;
                return true;
            }
            else
            {
                if(cardBefore == null)
                {
                    cardBefore = card;
                    NumOfTries = 1;
                    return false;
                }
                if(NumOfTries >= 3)
                {
                    cardBefore = card;
                    return false;
                }
                if(NumOfTries < 3)
                {
                    NumOfTries++;
                    cardBefore = card;
                    return false;
                }
            }
            return false;
        }
        finally
        {
            
        }
    }
    
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
//    public boolean validateCard(Card card, int pinCode) throws NullPointerException{
//        try
//        {
//            if (!card.isBlocked()) {
//                if (card.checkPin(pinCode)) {
//                    cardInside = card;
//                    cardBefore = card;
//                    return true;
//                } 
//                else 
//                {
//                    if (cardBefore == null)
//                    {
//                        NumOfTries = 1;
//                    }
//                    else if (card.equals(cardBefore)) 
//                    {
//                        if (NumOfTries >= 3) 
//                        {
//                            card.block();
//                        } else 
//                        {
//                            NumOfTries++;
//                        }
//                    } 
//                    else 
//                    {
//                        NumOfTries = 0;
//                    }
//                }
//            }
//            cardBefore = card;
//            return false;
//        }
//            if (!card.isBlocked() && card.checkPin(pinCode))
//            {
//                if (NumOfTries < 3)
//                {
//                    cardInside = card;
//                    isCardValid = true;
//                }
//                else
//                {
//                    card.block();
//                }
//            }
//            return isCardValid;
        /*catch (NullPointerException e)
        {
            System.err.println("You try to validating without a card");
        }*/
//        finally
//        {
//            
//        }
//    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance() throws NoCardInserted {
        double balance;
        try 
        {
            if (cardInside == null) throw new NoCardInserted("Trying to check balance while card is not valid");
            balance =  cardInside.getAccount().getBalance();
            return balance;
        }
        /*catch (NoCardInserted e)
        {
            e.printErrMsg();
        }
        catch (NullPointerException e)
        {
            System.err.println("You have no card in ATM while trying to check balance");
        }*/
        finally
        {
            
        }

    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount) throws NotEnoughMoneyInATM, NotEnoughMoneyInAccount, NoCardInserted{
        try
        {
            if (cardInside != null)
            {
                if(moneyInATM >= amount)
                {
                    if(cardInside.getAccount().getBalance() >= amount)
                    {
                        moneyInATM -= cardInside.getAccount().withdrow(amount);
                        return cardInside.getAccount().getBalance();
                    }
                    else
                    {
                        throw new NotEnoughMoneyInAccount("Not enough money in account"); 
                    }
                }
                else
                {
                    throw new NotEnoughMoneyInATM("Not enough money in ATM");
                }
            }
            else
            {
                throw new NoCardInserted("Trying to get cash with non valid card");
            }
        }
        finally
        {
            
        }
    }
}

