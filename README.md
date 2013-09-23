# NAVI API

## Introduction
The Navi API is a simple API used to lookup Administrative Level information
for a latitude/longitude based on information from the [Global Administrative
Level database](http://gadm.org/) v2. The AgMIP IT Team has loaded this
information into a PostGIS database for querying. Navi is the API for
interacting with that database.

## Requirements
* Java 1.7+
* PostGIS
* GADM v2 database

## Endpoints

### POST /point
**Content-Type:** application/json  
**Accept:** application/json  
**Body:** {"lat": "<lat as string>", "lng" : "<lng as string>"}  
**Returns:** {"lat": "<lat>", "lng": "<lng>", "geohash": "<geohash of lat/lng>"
  "iso\_country": "<3-character ISO-3166 Code>", "adm0" : "<administrative level
  0 name", "adm1" : "<administrative level 1 name>", "adm2" : "<administrative level
  2 name>", "error" : "<textual errors>"}

### POST /points
**Content-Type:** application/json  
**Accept:** application/json  
**Body:** [{"lat": "<lat as string>", "lng" : "<lng as string>"}, …]  
**Returns:** [{"lat": "<lat>", "lng": "<lng>", "geohash": "<geohash of lat/lng>"
  "iso\_country": "<3-character ISO-3166 Code>", "adm0" : "<administrative level
  0 name", "adm1" : "<administrative level 1 name>", "adm2" : "<administrative level
  2 name>", "error" : "<textual errors>"}, …]
