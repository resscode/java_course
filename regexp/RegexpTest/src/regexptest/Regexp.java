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
    private String pattern = "(?<phone>^([+\\s]+)([\\d\\s()]+)[\\s])(?<emails>.*)";
    private String patternEmail = "([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})";
    private String message;
    private boolean answer = false;

    public Regexp(String input) {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(line);
        while (m.find()) {
            String emails = m.group("emails");
            Matcher e = Pattern.compile(patternEmail).matcher(emails);
            while(e.find()){
                System.out.println(e.group());
            }
            System.out.println(m.group("phone"));
            System.out.print(m.group());
            allMatches.add(m.group());
        }
    }

    public String toString() {
        return message;
    }
}
