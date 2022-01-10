package ru.ekbtreeshelp.api.tree.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgis.Point;
import org.springframework.data.domain.PageRequest;
import ru.ekbtreeshelp.api.config.GeoConfig;
import ru.ekbtreeshelp.core.repository.TreeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TreeMapInfoServiceTest {

    @InjectMocks
    TreeMapInfoService treeMapInfoService;

    @Mock
    private TreeRepository treeRepository;
    @Mock
    private GeoConfig geoConfig;

    @Test
    void getInRegion() {
        Point topLeft = new Point();
        Point bottomRight = new Point();
        Integer srid = 1;

        when(geoConfig.getSrid()).thenReturn(srid);

        treeMapInfoService.getInRegion(topLeft, bottomRight);

        verify(treeRepository, times(1))
                .findInRegion(0.0, 0.0, 0.0, 0.0, srid);
    }

    @Test
    void getClustersInRegion() {
        Point topLeft = new Point();
        Point bottomRight = new Point();
        Integer srid = 1;
        Double clusterDistance = 1.0;

        when(geoConfig.getClusterDistance()).thenReturn(clusterDistance);
        when(geoConfig.getSrid()).thenReturn(srid);

        treeMapInfoService.getClustersInRegion(topLeft, bottomRight);

        verify(treeRepository, times(1))
                .findClustersInRegion(0.0, 0.0, 0.0, 0.0, clusterDistance, srid);
    }

    @Test
    void getAllByAuthorId() {
        treeMapInfoService.getAllByAuthorId(1L,1, 1);
        verify(treeRepository, times(1))
                .findAllByAuthorId(1L, PageRequest.of(1, 1));
    }
}