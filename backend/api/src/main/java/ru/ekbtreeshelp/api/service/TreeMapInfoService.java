package ru.ekbtreeshelp.api.service;

import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.api.config.GeoConfig;
import ru.ekbtreeshelp.api.dto.GeographicalPointDto;
import ru.ekbtreeshelp.api.dto.TreeClusterDto;
import ru.ekbtreeshelp.api.entity.Tree;
import ru.ekbtreeshelp.api.repository.TreeRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.amazonaws.util.json.Jackson.fromJsonString;

@Service
@RequiredArgsConstructor
public class TreeMapInfoService {

    private final TreeRepository treeRepository;
    private final GeoConfig geoConfig;

    public List<Tree> getInRegion(Point topLeft, Point bottomRight) {
        return treeRepository.findInRegion(
                topLeft.getX(), topLeft.getY(),
                bottomRight.getX(), bottomRight.getY(),
                geoConfig.getSrid()
        );
    }

    public List<TreeClusterDto> getClustersInRegion(Point topLeft, Point bottomRight) {
        return treeRepository.findClustersInRegion(
                topLeft.getX(), topLeft.getY(),
                bottomRight.getX(), bottomRight.getY(),
                geoConfig.getClusterDistance(), geoConfig.getSrid()
        ).stream().map(clusterInfo -> {
            Map<String, Object> pointInfo = (Map<String, Object>) fromJsonString((String) clusterInfo[0], Map.class);
            List<Double> coordinatesInfo = (List<Double>) pointInfo.get("coordinates");
            int count = (int) clusterInfo[1];
            return new TreeClusterDto(new GeographicalPointDto(coordinatesInfo.get(0), coordinatesInfo.get(1)), count);
        }).collect(Collectors.toList());
    }

    public List<Tree> getAllByAuthorId(Long authorId) {
        return treeRepository.findAllByAuthorId(authorId);
    }
}
