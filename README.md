# BAG 
Input dataset prepared following: http://www.geon.nl/nieuws/nieuws/item/3115-van-gis-naar-google

ogr2ogr -f KML -s_srs "+proj=sterea +lat_0=52.15616055555555 +lon_0=5.38763888888889 +k=0.999908 +x_0=155000 +y_0=463000 +ellps=bessel +units=m +towgs84=565.2369,50.0087,465.658,-0.406857330322398,0.350732676542563,-1.8703473836068,4.0812 +no_defs no_defs" -t_srs EPSG:3857 /home/jan/Downloads/bagexample001.kml /home/jan/Downloads/bagexample001.xml

File /home/jan/Downloads/bagexample001.xml retrieved from
http://geodata.nationaalgeoregister.nl/bagviewer/wfs?service=WFS&version=2.0.0&request=GetFeature&typename=bagviewer:ligplaats&count=100&startindex=0

Or with filter:
http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=bag:verblijfsobject&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:woonplaats%3C/PropertyName%3E%3CLiteral%3EAssendelft%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:postcode%3C/PropertyName%3E%3CLiteral%3E1566JG%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E

Or directly with file /home/jan/Downloads/bagexample001.kml retrieved from
http://geodata.nationaalgeoregister.nl/bag/wfs?service=WFS&version=2.0.0&request=GetFeature&typename=bag:pand&cql_filter=%28bag:bouwjaar=%271928%27%29&srsName=EPSG:3857&outputFormat=kml&count=500&startindex=0

Or directly with /home/jan/Downloads/pand.kml downloaded with
http://geodata.nationaalgeoregister.nl/bag/wfs?service=wfs&version=2.0.0&request=GetFeature&typeName=pand&filter=%3CFilter%3E%3CAnd%3E%3CPropertyIsEqualTo%3E%3CPropertyName%3Ebag:identificatie%3C/PropertyName%3E%3CLiteral%3E396100000067402%3C/Literal%3E%3C/PropertyIsEqualTo%3E%3C/And%3E%3C/Filter%3E&srsName=EPSG:3857&outputFormat=kml

Copyright © 2015 Jan Boon janboon438@gmail.com. Code released under the The MIT License 

