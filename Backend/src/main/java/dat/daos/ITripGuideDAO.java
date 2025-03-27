package dat.daos;

import dat.dtos.TripDTO;

import java.util.List;
import java.util.Set;

public interface ITripGuideDAO {
    //Her har jeg ændret fra void til TripDTO, da jeg gerne vil vise at guiden er blevet added
    TripDTO addGuideToTrip(int tripId, int guideId);
    List<TripDTO> getTripsByGuide(int guideId);

}
