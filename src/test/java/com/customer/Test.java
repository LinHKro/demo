package com.customer;

import com.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

public class Test {
    @Autowired
    CustomerRepository customerRepository;
    Comparator<String> comparator = (String d1, String d2) -> {
        if (d1.equals(d2)) {
            return 0;
        }
        if (Integer.parseInt(d1) > Integer.parseInt(d2)) {
            return 1;
        } else {
            return -1;
        }
    };


    public String testLamda(Function<String, String> fuction) {
        return fuction.apply("hello world!!");
    }

    public static void main(String[] args) {
        Test t = new Test();

        String str = "hello world ! str";
        System.out.println(str);
        BiConsumer<String, String> stringStringBiConsumer = (String d1, String d2) -> {
            System.out.println(d1 + d2);
        };
        Consumer dispatcher = str::equals;
//        Function<String,List<Customer>> findByCardId = t.customerRepository::findByCardId;
//        findByCardId.apply("100001");
        BiPredicate<String, Object> stringObjectBooleanBiFunction = String::equals;
        System.out.println(stringObjectBooleanBiFunction.test("a", "a"));
        Consumer<String> stringConsumer = System.out::println;
        System.out.println(stringConsumer);
        DoubleSupplier doubleSupplier = () -> 42;

        System.out.println(t.testLamda((String string) -> string + " return string"));

        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        stringList.forEach(System.out::println);
//        stringList.forEach(t.customerRepository::findByCardId);

        Stream<String> stream = stringList.stream().map(key -> key);
        stream.forEach(System.out::print);

        Map<String,Integer> map = new HashMap<>();
        map.put("1",1);
        map.put("2",2);
        map.put("3",31);
        map.put("4",41);

        map.putIfAbsent("5",5);
        map.computeIfAbsent("5", Integer::parseInt);

        map.forEach((key,value) -> System.out.println(key +" : "+value));

        map.forEach((key,value) -> {
            String st = "第"+key+"行"+key+"--"+value;
            System.out.println(st);
        });
    }
}
