import java.util.Scanner;
import java.util.Objects;

class Card{
    private String rank;
    private String suit;
    Card(String rank,String suit){
        this.rank=rank;
        this.suit=suit;
    }
    @Override
    public String toString(){
        return rank+" of "+suit;
    }
    @Override
    public boolean equals(Object obj){
        if(this==obj){ 
            return true;
        }        
        if(obj==null || getClass()!=obj.getClass()){ 
            return false;
        }
        Card c=(Card)obj;
        return rank.equals(c.rank) && suit.equals(c.suit);

    }
    @Override
    public int hashCode(){
        return Objects.hash(rank,suit); 
    }
}

public class Driver {
    public static void main(String [] args){
        int size=0;
        
        Card[] newcard=new Card[5];
        Card[] c={
            new Card("Ace","Spades"),
            new Card("King","Hearts"),
            new Card("Ace","Spades"),
            new Card("Queen","Clubs"),
            new Card("10","Diamonds")
        };
        for(Card card:c){
            boolean duplicates=false;
            for(int i=0;i<size;i++){
                if(card.equals(newcard[i])){
                    System.out.println("Duplicate found:"+card);
                    duplicates=true;
                    break;
                }
            }
            if(!duplicates){
                newcard[size]=card;
                size++;
            }
        }
    }         
}
