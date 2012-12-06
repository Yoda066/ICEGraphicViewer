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
 * YamlParser converts an yaml ICE visual files into arrays of Vertices and widths.
 * @author student
 */
public class YamlParser {

    private Color currentColor;
    private float currentWidth;
    private ArrayList<Float> widths = new ArrayList<Float>();
    private ArrayList<Vertex> entity = new ArrayList<>();
    private String errorMessage = null;

    public ArrayList<Vertex> getEntity() {
        return entity;
    }

    public ArrayList<Float> getWidths() {
        return widths;
    }

    public String getError() {
        return errorMessage;
    }

    
    /* Try to parse yaml object into ICE visual component.
     * @param file File to convert.
     */
    public void loadShip(File file) {
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            Yaml yaml = new Yaml();
            Map yamlObject = (Map) yaml.load(input);
            if (!yamlObject.get("type").equals("lines")) {
                errorMessage = "type must be lines!";
                entity = null;
                return;
            }

            //This list contains all objects from yaml files. {colors, widths, vertices...}
            ArrayList<Object[]> list = (ArrayList<Object[]>) yamlObject.get("vertices");

            //Inicialization
            entity.clear();
            widths.clear();
            currentColor = (Color.white);
            currentWidth = 1;

            //Creating new entitny from yaml object o. (Required for JAVA casting)
            for (Object[] element : list) {

                switch ((String) element[0]) {

                    case "width": {
                        try {
                            currentWidth = Float.parseFloat(element[1].toString());
                        } catch (NumberFormatException e) {
                            entity = null;
                            errorMessage = "Inavalid parameter: " + (String) element[1];
                            return;
                        }
                        break;
                    }

                    case "color": {
                        String stringColor = (String) element[1];
                        int r, g, b, a = 0;
                        String subStr = "";
                        try {
                            if (stringColor.startsWith("rgba") && stringColor.length() == 12) {
                                subStr = stringColor.substring(4);
                                r = Integer.parseInt(subStr.substring(0, 2), 16);
                                g = Integer.parseInt(subStr.substring(2, 4), 16);
                                b = Integer.parseInt(subStr.substring(4, 6), 16);
                                a = Integer.parseInt(subStr.substring(6, 8), 16);
                                currentColor = new Color(r, g, b, a);
                            } else if (stringColor.startsWith("rgb") && stringColor.length() == 9) {
                                subStr = stringColor.substring(3);
                                r = Integer.parseInt(subStr.substring(0, 2), 16);
                                g = Integer.parseInt(subStr.substring(2, 4), 16);
                                b = Integer.parseInt(subStr.substring(4, 6), 16);
                                currentColor = new Color(r, g, b);
                            }
                        } catch (NumberFormatException e) {
                            errorMessage = "Invalid color: " + (String) element[1];
                            entity = null;
                            return;
                        }
                        break;
                    }

                    case "vertex": {
                        ArrayList<Double> coordinates = new ArrayList<Double>();
                        ArrayList<?> values = (ArrayList<?>) element[1];
                        if (values.size() != 2) {
                            entity = null;
                            errorMessage = "Vertex must contains two parametres!";
                            return;
                        }

                        for (int i = 0; i < values.size(); i++) {
                            if (values.get(i) instanceof Double) {
                                coordinates.add((Double) values.get(i));
                            } else if (values.get(i) instanceof Integer) {
                                Integer pom = (Integer) values.get(i);
                                coordinates.add((double) pom.intValue());
                            }
                            else {
                            entity = null;
                            errorMessage = "Parameter "+values.get(i) +" is not valid parameter for vertex.";
                            return;
                            }
                        }

                        entity.add(new Vertex(coordinates.get(0), coordinates.get(1), currentColor));
                        if (entity.size() % 2 == 0) {
                            widths.add(currentWidth);
                        }
                        break;
                    }

                    default: {
                        entity = null;
                        errorMessage = "Invalid command " + (String) element[0];
                        return;
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            entity = null;
            errorMessage = new String("File not found.");
        }
    }
}
