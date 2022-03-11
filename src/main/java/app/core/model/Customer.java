package app.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String city;
    @NonNull
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<Order> orders;

    public void addOrder(Order order) {
        if(orders == null) {
             orders = new ArrayList<Order>();
        }
        order.setCustomer(this);
        orders.add(order);

    }




}
