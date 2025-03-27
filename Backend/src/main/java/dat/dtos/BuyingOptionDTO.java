package dat.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuyingOptionDTO {
    private String shopName;
    private String shopUrl;
    private int price;
}