package ru.ekbtreeshelp.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.core.entity.Tree;

import java.util.List;

@Repository
@Transactional
public interface TreeRepository extends JpaRepository<Tree, Long> {

    List<Tree> findAllByAuthorId(Long authorId);

    @Query(
            value = "SELECT * " +
                    "FROM trees AS tree " +
                    "WHERE " +
                    "ST_Within(\n" +
                    "    tree.geo_point," +
                    "    ST_MakeEnvelope(:bottomRightX, :bottomRightY, :topLeftX, :topLeftY, :srid)" +
                    ")",
            nativeQuery = true
    )
    List<Tree> findInRegion(Double topLeftX, Double topLeftY,
                            Double bottomRightX, Double bottomRightY, Integer srid);

    @Query(
            value = "SELECT cast(json(ST_Centroid(points)) as text) AS centre, " +
                    "       ST_NumGeometries(points) AS count " +
                    "FROM (" +
                    "    SELECT unnest(ST_ClusterWithin(geo_point, :clusterDistance)) AS points" +
                    "    FROM trees AS tree" +
                    "    WHERE" +
                    "    ST_Within(" +
                    "        tree.geo_point," +
                    "        ST_MakeEnvelope(:bottomRightX, :bottomRightY, :topLeftX, :topLeftY, :srid)" +
                    "    )" +
                    ") as cluster",
            nativeQuery = true
    )
    List<Object[]> findClustersInRegion(Double topLeftX, Double topLeftY,
                                              Double bottomRightX, Double bottomRightY,
                                              Double clusterDistance, Integer srid);
}
