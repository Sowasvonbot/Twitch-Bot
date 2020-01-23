package modules;

import java.util.List;

public class MethodData {

    private String name;
    private List<?> values;
    private returnValues returnValue;


    public String getName() {
        return name;
    }

    public List<?> getValues() {
        return values;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setValues(List<?> values) {
        this.values = values;
    }

    public returnValues getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(returnValues returnValue) {
        this.returnValue = returnValue;
    }

    public MethodData(String name, List<?> values, returnValues returnValue) {
        this.name = name;
        this.values = values;
        this.returnValue = returnValue;
    }

    public enum returnValues{
        INT, STRING, FUTURE, LONG, BOOLEAN
    }
}
