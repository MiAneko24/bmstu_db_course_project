package com.fuzzy.fuzzyexpertsystemstool.repositories;

import com.fuzzy.fuzzyexpertsystemstool.dao.Dao;
import com.fuzzy.fuzzyexpertsystemstool.dao.MembershipFunctionDao;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBMembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.model.MembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.types.BarrierType;

import java.util.ArrayList;
import java.util.List;

public class MembershipFunctionRepository {
    private final Dao<DBMembershipFunction> membershipFunctionDao = new MembershipFunctionDao();
    private List<DBMembershipFunction> membershipFunctions = null;
    private VariableRepository variableRepository = new VariableRepository();

    public MembershipFunction getMembershipFunction(int mId) {
        DBMembershipFunction membershipFunction = membershipFunctionDao.get(mId);
        return (membershipFunction != null) ?
                new MembershipFunction(
                        membershipFunction.getId(),
                        membershipFunction.getTerm(),
                        membershipFunction.getmType(),
                        (membershipFunction.getVariableId() != null)
                            ? variableRepository.getVariable(membershipFunction.getVariableId())
                            : null,
                        membershipFunction.getParameter1(),
                        membershipFunction.getParameter2(),
                        membershipFunction.getParameter3(),
                        membershipFunction.getParameter4(),
                        (membershipFunction.getPid() != null)
                                ? getMembershipFunction(membershipFunction.getPid())
                                : null,
                        membershipFunction.getBarrier(),
                        membershipFunction.isActive()
                ) : null;
//        return function.getTerm();
    }

    public String getMembershipFunctionText(int mId) {
        return membershipFunctionDao.get(mId).getTerm();
//        return function.getTerm();
    }

    public List<String> getMembershipFunctionsList(int vId) {
        List<String> result = new ArrayList<>();
        membershipFunctions = membershipFunctionDao.getAll(vId);
        if (membershipFunctions == null)
            return null;
        for (DBMembershipFunction s : membershipFunctions) {
            result.add(s.getTerm());
        }
        return result;
    }

    private double trapezoidal(double x, Double a, Double b, Double c, Double d) {
        double res;
        if (x <= a)
            res = 0.0;
        else if (x <= b)
            res = (x - a) / (b - a);
        else if (x <= c)
            res = 1.0;
        else if (x <= d)
            res = (d - x) / (d - c);
        else
            res = 0.0;
        return res;
    }

    private double triangle(double x, Double a, Double b, Double c) {
        double res;
        if (x <= a)
            res = 0.0;
        else if (x <= b) {
            res = (x - a) / (b - a);
        } else if (x <= c) {
            res = (c - x) / (c - b);
        } else
            res = 0.0;
        return res;
    }

    private double shoulder(double x, Double a, Double b, Double g) {
        double res;
        if (g < a)
            return 1 - shoulder(x, g, b, a);
        if (x <= a)
            res = 0.0;
        else if (x <= b) {
            res = 2 * Math.pow(((x - a) / (g - a)), 2);
        } else if (x <= g) {
            res = 1 - 2 * Math.pow(((g - x) / (g - a)), 2);
        }
        else
            res = 1;
        return res;
    }

    private double gauss(double x, double g, double b) {
        if (x <= g)
            return shoulder(x, g - b, g - b/2, g);
        return 1 - shoulder(x, g, g + b/2, g + b);
    }

    private double linguistic(double x, DBMembershipFunction parent, BarrierType barrierType) {
        double res = getMfValue(x, parent);
        switch (barrierType) {
            case Not:
                res = 1 - res;
                break;
            case Plus:
                res = Math.pow(res, 1.25);
                break;
            case Very:
                res = Math.pow(res, 2);
                break;
            case NotVery:
                res = 1 - Math.pow(res, 2);
                break;
            case MoreOrLess:
                res = Math.pow(res, 0.5);
                break;
            default:
                break;
        }
        return res;
    }


    private double getMfValue(double x, DBMembershipFunction function) {
        double res = 0.0;
        switch (function.getmType()) {
            case Triangle:
                res = triangle(x, function.getParameter1(), function.getParameter2(), function.getParameter3());
                break;
            case Trapezoidal:
                res = trapezoidal(x, function.getParameter1(), function.getParameter2(), function.getParameter3(), function.getParameter4());
                break;
            case Shoulder:
                res = shoulder(x, function.getParameter1(), function.getParameter2(), function.getParameter3());
                break;
            case Gauss:
                res = gauss(x, function.getParameter1(), function.getParameter2());
                break;
            case Linguistic:
                res = linguistic(x, membershipFunctionDao.get(function.getPid()), function.getBarrier());
                break;
            default:
                break;
        }
        return res;
    }

    public DBMembershipFunction getMembershipFunctionByIndex(int i) {
        if (membershipFunctions == null || i < 0 || i > membershipFunctions.size())
            return null;
        return membershipFunctions.get(i);
    }

    public List<List<Double>> getMembershipFunctionGraph(DBMembershipFunction function, double minValue, double maxValue) {
        List<List<Double>> resData = new ArrayList<>();
        resData.add(new ArrayList<Double>());
        resData.add(new ArrayList<Double>());
        for (double x = minValue; x < maxValue; x += (maxValue - minValue) / 500) {
            resData.get(0).add(x);
            resData.get(1).add(getMfValue(x, function));
        }
        return resData;
    }
}
