/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archive;

/**
 *
 * @author adovgobrod
 */
public class ZipUnzipper implements Unzipper {
    private ZipArchiveStream zipArchiveStream;
    public ZipUnzipper(ZipArchiveStream zipArchiveStream){
        this.zipArchiveStream = zipArchiveStream;
    }
    public boolean unzip(String destinationFolder){
        
    } 
    
}
