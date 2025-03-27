package dat.entities;


import dat.dtos.TripDTO;
import dat.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false, unique = true)
    private int id;

    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Setter
    @Column(name = "price", nullable = false)
    private double price;

    @Setter
    @Column(name = "starttime", nullable = false)
    private LocalDate starttime;

    @Setter
    @Column(name = "endtime")
    private LocalDate endtime;

    @Setter
    @Column(name = "startposition", nullable = false)
    private String startposition;

    @Setter
    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    public Trip(TripDTO tripDTO){
        this.name = tripDTO.getName();
        this.category = tripDTO.getCategory();
        this.price = tripDTO.getPrice();
        this.starttime = tripDTO.getStarttime();
        this.endtime = tripDTO.getEndtime();
        this.startposition = tripDTO.getStartposition();
    }
    public Trip(String name, Category category, double price, LocalDate starttime, LocalDate endtime, String startposition) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
    }

}
