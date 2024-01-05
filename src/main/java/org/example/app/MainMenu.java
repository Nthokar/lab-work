package org.example.app;

import org.example.Configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainMenu extends Menu {
    private static Configurations.Builder builder;
    public static MainMenu getMenu(Configurations.Builder builder) {
        MainMenu.builder = builder;
        var sections = new ArrayList<Section>();
        {
            sections.add(
                    new Section(
                            "specify function",
                            1,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setEps(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getF()) ? builder.getF().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            "specify h\n\t(flat step of iterative functions)",
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
                            "specify epsilon\n\t(maximum permissible error)",
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
                            "specify number of iterations\n\t(iterations of trying to find extreme by starting at random point)",
                            4,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setIter(Integer.parseInt((String) str));
                                    },
                                    Objects.nonNull(builder.getIter()) ? builder.getIter().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            "specify minimum random value\n\t(minimum start point)",
                            5,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setMinRandom(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getMinRandom()) ? builder.getMinRandom().toString() : "not specified"
                            )));
        }
        {
            sections.add(
                    new Section(
                            "specify maximum random value\n\t(maximum start point)",
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
                            "specify minimum stop value\n\t(points less than this value are not considered)",
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
                            "specify maximum stop value\n\t(points greater than this value are not considered)",
                            8,
                            new Input(
                                    (str) -> {
                                        MainMenu.builder.setMaxStop(Double.parseDouble((String) str));
                                    },
                                    Objects.nonNull(builder.getMaxStop()) ? builder.getMaxStop().toString() : "not specified"
                            )));
        }

        return new MainMenu(sections);
    }
    private MainMenu(List<Section> sections) {
        super(sections);
    }
}