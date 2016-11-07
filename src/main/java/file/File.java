/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import java.util.Enumeration;

/**
 *
 * @author adovgobrod
 */
public interface File {
    public void openFile(String filePath);
    public void closeFile(String filePath);
    public Enumeration getEntries();
}
