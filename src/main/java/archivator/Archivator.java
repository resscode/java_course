/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivator;

import java.util.Enumeration;

/**
 *
 * @author adovgobrod
 */
public interface Archivator {
    public void AddEntry();
    public void setInputStream();
    public void setOutputStream();
    public void setArchiveStream();
}
