Get all stops in provided area from TransportBy  
`java -jar TransportByParser-1.0.0.jar scrape:stops top left bottom right`  
@param top latitude of the top border of scrapping area  
@param left longitude of the left border of scrapping area  
@param bottom latitude of the bottom border of scrapping area  
@param right longitude of the right border of scrapping area  
@return [stops.json](src/main/resources/transport_by/stops.json) file with all stops at provided area

Scrape all routes for provided stops from TransportBy  
`java -jar TransportByParser-1.0.0.jar scrape:routs pathToTransportStopsJson`  
@param [pathToTransportStopsJson](src/main/resources/transport_by/stops.json)  path to file with TransportBy stops, can
be obtained by `scrape:stops` command   
@return [routs.json](src/main/resources/transport_by/routs.json) file with all TransportBy routes for provided stops

Scrape stops for provided routes from TransportBy  
`java -jar TransportByParser-1.0.0.jar scrape:routs_stops pathToTransportByRoutesJson`  
@param [pathToTransportStopsJson](src/main/resources/transport_by/routs.json)  path to file with TransportBy routs, can
be obtained by `scrape:routs` command   
@return [routs_with_stops.json](src/main/resources/transport_by/routs_with_stops.json) file with TransportBy routes with
corresponded stops

Scrape schedule from TransportBy  
`java -jar TransportByParser-1.0.0.jar scrape:schedules pathToTransportByRoutesWithStopsJson`  
@param pathToTransportByRoutesWithStopsJson  [file](src/main/resources/transport_by/routs_with_stops.json) with
TransportBy routs and stops, can be obtained by `scrape:routs_stops` command   
@return [routs_with_stops_and_schedule.json](src/main/resources/transport_by/routs_with_stops_and_schedule.json) file
with full TransportBy schedule

Convert TransportBy data to GoTrans  
`java -jar TransportByParser-1.0.0.jar convert:gotrans pathToTransportByJson`  
@param [pathToTransportByJson](src/main/resources/transport_by/routs_with_stops_and_schedule.json)  path to file with
full TransportBy schedule, can be obtained by `scrape:schedules` command   
@return [go_trans.json](src/main/resources/gotrans/go_trans.json) file with data in GoTrans format

Release jar located in `dist/` folder

#### GTFS information

[Publicly-accessible public transportation data](https://www.transitwiki.org/TransitWiki/index.php/Publicly-accessible_public_transportation_data)  
[Best Practices for GTFS](http://gtfs.org/best-practices/)  
[GTFS documentation](https://developers.google.com/transit/gtfs/reference)  





