package com.adashkevich.transport_by_parser.model.gotrans;

import java.util.ArrayList;
import java.util.List;

public class GoTransRout {
    public RoutType type;
    public String name;
    public String code;
    public List<GoTransDirection> directions = new ArrayList<>();
}
