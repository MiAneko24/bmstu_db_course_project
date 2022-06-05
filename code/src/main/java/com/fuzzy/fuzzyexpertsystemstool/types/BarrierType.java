package com.fuzzy.fuzzyexpertsystemstool.types;

import java.util.ArrayList;
import java.util.List;

public enum BarrierType {
    Very("Very"),
    MoreOrLess("More or less"),
    Plus("Plus"),
    Not("Not"),
    NotVery("Not very"),
    Nothing(null);

    private final String text;

    BarrierType(final String t) {
        text = t;
    }

    @Override
    public String toString() {
        return text;
    }

    public static BarrierType getBarrierType(String s) {
        BarrierType bt;
        if (s == null)
            bt = BarrierType.Nothing;
        else
            switch (s) {
                case "Very":
                    bt = BarrierType.Very;
                    break;
                case "More or less":
                    bt = BarrierType.MoreOrLess;
                    break;
                case "Plus":
                    bt = BarrierType.Plus;
                    break;
                case "Not":
                    bt = BarrierType.Not;
                    break;
                case "Not very":
                    bt = BarrierType.NotVery;
                    break;
                default:
                    bt = BarrierType.Nothing;
                    break;
        }
        return bt;
    }

    public static List<String> getBarriers() {
        List<String> res = new ArrayList<>();
        res.add(Very.toString());
        res.add(NotVery.toString());
        res.add(Nothing.toString());
        res.add(Not.toString());
        res.add(Plus.toString());
        res.add(MoreOrLess.toString());
        return res;
    }
}
