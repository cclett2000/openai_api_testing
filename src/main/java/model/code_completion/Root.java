// response model root

package model.code_completion;

import java.util.ArrayList;

public class Root{
    public String id;
    public String object;
    public int created;
    public String model;
    public ArrayList<Choice> choices;
    public Usage usage;
}

