package dat.dtos;


import lombok.NoArgsConstructor;


@NoArgsConstructor
public class TripGuideResponseDTO {
    private TripDTO trip;
    private GuideDTO guide;

    public TripGuideResponseDTO(TripDTO trip, GuideDTO guide) {
        this.trip = trip;
        this.guide = guide;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public GuideDTO getGuide() {
        return guide;
    }
}