package dmd.clientmanagement.service.implementation;

import dmd.clientmanagement.dto.ContentPlannerDto;
import dmd.clientmanagement.entity.ContentPlanner;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.mapper.ContentPlannerMapper;
import dmd.clientmanagement.repository.ContentPlannerRepository;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.service.ContentPlannerService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentPlannerServiceImpl implements ContentPlannerService {
    private final ContentPlannerRepository contentPlannerRepository;
    private final UserRepository userRepository;
    private final ContentPlannerMapper contentPlannerMapper;
    private final ServiceTypeRepository serviceTypeRepository;


    @Override
    public ContentPlannerDto createContentPlan(ContentPlannerDto contentPlannerDto) {
        User user = userRepository.findById(contentPlannerDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(contentPlannerDto.getUserId()));

        ServiceType serviceType = serviceTypeRepository.findByName("marketing")
                .orElseThrow(() -> new IllegalArgumentException("ServiceType not found"));

        if (!user.getServiceTypes().contains(serviceType)) {
            user.getServiceTypes().add(serviceType);
            userRepository.save(user); // Persist the relationship
        }

        ContentPlanner contentPlanner = contentPlannerMapper.toEntity(contentPlannerDto, user, serviceType);
        ContentPlanner savedContentPlanner = contentPlannerRepository.save(contentPlanner);
        return contentPlannerMapper.toDto(savedContentPlanner);
    }

    @Override
    public List<ContentPlannerDto> getContentPlansByUserId(Long userId) {
        List<ContentPlanner> contentPlanners = contentPlannerRepository.findByUserId(userId);
        return contentPlanners.stream()
                .map(contentPlannerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContentPlannerDto updateContentPlan(Long plannerId, ContentPlannerDto contentPlannerDto) {
        ContentPlanner existingContentPlan = contentPlannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException("Content Plan not found"));

        if (contentPlannerDto.getTitle() != null) {
            existingContentPlan.setTitle(contentPlannerDto.getTitle());
        }

        if (contentPlannerDto.getDescription() != null) {
            existingContentPlan.setDescription(contentPlannerDto.getDescription());
        }

        if (contentPlannerDto.getName() != null) {
            existingContentPlan.setName(contentPlannerDto.getName());
        }

        if (contentPlannerDto.getPlatform() != null) {
            existingContentPlan.setPlatform(contentPlannerDto.getPlatform());
        }

        if (contentPlannerDto.getScheduledDate() != null) {
            existingContentPlan.setScheduledDate(contentPlannerDto.getScheduledDate());
        }

        ContentPlanner updatedContentPlan = contentPlannerRepository.save(existingContentPlan);
        return contentPlannerMapper.toDto(updatedContentPlan);
    }

    @Override
    public void deleteContentPlan(Long id) {
        contentPlannerRepository.deleteById(id);
    }
}
