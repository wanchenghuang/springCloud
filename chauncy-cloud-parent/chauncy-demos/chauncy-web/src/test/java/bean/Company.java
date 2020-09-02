package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2020-08-26 23:33
 */
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class Company {

    private String name;

    private Employee employee;

    public Company(String name){
        this.name = name;
    }
}
