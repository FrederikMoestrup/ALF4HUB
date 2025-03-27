package dat.dtos;


import dat.entities.Trip;
import dat.enums.Category;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {

    private int id;
    private String name;
    private Category category;
    private double price;
    private LocalDate starttime;
    private LocalDate endtime;
    private String startposition;

    public TripDTO(Trip trip){
        this.id = trip.getId();
        this.name = trip.getName();
        this.category = trip.getCategory();
        this.price = trip.getPrice();
        this.starttime = trip.getStarttime();
        this.endtime = trip.getEndtime();
        this.startposition = trip.getStartposition();
    }

    public TripDTO(String name, Category category, double price, LocalDate starttime, LocalDate endtime, String startposition){
        this.name = name;
        this.category = category;
        this.price = price;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
    }

}
