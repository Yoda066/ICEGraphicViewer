package ice;

import java.awt.Color;
import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author student
 */
public class YamlParser {

    private Color currentColor = (Color.white);
    private int currentWidth = 1;
    private ArrayList<Integer> widths = new ArrayList<Integer>();
    private ArrayList<Vertex> entity = new ArrayList<>();
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList<Vertex> getEntity() {
        return entity;
    }

    public ArrayList<Integer> getWidths() {
        return widths;
    }

    public void loadShip() throws FileNotFoundException {
        //OPENING YAML FILE
        InputStream input = new FileInputStream(file);
        Yaml yaml = new Yaml();
        Map o = (Map) yaml.load(input);
        ArrayList<Object[]> l = (ArrayList<Object[]>) o.get("vertices");

        //CREATING NEW ENTITY FROM OBJECT
        entity.clear();
        widths.clear();

        for (Object[] prvok : l) {
            if (prvok[1] instanceof ArrayList) {
                ArrayList<Double> coordinates = new ArrayList<Double>();
                ArrayList<?> pole = (ArrayList<?>) prvok[1];

                for (int i = 0; i < pole.size(); i++) {
                    if (pole.get(i) instanceof Double) {
                        coordinates.add((Double) pole.get(i));
                    }
                    if (pole.get(i) instanceof Integer) {
                        Integer pom = (Integer) pole.get(i);
                        coordinates.add((double) pom.intValue());
                    }
                }

                entity.add(new Vertex(coordinates.get(0), coordinates.get(1), currentColor));
                if (entity.size() % 2 == 0) {
                    widths.add(currentWidth);
                }
            }

            if (prvok[1] instanceof Integer) {
                currentWidth = (int) prvok[1];
            }

            if (prvok[1] instanceof String) {
                String c = (String) prvok[1];
                int r, g, b, a = 0;
                String pom = "";
                if (c.startsWith("rgba")) {
                    pom = c.substring(4);
                    a = Integer.parseInt(pom.substring(6, 8), 16);
                } else {
                    pom = c.substring(3);
                }
                r = Integer.parseInt(pom.substring(0, 2), 16);
                g = Integer.parseInt(pom.substring(2, 4), 16);
                b = Integer.parseInt(pom.substring(4, 6), 16);
                currentColor = new Color(r, g, b, a);
            }
        }
    }
}
