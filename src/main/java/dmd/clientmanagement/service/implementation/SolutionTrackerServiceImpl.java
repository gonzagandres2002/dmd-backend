package dmd.clientmanagement.service.implementation;

import dmd.clientmanagement.dto.SolutionTrackerDto;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.SolutionTracker;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.mapper.SolutionTrackerMapper;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import dmd.clientmanagement.repository.SolutionTrackerRepository;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.service.SolutionTrackerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SolutionTrackerServiceImpl implements SolutionTrackerService {

    private final SolutionTrackerRepository solutionTrackerRepository;
    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final SolutionTrackerMapper solutionTrackerMapper;

    public List<SolutionTrackerDto> getSolutionsByUserId(Long userId) {
        List<SolutionTracker> solutions = solutionTrackerRepository.findByUserId(userId);
        return solutions.stream().map(solutionTrackerMapper::toDto).toList();
    }

    public SolutionTrackerDto updateSolution(Long solutionId, SolutionTrackerDto solutionTrackerDto) {
        // Fetch the existing solution by its ID
        SolutionTracker existingSolution = solutionTrackerRepository.findById(solutionId).orElseThrow(() -> new IllegalArgumentException("Solution not found"));

        // Update fields only if they are provided in the DTO
        if (solutionTrackerDto.getTitle() != null) {
            existingSolution.setTitle(solutionTrackerDto.getTitle());
        }

        if (solutionTrackerDto.getDescription() != null) {
            existingSolution.setDescription(solutionTrackerDto.getDescription());
        }

        if (solutionTrackerDto.getProgressPercentage() != null) {
            existingSolution.setProgressPercentage(solutionTrackerDto.getProgressPercentage());
        }

        if (solutionTrackerDto.getStartDate() != null) {
            existingSolution.setStartDate(solutionTrackerDto.getStartDate());
        }

        if (solutionTrackerDto.getEndDate() != null) {
            existingSolution.setEndDate(solutionTrackerDto.getEndDate());
        }

        SolutionTracker updatedSolution = solutionTrackerRepository.save(existingSolution);

        // Save the updated solution back to the repository
        return solutionTrackerMapper.toDto(updatedSolution);
    }


    public SolutionTrackerDto createSolution(SolutionTrackerDto solutionTrackerDto) {
        User user = userRepository.findById(solutionTrackerDto.getUserId()).orElseThrow(() -> new UserNotFoundException(solutionTrackerDto.getUserId()));

        ServiceType serviceType = serviceTypeRepository.findByName("software").orElseThrow(() -> new IllegalArgumentException("ServiceType not found"));

        if (!user.getServiceTypes().contains(serviceType)) {
            user.getServiceTypes().add(serviceType);
            userRepository.save(user); // Persist the relationship
        }

        SolutionTracker solutionTracker = solutionTrackerMapper.toEntity(solutionTrackerDto, user, serviceType);
        SolutionTracker savedSolution = solutionTrackerRepository.save(solutionTracker);
        return solutionTrackerMapper.toDto(savedSolution);
    }

    public void deleteSolution(Long solutionId) {
        solutionTrackerRepository.deleteById(solutionId);
    }
}
