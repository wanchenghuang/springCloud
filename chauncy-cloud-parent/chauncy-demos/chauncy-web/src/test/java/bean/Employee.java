package bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author cheng
 * @create 2020-08-24 22:54
 */
@AllArgsConstructor
@Data
public class Employee {

    private String name;
    private int age;
    private double salary;
}
