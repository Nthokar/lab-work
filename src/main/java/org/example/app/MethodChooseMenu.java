package org.example.app;

import org.example.Configuration;
import org.example.optimization.binary.Binary;
import org.example.optimization.gold.Gold;
import org.example.optimization.newton.Newton;

import java.util.ArrayList;
import java.util.List;

public class MethodChooseMenu extends Menu {
    public static MethodChooseMenu getMenu(Configuration configuration) {
        var sections = new ArrayList<Section>();
        sections.add(new Section(
                () -> "Gold",
                1,
                () -> {
                    var gold = new Gold(MainMenu.builder.build());
                    var extremes = gold.findExtremes();
                    System.out.println(extremes);
                }
        ));
        sections.add(new Section(
                () -> "Newton",
                2,
                () -> {
                    var newton = new Newton(MainMenu.builder.build());
                    var extremes = newton.findExtremes();
                    System.out.println(extremes);
                }
        ));
        sections.add(new Section(
                () -> "Binary",
                3,
                () -> {
                    var binary = new Binary(MainMenu.builder.build());
                    var extremes = binary.findExtremes();
                    System.out.println(extremes);
                }
        ));
        return new MethodChooseMenu(sections);
    }
    public MethodChooseMenu(List<Section> sections) {
        super(sections);
    }
}
