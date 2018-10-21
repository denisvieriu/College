package ro.ubb.inh.model;

import lombok.*;

import javax.persistence.Entity;

/**
 * Created by radu.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)

public class Battery extends Product{

    private boolean rechargeable;

    @Builder
    public Battery(String name, int price, boolean rechargeable) {
        super(null, name, price);
        this.rechargeable = rechargeable;
    }
}
