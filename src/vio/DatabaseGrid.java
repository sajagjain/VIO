package vio;

import java.io.File;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class DatabaseGrid{
    final DB db;
    public DatabaseGrid(String dbServer) {
        
        final MongoClient client = new MongoClient(new MongoClientURI(dbServer));
        final DB db = client.getDB("vio");
        this.db=db;
        final DBCollection collection=db.getCollection("video");
    }
    public void insertVideo(String fileName){
        try{
                
                String videoFileName="C://Users//sajag jain//Documents//NetBeansProjects//VIO//"+fileName+".wmv";
        	File videoFile = new File(videoFileName);

		// create a "photo" namespace
		GridFS gfsVideo = new GridFS(db, fileName);

		// get image file from local drive
		GridFSInputFile gfsFile = gfsVideo.createFile(videoFile);

		// set a new filename for identify purpose
		gfsFile.setFilename(fileName);

		// save the image file into mongoDB
		gfsFile.save();

		// print the result
		DBCursor cursor = gfsVideo.getFileList();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
        }catch(Exception ex){
        ex.printStackTrace();
        }
    }
    public void findVideo(String fileName){
        try{
        
        GridFS gfsVideo = new GridFS(db, fileName);  
        
        GridFSDBFile videoForOutput = gfsVideo.findOne(fileName);
	// save it into a new image file
	OutputStream out=new ByteArrayOutputStream();
        videoForOutput.writeTo("C://Grid//"+fileName+".wmv");
        videoForOutput.writeTo(out);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}