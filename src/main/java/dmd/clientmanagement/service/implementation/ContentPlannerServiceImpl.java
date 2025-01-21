package dmd.clientmanagement.service.implementation;

import dmd.clientmanagement.dto.ContentPlannerDto;
import dmd.clientmanagement.entity.ContentPlanner;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.mapper.ContentPlannerMapper;
import dmd.clientmanagement.repository.ContentPlannerRepository;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.service.ContentPlannerService;
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

    @Override
    public ContentPlannerDto createContentPlan(ContentPlannerDto contentPlannerDto) {
        User user = userRepository.findById(contentPlannerDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(contentPlannerDto.getUserId()));
        ContentPlanner contentPlanner = contentPlannerMapper.toEntity(contentPlannerDto, user);
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
    public void deleteContentPlan(Long id) {
        contentPlannerRepository.deleteById(id);
    }
}
