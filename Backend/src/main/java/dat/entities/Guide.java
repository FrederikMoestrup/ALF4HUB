package dat.entities;

import dat.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "guide")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guide_id", nullable = false, unique = true)
    private int id;

    @Setter
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Setter
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "phone", nullable = false, unique = true)
    private int phone;

    @Setter
    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;


    @OneToMany(mappedBy = "guide", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Trip> trips;

    public Guide(GuideDTO guideDTO){
        this.id = guideDTO.getId();
        this.firstname = guideDTO.getFirstname();
        this.lastname = guideDTO.getLastname();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
    }

    public Guide(String firstname, String lastname, String email, int phone, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setTrips(Set<Trip> trips) {
        if(trips != null) {
            this.trips = trips;
            for (Trip trip : trips) {
                trip.setGuide(this);
            }
        }
    }
    public void addTrip(Trip trip) {
        if ( trip != null) {
            this.trips.add(trip);
            trip.setGuide(this);
        }
    }
}
