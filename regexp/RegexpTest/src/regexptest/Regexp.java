/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regexptest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author adovgobrod
 */
public class Regexp {

    private String line = "   +1 (4542) 114214 111@111.org		d@ghhg.com,,,,, abc@kjkj.org";
    private String pattern = "^[+\\s]+([0-9]+)([(\\s]+)([0-9\\s]+)([)])([0-9\\s]+)";
    private String message;
    private boolean answer = false;

    public Regexp(String input) {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(line);
        while (m.find()) {
            System.out.print(m.group());
            allMatches.add(m.group());
        }
    }

    public String toString() {
        return message;
    }
}
