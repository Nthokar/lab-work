package org.example.app;

import lombok.Getter;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Section implements Runnable {
    @Getter
    Supplier<String> title;
    @Getter
    Supplier<String> description;
    @Getter
    Supplier getCur;
    @Getter
    Integer index;
    @Getter
    Runnable runnable;
    @Getter
    Function function;

//    @Getter
//    Function getCurrentValue;
    @Getter
    boolean closable;
    public Section(Supplier<String> title, Supplier<String> description, Supplier<String> getCur, Integer index, Runnable runnable){
        this.title = title;
        this.index = index;
        this.description = description;
        this.getCur = getCur;
        this.runnable = runnable;
    }
    public Section(Supplier<String> title, Supplier<String> description, Supplier<String> getCur, Integer index, Function function){
        this.title = title;
        this.index = index;
        this.description = description;
        this.getCur = getCur;
        this.function = function;
    }

    public Section(Supplier<String> title, Integer index, Runnable runnable){
        this.title = title;
        this.index = index;
        this.runnable = runnable;
    }
    public Section(Supplier<String> title, Integer index, Function function){
        this.title = title;
        this.index = index;
        this.function = function;
    }

//    public Section(String title, Integer index, Runnable runnable, Function getCurrentValue){
//        this.title = title;
//        this.index = index;
//        this.runnable = runnable;
//        this.getCurrentValue = getCurrentValue;
//    }
//    public Section(String title, Integer index, Function function, Function getCurrentValue){
//        this.title = title;
//        this.index = index;
//        this.function = function;
//        this.getCurrentValue = getCurrentValue;
//    }

    public void run(){
        if (Objects.nonNull(runnable)) {
            runnable.run();
        }
        if (Objects.nonNull(function)){
            function.apply(null);
        }
    }

    public String toPrint() {
        var title1 = String.format("[%s] - %s", index, title.get());
        if (Objects.nonNull(getCur)) {
            var width = 75;
//            title1 = String.format("%-" + (width - title1.length()) + "s%s", title1, getCur.get());
            var cur = getCur.get();
            title1 = String.format("%-" + width + "s[%s]", title1, cur);

        }
        if (Objects.nonNull(description)) return String.format("%s\n\t%s", title1, description.get());
        else return String.format("%s", title1);
    }
}