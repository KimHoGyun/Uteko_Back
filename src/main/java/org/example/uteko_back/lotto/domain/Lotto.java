package org.example.uteko_back.lotto.domain;

import java.util.*;
public class Lotto {
    private final List<Integer> numbers;
    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = new  ArrayList<>(numbers);
    }
    public void validate(List<Integer> numbers) {
        if(numbers.size() !=6 ){
            throw new IllegalArgumentException("[Error] 로또 번호는 6개여야 합니다");
        }
        Set <Integer> uniqueNumbers = new HashSet<>(numbers);
        if(uniqueNumbers.size()!=numbers.size()){
            throw  new IllegalArgumentException("[Error] 로또 번호는 중복 되면 안됩니다");
        }
        for(int number : numbers){
            if(number < 1 || number > 45){
                throw new IllegalArgumentException("[Error] 로또 번호는 1부터 45입니다.");
            }
        }
    }
    public int countMatch(Lotto other){
        return (int)other.numbers.stream()
                .filter(this.numbers::contains)
                .count();
    }
    public boolean contains(int number){
        return this.numbers.contains(number);
    }
}
