package org.agmip.ws.navi.db;

import org.agmip.ws.navi.core.NaviPoint;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(NaviPoint.NaviPointMapper.class)
public interface NaviDAO {
	@SqlQuery("select iso, name_0, name_1, name_2 from gadm2 WHERE st_contains(gadm2.gadm2_geom, ST_GeometryFromText(:gispoint, 4326))")
	NaviPoint findByLatLng(@Bind("gispoint") String gisPoint);
}
