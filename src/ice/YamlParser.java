// Copyright David Horvath 2012.
// Distributed under the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)
package ice;

import java.awt.Color;
import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * YamlParser converts an yaml ICE visual files into arrays of Vertices and
 * widths.
 *
 * @author David Horvath
 */
public class YamlParser {

    private Color currentColor;
    private float currentWidth;
    private ArrayList<Float> widths = new ArrayList<>();
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

    /*
     * Try to parse yaml object into ICE visual component. @param file File to
     * convert.
     */
    public void loadEntity(File file) {
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            Yaml yaml = new Yaml();
            Map yamlObject = null;
            try {
                yamlObject = (Map) yaml.load(input);
            } catch (ClassCastException e) {
                errorMessage = "Please choose visual file!";
                entity = null;
                return;
            }

            if (!yamlObject.containsKey("type")) {
                errorMessage = "Please choose visual file!";
                entity = null;
                return;
            }

            if (!yamlObject.get("type").equals("lines")) {
                errorMessage = "type must be lines!";
                entity = null;
                return;
            }

            //This list contains all objects from yaml files. {colors, widths, vertices...}
            ArrayList<Object[]> list = (ArrayList<Object[]>) yamlObject.get("vertices");

            //Inicialization
            entity = new ArrayList<>();
            widths = new ArrayList<>();
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
                            } else {
                                entity = null;
                                errorMessage = "Parameter " + values.get(i) + " is not valid parameter for vertex.";
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

    /// Print an yaml document object of entity
    public void saveEntity(String path) {
        //data is completed data to be saved
        LinkedHashMap<Object, Object> data = new LinkedHashMap<>();
        data.put("type", "lines");

        //array of vertices, widths and colors
        ArrayList<Object[]> vertices = new ArrayList<>();
        for (int i = 0; i < widths.size(); i++) {
            if (currentWidth != widths.get(i)) {
                currentWidth = widths.get(i);
                vertices.add(new Object[]{"width", currentWidth});
            }
            if (currentColor != entity.get(i * 2).color) {
                currentColor = entity.get(i * 2).color;

                vertices.add(new Object[]{"color", colorToString(currentColor)});
            }
            vertices.add(new Object[]{"vertex", vertexPosition(entity.get(i * 2))});

            if (currentColor != entity.get(i * 2 + 1).color) {
                currentColor = entity.get(i * 2 + 1).color;
                vertices.add(new Object[]{"color", colorToString(currentColor)});
            }

            vertices.add(new Object[]{"vertex", vertexPosition(entity.get(i * 2 + 1))});
        }

        data.put("vertices", vertices);

        Yaml yaml = new Yaml();
        String output = yaml.dump(data);
        System.out.println(output);
    }

    private ArrayList<Double> vertexPosition(Vertex v) {
        ArrayList<Double> vertexPosition = new ArrayList<>(2);
        //to ensure first two values are not null
        vertexPosition.add(v.X);
        vertexPosition.add(v.Y);
        return vertexPosition;
    }

    private String colorToString(Color c) {
        String result = "rgba";
        result += Integer.toHexString(c.getRed());
        result += Integer.toHexString(c.getGreen());
        result += Integer.toHexString(c.getBlue());
        result += Integer.toHexString(c.getAlpha());
        result = result.toUpperCase();
        return result;
    }
}
