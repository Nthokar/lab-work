package org.example.app;

import org.example.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainMenu extends Menu {
    public static Configuration.Builder builder;
    public static MainMenu getMenu(Configuration.Builder builder) {
        MainMenu.builder = builder;
        var sections = new ArrayList<Section>();
        {
            sections.add(
                    new Section(
                            () -> "specify function",
                            null,
                            () -> Objects.nonNull(MainMenu.builder.getFStr()) ? MainMenu.builder.getFStr().toString() : "not specified",
                            1,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setFStr(str);
                                    },
                                    Objects.nonNull(MainMenu.builder.getFStr()) ? MainMenu.builder.getFStr() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "specify h",
                            () -> "flat step of iterative functions",
                            () -> Objects.nonNull(MainMenu.builder.getH()   ) ? MainMenu.builder.getH() .toString() : "not specified",
                            2,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setH(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getH()) ? builder.getH().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "specify epsilon",
                            () -> "(maximum permissible error)",
                            () -> Objects.nonNull(MainMenu.builder.getEps()) ? MainMenu.builder.getEps().toString() : "not specified",
                            3,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setEps(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getEps()) ? builder.getEps().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "specify number of iterations",
                            () -> "iterations of trying to find extreme by starting at random point",
                            () -> Objects.nonNull(MainMenu.builder.getIter()) ? MainMenu.builder.getIter().toString() : "not specified",
                            4,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setIter(Integer.parseInt((String) str));
                                    },
                                    Objects.nonNull(MainMenu.builder.getIter()) ? builder.getIter().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "specify minimum random value",
                            () -> "minimum start point",
                            () -> Objects.nonNull(MainMenu.builder.getMinRandom()) ? MainMenu.builder.getMinRandom().toString() : "not specified",
                            5,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setMinRandom(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(MainMenu.builder.getMinRandom()) ? MainMenu.builder.getMinRandom().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "specify maximum random value",
                            () -> "maximum start point",
                            () -> Objects.nonNull(MainMenu.builder.getMaxRandom()) ? MainMenu.builder.getMaxRandom().toString() : "not specified",
                            6,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setMaxRandom(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getMaxRandom()) ? builder.getMaxRandom().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "specify minimum stop value",
                            () -> "points less than this value are not considered",
                            () -> Objects.nonNull(MainMenu.builder.getMinStop()) ? MainMenu.builder.getMinStop().toString() : "not specified",
                            7,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setMinStop(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getMinStop()) ? builder.getMinStop().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "specify maximum stop value",
                            () -> "points greater than this value are not considered",
                            () -> Objects.nonNull(MainMenu.builder.getMaxStop()) ? MainMenu.builder.getMaxStop().toString() : "not specified",
                            8,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setMaxStop(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getMaxStop()) ? builder.getMaxStop().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            () -> "choose method",
                            9,
                            MethodChooseMenu.getMenu(builder.build())
                            ));
        }


        return new MainMenu(sections);
    }
    private MainMenu(List<Section> sections) {
        super(sections);
    }
}