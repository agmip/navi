package org.agmip.ws.navi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.agmip.ace.util.GeoPoint;
import org.agmip.ws.navi.core.NaviLatLng;
import org.agmip.ws.navi.core.NaviPoint;
import org.agmip.ws.navi.db.NaviDAO;

@Path("/navi/1")
public class NaviResource {
    private final NaviDAO dao;

    public NaviResource(NaviDAO dao) {
        this.dao = dao;
    }

    @Path("/point")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public NaviPoint locatePoint(@QueryParam("lat") String lat,
            @QueryParam("lng") String lng) {
        if(lat == null || lng == null) {
            throw new WebApplicationException(errorResponse(Response.Status.BAD_REQUEST, "Missing latitude or longitude"));
        }
        if(GeoPoint.calculateGeoHash(lat, lng) == null) {
            throw new WebApplicationException(errorResponse(Response.Status.BAD_REQUEST, "Invalid latitude and longitude"));
        }
        NaviPoint point = dao.findByLatLng(NaviPoint.generateGISPoint(lat, lng));
        if (point == null) {
            throw new WebApplicationException(errorResponse(Response.Status.BAD_REQUEST, "Unable to find geolocation information about this point."));
        }
        return point;
    }

    @Path("/point")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public NaviPoint locatePointByJSON(@Valid NaviLatLng llPoint) {
        NaviPoint point = getNaviPoint(llPoint);
        if (point.getError() != null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(point).build());
        }
        return point;
    }

    @Path("/points")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public List<NaviPoint> locatePoints(@Valid List<NaviLatLng> llPoints) {
        List<NaviPoint> points = new ArrayList<>();
        for(NaviLatLng ll : llPoints) {
            points.add(getNaviPoint(ll));
        }
        return points;
    }

    private NaviPoint getNaviPoint(NaviLatLng ll) {
        if (GeoPoint.calculateGeoHash(ll.getLat(), ll.getLng()) == null) {
            return new NaviPoint("Invalid latitude or longitude. Lat: "+ll.getLat() +" Lng: "+ll.getLng());
        } else {
            NaviPoint point = dao.findByLatLng(NaviPoint.generateGISPoint(ll.getLat(), ll.getLng()));
            if (point == null) {
                return new NaviPoint("Unable to find geolocation information about this point. Lat: "+ll.getLat()+" Lng: "+ll.getLng());
            } else {
                return point;
            }
        }
    }

    private Response errorResponse(Response.Status status, String error) {
        return Response.status(status).entity("{\"error\": \""+error+"\"}").build();
    }
}
