package edu.neu.csye6200.parkingapp.service;

import edu.neu.csye6200.parkingapp.dto.RenterDTO;
import edu.neu.csye6200.parkingapp.model.Renter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.neu.csye6200.parkingapp.repository.RenterRepository;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class RenterService {

    @Autowired
    private RenterRepository renterRepository;

    public Optional<RenterDTO> getRenterById(Long id) {
        Optional<Renter> renter = renterRepository.findById(id);
        if (renter.isPresent()) {
            Renter r = renter.get();
            RenterDTO renterDTO = new RenterDTO(r.getId(), r.getFirstName(), r.getLastName(), r.getPassword(), r.getEmail(), r.getPhone());
            return Optional.of(renterDTO);
        }
        return Optional.empty();
    }

    public RenterDTO saveRenter(@Valid RenterDTO renterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            throw new RuntimeException("Validation failed: " + bindingResult.getAllErrors());
        }

        // Convert DTO to entity
        Renter renter = new Renter();
        renter.setFirstName(renterDTO.getFirstName());
        renter.setLastName(renterDTO.getLastName());
        renter.setPassword(renterDTO.getPassword());
        renter.setEmail(renterDTO.getEmail());
        renter.setPhone(renterDTO.getPhone());

        // Save to database
        Renter savedRenter = renterRepository.save(renter);

        // Return the saved entity as DTO
        return new RenterDTO(savedRenter.getId(), savedRenter.getFirstName(), savedRenter.getLastName(), savedRenter.getPassword(), savedRenter.getEmail(), savedRenter.getPhone());
    }
}
