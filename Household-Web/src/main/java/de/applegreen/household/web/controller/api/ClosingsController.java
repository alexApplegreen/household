package de.applegreen.household.web.controller.api;

import de.applegreen.household.business.HasLogger;
import de.applegreen.household.business.dto.ClosingDetailDTO;
import de.applegreen.household.business.dto.ClosingsDTO;
import de.applegreen.household.model.Closing;
import de.applegreen.household.persistence.ClosingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class ClosingsController implements HasLogger {

    private final ClosingRepository closingRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClosingsController(ClosingRepository closingRepository,
                              ModelMapper modelMapper) {
        this.closingRepository = closingRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves a list of all closings
     * @return List of closings if present, 204 else
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/closings")
    public ResponseEntity getClosings() {
        List<Closing> closings = this.closingRepository.findAll();
        if (closings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ClosingsDTO> res = new LinkedList<>();
        closings.forEach(item -> res.add(this.modelMapper.map(item, ClosingsDTO.class)));
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity showDetail(@PathVariable("id") Long id) {
        Optional<Closing> closing_opt = this.closingRepository.findById(id);
        if (closing_opt.isPresent()) {
            // ClosingDetailDTO closingDetailDTO = this.modelMapper.map()
        }
        return ResponseEntity.ok().build();
    }


}
