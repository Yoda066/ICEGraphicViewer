// Copyright David Horvath 2012.
// Distributed under the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)
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

    private Color currentColor;
    private float currentWidth;
    private ArrayList<Float> widths = new ArrayList<Float>();
    private ArrayList<Vertex> entity = new ArrayList<>();
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList<Vertex> getEntity() {
        return entity;
    }

    public ArrayList<Float> getWidths() {
        return widths;
    }

    public void loadShip() throws FileNotFoundException {

        //Opening yaml file
        InputStream input = new FileInputStream(file);
        Yaml yaml = new Yaml();
        Map o = (Map) yaml.load(input);
        ArrayList<Object[]> e = (ArrayList<Object[]>) o.get("vertices");

        //Inicialization
        entity.clear();
        widths.clear();
        currentColor = (Color.white);
        currentWidth = 1;

        //Creating new entitny from yaml object o        
        for (Object[] element : e) {

            if (element[1] instanceof ArrayList) {
                ArrayList<Double> coordinates = new ArrayList<Double>();
                ArrayList<?> pole = (ArrayList<?>) element[1];

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

            if (element[1] instanceof Double) {
                currentWidth = Float.parseFloat(element[1].toString());
            }

            if (element[1] instanceof String) {
                String c = (String) element[1];
                int r, g, b, a = 0;
                String pom = "";
                if (c.startsWith("rgba")) {
                    pom = c.substring(4);
                    r = Integer.parseInt(pom.substring(0, 2), 16);
                    g = Integer.parseInt(pom.substring(2, 4), 16);
                    b = Integer.parseInt(pom.substring(4, 6), 16);
                    a = Integer.parseInt(pom.substring(6, 8), 16);
                    currentColor = new Color(r, g, b, a);
                } else {
                    pom = c.substring(3);
                    r = Integer.parseInt(pom.substring(0, 2), 16);
                    g = Integer.parseInt(pom.substring(2, 4), 16);
                    b = Integer.parseInt(pom.substring(4, 6), 16);
                    currentColor = new Color(r, g, b);
                }
            }
        }
    }
}
