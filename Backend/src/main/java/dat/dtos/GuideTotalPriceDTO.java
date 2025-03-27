package dat.dtos;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class GuideTotalPriceDTO {
    private int guideId;
    private double totalPrice;

    public GuideTotalPriceDTO(int guideId, double totalPrice) {
        this.guideId = guideId;
        this.totalPrice = totalPrice;
    }

    public int getGuideId() {
        return guideId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}