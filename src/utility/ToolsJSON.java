package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

public final class ToolsJSON {
    private static String slash = System.getProperty("file.separator");

    private ToolsJSON() {
    }

    /**
     * Array : [tutored avg min, tutored avg max, tutored abs min, tutored abs max,
     * tutor avg min, tutor avg max, tutor abs min, tutor abs max]
     * 
     * @return array
     */
    public static double[] readFilters() {
        File file = new File("." + slash + "res" + slash + "data" + slash + "filters.json");
        String stringJson = fileToString(file);
        double[] tab = new double[8];

        JSONObject json = new JSONObject(stringJson);
        tab[0] = json.getJSONObject("tutored").getJSONObject("average").getDouble("minAverage");
        tab[1] = json.getJSONObject("tutored").getJSONObject("average").getDouble("maxAverage");
        tab[2] = json.getJSONObject("tutored").getJSONObject("absences").getDouble("minAbsences");
        tab[3] = json.getJSONObject("tutored").getJSONObject("absences").getDouble("maxAbsences");

        tab[4] = json.getJSONObject("tutor").getJSONObject("average").getDouble("minAverage");
        tab[5] = json.getJSONObject("tutor").getJSONObject("average").getDouble("maxAverage");
        tab[6] = json.getJSONObject("tutor").getJSONObject("absences").getDouble("minAbsences");
        tab[7] = json.getJSONObject("tutor").getJSONObject("absences").getDouble("maxAbsences");

        return tab;
    }

    private static String fileToString(File file) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            do {
                line = br.readLine();
                sb.append(line);
            } while (line != null);

        } catch (IOException e) {
            System.err.println("I/O Exception" + e);
            e.printStackTrace();
            return "";
        }

        String toReturn = sb.toString();
        toReturn = toReturn.replaceAll(" ", "");
        toReturn = toReturn.substring(0, toReturn.length() - 4);
        return toReturn;
    }

    public static void main(String[] args) {
        for (double d : readFilters()) {
            System.out.println(d);
        }
    }
}
