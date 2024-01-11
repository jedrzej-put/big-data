package com.example.bigdata;

public class InputStream {
    public static String[] getStaticFlightEventsStream(){
        return new String[]{
                "{\"aircraft\":\"Su-37\",\"airline\":\"OIAA\",\"airport\":\"EBKT\",\"flight\":\"WN4795\",\"clouds\":\"OVC\",\"wind_dir\":\"W\",\"wind_str\":75.21,\"ets\":\"2023-03-28 20:50:14.0\",\"its\":\"2023-03-28 20:50:17.0\"}",
                "{\"aircraft\":\"MS-21\",\"airline\":\"GANR\",\"airport\":\"EBKT\",\"flight\":\"WN4798\",\"clouds\":\"OVC\",\"wind_dir\":\"NE\",\"wind_str\":80.21,\"ets\":\"2023-03-28 20:50:15.0\",\"its\":\"2023-03-28 20:50:23.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"BIIS\",\"airport\":\"EBKT\",\"flight\":\"WN4799\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":90.21,\"ets\":\"2023-03-28 20:50:17.0\",\"its\":\"2023-03-28 20:55:17.0\"}",
                "{\"aircraft\":\"Yak-9\",\"airline\":\"GASK\",\"airport\":\"EBKT\",\"flight\":\"WN4789\",\"clouds\":\"OVC\",\"wind_dir\":\"S\",\"wind_str\":100.21,\"ets\":\"2023-03-28 20:50:18.0\",\"its\":\"2023-03-28 20:50:31.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GASK\",\"airport\":\"EBKT\",\"flight\":\"WN4790\",\"clouds\":\"OVC\",\"wind_dir\":\"SE\",\"wind_str\":40.21,\"ets\":\"2023-03-28 20:50:18.0\",\"its\":\"2023-03-28 21:30:18.0\"}",

                "{\"aircraft\":\"Su-37\",\"airline\":\"OIAA\",\"airport\":\"ZBAA\",\"flight\":\"WA5795\",\"clouds\":\"OVC\",\"wind_dir\":\"N\",\"wind_str\":75.18,\"ets\":\"2023-03-28 20:50:16.0\",\"its\":\"2023-03-28 20:50:44.0\"}",
                "{\"aircraft\":\"MS-21\",\"airline\":\"GANR\",\"airport\":\"ZBAA\",\"flight\":\"WA5798\",\"clouds\":\"OVC\",\"wind_dir\":\"S\",\"wind_str\":89.21,\"ets\":\"2023-03-28 20:50:18.0\",\"its\":\"2023-03-28 20:50:33.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"BIIS\",\"airport\":\"ZBAA\",\"flight\":\"WA5799\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":90.21,\"ets\":\"2023-03-28 20:20:17.0\",\"its\":\"2023-03-28 20:45:17.0\"}",
                "{\"aircraft\":\"Yak-9\",\"airline\":\"GASK\",\"airport\":\"ZBAA\",\"flight\":\"WA5789\",\"clouds\":\"OVC\",\"wind_dir\":\"W\",\"wind_str\":105.21,\"ets\":\"2023-03-28 20:50:39.0\",\"its\":\"2023-03-28 20:50:51.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"BIIS\",\"airport\":\"ZBAA\",\"flight\":\"WA5710\",\"clouds\":\"OVC\",\"wind_dir\":\"SW\",\"wind_str\":10.21,\"ets\":\"2023-03-28 20:20:17.0\",\"its\":\"2023-03-28 21:05:17.0\"}",

                "{\"aircraft\":\"Su-37\",\"airline\":\"OIAA\",\"airport\":\"OIAW\",\"flight\":\"WT7795\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":100.25,\"ets\":\"2023-03-28 20:50:36.0\",\"its\":\"2023-03-28 20:50:43.0\"}",
                "{\"aircraft\":\"MS-21\",\"airline\":\"GANR\",\"airport\":\"OIAW\",\"flight\":\"WT7798\",\"clouds\":\"OVC\",\"wind_dir\":\"N\",\"wind_str\":109.31,\"ets\":\"2023-03-28 20:50:38.0\",\"its\":\"2023-03-28 20:50:55.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"BIIS\",\"airport\":\"OIAW\",\"flight\":\"WT7799\",\"clouds\":\"OVC\",\"wind_dir\":\"E\",\"wind_str\":115.98,\"ets\":\"2023-03-28 20:30:17.0\",\"its\":\"2023-03-28 20:30:45.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GASK\",\"airport\":\"OIAW\",\"flight\":\"WT7789\",\"clouds\":\"BKN\",\"wind_dir\":\"NW\",\"wind_str\":300.11,\"ets\":\"2023-03-28 20:30:39.0\",\"its\":\"2023-03-28 20:30:57.0\"}",
                "{\"aircraft\":\"MS-21\",\"airline\":\"OIAB\",\"airport\":\"OIAW\",\"flight\":\"WT5710\",\"clouds\":\"OVC\",\"wind_dir\":\"S\",\"wind_str\":130.21,\"ets\":\"2023-03-28 20:30:55.0\",\"its\":\"2023-03-28 20:31:15.0\"}",
                "{\"aircraft\":\"MS-21\",\"airline\":\"CIIS\",\"airport\":\"OIAW\",\"flight\":\"WT1799\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":115.98,\"ets\":\"2023-03-28 20:30:17.0\",\"its\":\"2023-03-28 20:30:27.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"OIAB\",\"airport\":\"OIAW\",\"flight\":\"WT1789\",\"clouds\":\"OVC\",\"wind_dir\":\"SE\",\"wind_str\":150.11,\"ets\":\"2023-03-28 20:30:39.0\",\"its\":\"2023-03-28 20:36:39.0\"}",
                "{\"aircraft\":\"MS-21\",\"airline\":\"GANL\",\"airport\":\"OIAW\",\"flight\":\"WT1710\",\"clouds\":\"OVC\",\"wind_dir\":\"N\",\"wind_str\":130.21,\"ets\":\"2023-03-28 20:30:55.0\",\"its\":\"2023-03-28 20:30:56.0\"}",

                "{\"aircraft\":\"Su-37\",\"airline\":\"OIAA\",\"airport\":\"ENAL\",\"flight\":\"AB9795\",\"clouds\":\"OVC\",\"wind_dir\":\"S\",\"wind_str\":75.21,\"ets\":\"2023-03-28 20:50:46.0\",\"its\":\"2023-03-28 20:50:59.0\"}",
                "{\"aircraft\":\"MS-21\",\"airline\":\"GANR\",\"airport\":\"ENAL\",\"flight\":\"AB9798\",\"clouds\":\"OVC\",\"wind_dir\":\"E\",\"wind_str\":80.21,\"ets\":\"2023-03-28 20:50:48.0\",\"its\":\"2023-03-28 20:51:08.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"BIIS\",\"airport\":\"ENAL\",\"flight\":\"AB9799\",\"clouds\":\"OVC\",\"wind_dir\":\"W\",\"wind_str\":90.21,\"ets\":\"2023-03-28 20:20:47.0\",\"its\":\"2023-03-28 20:23:47.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GASK\",\"airport\":\"ENAL\",\"flight\":\"AB9789\",\"clouds\":\"OVC\",\"wind_dir\":\"NE\",\"wind_str\":100.21,\"ets\":\"2023-03-28 20:50:49.0\",\"its\":\"2023-03-28 20:50:51.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANR\",\"airport\":\"ENAL\",\"flight\":\"AB9889\",\"clouds\":\"OVC\",\"wind_dir\":\"SE\",\"wind_str\":90.21,\"ets\":\"2023-03-28 20:50:55.0\",\"its\":\"2023-03-28 20:50:59.0\"}",

                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANA\",\"airport\":\"OIAW\",\"flight\":\"PA3795\",\"clouds\":\"OVC\",\"wind_dir\":\"S\",\"wind_str\":55.21,\"ets\":\"2023-03-28 20:50:04.0\",\"its\":\"2023-03-28 20:50:14.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANB\",\"airport\":\"OIAW\",\"flight\":\"PA3798\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":45.21,\"ets\":\"2023-03-28 20:50:04.0\",\"its\":\"2023-03-28 20:50:24.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANC\",\"airport\":\"OIAW\",\"flight\":\"PA3799\",\"clouds\":\"OVC\",\"wind_dir\":\"NE\",\"wind_str\":30.21,\"ets\":\"2023-03-28 20:50:04.0\",\"its\":\"2023-03-28 20:50:34.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GAND\",\"airport\":\"OIAW\",\"flight\":\"PA3789\",\"clouds\":\"OVC\",\"wind_dir\":\"N\",\"wind_str\":27.21,\"ets\":\"2023-03-28 20:50:04.0\",\"its\":\"2023-03-28 20:50:59.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"RAMW\",\"airport\":\"OIAW\",\"flight\":\"PA3786\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":70.21,\"ets\":\"2023-03-28 20:50:04.0\",\"its\":\"2023-03-28 20:50:51.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"DAPW\",\"airport\":\"OIAW\",\"flight\":\"PA3787\",\"clouds\":\"OVC\",\"wind_dir\":\"NE\",\"wind_str\":101.21,\"ets\":\"2023-03-28 20:50:04.0\",\"its\":\"2023-03-28 20:50:33.0\"}",

                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANE\",\"airport\":\"OIAW\",\"flight\":\"UK2795\",\"clouds\":\"OVC\",\"wind_dir\":\"NE\",\"wind_str\":75.21,\"ets\":\"2023-03-28 20:50:21.0\",\"its\":\"2023-03-28 20:50:34.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANF\",\"airport\":\"OIAW\",\"flight\":\"UK2798\",\"clouds\":\"OVC\",\"wind_dir\":\"W\",\"wind_str\":45.21,\"ets\":\"2023-03-28 20:50:21.0\",\"its\":\"2023-03-28 20:50:55.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANG\",\"airport\":\"OIAW\",\"flight\":\"UK2798\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":30.21,\"ets\":\"2023-03-28 20:50:21.0\",\"its\":\"2023-03-28 20:50:44.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANG\",\"airport\":\"OIAW\",\"flight\":\"UK2797\",\"clouds\":\"OVC\",\"wind_dir\":\"S\",\"wind_str\":50.21,\"ets\":\"2023-03-28 20:50:21.0\",\"its\":\"2023-03-28 20:50:37.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANG\",\"airport\":\"OIAW\",\"flight\":\"UK2796\",\"clouds\":\"OVC\",\"wind_dir\":\"SE\",\"wind_str\":11.21,\"ets\":\"2023-03-28 20:50:21.0\",\"its\":\"2023-03-28 20:50:59.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANG\",\"airport\":\"OIAW\",\"flight\":\"UK2793\",\"clouds\":\"OVC\",\"wind_dir\":\"NW\",\"wind_str\":30.21,\"ets\":\"2023-03-28 20:50:21.0\",\"its\":\"2023-03-28 20:50:23.0\"}",
                "{\"aircraft\":\"Spitfire\",\"airline\":\"GANH\",\"airport\":\"OIAW\",\"flight\":\"UK2789\",\"clouds\":\"BKN\",\"wind_dir\":\"E\",\"wind_str\":207.21,\"ets\":\"2023-03-28 20:50:21.0\",\"its\":\"2023-03-28 20:50:22.0\"}",
        };
    }
}
