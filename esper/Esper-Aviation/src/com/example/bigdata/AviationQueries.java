package com.example.bigdata;

public class AviationQueries {
    public static String basicQuery(){
        return  """
                @public @buseventtype create json schema
                FlightEvents(aircraft string, airline string, airport string, flight string, clouds string, wind_dir string, wind_str double, ets string, its string);

                create window cloudwindow#time_length_batch(1 min, 10) as FlightEvents;
                create window windswindow#time_length_batch(1 min, 10) as FlightEvents;

                on FlightEvents(clouds='BKN' or clouds='OVC') merge cloudwindow insert select *;
                on FlightEvents(wind_str > 140) merge windswindow insert select *;

                @name('answer')
                select c.airport, COUNT(*) as landing
                from cloudwindow c join windswindow w on c.flight = w.flight
                group by c.airport
                ;
                    """;
    }
}
