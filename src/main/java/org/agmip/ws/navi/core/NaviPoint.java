package org.agmip.ws.navi.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.agmip.ace.util.GeoPoint;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaviPoint {
	private static final Logger LOG = LoggerFactory.getLogger(NaviPoint.class);
	private final String lat;
	private final String lng;
	private final String geohash;
	private final String countryISO;
	private final String adm0;
	private final String adm1;
	private final String adm2;
	private final String error;
	
	
	// Required by Jackson
	public NaviPoint() {
		this.lat        = null;
		this.lng        = null;
		this.geohash    = null;
		this.countryISO = null;
		this.adm0       = null;
		this.adm1       = null;
		this.adm2       = null;
		this.error      = null;
	}
	
	public NaviPoint(String lat, String lng, String geohash, String countryISO,
					 String adm0, String adm1, String adm2) {
		this.lat        = lat;
		this.lng        = lng;
		this.geohash    = geohash;
		this.countryISO = countryISO;
		this.adm0       = adm0;
		this.adm1       = adm1;
		this.adm2       = adm2;
		this.error      = null;
	}
	
	public NaviPoint(String lat, String lng, String countryISO,
			String adm0, String adm1, String adm2) {
		this.lat        = lat;
		this.lng        = lng;
		this.countryISO = countryISO;
		this.adm0       = adm0;
		this.adm1       = adm1;
		this.adm2       = adm2;
		this.error      = null;
		
		this.geohash = GeoPoint.calculateGeoHash(lat, lng);
	}
	
	public NaviPoint(String error) {
		this.lat        = null;
		this.lng        = null;
		this.geohash    = null;
		this.countryISO = null;
		this.adm0       = null;
		this.adm1       = null;
		this.adm2       = null;
		this.error      = error;
	}

	public String getLat() {
		return this.lat;
	}
	
	public String getLng() {
		return this.lng;
	}
	
	public String getGeohash() {
		return this.geohash;
	}
	
	public String getCountryISO() {
		return this.countryISO;
	}
	
	public String getAdm0() {
		return this.adm0;
	}
	
	public String getAdm1() {
		return this.adm1;
	}
	
	public String getAdm2() {
		return this.adm2;
	}
	
	public String getError() {
		return this.error;
	}
	
	public static String generateGISPoint(String lat, String lng) {
		return "POINT ("+lng+" "+lat+")";
	}
	
	public static class NaviPointMapper implements ResultSetMapper<NaviPoint> {

		@Override
		public NaviPoint map(int index, ResultSet rs, StatementContext ctx) {
			try {
				String gisPoint = ctx.getBinding().forName("gispoint").toString();
				String[] ll = gisPoint.substring(7, gisPoint.length()-1).split("\\s");
				LOG.info("Lat segment: {}", ll[0]);
				LOG.info("Lng segment: {}", ll[1]);
				return new NaviPoint(ll[1], ll[0], rs.getString("iso"), rs.getString("name_0"), rs.getString("name_1"),
					rs.getString("name_2"));
			} catch (SQLException ex) {
				return new NaviPoint(ex.getMessage());
			}
		}
	}
	
}
