package com.amarasiricoreservice.service;

import com.amarasiricoreservice.util.MediaFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DocumentsUploadService {
    public String uploadLeaseDocuments(MultipartFile image, String uploadFolder, String outputFolder) throws IOException {
        File f1 = convertFile( image );
        FileOutputStream fileOut = null;
        List<String> outPutUrl = new ArrayList<>();

        try
        {
            long localTime = new java.util.Date().getTime();
            // Get the file and save it somewhere
            byte[] bytes = image.getBytes();
            MediaFile mf = new MediaFile();
            mf.setFile( bytes );
            mf.setFilePath( uploadFolder );

            mf.setName( localTime + "_" + image.getOriginalFilename() );
            mf.setType( image.getContentType() );
            String filePath = mf.getFilePath() + File.separator + mf.getName();
            fileOut = new FileOutputStream( filePath );
            fileOut.write( mf.getFile() );
            fileOut.close();

            String url = outputFolder + File.separator + localTime + "_" + image.getOriginalFilename();
            outPutUrl.add( url );
        }
        catch( IOException e )
        {
            System.out.println(e);
        }
        finally
        {
            if( fileOut != null )
            {
                fileOut.close();
            }
        }
        return outPutUrl.get( 0 );
    }

    public static File convertFile( MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
