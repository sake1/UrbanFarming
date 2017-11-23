package com.coopmart.urbanfarming.urbanfarming.Method;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * Created by MOTI on 04/07/2017.
 */

public class MagicBox {

    private static final int INT_LENGTH = 32;
    private static final int NUMBER_OF_DAYS = 7;
    /**
     * Use these variables to detect wether a teacher is unavailable, available or already assigned to an appointment
     * Used to avoid a hard coded int to make a comparison
     */
    public static final int UNAVAILABLE = 0;
    public static final int AVAILABLE = 1;
    public static final int OCCUPIED = 2;
    /**
     * This field can be used outside the class to generate the JSONObject with a correct key
     * Call MagicBox.days[x]
     */
    public static final String[] days =
            new String[] {"Monday", "Tuesday", "Wednessday", "Thursday", "Friday", "Saturday", "Sunday"};

    /**
     * Use this method to generate string needed in JsonArray inside JsonObject appointment
     * when calling mehod MagicBox.abrakadabra. Example how to use this mehod can be seen at abrakadabra documentation
     * @param start time then the appintment started
     * @param end time when the appointment finished
     * @return String in a certain format needed to generate correct JsonObject to pass to method MagicBox.abrakadabra
     */
    public static String dateStartEndFormatter(Date start, Date end) {
        return "\"" + start.getHours() + "." + start.getMinutes() + " - " + end.getHours() + "." + end.getMinutes() + "\"";
    }

    private static int[] decodeIntCode(int code) {
        int[] output = new int[INT_LENGTH];
        for(int i = INT_LENGTH - 1; i >= 0; i--) {
            output[i] = (code & (1 << i)) != 0 ? 1 : 0;
        }
        return output;
    }

    private static boolean valid(int h1, int m1, int h2, int m2) {
        return m1 % 30 == 0 && m2 % 30 == 0 &&
                h1 * 60 + m1 >= 7 * 60 && h2 * 60 + m2 <= 23 * 60 &&
                h1 * 60 + m2 < h2 * 60 + m2;
    }

    private static int d(int h1, int m1, int h2, int m2) {
        return (h2 - h1) * 2 + (m2 - m1) / 30;
    }

    private static int l(int h1, int m1, int h2, int m2) {
        return ((h2 - 7) * 2 + m2 / 30) - d(h1, m1, h2, m2);
    }

    private static int[] decodeJson(JSONArray input) throws Exception {
        int output = 0;
        for(int i = 0; i < input.length(); i++) {
            Pattern p = Pattern.compile("(\\d{1,2})\\.(\\d{1,2})\\s\\-\\s(\\d{1,2})\\.(\\d{1,2})");
            Matcher m = p.matcher(input.optString(i));
            if (m.find()) {
                int h1 = Integer.parseInt(m.group(1));
                int m1 = Integer.parseInt(m.group(2));
                int h2 = Integer.parseInt(m.group(3));
                int m2 = Integer.parseInt(m.group(4));
                if(!valid(h1, m1, h2, m2)) {
                    throw new DataFormatException("Date passed in the argument must be in range of [07:00, 23:00] and have minute value of 0 or 30");
                }
                output |= (((int) Math.pow(2, d(h1, m1, h2, m2)) - 1) << l(h1, m1, h2, m2));
            }
        }
        return decodeIntCode(output);
    }

    private static int[] overlap(int[] schedule, int[] appointment) throws DataFormatException {
        int[] output = new int[schedule.length];
        for(int i = 0; i < schedule.length; i++) {
            if(appointment[i] == 1 && schedule[i] == 0) {
                throw new DataFormatException("Unmatched schedule appointment. Assigning an appointment to unavailable time");
            }
            output[i] = appointment[i] == 1 ? OCCUPIED : schedule[i] == 1 ? AVAILABLE : UNAVAILABLE;
        }
        return output;
    }

    /**
     * @param schedule -> MUST be an int[7] object each represent an int code for each day started from monday to sunday
     * @return JSONObject containing a key for each day,
     *         each key have a value of JSONArray containing int[32]
     *         with each of it's elements have value of either 0 or 1
     * @throws Exception
     *
     * How to call this method example:
     try {
        //Make your own method to generate the needed int[7]
        int[] scheduleFromServer = new int[]{1431655765, 1431655765, 1431655765, 1431655765, 1431655765, 1431655765, 1431655765};

        //Make your own method to generate the needed JSON containing appointment data
        //USE MagicBox.dateStartEndFormatter(Date, Date) to get a correct string format inside the JSON
        JSONObject appointmentFromServer = new JSONObject("{" + "\"" + MagicBox.days[0] +  "\":[" + MagicBox.dateStartEndFormatter(new Date(0,0,0,10,0,0), new Date(0,0,0,13,0,0)) + ", " + MagicBox.dateStartEndFormatter(new Date(0,0,0,17,30,0), new Date(0,0,0,20,0,0)) + "]"
                + "," + "\"" + MagicBox.days[1] +  "\":[" + MagicBox.dateStartEndFormatter(new Date(0,0,0,10,0,0), new Date(0,0,0,13,0,0)) + ", " + MagicBox.dateStartEndFormatter(new Date(0,0,0,17,30,0), new Date(0,0,0,20,0,0)) + "]"
                + "," + "\"" + MagicBox.days[2] +  "\":[" + MagicBox.dateStartEndFormatter(new Date(0,0,0,10,0,0), new Date(0,0,0,13,0,0)) + ", " + MagicBox.dateStartEndFormatter(new Date(0,0,0,17,30,0), new Date(0,0,0,20,0,0)) + "]"
                + "," + "\"" + MagicBox.days[3] +  "\":[" + MagicBox.dateStartEndFormatter(new Date(0,0,0,10,0,0), new Date(0,0,0,13,0,0)) + ", " + MagicBox.dateStartEndFormatter(new Date(0,0,0,17,30,0), new Date(0,0,0,20,0,0)) + "]"
                + "," + "\"" + MagicBox.days[4] +  "\":[" + MagicBox.dateStartEndFormatter(new Date(0,0,0,10,0,0), new Date(0,0,0,13,0,0)) + ", " + MagicBox.dateStartEndFormatter(new Date(0,0,0,17,30,0), new Date(0,0,0,20,0,0)) + "]"
                + "," + "\"" + MagicBox.days[5] +  "\":[" + MagicBox.dateStartEndFormatter(new Date(0,0,0,10,0,0), new Date(0,0,0,13,0,0)) + ", " + MagicBox.dateStartEndFormatter(new Date(0,0,0,17,30,0), new Date(0,0,0,20,0,0)) + "]"
                + "," + "\"" + MagicBox.days[6] +  "\":[" + MagicBox.dateStartEndFormatter(new Date(0,0,0,10,0,0), new Date(0,0,0,13,0,0)) + ", " + MagicBox.dateStartEndFormatter(new Date(0,0,0,17,30,0), new Date(0,0,0,20,0,0)) + "]}");

        JSONObject schedule = MagicBox.abrakadabra(scheduleFromServer, appointmentFromServer);
        Log.d("DEBUG", schedule.toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
     */
    public static JSONObject abrakadabra(int[] schedule, JSONObject appointment) throws Exception {
        if(schedule.length != NUMBER_OF_DAYS) {
            throw new DataFormatException("Passed parameter int[] must have exactly 7 elements inside");
        }
        JSONObject output = new JSONObject();
        for(int i = 0; i < schedule.length; i++) {
            if(!appointment.has(days[i])) {
                throw new DataFormatException("Passed JsonObject does not contain field \"" + days[i] + "\"");
            }
            output.put(days[i], new JSONArray(overlap(decodeIntCode(schedule[i]), decodeJson(appointment.optJSONArray(days[i])))));
        }
        return output;
    }

    private static int encode(int[] code) {
        int output = 0;
        for(int i = 0; i < code.length; i++) {
            output += code[i] << i;
        }
        return output;
    }

    /**
     * @param input -> JSONObject containing a key for each day of the week,
     *                 each key have a value of JSONArray containing int[32],
     *                 each element inside int[32] MUST be one of the value 0 or 1
     * @return an int[7] object each represent an int code for each day started from monday to sunday
     * @throws Exception
     *
     * How to call this method example:
     try {
        //Make your own method to generate the needed JSONOBject
        JSONObject dataFromClient = new JSONObject("{\"" + MagicBox.days[0] + "\":[1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0]," +
                "\"" + MagicBox.days[1] + "\":[1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0]," +
                "\"" + MagicBox.days[2] + "\":[1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0]," +
                "\"" + MagicBox.days[3] + "\":[1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0]," +
                "\"" + MagicBox.days[4] + "\":[1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0]," +
                "\"" + MagicBox.days[5] + "\":[1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0]," +
                "\"" + MagicBox.days[6] + "\":[1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0]}");
        int[] schedule = MagicBox.alakazam(dataFromClient);
        for(int i = 0; i < schedule.length; i++) {
            Log.d("DEBUG", schedule[i] + "");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
     */
    public static int[] alakazam(JSONObject input) throws Exception {
        for(String day : days) {
            if(!input.has(day)) {
                throw new DataFormatException("Passed JsonObject does not contain field \"" + day + "\"");
            }
        }
        int[] output = new int[NUMBER_OF_DAYS];
        for(int i = 0; i < NUMBER_OF_DAYS; i++) {
            JSONArray jsonArray = input.optJSONArray(days[i]);
            int[] code = new int[jsonArray.length()];
            for(int j = 0; j < code.length; j++) {
                if(jsonArray.optInt(j) != UNAVAILABLE && jsonArray.optInt(j) != AVAILABLE) {
                    throw new DataFormatException("Passed JsonArray contain unexpected value \"" + jsonArray.optInt(j) + "\"");
                }
                code[j] = jsonArray.optInt(j);
            }
            output[i] = encode(code);
        }
        return output;
    }
}
