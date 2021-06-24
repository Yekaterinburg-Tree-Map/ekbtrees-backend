package ru.ekbtreeshelp.api.converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ekbtreeshelp.api.config.GeoConfig;
import ru.ekbtreeshelp.api.dto.GeographicalPointDto;

@Mapper(componentModel = "spring")
public abstract class PointConverter {

    @Autowired
    protected GeoConfig geoConfig;

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    public Point fromDto(GeographicalPointDto geographicalPoint) {
        Point point = GEOMETRY_FACTORY.createPoint(
                new Coordinate(geographicalPoint.getLatitude(), geographicalPoint.getLongitude())
        );
        point.setSRID(geoConfig.getSrid());
        return point;
    }

    public GeographicalPointDto toDto(Point geographicalPoint) {
        return new GeographicalPointDto(geographicalPoint.getX(), geographicalPoint.getY());
    }
}
